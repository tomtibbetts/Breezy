package com.windhaven_consulting.breezy.macrocontroller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.GenericComponent;
import com.windhaven_consulting.breezy.component.library.ComponentTemplate;
import com.windhaven_consulting.breezy.component.library.ComponentTemplateLibraryManager;
import com.windhaven_consulting.breezy.component.library.MethodTemplate;
import com.windhaven_consulting.breezy.component.library.ParameterTemplate;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.exceptions.BreezyApplicationException;
import com.windhaven_consulting.breezy.manager.MacroExecutorManager;
import com.windhaven_consulting.breezy.manager.MountedBoardManager;
import com.windhaven_consulting.breezy.manager.impl.MountedBoard;
import com.windhaven_consulting.breezy.persistence.domain.Macro;
import com.windhaven_consulting.breezy.persistence.domain.MacroStep;

public class MacroExecutor implements Runnable {
	static final Logger LOG = LoggerFactory.getLogger(MacroExecutor.class);
	
	private MacroExecutorManager macroExecutorManager;
	
	private MountedBoardManager mountedBoardManager;

	private ComponentTemplateLibraryManager componentTemplateLibraryManager;
	
	private Macro macro;
	
	private Map<String, Integer> tagMap = new HashMap<String, Integer>();
	
	private Integer currentStep = 0;

	private boolean isRunning;
	
	public MacroExecutor(Macro macro, MacroExecutorManager macroExecutorManager, MountedBoardManager mountedBoardManager, ComponentTemplateLibraryManager componentTemplateLibraryManager) {
		this.macro = macro;
		this.macroExecutorManager = macroExecutorManager;
		this.mountedBoardManager = mountedBoardManager;
		this.componentTemplateLibraryManager = componentTemplateLibraryManager;
		
		initialize();
	}


	@Override
	public void run() {
	//	LOG.debug("************************* " + this.getClass().getName() + "::run: starting execution for macro, '" + macro.getName() + "' ******************************");
	
		isRunning = true;
		
		for(currentStep = 0; currentStep < macro.getSteps().size() && isRunning; currentStep++) {
	//		LOG.debug("Executing line: " + (currentStep + 1));
			MacroStep macroStep = macro.getSteps().get(currentStep);
			MountedBoard mountedBoard = mountedBoardManager.getById(macroStep.getMountedBoardId());
			
			if(mountedBoard == null) {
				throw new BreezyApplicationException(getExceptionMessage("no mounted board found for: " + macroStep.getMountedBoardId() + "."));
			}
			
			GenericComponent<BreezyPin>	component = mountedBoard.getComponent(macroStep.getComponentId());
			
			if(component == null) {
				LOG.debug("No component found for " + macroStep.getComponentId());
				throw new BreezyApplicationException(getExceptionMessage("no component found for: " + macroStep.getComponentId() + "."));
			}
	
			ComponentTemplate componentTemplate = componentTemplateLibraryManager.getComponentTemplateFor(component);
			
	//		LOG.debug("Looking for methodTemplate for function: " + macroStep.getFunction());
			MethodTemplate methodTemplate = componentTemplate.getMethodTemplate(macroStep.getFunction());
			
			int parameterCount = methodTemplate.getParameters().size();
	//		LOG.debug("Number of parameters: " + numberOfParameters);
			
			// we add an extra parameter (this) to SystemComponents so that they can execute call backs on us.
			int argumentCount = (component instanceof SystemComponent ? parameterCount + 1: parameterCount);
			
			Class<?>[] parameterTypes = new Class<?>[argumentCount];
			Object[] args = new Object[argumentCount];
			
			if(component instanceof SystemComponent) {
				args[parameterCount] = this;
				parameterTypes[parameterCount] = this.getClass();
			}
			
			int i = 0;
			for(ParameterTemplate parameterTemplate : methodTemplate.getParameters()) {
	//			LOG.debug("Field Value = : " + macroStep.getMethodParameters().get(i).getFieldValue() + ", argument type = " + parameterTemplate.getArgumentType().getName());
				args[i] = ValueConverterUtil.getValueForType(macroStep.getMethodParameters().get(i).getFieldValue(), parameterTemplate.getArgumentType());
				parameterTypes[i] = parameterTemplate.getArgumentType();
				i++;
			}
			
			try {
	//			LOG.debug("Method is: " + methodTemplate.getMethod() );
				
				Method method = component.getClass().getMethod(methodTemplate.getMethod(), parameterTypes);
				method.invoke(component, args);
				
			} catch (NoSuchMethodException | SecurityException e) {
				LOG.debug("No such method for function: " + macroStep.getFunction() );
				throw new BreezyApplicationException(getExceptionMessage("unable to process current instruction."), e);
			} catch (IllegalAccessException e) {
				LOG.debug("Illegal access for function: " + macroStep.getFunction() );
				throw new BreezyApplicationException(getExceptionMessage("unable to process current instruction."), e);
			} catch (IllegalArgumentException e) {
				LOG.debug("Illegal argument for function: " + macroStep.getFunction() );
				throw new BreezyApplicationException(getExceptionMessage("unable to process current instruction."), e);
			} catch (InvocationTargetException e) {
				LOG.debug("InvocationTargetException for function: " + macroStep.getFunction() );
				throw new BreezyApplicationException(getExceptionMessage("unable to process current instruction."), e);
			}
			
		}
	
	//	LOG.debug("************************* " + this.getClass().getName() + "::run: ending execution for macro, '" + macro.getName() + "' ******************************\n");
	
		macroExecutorManager.notifyStopped(macro);
	}
	
	public void stop() {
//		LOG.debug(this.getClass().getName() + "::stopping for macro, '" + macro.getName() + "'");
		
		isRunning = false;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void redirect(String tag) {
		Integer nextStep = tagMap.get(tag);
		
		if(nextStep != null) {
			// at the end of each loop, the counter is incremented for the next iteration.
			// so, we must subtract 1 to compensate
			currentStep = nextStep - 1;
		}
		else {
			throw new BreezyApplicationException(getExceptionMessage(". Tag: " + tag + " not found at line " + currentStep + 1));
		}
	}
	
	private void initialize() {
		// Harvest the tags
		for(Integer i = 0; i < macro.getSteps().size(); i++) {
			MacroStep macroStep = macro.getSteps().get(i);
			
			if(StringUtils.isNotEmpty(macroStep.getTag())) {
				tagMap.put(macroStep.getTag(), i);
			}
		}
	}
	
	private String getExceptionMessage(String exceptionMessage) {
		return "Macro '" + macro.getName() + "', id: " + macro.getId() + " " + exceptionMessage;
	}


}
