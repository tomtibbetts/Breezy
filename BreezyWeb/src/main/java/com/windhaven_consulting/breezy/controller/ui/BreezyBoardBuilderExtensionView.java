package com.windhaven_consulting.breezy.controller.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;

import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionType;
import com.windhaven_consulting.breezy.manager.viewobject.Extension;

public class BreezyBoardBuilderExtensionView implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private UUID id;
	
	private ExtensionType extensionType;

	private String description;
	
	private List<MapEntryView<String, String>> propertyEntries = new ArrayList<MapEntryView<String, String>>();

	public BreezyBoardBuilderExtensionView() {
		super();
	}
	
	public BreezyBoardBuilderExtensionView(Extension extension) {
		this.description = extension.getDescription();
		this.id = extension.getId();
		this.name = extension.getName();
		this.extensionType = extension.getExtensionType();
		
		for(Entry<String, String> entry : extension.getProperties().entrySet()) {
			MapEntryView<String, String> mapEntryView = new MapEntryView<String, String>(entry);
			propertyEntries.add(mapEntryView);
		}
	}
	
	public Extension updateExtensionFromView(Extension extension) {
		extension.setDescription(description);
		extension.setExtensionType(extensionType);
		extension.setId(id);
		extension.setName(name);
		extension.getProperties().clear();
		
		for(MapEntryView<String, String> mapEntryView : propertyEntries) {
			extension.getProperties().put(mapEntryView.getKey(), mapEntryView.getValue());
		}
		
		return extension;
	}

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
		return extensionType;
	}

	public void setType(ExtensionType type) {
		this.extensionType = type;
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
