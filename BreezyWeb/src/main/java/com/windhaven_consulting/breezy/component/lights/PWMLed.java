package com.windhaven_consulting.breezy.component.lights;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.GenericComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledMethod;
import com.windhaven_consulting.breezy.component.annotation.ControlledParameter;
import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;
import com.windhaven_consulting.breezy.embeddedcontroller.OutputType;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMPinState;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;

@ControlledComponent(value="PWM LED",
numberOfOutputs=1,
pinNames={"PWM LED"},
outputType=OutputType.PWM_OUTPUT)
public class PWMLed extends GenericComponent<PWMOutputPin> {

	private static final Logger LOG = LoggerFactory.getLogger(PWMLed.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int MAX_NUMBER_OF_STEPS = 4096;

	@ControlledMethod("Turn On")
	public void turnOn() {
		PWMOutputPin pwmOutputPin = getOutputPin();
		pwmOutputPin.setAlwaysOn();
	}

	@ControlledMethod("Turn Off")
	public void turnOff() {
		PWMOutputPin pwmOutputPin = getOutputPin();
		pwmOutputPin.setAlwaysOff();
	}
	
	@ControlledMethod("Blink Forever")
	public void blink(@ControlledParameter(name = "On Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long onTime,
			@ControlledParameter(name = "Start State", parameterFieldType = ParameterFieldType.LOGIC_STATE) PinState startState) {
		PWMPinState pwmPinState = (startState == null ? PWMPinState.HIGH : PWMPinState.LOW);

		getOutputPin().blink(onTime, pwmPinState);
	}
	
	@ControlledMethod("Blink Timed")
	public void blink(@ControlledParameter(name = "On Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long onTime,
			@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration,
			@ControlledParameter(name = "Start State", parameterFieldType = ParameterFieldType.PIN_STATE) PinState startState,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {
		PWMPinState pwmPinState = (startState == null ? PWMPinState.HIGH : PWMPinState.LOW);
		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);
		
		getOutputPin().blink(onTime, duration, pwmPinState, blockToCompletion);
	}
	
	@ControlledMethod("Pulse")
	public void pulse(@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration,
			@ControlledParameter(name = "Start State", parameterFieldType = ParameterFieldType.PIN_STATE) PinState startState,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {
		PWMPinState pwmPinState = (startState == null ? PWMPinState.HIGH : PWMPinState.LOW);
		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);

		getOutputPin().pulse(duration, pwmPinState, blockToCompletion);
	}
	
	// should this be by percent?
	@ControlledMethod("Set By Duration")
	public void setByDuration(@ControlledParameter(name = "Duration", parameterFieldType = ParameterFieldType.NUMBER, required = true) int duration) {
		PWMOutputPin pwmOutputPin = getOutputPin();
		pwmOutputPin.setPwm(duration);
	}

	// should this be by percent or steps?
	@ControlledMethod("Set By Position")
	public void setByPosition(@ControlledParameter(name = "On Position", parameterFieldType = ParameterFieldType.NUMBER, required = true) int onPosition,
			@ControlledParameter(name = "Off Position", parameterFieldType = ParameterFieldType.NUMBER, required = true) int offPosition) {
		PWMOutputPin pwmOutputPin = getOutputPin();
		pwmOutputPin.setPwm(onPosition, offPosition);
	}

	
	@ControlledMethod("Synchronous Pulse")
	public void pulse (@ControlledParameter(name = "Number of Steps", parameterFieldType = ParameterFieldType.NUMBER, required = true) int numberOfSteps,
			@ControlledParameter(name = "Step Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) int stepDuration,
			@ControlledParameter(name = "Transition Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) int transitionDuration) {
		PWMOutputPin pwmOutputPin = getOutputPin();
		int tick = MAX_NUMBER_OF_STEPS / numberOfSteps;

		try {
			Thread.sleep(transitionDuration);
			for(int i = 1; i <= numberOfSteps; i++) {
				pwmOutputPin.setPwm(1, tick * i);
				Thread.sleep(stepDuration);
			}

			Thread.sleep(transitionDuration);
			
			for(int i = numberOfSteps; i > 0; i--) {
				pwmOutputPin.setPwm(1, tick * i);
				Thread.sleep(stepDuration);
			}
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
		}
	}

	@Override
	public void test() {
		try {
			turnOn();
			Thread.sleep(1000);
			turnOff();
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
		}
	}

}
