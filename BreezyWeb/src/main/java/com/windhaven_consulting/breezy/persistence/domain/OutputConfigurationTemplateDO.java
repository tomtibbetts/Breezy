package com.windhaven_consulting.breezy.persistence.domain;

import java.util.UUID;

public class OutputConfigurationTemplateDO extends PersistentObject {

	private UUID extensionTemplateId;
	
	private String mappedPin;

	public String getMappedPin() {
		return mappedPin;
	}

	public void setMappedPin(String mappedPin) {
		this.mappedPin = mappedPin;
	}
	
	public UUID getExtensionTemplateId() {
		return extensionTemplateId;
	}

	public void setExtensionTemplateId(UUID extensionTemplateId) {
		this.extensionTemplateId = extensionTemplateId;
	}

}
