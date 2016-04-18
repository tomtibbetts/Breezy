package com.windhaven_consulting.breezy.component.library;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class ComponentTemplate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String componentName;
	
	private String clazzName;

	private Map<String, MethodTemplate> methodMap = new TreeMap<String, MethodTemplate>();

	private int numberOfOutputs;

	public ComponentTemplate(String clazzName, String componentName, int numberOfOutputs) {
		this.clazzName = clazzName;
		this.componentName = componentName;
		this.numberOfOutputs = numberOfOutputs;
	}
	
	public void add(MethodTemplate methodTemplate) {
		methodMap.put(methodTemplate.getComponentMethodName(), methodTemplate);
	}
	
	public int getNumberOfOutputs() {
		return numberOfOutputs;
	}

	public String getComponentName() {
		return componentName;
	}

	public String getClazzName() {
		return clazzName;
	}

	public MethodTemplate getMethodTemplate(String key) {
		return methodMap.get(key);
	}
	
	public Collection<MethodTemplate> getMethods() {
		return methodMap.values();
	}

}
