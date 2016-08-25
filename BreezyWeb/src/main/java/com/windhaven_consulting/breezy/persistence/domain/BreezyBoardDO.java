package com.windhaven_consulting.breezy.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.windhaven_consulting.breezy.persistence.dataservice.Revisionable;

public class BreezyBoardDO extends PersistentObject implements Revisionable {
	private boolean mounted;
	
	private String description;
	
	private List<InputPinConfigurationDO> inputPinConfigurationDOs = new ArrayList<InputPinConfigurationDO>();
	
	private List<ComponentConfigurationDO> componentConfigurationDOs = new ArrayList<ComponentConfigurationDO>();
	
	private List<ExtensionDO> extensionDOs = new ArrayList<ExtensionDO>();

	private String releaseRevisionNumber;

	private boolean restricted = false;

	public boolean isMounted() {
		return mounted;
	}

	public void setMounted(boolean mounted) {
		this.mounted = mounted;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("inputPinConfigurations")
	public List<InputPinConfigurationDO> getInputPinConfigurationDOs() {
		return inputPinConfigurationDOs;
	}

	@JsonProperty("inputPinConfigurations")
	public void setInputPinConfigurationDOs(List<InputPinConfigurationDO> inputPinConfigurationDOs) {
		this.inputPinConfigurationDOs = inputPinConfigurationDOs;
	}

	@JsonProperty("componentConfigurations")
	public List<ComponentConfigurationDO> getComponentConfigurationDOs() {
		return componentConfigurationDOs;
	}

	@JsonProperty("componentConfigurations")
	public void setComponentConfigurationDOs(List<ComponentConfigurationDO> componentConfigurationDOs) {
		this.componentConfigurationDOs = componentConfigurationDOs;
	}

	@JsonProperty("extensions")
	public List<ExtensionDO> getExtensionDOs() {
		return extensionDOs;
	}

	@JsonProperty("extensions")
	public void setExtensionDOs(List<ExtensionDO> extensionDOs) {
		this.extensionDOs = extensionDOs;
	}

	public boolean isRestricted() {
		return restricted;
	}

	public void setRestricted(boolean restricted) {
		this.restricted = restricted;
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
