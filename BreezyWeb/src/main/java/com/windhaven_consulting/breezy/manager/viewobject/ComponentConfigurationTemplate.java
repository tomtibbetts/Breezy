package com.windhaven_consulting.breezy.manager.viewobject;

import java.util.ArrayList;
import java.util.List;

import com.windhaven_consulting.breezy.persistence.domain.PersistentObject;

public class ComponentConfigurationTemplate extends PersistentObject {

	private String componentType;
	
	private List<OutputConfigurationTemplate> outputConfigurationTemplates = new ArrayList<OutputConfigurationTemplate>();

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public List<OutputConfigurationTemplate> getOutputConfigurationTemplates() {
		return outputConfigurationTemplates;
	}

	public void setOutputConfigurationTemplates(List<OutputConfigurationTemplate> outputConfigurationTemplates) {
		this.outputConfigurationTemplates = outputConfigurationTemplates;
	}
}
