package com.windhaven_consulting.breezy.component.lights;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.AbstractMultiDigitalOutComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledMethod;
import com.windhaven_consulting.breezy.component.annotation.ControlledParameter;
import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;

public abstract class AbstractSemaphore extends AbstractMultiDigitalOutComponent {
	static final Logger LOG = LoggerFactory.getLogger(AbstractSemaphore.class);

	private static final long serialVersionUID = 1L;

	@ControlledMethod("Turn On")
	public void turnOn(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.DIGITAL_OUTPUT_PIN, required = true) UUID digitalOutputPinId) {
		allOff();
		
		DigitalOutputPin digitalOutputPin = getOutputPin(digitalOutputPinId);
		digitalOutputPin.setHigh();
	}

	@ControlledMethod("Turn Off")
	public void turnOff(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.DIGITAL_OUTPUT_PIN, required = true) UUID digitalOutputPinId) {
		DigitalOutputPin digitalOutputPin = getOutputPin(digitalOutputPinId);
		digitalOutputPin.setLow();
	}
	
	@ControlledMethod("Blink Forever")
	public void blink(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.DIGITAL_OUTPUT_PIN, required = true) UUID digitalOutputPinId,
			@ControlledParameter(name = "On Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long onTime ) {
		allOff();
		
		DigitalOutputPin digitalOutputPin = getOutputPin(digitalOutputPinId);
		digitalOutputPin.blink(onTime);
	}
	
	@ControlledMethod("Blink Timed")
	public void blink(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.DIGITAL_OUTPUT_PIN, required = true) UUID digitalOutputPinId,
			@ControlledParameter(name = "On Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long onTime,
			@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {
		allOff();
		
		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);
		DigitalOutputPin digitalOutputPin = getOutputPin(digitalOutputPinId);
		digitalOutputPin.blink(onTime, duration, PinState.HIGH, blockToCompletion);
	}

	@ControlledMethod("Pulse")
	public void pulse(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.DIGITAL_OUTPUT_PIN, required = true) UUID digitalOutputPinId,
			@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {
		allOff();

		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);
		DigitalOutputPin digitalOutputPin = getOutputPin(digitalOutputPinId);
		digitalOutputPin.pulse(duration, PinState.HIGH, blockToCompletion);
	}

	@Override
	public void test() {
		allOff();
		
		try {
			for(DigitalOutputPin digitalOutputPin : getOutputPins()) {
				digitalOutputPin.setHigh();
				
				Thread.sleep(200);

				digitalOutputPin.setLow();
			}
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
		}
	}

}
