package com.windhaven_consulting.breezy.embeddedcontroller.extensions;

public enum ExtensionType {
	
	SYSTEM("System"),
	MCP23S08("MCP23S08(SPI) I/O Extender"),
	MCP23017("MCP23017(I2C) I/O Extender"),
	MCP23S17("MCP23S17(SPI) I/O Extender");

	private final String label;
	
	ExtensionType(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
