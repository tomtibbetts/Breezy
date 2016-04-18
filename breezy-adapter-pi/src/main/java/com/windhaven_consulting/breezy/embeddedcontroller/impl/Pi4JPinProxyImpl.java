package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.UUID;

import com.pi4j.io.gpio.GpioPin;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;

/**
 * This is an adapter class for the Pi4J library.  This class abstracts Pi4J functionality
 * 
 * @author Tom Tibbetts
 *
 */
public class Pi4JPinProxyImpl implements BreezyPin {

	private GpioPin gpioPin;
	private String name;
	private UUID id;

	public Pi4JPinProxyImpl(String name, UUID id, GpioPin gpioPin) {
		this.name = name;
		this.id = id;
		this.gpioPin = gpioPin;
	}

	public String getName() {
		return name;
	}

	@Override
	public UUID getId() {
		return id;
	}
	
	public GpioPin getGpioPin() {
		return gpioPin;
	}
}
