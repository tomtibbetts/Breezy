package com.windhaven_consulting.breezy.embeddedcontroller;

import java.util.List;

public enum BreezySPIChannel implements PropertyValueEnum {
	CHANNEL_0("Channel 0", "CHANNEL_0"),
	CHANNEL_1("Channel 1", "CHANNEL_1");
	
	private String label;
	
	private String value;

	private BreezySPIChannel(String label, String value) {
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
		throw new UnsupportedOperationException();
	}
}
