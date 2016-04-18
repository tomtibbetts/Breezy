package com.windhaven_consulting.breezy.persistence.enumeration;

public enum EventTriggerType {
	INPUT("Input");
	
	private String description;
	
	private EventTriggerType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
