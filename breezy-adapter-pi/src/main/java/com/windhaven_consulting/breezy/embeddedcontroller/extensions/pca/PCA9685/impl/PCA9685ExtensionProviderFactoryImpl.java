package com.windhaven_consulting.breezy.embeddedcontroller.extensions.pca.PCA9685.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyI2CBus;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerRuntimeException;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.I2CBusProperty;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl.I2CBusExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.pca.PCA9685.PCA9685Address;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.pca.PCA9685.PCA9685Pin;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.BreezyToPi4JI2CBus;

@Named("pca9685ExtensionProviderFactory")
@ApplicationScoped
public class PCA9685ExtensionProviderFactoryImpl extends I2CBusExtensionProviderFactory<PWMOutputPin> {
	static final Logger LOG = LoggerFactory.getLogger(PCA9685ExtensionProviderFactoryImpl.class);

	private static final BigDecimal DEFAULT_FREQUENCY = new BigDecimal("48.828");
	private static final BigDecimal DEFAULT_FREQUENCY_CORRECTION_FACTOR = new BigDecimal("1.0578");
	
	@PostConstruct
	public void postConstruct() {
		super.postConstruct();
		addProperties(I2CBusProperty.ADDRESS, Arrays.asList(PCA9685Address.values()));
	}

	@Override
	public ExtensionProvider<PWMOutputPin> getExtensionProvider(GpioController gpioController, GpioProvider gpioProvider, GpioPinListenerDigital gpioPinListenerDigital) {
		return new PCA9685ExtensionProviderImpl(gpioController, gpioProvider, gpioPinListenerDigital);
	}

	@Override
	public List<BreezyPin> getAvailablePins() {
		return PCA9685Pin.getPins();
	}

	@Override
	public void validateProperties(Map<String, String> properties) throws EmbeddedControllerException {
		if(!properties.containsKey(I2CBusProperty.BUS_NUMBER.name())) {
			throw new EmbeddedControllerException("PCA9685 extension bus number was not provided.");
		}
		
		if(!properties.containsKey(I2CBusProperty.ADDRESS.name())) {
			throw new EmbeddedControllerException("PCA9685 extension address was not provided.");
		}
	}

	@Override
	public GpioProvider getGpioProvider(Map<String, String> properties) {
//		LOG.debug("Initializing PCA9685ExtensionProviderImpl");
		
		GpioProvider gpioProvider = null;
		String busNumber = properties.get(I2CBusProperty.BUS_NUMBER.name());
		BreezyI2CBus breezyI2CBus = BreezyI2CBus.valueOf(busNumber);
		
		try {
			I2CBus i2cBus = I2CFactory.getInstance(BreezyToPi4JI2CBus.getBusAsInteger(breezyI2CBus));
			int address = Integer.decode(properties.get(I2CBusProperty.ADDRESS.name()));
			
			gpioProvider = new PCA9685GpioProvider(i2cBus, address, DEFAULT_FREQUENCY, DEFAULT_FREQUENCY_CORRECTION_FACTOR);
		} catch (IOException e) {
			throw new EmbeddedControllerRuntimeException("Cannot create MCP23017GpioProvider, IO Exception thrown", e);
		}
		
		return gpioProvider;
	}

}
