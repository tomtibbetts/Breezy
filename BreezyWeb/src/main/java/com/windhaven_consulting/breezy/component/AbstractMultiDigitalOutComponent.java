package com.windhaven_consulting.breezy.component;

import java.util.UUID;

import com.windhaven_consulting.breezy.component.annotation.ControlledMethod;
import com.windhaven_consulting.breezy.component.annotation.ControlledParameter;
import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;

public abstract class AbstractMultiDigitalOutComponent extends Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ControlledMethod("Turn On")
	public void turnOn(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.DIGITAL_OUTPUT_PIN, required = true) UUID digitalOutputPinId) {
		DigitalOutputPin digitalOutputPin = getDigitalOutputPin(digitalOutputPinId);
		digitalOutputPin.setHigh();
	}

	@ControlledMethod("Turn Off")
	public void turnOff(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.DIGITAL_OUTPUT_PIN, required = true) UUID digitalOutputPinId) {
		DigitalOutputPin digitalOutputPin = getDigitalOutputPin(digitalOutputPinId);
		digitalOutputPin.setLow();
	}
	
	@ControlledMethod("Blink Forever")
	public void blink(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.DIGITAL_OUTPUT_PIN, required = true) UUID digitalOutputPinId,
			@ControlledParameter(name = "On Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long onTime ) {
		DigitalOutputPin digitalOutputPin = getDigitalOutputPin(digitalOutputPinId);
		digitalOutputPin.blink(onTime);
	}
	
	@ControlledMethod("Blink Timed")
	public void blink(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.DIGITAL_OUTPUT_PIN, required = true) UUID digitalOutputPinId,
			@ControlledParameter(name = "On Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long onTime,
			@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {
		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);
		DigitalOutputPin digitalOutputPin = getDigitalOutputPin(digitalOutputPinId);
		digitalOutputPin.blink(onTime, duration, PinState.HIGH, blockToCompletion);
	}

	@ControlledMethod("Pulse")
	public void pulse(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.DIGITAL_OUTPUT_PIN, required = true) UUID digitalOutputPinId,
			@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {
		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);
		DigitalOutputPin digitalOutputPin = getDigitalOutputPin(digitalOutputPinId);
		digitalOutputPin.pulse(duration, PinState.HIGH, blockToCompletion);
	}

	@ControlledMethod("All On")
	public void allOn() {
		for(DigitalOutputPin digitalOutputPin : getOutputPins()) {
			digitalOutputPin.setHigh();
		}
	}
	
	@ControlledMethod("All Off")
	public void allOff() {
		for(DigitalOutputPin digitalOutputPin : getOutputPins()) {
			digitalOutputPin.setLow();
		}
	}

}
