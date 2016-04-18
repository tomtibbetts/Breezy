package com.windhaven_consulting.breezy.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.windhaven_consulting.breezy.persistence.dataservice.Revisionable;

public class BreezyBoardTemplateDO extends PersistentObject implements Revisionable {

	private List<ExtensionTemplateDO> extensionTemplateDOs = new ArrayList<ExtensionTemplateDO>();
	
	private List<InputConfigurationTemplateDO> inputConfigurationTemplateDOs = new ArrayList<InputConfigurationTemplateDO>();
	
	private List<ComponentConfigurationTemplateDO> componentConfigurationTemplateDOs = new ArrayList<ComponentConfigurationTemplateDO>();
	
	private String description;
	
	private boolean inactive;

	private String releaseRevisionNumber;

	@JsonProperty("extensionTemplates")
	public List<ExtensionTemplateDO> getExtensionTemplateDOs() {
		return extensionTemplateDOs;
	}

	@JsonProperty("extensionTemplates")
	public void setExtensionTemplateDOs(List<ExtensionTemplateDO> extensionTemplateDOs) {
		this.extensionTemplateDOs = extensionTemplateDOs;
	}

	@JsonProperty("inputConfigurationTemplates")
	public List<InputConfigurationTemplateDO> getInputConfigurationTemplateDOs() {
		return inputConfigurationTemplateDOs;
	}

	@JsonProperty("inputConfigurationTemplates")
	public void setInputConfigurationTemplateDOs(List<InputConfigurationTemplateDO> inputConfigurationTemplateDOs) {
		this.inputConfigurationTemplateDOs = inputConfigurationTemplateDOs;
	}

	@JsonProperty("componentConfigurationTemplates")
	public List<ComponentConfigurationTemplateDO> getComponentConfigurationTemplateDOs() {
		return componentConfigurationTemplateDOs;
	}

	@JsonProperty("componentConfigurationTemplates")
	public void setComponentConfigurationTemplateDOs(List<ComponentConfigurationTemplateDO> componentConfigurationTemplateDOs) {
		this.componentConfigurationTemplateDOs = componentConfigurationTemplateDOs;
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
