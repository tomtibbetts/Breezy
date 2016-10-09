package com.windhaven_consulting.breezy.component.motors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.Component;
import com.windhaven_consulting.breezy.component.annotation.ControlledComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledMethod;
import com.windhaven_consulting.breezy.component.annotation.ControlledParameter;
import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;

@ControlledComponent(value="Stall Motor With Indicator",
	numberOfOutputs=2,
	pinNames={"Motor Out", "Indicator Out"})
public class StallMotorWithIndicator extends Component {
	static final Logger LOG = LoggerFactory.getLogger(StallMotorWithIndicator.class);

	private static final long serialVersionUID = 1L;

	@ControlledMethod("Normal")
	public void normal() {
		getOutputPin(0).setHigh();
		getOutputPin(1).setHigh();
	}
	
	@ControlledMethod("Reverse")
	public void reverse() {
		getOutputPin(0).setLow();
		getOutputPin(1).setLow();
	}
	
	@ControlledMethod("Toggle")
	public void toggle() {
		getOutputPin(0).toggle();
		getOutputPin(1).toggle();
	}

	@ControlledMethod("Normal Blinking")
	public void normal(@ControlledParameter(name = "On Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long onTime,
			@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {
		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);
		
		if(PinState.LOW == getOutputPin(0).getState()) {
			getOutputPin(0).setHigh();
			getOutputPin(1).blink(onTime, duration, PinState.LOW, blockToCompletion);
		}
	}
	
	@ControlledMethod("Reverse Blinking")
	public void reverse(@ControlledParameter(name = "On Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long onTime,
			@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {
		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);
		
		if(PinState.HIGH == getOutputPin(0).getState()) {
			getOutputPin(0).setLow();
			getOutputPin(1).blink(onTime, duration, PinState.HIGH, blockToCompletion);
		}
	}
	
	@ControlledMethod("Toggle Blinking")
	public void toggle(@ControlledParameter(name = "On Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long onTime,
			@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {
		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);

		if(PinState.LOW == getOutputPin(0).getState()) {
			getOutputPin(0).setHigh();
			getOutputPin(1).blink(onTime, duration, PinState.LOW, blockToCompletion);
		} else {
			getOutputPin(0).setLow();
			getOutputPin(1).blink(onTime, duration, PinState.HIGH, blockToCompletion);
		}
	}

	@Override
	public void test() {
		normal(500, 4000, true);
		reverse(500, 4000, false);
	}
}
