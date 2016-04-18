package com.windhaven_consulting.breezy.persistence.domain;

import java.io.Serializable;

public class OutputPinConfiguration extends PersistentObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String description;
	
	private String extension;
	
	private String extensionMappedPin;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getExtensionMappedPin() {
		return extensionMappedPin;
	}

	public void setExtensionMappedPin(String extensionMappedPin) {
		this.extensionMappedPin = extensionMappedPin;
	}

}
