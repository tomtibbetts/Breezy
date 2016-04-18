package com.windhaven_consulting.breezy.persistence.domain;

import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;

public class InputConfigurationTemplate extends PersistentObject {

	private String mappedPin;
	
	private PinPullResistance pinPullResistance;
	
	private ExtensionTemplate extensionTemplate;

	public String getMappedPin() {
		return mappedPin;
	}

	public void setMappedPin(String mappedPin) {
		this.mappedPin = mappedPin;
	}

	public PinPullResistance getPinPullResistance() {
		return pinPullResistance;
	}

	public void setPinPullResistance(PinPullResistance pinPullResistance) {
		this.pinPullResistance = pinPullResistance;
	}

	public ExtensionTemplate getExtensionTemplate() {
		return extensionTemplate;
	}

	public void setExtensionTemplate(ExtensionTemplate extensionTemplate) {
		this.extensionTemplate = extensionTemplate;
	}
	
}
