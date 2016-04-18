package com.windhaven_consulting.breezy.embeddedcontroller;

public enum PinPullResistance {
	NONE("None"),
	PULL_UP("Pull Up"),
	PULL_DOWN("Pull Down");

	private String label;

	private PinPullResistance(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
