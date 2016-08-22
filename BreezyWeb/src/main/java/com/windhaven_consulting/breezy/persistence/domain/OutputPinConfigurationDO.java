package com.windhaven_consulting.breezy.persistence.domain;

import java.util.UUID;

public class OutputPinConfigurationDO extends PersistentObject {
	private String description;
	
	private UUID extensionId;
	
	private String extensionMappedPin;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UUID getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(UUID extensionId) {
		this.extensionId = extensionId;
	}

	public String getExtensionMappedPin() {
		return extensionMappedPin;
	}

	public void setExtensionMappedPin(String extensionMappedPin) {
		this.extensionMappedPin = extensionMappedPin;
	}

}
