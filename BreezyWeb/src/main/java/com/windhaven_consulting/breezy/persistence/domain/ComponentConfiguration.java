package com.windhaven_consulting.breezy.persistence.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

public class ComponentConfiguration extends PersistentObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String componentType;
	
	private String description;
	
	private List<OutputPinConfiguration> outputPinConfigurations = new ArrayList<OutputPinConfiguration>();
	
	private boolean inverted;
	
	private boolean provisionable = true;
	
	private Integer forwardLatency = NumberUtils.INTEGER_ZERO;
	
	private Integer trailingLatency = NumberUtils.INTEGER_ZERO;

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

	public List<OutputPinConfiguration> getOutputPinConfigurations() {
		return outputPinConfigurations;
	}

	public void setOutputPinConfigurations(List<OutputPinConfiguration> outputPinConfigurations) {
		this.outputPinConfigurations = outputPinConfigurations;
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
	
}
