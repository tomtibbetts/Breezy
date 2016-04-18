package com.windhaven_consulting.breezy.persistence.domain;



public class OutputConfigurationTemplate extends PersistentObject {

	private ExtensionTemplate extensionTemplate;
	
	private String mappedPin;

	public String getMappedPin() {
		return mappedPin;
	}

	public void setMappedPin(String mappedPin) {
		this.mappedPin = mappedPin;
	}

	public ExtensionTemplate getExtensionTemplate() {
		return extensionTemplate;
	}

	public void setExtensionTemplate(ExtensionTemplate extensionTemplate) {
		this.extensionTemplate = extensionTemplate;
	}
	
}
