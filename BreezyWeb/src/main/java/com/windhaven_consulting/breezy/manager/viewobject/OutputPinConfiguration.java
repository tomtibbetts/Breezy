package com.windhaven_consulting.breezy.manager.viewobject;

import java.io.Serializable;

import com.windhaven_consulting.breezy.persistence.domain.PersistentObject;

public class OutputPinConfiguration extends PersistentObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String description;
	
	private Extension extension;
	
	private String extensionMappedPin;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Extension getExtension() {
		return extension;
	}

	public void setExtension(Extension extension) {
		this.extension = extension;
	}

	public String getExtensionMappedPin() {
		return extensionMappedPin;
	}

	public void setExtensionMappedPin(String extensionMappedPin) {
		this.extensionMappedPin = extensionMappedPin;
	}

}
