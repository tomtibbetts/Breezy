package com.windhaven_consulting.breezy.embeddedcontroller.extensions.MCP23017;

import java.util.Arrays;
import java.util.List;

import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;


public enum MCP23017Property implements PropertyValueEnum {
	// Please keep enumerations in alphabetical order
	ADDRESS("Address"),
	BUS_NUMBER("Bus Number");
	
	private String label;

	private MCP23017Property(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getName() {
		return this.name();
	}

	@Override
	public String getValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<PropertyValueEnum> getProperties() {
		return Arrays.asList(MCP23017Property.values());
	}
}
