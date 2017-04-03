package com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23S08.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezySPIChannel;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.SPIBusProperty;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl.SPIBusExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23S08.MCP23S08Address;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23S08.MCP23S08Pin;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.BreezyToPi4JSPIChannel;
import com.windhaven_consulting.breezy.pi4j.custom.extension.mcp.MCP23S08GpioProvider;


@Named("mcp23S08ExtensionProviderFactory")
@ApplicationScoped
public class MCP23S08ExtensionProviderFactoryImpl extends SPIBusExtensionProviderFactory<DigitalOutputPin> {
	static final Logger LOG = LoggerFactory.getLogger(MCP23S08ExtensionProviderFactoryImpl.class);
	
	@PostConstruct
	public void postConstruct() {
		super.postConstruct();
		addProperties(SPIBusProperty.ADDRESS, Arrays.asList(MCP23S08Address.values()));
	}
	
	@Override
	public List<BreezyPin> getAvailablePins() {
		return MCP23S08Pin.getPins();
	}

	@Override
	public ExtensionProvider<DigitalOutputPin> getExtensionProvider(GpioController gpioController, GpioProvider gpioProvider, GpioPinListenerDigital gpioPinListenerDigital) {
		return new MCP23S08ExtensionProviderImpl(gpioController, gpioProvider, gpioPinListenerDigital);
	}

	@Override
	public void validateProperties(Map<String, String> properties) {
			if(!properties.containsKey(SPIBusProperty.CHANNEL.name())) {
			throw new EmbeddedControllerException("MCP23S08 extension channel number was not provided");
		}
		
		if(!properties.containsKey(SPIBusProperty.ADDRESS.name())) {
			throw new EmbeddedControllerException("MCP23S08 extension address was not provided");
		}
	}

	@Override
	public GpioProvider getGpioProvider(Map<String, String> properties) {
//		LOG.debug("Initializing MCP23S08ExtensionProviderImpl");
		
		GpioProvider gpioProvider = null;
		
		String spiChannel = properties.get(SPIBusProperty.CHANNEL.name());
		BreezySPIChannel breezySPIChannel = BreezySPIChannel.valueOf(spiChannel);
		byte address = Byte.decode(properties.get(SPIBusProperty.ADDRESS.name()));
	
//		LOG.debug("Channel: " + spiChannel);
//		LOG.debug("address: " + address);
		
		try {
			gpioProvider = new MCP23S08GpioProvider(address, BreezyToPi4JSPIChannel.getChannel(breezySPIChannel));
		} catch (IOException e) {
//			LOG.debug("Cannot create MCP23S17GpioProvider, IO Exception thrown: " + e.getMessage());
			
			throw new EmbeddedControllerException("Cannot create MCP23S08GpioProvider, IO Exception thrown", e);
		}
	
//		LOG.debug("End Initializing MCP23S08ExtensionProviderImpl");
		
		return gpioProvider;
	}

}
