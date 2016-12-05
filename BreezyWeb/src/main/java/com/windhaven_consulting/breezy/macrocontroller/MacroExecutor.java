package com.windhaven_consulting.breezy.macrocontroller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.GenericComponent;
import com.windhaven_consulting.breezy.component.library.ComponentTemplate;
import com.windhaven_consulting.breezy.component.library.ComponentTemplateLibraryManager;
import com.windhaven_consulting.breezy.component.library.MethodTemplate;
import com.windhaven_consulting.breezy.component.library.ParameterTemplate;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMPinState;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;
import com.windhaven_consulting.breezy.exceptions.BreezyApplicationException;
import com.windhaven_consulting.breezy.manager.MacroExecutorManager;
import com.windhaven_consulting.breezy.manager.MountedBoardManager;
import com.windhaven_consulting.breezy.manager.impl.MountedBoard;
import com.windhaven_consulting.breezy.persistence.domain.Macro;
import com.windhaven_consulting.breezy.persistence.domain.MacroStep;

public class MacroExecutor implements MacroControllerComponent {
	static final Logger LOG = LoggerFactory.getLogger(MacroExecutor.class);
	
	private MacroExecutorManager macroExecutorManager;
	
	private MountedBoardManager mountedBoardManager;

	private ComponentTemplateLibraryManager componentTemplateLibraryManager;
	
	private Macro macro;
	
	private Thread executorThread;
	
	private Map<String, Integer> tagMap = new HashMap<String, Integer>();
	
	private Integer currentStep = 0;

	private boolean isRunning;
	
	private Map<Class<?>, ValueConverter> valueConverterMap = new HashMap<Class<?>, ValueConverter>();

	public MacroExecutor(Macro macro, MacroExecutorManager macroExecutorManager, MountedBoardManager mountedBoardManager, ComponentTemplateLibraryManager componentTemplateLibraryManager) {
		this.macro = macro;
		this.macroExecutorManager = macroExecutorManager;
		this.mountedBoardManager = mountedBoardManager;
		this.componentTemplateLibraryManager = componentTemplateLibraryManager;
		
		initialize();
	}

	public void start() {
		if(!isRunning) {
//			LOG.debug("Starting Macro '" + macro.getName() + "'.");

			// start executing the macro
			if(executorThread == null) {
				executorThread = new Thread(new Executor(this));
			} else {
//				LOG.debug(this.getClass().getName() + "::start() attempting to start and already running macro.");
				executorThread.interrupt();
				executorThread = new Thread(new Executor(this));
			}
			
			isRunning = true;
			executorThread.start();
		}
	}

	public void stop() {
//		LOG.debug(this.getClass().getName() + "::stopping for macro, '" + macro.getName() + "'");
		
		isRunning = false;
	}

	/**
	 * This section mirrors the MacroControllerComponent implementation
	 */
	public void waitOn(String mountedBoardId, String inputPinId, PinState inputState) {
		MountedBoard mountedBoard = mountedBoardManager.getById(mountedBoardId);
		
		if(mountedBoard == null) {
			throw new BreezyApplicationException("No mounted board was found for " + mountedBoardId);
		}
		
		DigitalInputPin digitalInputPin = mountedBoard.getInputPinById(UUID.fromString(inputPinId));
		
		if(digitalInputPin == null) {
			throw new BreezyApplicationException("No digital input pin, " + inputPinId + ", was found for " + mountedBoardId);
		}
		
		while(!digitalInputPin.isState(inputState) && isRunning) {
			try {
				executorThread.join(10);
			} catch (InterruptedException e) {
				throw new BreezyApplicationException(getExceptionMessage("was unexpectedly interrupted."), e);
			}
		}
	}

	public void jumpTo(String tag) {
		Integer nextStep = tagMap.get(tag);
		
		if(nextStep != null) {
			currentStep = nextStep - 1;
		}
		else {
			throw new BreezyApplicationException(getExceptionMessage(". Tag: " + tag + " not found at line " + currentStep + 1));
		}
	}

