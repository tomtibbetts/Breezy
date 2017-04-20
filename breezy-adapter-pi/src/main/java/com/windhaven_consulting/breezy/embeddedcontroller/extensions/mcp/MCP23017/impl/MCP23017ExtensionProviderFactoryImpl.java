package com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23017.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.gpio.extension.mcp.MCP23017GpioProvider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyI2CBus;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerRuntimeException;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.I2CBusProperty;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl.I2CBusExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23017.MCP23017Address;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23017.MCP23017Pin;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23017.MCP23017Property;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.BreezyToPi4JI2CBus;


@Named("mcp23017ExtensionProviderFactory")
@ApplicationScoped
public class MCP23017ExtensionProviderFactoryImpl extends I2CBusExtensionProviderFactory<DigitalOutputPin> {
	static final Logger LOG = LoggerFactory.getLogger(MCP23017ExtensionProviderFactoryImpl.class);

	@PostConstruct
	public void postConstruct() {
		super.postConstruct();
		addProperties(MCP23017Property.ADDRESS, Arrays.asList(MCP23017Address.values()));
	}
	
	@Override
	public ExtensionProvider<DigitalOutputPin> getExtensionProvider(GpioController gpioController, GpioProvider gpioProvider, GpioPinListenerDigital gpioPinListenerDigital) {
		return new MCP23017ExtensionProviderImpl(gpioController, gpioProvider, gpioPinListenerDigital);
	}

	@Override
	public List<BreezyPin> getAvailablePins() {
		return MCP23017Pin.getPins();
	}

	@Override
	public void validateProperties(Map<String, String> properties) throws EmbeddedControllerException {
		if(!properties.containsKey(I2CBusProperty.BUS_NUMBER.name())) {
			throw new EmbeddedControllerException("MCP23017 extension bus number was not provided.");
		}
		
		if(!properties.containsKey(I2CBusProperty.ADDRESS.name())) {
			throw new EmbeddedControllerException("MCP23017 extension address was not provided.");
		}
	}

	@Override
	public GpioProvider getGpioProvider(Map<String, String> properties) {
//		LOG.debug("Initializing MCP23017ExtensionProviderImpl");
			
		GpioProvider gpioProvider = null;
		
		String busNumber = properties.get(I2CBusProperty.BUS_NUMBER.name());
		BreezyI2CBus breezyI2CBus = BreezyI2CBus.valueOf(busNumber);
		
		int address = Integer.decode(properties.get(I2CBusProperty.ADDRESS.name()));
		
		try {
			gpioProvider = new MCP23017GpioProvider(BreezyToPi4JI2CBus.getBusAsInteger(breezyI2CBus).intValue(), address);
		} catch (IOException e) {
			throw new EmbeddedControllerRuntimeException("Cannot create MCP23017GpioProvider, IO Exception thrown", e);
		}
	
//		LOG.debug("End Initializing MCP23017ExtensionProviderImpl");
		
		return gpioProvider;
	}

}
