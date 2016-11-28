package com.windhaven_consulting.breezy.manager.viewobject;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionType;
import com.windhaven_consulting.breezy.persistence.domain.PersistentObject;

public class Extension extends PersistentObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ExtensionType extensionType;

	private String description;
	
	private Map<String, String> properties = new TreeMap<String, String>();
	
	public ExtensionType getExtensionType() {
		return extensionType;
	}

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

	public void addProperties(Map<String, String> properties) {
		this.properties.putAll(properties);
	}
	
}
