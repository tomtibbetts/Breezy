package com.windhaven_consulting.breezy.persistence.domain;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionType;

public class ExtensionDO extends PersistentObject {
	private ExtensionType extensionType;

	private String description;
	
	private Map<String, String> properties = new HashMap<String, String>();

	@JsonProperty("type")
	public ExtensionType getExtensionType() {
		return extensionType;
	}

	@JsonProperty("type")
	public void setExtensionType(ExtensionType extensionType) {
		this.extensionType = extensionType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	
}
