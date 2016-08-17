package com.windhaven_consulting.breezy.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.annotation.ControlledComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledMethod;
import com.windhaven_consulting.breezy.component.annotation.ControlledParameter;
import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;
import com.windhaven_consulting.breezy.component.motors.StallMotor;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;

@ControlledComponent("Digital Output")
public class DigitalOutput extends Component {
	private static final Logger LOG = LoggerFactory.getLogger(StallMotor.class);

	private static final long serialVersionUID = 1L;

	@ControlledMethod("Turn On")
	public void turnOn() {
		getOutputPin().setHigh();
	}
	
	@ControlledMethod("Turn Off")
	public void turnOff() {
		getOutputPin().setLow();
	}
	
	@ControlledMethod("Toggle")
	public void toggle() {
		getOutputPin().toggle();
	}
	
	@ControlledMethod("Blink Forever")
	public void blink(@ControlledParameter(name = "On Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long onTime,
			@ControlledParameter(name = "Start State", parameterFieldType = ParameterFieldType.LOGIC_STATE) PinState startState) {
		startState = (startState == null ? PinState.HIGH : startState);

		getOutputPin().blink(onTime, startState);
	}
	
	@ControlledMethod("Blink Timed")
	public void blink(@ControlledParameter(name = "On Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long onTime,
			@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration,
			@ControlledParameter(name = "Start State", parameterFieldType = ParameterFieldType.PIN_STATE) PinState startState,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {
		startState = (startState == null ? PinState.HIGH : startState);
		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);
		
		getOutputPin().blink(onTime, duration, startState, blockToCompletion);
	}
	
	@ControlledMethod("Pulse")
	public void pulse(@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration,
			@ControlledParameter(name = "Start State", parameterFieldType = ParameterFieldType.PIN_STATE) PinState startState,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {
		startState = (startState == null ? PinState.HIGH : startState);
		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);

		getOutputPin().pulse(duration, startState, blockToCompletion);
	}

	@Override
	public void test() {
		turnOn();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
		}
		turnOff();
	}
	
	
}
