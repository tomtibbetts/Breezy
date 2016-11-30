package com.windhaven_consulting.breezy.component;

import java.util.UUID;

import com.windhaven_consulting.breezy.component.annotation.ControlledComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledMethod;
import com.windhaven_consulting.breezy.component.annotation.ControlledParameter;
import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;
import com.windhaven_consulting.breezy.embeddedcontroller.OutputType;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;

@ControlledComponent(value="Sample PWM Component - do not use",
numberOfOutputs=1,
pinNames={"PWM 1"},
outputType=OutputType.PWM_OUTPUT)
public class PWMSampleComponent extends GenericComponent<PWMOutputPin> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// need to create: ParameterFieldType.PWM_OUTPUT_PIN?  Only affects which field is opened up in the editor, I think
	@ControlledMethod("Turn On")
	public void turnOn(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.DIGITAL_OUTPUT_PIN, required = true) UUID digitalOutputPinId) {
		PWMOutputPin pwmOutputPin = getOutputPin(digitalOutputPinId);
		pwmOutputPin.getId();
		
//		DigitalOutputPin digitalOutputPin = getDigitalOutputPin(digitalOutputPinId);
//		digitalOutputPin.setHigh();
	}

	@ControlledMethod("Turn Off")
	public void turnOff(@ControlledParameter(name = "Pin Name", parameterFieldType = ParameterFieldType.DIGITAL_OUTPUT_PIN, required = true) UUID digitalOutputPinId) {
//		DigitalOutputPin digitalOutputPin = getDigitalOutputPin(digitalOutputPinId);
//		digitalOutputPin.setLow();
	}
	
	@Override
	public void test() {
		// TODO Auto-generated method stub

	}

}
