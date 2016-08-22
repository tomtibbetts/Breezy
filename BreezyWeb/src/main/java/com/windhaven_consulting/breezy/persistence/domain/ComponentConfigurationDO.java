package com.windhaven_consulting.breezy.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComponentConfigurationDO extends PersistentObject {
	private String componentType;
	
	private String description;
	
	private List<OutputPinConfigurationDO> outputPinConfigurationDOs = new ArrayList<OutputPinConfigurationDO>();
	
	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("outputPinConfigurations")
	public List<OutputPinConfigurationDO> getOutputPinConfigurationDOs() {
		return outputPinConfigurationDOs;
	}

	@JsonProperty("outputPinConfigurations")
	public void setOutputPinConfigurationDOs(List<OutputPinConfigurationDO> outputPinConfigurationDOs) {
		this.outputPinConfigurationDOs = outputPinConfigurationDOs;
	}

	public boolean isInverted() {
		return inverted;
	}

	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}

	public boolean isProvisionable() {
		return provisionable;
	}

	public void setProvisionable(boolean provisionable) {
		this.provisionable = provisionable;
	}

	public Integer getForwardLatency() {
		return forwardLatency;
	}

	public void setForwardLatency(Integer forwardLatency) {
		this.forwardLatency = forwardLatency;
	}

	public Integer getTrailingLatency() {
		return trailingLatency;
	}

	public void setTrailingLatency(Integer trailingLatency) {
		this.trailingLatency = trailingLatency;
	}

	private boolean inverted;
	
	private boolean provisionable = true;
	
	private Integer forwardLatency = NumberUtils.INTEGER_ZERO;
	
	private Integer trailingLatency = NumberUtils.INTEGER_ZERO;

}
