package com.windhaven_consulting.breezy.embeddedcontroller;

public enum BreezyPinProperty {
	NAME("Name"),
	ID("Id");
	
	private String label;

	private BreezyPinProperty(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
