package com.windhaven_consulting.breezy.embeddedcontroller.extensions;

import java.util.Arrays;
import java.util.List;

import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;


public enum I2CBusProperty implements PropertyValueEnum {
	// Please keep enumerations in alphabetical order
	ADDRESS("Address"),
	BUS_NUMBER("Bus Number");
	
	private String label;

	private I2CBusProperty(String label) {
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
		return Arrays.asList(I2CBusProperty.values());
	}
}
