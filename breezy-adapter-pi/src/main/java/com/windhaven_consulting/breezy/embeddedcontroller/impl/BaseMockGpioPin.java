package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.HashMap;
import java.util.Map;

import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;

public class BaseMockGpioPin {
	private GpioProvider provider;
	private Pin pin;
	private String pinName;
	private Map<String, String> properties = new HashMap<String, String>();


	public BaseMockGpioPin(GpioProvider provider, Pin pin) {
		this.provider = provider;
		this.pin = pin;
		this.pinName = pin.getName();
	}

	/**
	 * 
	 * @return
	 */
	public GpioProvider getProvider() {
		return provider;
	}

	/**
	 * 
	 * @return
	 */
	public Pin getPin() {
		return pin;
	}

	public void setName(String name) {
		this.pinName = name;
	}

	public String getName() {
		return pinName;
	}

	/**
	 * 
	 */
	public void setProperty(String key, String value) {
		properties.put(key, value);
	}

	/**
	 * 
	 */
	public boolean hasProperty(String key) {
		return properties.containsKey(key);
	}

	/**
	 * 
	 */
	public String getProperty(String key) {
		return properties.get(key);
	}

	/**
	 * 
	 */
	public String getProperty(String key, String defaultValue) {
		String value = defaultValue;
		
		if(properties.containsKey(key)) {
			value = properties.get(key);
		}
		
		return value;
	}

	/**
	 * 
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * 
	 */
	public void removeProperty(String key) {
		properties.remove(key);
	}

	/**
	 * 
	 */
	public void clearProperties() {
		properties.clear();
	}

}
