package com.windhaven_consulting.breezy.controller.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionType;

public class BreezyBoardBuilderExtensionView implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private UUID id;
	
	private ExtensionType type;

	private String description;
	
	private List<MapEntryView<String, String>> propertyEntries = new ArrayList<MapEntryView<String, String>>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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

	public List<MapEntryView<String, String>> getPropertyEntries() {
		return propertyEntries;
	}

	public void setPropertyEntries(List<MapEntryView<String, String>> propertyEntries) {
		this.propertyEntries = propertyEntries;
	}
}
