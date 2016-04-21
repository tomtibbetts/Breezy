package com.windhaven_consulting.breezy.embeddedcontroller.extensions.MCP23S17.impl;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.gpio.extension.mcp.MCP23S17GpioProvider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPinProperty;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezySPIChannel;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.MCP23017.MCP23017Property;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.MCP23S17.BreezyToMCP23S17Pin;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.MCP23S17.MCP23S17Pin;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.MCP23S17.MCP23S17Property;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.BreezyToPi4JPinPullResistance;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.BreezyToPi4JSPIChannel;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.MockDigitalInputPinProxyImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.MockDigitalOutputPinProxyImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JDigitalInputPinProxyImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JDigitalOutputPinProxyImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JPinProxyImpl;

public class MCP23S17ExtensionProviderImpl implements ExtensionProvider {
	static final Logger LOG = LoggerFactory.getLogger(MCP23S17ExtensionProviderImpl.class);

	private boolean windowsEnvironment;
	private GpioController gpioController;
	private GpioPinListenerDigital gpioPinListenerDigital;
	private Map<String, String> properties;
	private MCP23S17GpioProvider mcp23S17GpioProvider;

	public MCP23S17ExtensionProviderImpl(GpioController gpioController, GpioPinListenerDigital gpioPinListenerDigital, Map<String, String> properties, boolean windowsEnvironment) {
		this.gpioController = gpioController;
		this.gpioPinListenerDigital = gpioPinListenerDigital;
		this.properties = properties;
		this.windowsEnvironment = windowsEnvironment;
		
		initialize();
	}

	@Override
	public DigitalInputPin provisionDigitalInputPin(String name, String pinName, UUID pinId, PinPullResistance pinPullResistance, Integer debounce, boolean isEventTrigger) {
//		LOG.debug("provisioning Input Pin, name: " + name + ", pinName: " + pinName + ", pinId: " + pinId.toString() + ", isEventTrigger: " + isEventTrigger);
		
		if(isWindowsEnvironment()) {
			return new MockDigitalInputPinProxyImpl(name, pinId);
		}
		else {
			BreezyPin breezyPin = MCP23S17Pin.getByName(pinName);
			com.pi4j.io.gpio.Pin pi4JPin = BreezyToMCP23S17Pin.getPin(breezyPin);
			com.pi4j.io.gpio.PinPullResistance pi4JPinPullResistance = BreezyToPi4JPinPullResistance.getPinPullResistance(pinPullResistance);
			GpioPinDigitalInput gpioPin = gpioController.provisionDigitalInputPin(mcp23S17GpioProvider, pi4JPin, pi4JPinPullResistance);
			
			gpioPin.setDebounce(debounce);
			gpioPin.setProperty(BreezyPinProperty.NAME.name(), name);
			gpioPin.setProperty(BreezyPinProperty.ID.name(), pinId.toString());
			
			if(isEventTrigger) {
//				LOG.debug("Adding event trigger. Listener is not null = " + (gpioPinListenerDigital != null));
				gpioPin.addListener(gpioPinListenerDigital);
			}
			
//			LOG.debug("end provisioning digital input pin.\n");
			return new Pi4JDigitalInputPinProxyImpl(name, pinId, gpioPin);
		}
	}

	@Override
	public DigitalOutputPin provisionDigitalOutputPin(String name, String pinName, UUID pinId) {
		if(isWindowsEnvironment()) {
			return new MockDigitalOutputPinProxyImpl(name, pinId);
		}
		else {
			BreezyPin breezyPin = MCP23S17Pin.getByName(pinName);
			com.pi4j.io.gpio.Pin pi4JPin = BreezyToMCP23S17Pin.getPin(breezyPin);
			GpioPinDigitalOutput gpioPin = gpioController.provisionDigitalOutputPin(mcp23S17GpioProvider, pi4JPin);
			
			gpioPin.setProperty(BreezyPinProperty.NAME.name(), name);
			gpioPin.setProperty(BreezyPinProperty.ID.name(), pinId.toString());
			
			return new Pi4JDigitalOutputPinProxyImpl(name, pinId, gpioPin);
		}
	}

	@Override
	public void unprovisionPin(BreezyPin breezyPin) {
		if(!windowsEnvironment) {
			Pi4JPinProxyImpl pi4jPinProxyImpl = (Pi4JPinProxyImpl) breezyPin;
			gpioController.unprovisionPin(pi4jPinProxyImpl.getGpioPin());
		}
	}

	private void initialize() {
//		LOG.debug("Initializing MCP23S17ExtensionProviderImpl");
		
		validateProperties();
		
		if(!isWindowsEnvironment()) {
			String spiChannel = properties.get(MCP23S17Property.CHANNEL.name());
//			LOG.debug("Channel: " + spiChannel);
			BreezySPIChannel breezySPIChannel = BreezySPIChannel.valueOf(spiChannel);
			byte address = Byte.decode(properties.get(MCP23S17Property.ADDRESS.name()));
//			LOG.debug("address: " + address);
			
			try {
				mcp23S17GpioProvider = new MCP23S17GpioProvider(address, BreezyToPi4JSPIChannel.getChannel(breezySPIChannel));
			} catch (IOException e) {
				LOG.debug("Cannot create MCP23S17GpioProvider, IO Exception thrown: " + e.getMessage());
				
				throw new EmbeddedControllerException("Cannot create MCP23S17GpioProvider, IO Exception thrown", e);
			}
		}

//		LOG.debug("End Initializing MCP23S17ExtensionProviderImpl");
	}

	private void validateProperties() {
		if(!properties.containsKey(MCP23S17Property.CHANNEL.name())) {
			throw new EmbeddedControllerException("MCP23S17 extension channel number was not provided");
		}
		
		if(!properties.containsKey(MCP23017Property.ADDRESS.name())) {
			throw new EmbeddedControllerException("MCP23S17 extension address was not provided");
		}
	}

	private boolean isWindowsEnvironment() {
		return windowsEnvironment;
	}

}
