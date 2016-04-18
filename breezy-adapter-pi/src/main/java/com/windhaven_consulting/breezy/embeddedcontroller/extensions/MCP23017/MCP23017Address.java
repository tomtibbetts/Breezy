package com.windhaven_consulting.breezy.embeddedcontroller.extensions.MCP23017;

import java.util.Arrays;
import java.util.List;

import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;


public enum MCP23017Address implements PropertyValueEnum {
	ADDRESS_0("Address 0", "0x20"),
	ADDRESS_1("Address 1", "0x21"),
	ADDRESS_2("Address 2", "0x22"),
	ADDRESS_3("Address 3", "0x23"),
	ADDRESS_4("Address 4", "0x24"),
	ADDRESS_5("Address 5", "0x25"),
	ADDRESS_6("Address 6", "0x26"),
	ADDRESS_7("Address 7", "0x27");
	
	private String label;
	private String value;
	
	private MCP23017Address(String label, String value) {
		this.label = label;
		this.value = value;
	}

	@Override
	public String getName() {
		return this.name();
	}
	
	public String getLabel() {
		return label;
	}

	public String getValue() {
		return value;
	}

	@Override
	public List<PropertyValueEnum> getProperties() {
		return Arrays.asList(MCP23017Address.values());
	}

}