	public void jumpOn(String mountedBoardId, String inputPinId, PinState inputState, String tag) {
		MountedBoard mountedBoard = mountedBoardManager.getById(mountedBoardId);
		
		if(mountedBoard == null) {
			throw new BreezyApplicationException("No mounted board was found for " + mountedBoardId);
		}
		
		DigitalInputPin digitalInputPin = mountedBoard.getInputPinById(UUID.fromString(inputPinId));
		
		if(digitalInputPin == null) {
			throw new BreezyApplicationException("No digital input pin, " + inputPinId + ", was found for " + mountedBoardId);
		}
		
		if(digitalInputPin.isState(inputState)) {
			jumpTo(tag);
		}
	}

	@Override
	public void delay(long milleseconds) {
		try {
			long delay = milleseconds;
			
			while(delay > 0 && isRunning) {
				if(delay > 100) {
					executorThread.join(100);
					delay = delay - 100;
				}
				else {
					executorThread.join(delay);
					delay = 0;
				}
			}
		} catch (InterruptedException e) {
			LOG.debug(e.getLocalizedMessage());
			throw new BreezyApplicationException(getExceptionMessage("was unexpectedly interrupted."), e);
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
		
		valueConverterMap.put(String.class, new StringConverterImpl());
		valueConverterMap.put(long.class, new LongConverterImpl());
		valueConverterMap.put(int.class, new IntConverterImpl());
		valueConverterMap.put(PinState.class, new PinStateConverterImpl());
		valueConverterMap.put(PWMPinState.class, new PWMPinStateConverterImpl());
		valueConverterMap.put(Boolean.class, new BooleanConverterImpl());
		valueConverterMap.put(UUID.class, new UUIDConverterImpl());
	}
	
	private String getExceptionMessage(String exceptionMessage) {
		return "Macro '" + macro.getName() + "', id: " + macro.getId() + " " + exceptionMessage;
	}

	private class Executor implements Runnable {

		private MacroExecutor macroExecutor;

		public Executor(MacroExecutor macroExecutor) {
			this.macroExecutor = macroExecutor;
		}
		
		@Override
		public void run() {
//			LOG.debug("************************* " + this.getClass().getName() + "::run: starting execution for macro, '" + macro.getName() + "' ******************************");

			for(currentStep = 0; currentStep < macro.getSteps().size() && isRunning; currentStep++) {
//				LOG.debug("Executing line: " + (currentStep + 1));
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
				
//				LOG.debug("Looking for methodTemplate for function: " + macroStep.getFunction());
				MethodTemplate methodTemplate = componentTemplate.getMethodTemplate(macroStep.getFunction());
				
				int numberOfParameters = methodTemplate.getParameters().size();
//				LOG.debug("Number of parameters: " + numberOfParameters);
				
				Class<?>[] parameterTypes = new Class<?>[numberOfParameters];
				Object[] args = new Object[numberOfParameters];
				
				int i = 0;
				for(ParameterTemplate parameterTemplate : methodTemplate.getParameters()) {
//					LOG.debug("Field Value = : " + macroStep.getMethodParameters().get(i).getFieldValue() + ", argument type = " + parameterTemplate.getArgumentType().getName());
					args[i] = getArgumentForParameter(macroStep.getMethodParameters().get(i).getFieldValue(), parameterTemplate.getArgumentType());
					parameterTypes[i] = parameterTemplate.getArgumentType();
					i++;
				}
				
				try {
					Object invokingObject;
					
					if(component instanceof MacroControllerComponent) {
						invokingObject = macroExecutor;
					}
					else {
						invokingObject = component;
					}
					
//					LOG.debug("Method is: " + methodTemplate.getMethod() );
					
					Method method = invokingObject.getClass().getMethod(methodTemplate.getMethod(), parameterTypes);
					method.invoke(invokingObject, args);
					
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

//			LOG.debug("************************* " + this.getClass().getName() + "::run: ending execution for macro, '" + macro.getName() + "' ******************************\n");

			macroExecutorManager.notifyStopped(macro);
		}

		private Object getArgumentForParameter(String value, Class<?> parameterType) {
			ValueConverter valueConverter = valueConverterMap.get(parameterType);
			
			if(valueConverter == null) {
				throw new BreezyApplicationException(getExceptionMessage("No converter found for type: " + parameterType.getName()));
			}
			
			return valueConverter.convert(value);
		}
	}

}
