package com.windhaven_consulting.breezy.persistence.domain;

import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;

public class InputConfigurationTemplateDO extends PersistentObject {
	private UUID extensionTemplateId;

	private String mappedPin;
	
	private PinPullResistance pinPullResistance;

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
	
	public UUID getExtensionTemplateId() {
		return extensionTemplateId;
	}

	public void setExtensionTemplateId(UUID extensionTemplateId) {
		this.extensionTemplateId = extensionTemplateId;
	}
	
}
