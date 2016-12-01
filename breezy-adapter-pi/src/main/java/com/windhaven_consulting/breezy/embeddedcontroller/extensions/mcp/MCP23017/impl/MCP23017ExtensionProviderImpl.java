package com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23017.impl;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.gpio.extension.mcp.MCP23017GpioProvider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyI2CBus;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPinProperty;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.I2CBusProperty;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23017.BreezyToMCP23017Pin;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23017.MCP23017Pin;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.BreezyToPi4JI2CBus;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.BreezyToPi4JPinPullResistance;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.MockDigitalInputPinProxyImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.MockDigitalOutputPinProxyImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JDigitalInputPinProxyImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JDigitalOutputPinProxyImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JPinProxyImpl;

public class MCP23017ExtensionProviderImpl implements ExtensionProvider<DigitalOutputPin> {
	static final Logger LOG = LoggerFactory.getLogger(MCP23017ExtensionProviderImpl.class);

	private boolean windowsEnvironment;
	private GpioController gpioController;
	private GpioPinListenerDigital gpioPinListenerDigital;
	private Map<String, String> properties;
	private MCP23017GpioProvider mcp23017GpioProvider;

	public MCP23017ExtensionProviderImpl(GpioController gpioController, GpioPinListenerDigital gpioPinListenerDigital, Map<String, String> properties, boolean windowsEnvironment) {
		this.gpioController = gpioController;
		this.gpioPinListenerDigital = gpioPinListenerDigital;
		this.properties = properties;
		this.windowsEnvironment = windowsEnvironment;
		
		initialize();
	}

	@Override
	public DigitalInputPin provisionDigitalInputPin(String name, String pinName, UUID pinId, PinPullResistance pinPullResistance, Integer debounce, boolean isEventTrigger) {
		if(isWindowsEnvironment()) {
			return new MockDigitalInputPinProxyImpl(name, pinId);
		}
		else {
			BreezyPin breezyPin = MCP23017Pin.getByName(pinName);
			com.pi4j.io.gpio.Pin pi4JPin = BreezyToMCP23017Pin.getPin(breezyPin);
			com.pi4j.io.gpio.PinPullResistance pi4JPinPullResistance = BreezyToPi4JPinPullResistance.getPinPullResistance(pinPullResistance);
			GpioPinDigitalInput gpioPin = gpioController.provisionDigitalInputPin(mcp23017GpioProvider, pi4JPin, pi4JPinPullResistance);
			
			gpioPin.setDebounce(debounce);
			gpioPin.setProperty(BreezyPinProperty.NAME.name(), name);
			gpioPin.setProperty(BreezyPinProperty.ID.name(), pinId.toString());
			
			if(isEventTrigger) {
				gpioPin.addListener(gpioPinListenerDigital);
			}
			
			return new Pi4JDigitalInputPinProxyImpl(name, pinId, gpioPin);
		}
	}

	@Override
	public DigitalOutputPin provisionDigitalOutputPin(String name, String pinName, UUID pinId) {
		if(isWindowsEnvironment()) {
			return new MockDigitalOutputPinProxyImpl(name, pinId);
		}
		else {
			BreezyPin breezyPin = MCP23017Pin.getByName(pinName);
			com.pi4j.io.gpio.Pin pi4JPin = BreezyToMCP23017Pin.getPin(breezyPin);
			GpioPinDigitalOutput gpioPin = gpioController.provisionDigitalOutputPin(mcp23017GpioProvider, pi4JPin, PinState.LOW);
			
			gpioPin.setProperty(BreezyPinProperty.NAME.name(), name);
			gpioPin.setProperty(BreezyPinProperty.ID.name(), pinId.toString());
			
			return new Pi4JDigitalOutputPinProxyImpl(name, pinId, gpioPin);
		}
	}

	@Override
	public DigitalOutputPin provisionOutputPin(String name, String pinName, UUID pinId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unprovisionPin(BreezyPin breezyPin) {
		if(!windowsEnvironment) {
			Pi4JPinProxyImpl pi4jPinProxyImpl = (Pi4JPinProxyImpl) breezyPin;
			gpioController.unprovisionPin(pi4jPinProxyImpl.getGpioPin());
		}
	}

	private void initialize() {
//		LOG.debug("Initializing MCP23017ExtensionProviderImpl");
		
		validateProperties();
		
		if(!isWindowsEnvironment()) {
			String busNumber = properties.get(I2CBusProperty.BUS_NUMBER.name());
			BreezyI2CBus breezyI2CBus = BreezyI2CBus.valueOf(busNumber);
			
			int address = Integer.decode(properties.get(I2CBusProperty.ADDRESS.name()));
			
			try {
				mcp23017GpioProvider = new MCP23017GpioProvider(BreezyToPi4JI2CBus.getBus(breezyI2CBus).intValue(), address);
			} catch (IOException e) {
				throw new EmbeddedControllerException("Cannot create MCP23017GpioProvider, IO Exception thrown", e);
			}
		}

//		LOG.debug("End Initializing MCP23017ExtensionProviderImpl");
	}

	private void validateProperties() {
		if(!properties.containsKey(I2CBusProperty.BUS_NUMBER.name())) {
			throw new EmbeddedControllerException("MCP23017 extension bus number was not provided");
		}
		
		if(!properties.containsKey(I2CBusProperty.ADDRESS.name())) {
			throw new EmbeddedControllerException("MCP23017 extension address was not provided");
		}
	}

	private boolean isWindowsEnvironment() {
		return windowsEnvironment;
	}

}
