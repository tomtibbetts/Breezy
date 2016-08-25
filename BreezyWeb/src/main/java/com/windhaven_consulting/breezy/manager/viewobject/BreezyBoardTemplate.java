package com.windhaven_consulting.breezy.manager.viewobject;

import java.util.ArrayList;
import java.util.List;

import com.windhaven_consulting.breezy.persistence.dataservice.Revisionable;
import com.windhaven_consulting.breezy.persistence.domain.PersistentObject;

// since this a manager level object, should it extend PersistentObject?  And, do I care about Revisionable at this point?
public class BreezyBoardTemplate extends PersistentObject implements Revisionable {

	private List<ExtensionTemplate> extensionTemplates = new ArrayList<ExtensionTemplate>();
	
	private List<InputConfigurationTemplate> inputConfigurationTemplates = new ArrayList<InputConfigurationTemplate>();
	
	private List<ComponentConfigurationTemplate> componentConfigurationTemplates = new ArrayList<ComponentConfigurationTemplate>();
	
	private String description;
	
	private boolean inactive;

	private String releaseRevisionNumber;

	public List<ExtensionTemplate> getExtensionTemplates() {
		return extensionTemplates;
	}

	public void setExtensionTemplates(List<ExtensionTemplate> extensionTemplates) {
		this.extensionTemplates = extensionTemplates;
	}

	public List<InputConfigurationTemplate> getInputConfigurationTemplates() {
		return inputConfigurationTemplates;
	}

	public void setInputConfigurationTemplates(List<InputConfigurationTemplate> inputConfigurationTemplates) {
		this.inputConfigurationTemplates = inputConfigurationTemplates;
	}

	public List<ComponentConfigurationTemplate> getComponentConfigurationTemplates() {
		return componentConfigurationTemplates;
	}

	public void setComponentConfigurationTemplates(List<ComponentConfigurationTemplate> componentConfigurationTemplates) {
		this.componentConfigurationTemplates = componentConfigurationTemplates;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isInactive() {
		return inactive;
	}

	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}

	@Override
	public String getReleaseRevisionNumber() {
		return releaseRevisionNumber;
	}

	@Override
	public void setReleaseRevisionNumber(String releaseRevisionNumber) {
		this.releaseRevisionNumber = releaseRevisionNumber;
	}
	
}
