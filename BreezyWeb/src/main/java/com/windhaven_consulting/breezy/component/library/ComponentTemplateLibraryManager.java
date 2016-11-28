package com.windhaven_consulting.breezy.component.library;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.component.Component;
import com.windhaven_consulting.breezy.component.annotation.ComponentType;
import com.windhaven_consulting.breezy.component.annotation.ControlledComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledParameter;
import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;
import com.windhaven_consulting.breezy.exceptions.BreezyApplicationException;

@ApplicationScoped
public class ComponentTemplateLibraryManager implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private BeanManager beanManager;
	
	private Map<String, ComponentTemplate> componentDescriptorByTypeMap = new TreeMap<String, ComponentTemplate>();
	
	private Map<String, ComponentTemplate> componentDescriptorByClassMap = new HashMap<String, ComponentTemplate>();
	
	@PostConstruct
	public void postConstruct() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	Set<Bean<?>> beans = beanManager.getBeans(Object.class);
    	
		for(Bean<?> bean : beans) {
			for(Annotation classAnnotation : bean.getBeanClass().getAnnotations()) {
				if(ControlledComponent.class.getName().equals(classAnnotation.annotationType().getName())) {
					ComponentTemplate componentDescriptor = getComponentTemplate(bean.getBeanClass(), classAnnotation);
					componentDescriptorByTypeMap.put(componentDescriptor.getComponentName(), componentDescriptor);
					componentDescriptorByClassMap.put(componentDescriptor.getClazzName(), componentDescriptor);

					break;
				}
			}
		}
	}
	
	public Collection<ComponentTemplate> getComponentTemplates() {
		return componentDescriptorByTypeMap.values();
	}
	
	public Component getNewComponentByType(String type) {
		ComponentTemplate componentDescriptor = componentDescriptorByTypeMap.get(type);
		
		if(componentDescriptor == null) {
			throw new BreezyApplicationException("Component not found for type: " + type);
		}
		
		Class<?> clazz;
		Component component = null;
		
		try {
			clazz = Class.forName(componentDescriptor.getClazzName());
			component = (Component) clazz.newInstance();
		} catch (ClassNotFoundException e) {
			throw new BreezyApplicationException("Cannot create component of type: " + type + ". Component type not found.");
		} catch (InstantiationException e) {
			throw new BreezyApplicationException("Cannot create component of type: " + type + ". Instantiation exception.");
		} catch (IllegalAccessException e) {
			throw new BreezyApplicationException("Cannot create component of type: " + type + ". Illegal access exception.");
		}
		
		return component;
	}

	public ComponentTemplate getComponentTemplateFor(Component component) {
		return componentDescriptorByClassMap.get(component.getClass().getName());
	}
	
	public ComponentTemplate getComponentTemplateFor(String componentName) {
		return componentDescriptorByTypeMap.get(componentName);
	}
	
    // harvest the class level annotations up through the superclass layers
    private ComponentTemplate getComponentTemplate(Class<?> beanClass, Annotation classAnnotation) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	ControlledComponent controlledComponent = (ControlledComponent) classAnnotation;
    	int numberOfOutputs = controlledComponent.numberOfOutputs();
    	String name = controlledComponent.value();
    	String[] pinNames = controlledComponent.pinNames();
    	ComponentType componentType = controlledComponent.componentType();
    	
    	ComponentTemplate componentTemplate = new ComponentTemplate(beanClass.getName(), name, numberOfOutputs, pinNames, componentType);
    	
    	while(beanClass != null) {
        	for(Method classMethod : beanClass.getDeclaredMethods()) {
        		for(Annotation classMethodAnnotation : classMethod.getDeclaredAnnotations()) {
            		MethodTemplate methodTemplate = getMethodTemplate(classMethod, classMethodAnnotation);
               		componentTemplate.add(methodTemplate);
        		}
        	}
        	
        	beanClass = beanClass.getSuperclass();
    	}
    	
		return componentTemplate;
	}

    // harvest method level annotations
	private MethodTemplate getMethodTemplate(Method classMethod, Annotation methodAnnotation) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String methodName = "";
		
		for(Method methodAnnotationMethod : methodAnnotation.annotationType().getDeclaredMethods()) {
    		methodName = (String) methodAnnotationMethod.invoke(methodAnnotation, (Object[]) null);
    		break;
		}

		MethodTemplate methodTemplate = new MethodTemplate(classMethod.getName(), methodName);
		
		for(Parameter parameter : classMethod.getParameters()) {
			for(Annotation parameterAnnotation : parameter.getAnnotations()) {
				ParameterTemplate parameterTemplate = getParameterTemplate(parameter, parameterAnnotation);
				
				methodTemplate.add(parameterTemplate);
			}
		}
		
		return methodTemplate;
	}

	// harvest parameter level annotations
	private ParameterTemplate getParameterTemplate(Parameter parameter, Annotation parameterAnnotation) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> parameterType = (Class<?>) parameter.getParameterizedType();
		ControlledParameter controlledParameter = (ControlledParameter) parameterAnnotation;
		boolean required = controlledParameter.required();
		String argumentLabel = controlledParameter.name();
		ParameterFieldType parameterFieldType = controlledParameter.parameterFieldType();

		ParameterTemplate parameterTemplate = new ParameterTemplate(parameter.getName(), argumentLabel, parameterType, required, parameterFieldType);

		return parameterTemplate;
	}

}
