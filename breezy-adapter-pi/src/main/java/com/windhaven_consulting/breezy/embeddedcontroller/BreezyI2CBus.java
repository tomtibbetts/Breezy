package com.windhaven_consulting.breezy.embeddedcontroller;

import java.util.Arrays;
import java.util.List;


public enum BreezyI2CBus implements PropertyValueEnum {
	BUS_0("Bus 0", "BUS_0"),
	BUS_1("Bus 1", "BUS_1");

	private String label;
	private String value;

	private BreezyI2CBus(String label, String value) {
		this.label = label;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}

	@Override
	public String getName() {
		return this.name();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public List<PropertyValueEnum> getProperties() {
		return Arrays.asList(BreezyI2CBus.values());
	}
}
