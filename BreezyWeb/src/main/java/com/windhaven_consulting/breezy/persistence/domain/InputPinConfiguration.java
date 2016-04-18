package com.windhaven_consulting.breezy.persistence.domain;

import java.io.Serializable;

import org.apache.commons.lang3.math.NumberUtils;

import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;

public class InputPinConfiguration extends PersistentObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String description;
	
	private PinPullResistance pinPullResistance;
	
	private String extension;
	
	private String extensionMappedPin;
	
	private Integer debounce = NumberUtils.INTEGER_ZERO;
	
	private boolean eventTrigger;
	
	private boolean provisionable = true;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PinPullResistance getPinPullResistance() {
		return pinPullResistance;
	}

	public void setPinPullResistance(PinPullResistance pinPullResistance) {
		this.pinPullResistance = pinPullResistance;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public String getExtensionMappedPin() {
		return extensionMappedPin;
	}

	public void setExtensionMappedPin(String extensionMappedPin) {
		this.extensionMappedPin = extensionMappedPin;
	}

	public Integer getDebounce() {
		return debounce;
	}

	public void setDebounce(Integer debounce) {
		this.debounce = debounce;
	}

	public boolean isEventTrigger() {
		return eventTrigger;
	}

	public void setEventTrigger(boolean eventTrigger) {
		this.eventTrigger = eventTrigger;
	}

	public boolean isProvisionable() {
		return provisionable;
	}

	public void setProvisionable(boolean provisionable) {
		this.provisionable = provisionable;
	}
	
}
