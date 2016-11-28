package com.windhaven_consulting.breezy.embeddedcontroller.extensions.pca.PCA9685.impl;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyI2CBus;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.I2CBusProperty;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.BreezyToPi4JI2CBus;

public class PCA9685ExtensionProviderImpl implements ExtensionProvider {
	static final Logger LOG = LoggerFactory.getLogger(PCA9685ExtensionProviderImpl.class);

	private boolean windowsEnvironment;
	private GpioController gpioController;
	private GpioPinListenerDigital gpioPinListenerDigital;
	private Map<String, String> properties;
	private PCA9685GpioProvider pca9685GpioProvider;

	public PCA9685ExtensionProviderImpl(GpioController gpioController, GpioPinListenerDigital gpioPinListenerDigital, Map<String, String> properties, boolean windowsEnvironment) {
		this.gpioController = gpioController;
		this.gpioPinListenerDigital = gpioPinListenerDigital;
		this.properties = properties;
		this.windowsEnvironment = windowsEnvironment;
		
		initialize();
	}

	@Override
	public DigitalInputPin provisionDigitalInputPin(String name, String pinName, UUID id, PinPullResistance pinPullResistance, Integer debounce, boolean isEventTrigger) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DigitalOutputPin provisionDigitalOutputPin(String name, String pinName, UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unprovisionPin(BreezyPin breezyPin) {
		// TODO Auto-generated method stub

	}
	
	private void initialize() {
		LOG.debug("Initializing PCA9685ExtensionProviderImpl");
		
		validateProperties();
		
		if(!isWindowsEnvironment()) {
			String busNumber = properties.get(I2CBusProperty.BUS_NUMBER.name());
			BreezyI2CBus breezyI2CBus = BreezyI2CBus.valueOf(busNumber);
			
			int address = Integer.decode(properties.get(I2CBusProperty.ADDRESS.name()));
			
			try {
				pca9685GpioProvider = new PCA9685GpioProvider(BreezyToPi4JI2CBus.getBus(breezyI2CBus).intValue(), address);
			} catch (IOException e) {
				throw new EmbeddedControllerException("Cannot create MCP23017GpioProvider, IO Exception thrown", e);
			}
		}

		LOG.debug("End Initializing PCA9685ExtensionProviderImpl");
	}

	private void validateProperties() {
		if(!properties.containsKey(I2CBusProperty.BUS_NUMBER.name())) {
			throw new EmbeddedControllerException("PCA9685 extension bus number was not provided");
		}
		
		if(!properties.containsKey(I2CBusProperty.ADDRESS.name())) {
			throw new EmbeddedControllerException("PCA9685 extension address was not provided");
		}
	}

	private boolean isWindowsEnvironment() {
		return windowsEnvironment;
	}

}
