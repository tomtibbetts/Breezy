package com.windhaven_consulting.breezy.persistence.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionType;

public class Extension extends PersistentObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ExtensionType type;

	private String description;
	
	private Map<String, String> properties = new HashMap<String, String>();
	
	public ExtensionType getType() {
		return type;
	}

	public void setType(ExtensionType type) {
		this.type = type;
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
