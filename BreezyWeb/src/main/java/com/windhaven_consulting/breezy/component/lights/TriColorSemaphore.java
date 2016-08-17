package com.windhaven_consulting.breezy.component.lights;

import com.windhaven_consulting.breezy.component.annotation.ControlledComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledMethod;
import com.windhaven_consulting.breezy.component.annotation.ControlledParameter;
import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;


@ControlledComponent(value="Tri-color Semaphore", numberOfOutputs=3)
public class TriColorSemaphore extends AbstractSemaphore {
	private static final long serialVersionUID = 1L;

	@ControlledMethod("Turn On")
	public void turnOn(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.NUMBER, required = true) int colorIndex) {
		super.turnOn(colorIndex);
	}

	@ControlledMethod("Turn Off")
	public void turnOff(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.NUMBER, required = true) int colorIndex) {
		super.turnOff(colorIndex);
	}
	
	@ControlledMethod("Blink Forever")
	public void blink(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.NUMBER, required = true) int colorIndex,
			@ControlledParameter(name = "On Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long onTime ) {
		super.blink(colorIndex, onTime);
	}
	
	@ControlledMethod("Blink Timed")
	public void blink(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.NUMBER, required = true) int colorIndex,
			@ControlledParameter(name = "On Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long onTime,
			@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {
		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);
		super.blink(colorIndex, onTime, duration, blockToCompletion);
	}
	
	@ControlledMethod("Pulse")
	public void pulse(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.NUMBER, required = true) int colorIndex,
			@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {
		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);
		super.pulse(colorIndex, duration, blockToCompletion);
	}
	
	@ControlledMethod("All On")
	public void allOn() {
		super.allOn();
	}
	
	@ControlledMethod("All Off")
	public void allOff() {
		super.allOff();
	}

	public void test() {
		super.test();
	}
}
