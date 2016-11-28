package com.windhaven_consulting.breezy.embeddedcontroller.extensions;

import com.windhaven_consulting.breezy.embeddedcontroller.InputType;
import com.windhaven_consulting.breezy.embeddedcontroller.OutputType;

public enum ExtensionType {
	
	SYSTEM("System", InputType.DIGITAL_INPUT, OutputType.DIGITAL_OUTPUT),
	MCP23S08("MCP23S08(SPI) I/O Extender", InputType.DIGITAL_INPUT, OutputType.DIGITAL_OUTPUT),
	MCP23017("MCP23017(I2C) I/O Extender", InputType.DIGITAL_INPUT, OutputType.DIGITAL_OUTPUT),
	MCP23S17("MCP23S17(SPI) I/O Extender", InputType.DIGITAL_INPUT, OutputType.DIGITAL_OUTPUT),
	PCA9685("PCA9685(I2C) PWM I/O Extender", InputType.NONE, OutputType.PWM_OUTPUT);

	private final String label;
	
	private final InputType inputType;
	
	private final OutputType outputType;
	
	ExtensionType(String label, InputType inputType, OutputType outputType) {
		this.label = label;
		this.inputType = inputType;
		this.outputType = outputType;
	}
	
	public String getLabel() {
		return label;
	}
	
	public InputType getInputType() {
		return inputType;
	}
	
	public OutputType getOutputType() {
		return outputType;
	}
}
