package com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23S08;

import java.util.Arrays;
import java.util.List;

import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;


public enum MCP23S08Address implements PropertyValueEnum {
	ADDRESS_0("Address 0", "0x40"),
	ADDRESS_1("Address 1", "0x42"),
	ADDRESS_2("Address 2", "0x44"),
	ADDRESS_3("Address 3", "0x46");
	
	private String label;
	private String value;
	
	private MCP23S08Address(String label, String value) {
		this.label = label;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String getName() {
		return this.name();
	}

	@Override
	public List<PropertyValueEnum> getProperties() {
		return Arrays.asList(MCP23S08Address.values());
	}

}
