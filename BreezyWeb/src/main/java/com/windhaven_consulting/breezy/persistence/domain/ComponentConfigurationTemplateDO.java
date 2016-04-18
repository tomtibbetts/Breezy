package com.windhaven_consulting.breezy.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComponentConfigurationTemplateDO extends PersistentObject {

	private String componentType;
	
	private List<OutputConfigurationTemplateDO> outputConfigurationTemplateDOs = new ArrayList<OutputConfigurationTemplateDO>();

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	@JsonProperty("outputConfigurationTemplates")
	public List<OutputConfigurationTemplateDO> getOutputConfigurationTemplateDOs() {
		return outputConfigurationTemplateDOs;
	}

	@JsonProperty("outputConfigurationTemplates")
	public void setOutputConfigurationTemplateDOs(List<OutputConfigurationTemplateDO> outputConfigurationTemplateDOs) {
		this.outputConfigurationTemplateDOs = outputConfigurationTemplateDOs;
	}
}
