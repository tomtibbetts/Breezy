package com.windhaven_consulting.breezy.embeddedcontroller.extensions.system.impl;

import java.util.Map;
import java.util.UUID;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPinProperty;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.system.SystemPin;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.BreezyToPi4JPinPullResistance;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.MockDigitalInputPinProxyImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.MockDigitalOutputPinProxyImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JDigitalInputPinProxyImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JDigitalOutputPinProxyImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JPinProxyImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.SystemPinToPi4JPin;

public class SystemExtensionProviderImpl implements ExtensionProvider<DigitalOutputPin> {

	private boolean windowsEnvironment;
	private GpioController gpioController;
	private GpioPinListenerDigital gpioPinListenerDigital;

	public SystemExtensionProviderImpl(GpioController gpioController, GpioPinListenerDigital gpioPinListenerDigital, Map<String, String> properties, boolean windowsEnvironment) {
		this.gpioController = gpioController;
		this.gpioPinListenerDigital = gpioPinListenerDigital;
		this.windowsEnvironment = windowsEnvironment;
	}
	
	@Override
	public DigitalInputPin provisionDigitalInputPin(String name, String pinName, UUID pinId, PinPullResistance pinPullResistance, Integer debounce, boolean isEventTrigger) {
		if(isWindowsEnvironment()) {
			return new MockDigitalInputPinProxyImpl(name, pinId);
		}
		else {
			BreezyPin breezyPin = SystemPin.getByName(pinName);
			com.pi4j.io.gpio.Pin pi4JPin = SystemPinToPi4JPin.getPin(breezyPin);
			com.pi4j.io.gpio.PinPullResistance pi4JPinPullResistance = BreezyToPi4JPinPullResistance.getPinPullResistance(pinPullResistance);
			
			GpioPinDigitalInput gpioPin = gpioController.provisionDigitalInputPin(pi4JPin, pi4JPinPullResistance);
			
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
	public DigitalOutputPin provisionOutputPin(String name, String pinName, UUID pinId) {
		if(isWindowsEnvironment()) {
			return new MockDigitalOutputPinProxyImpl(name, pinId);
		}
		else {
			BreezyPin breezyPin = SystemPin.getByName(pinName);
			com.pi4j.io.gpio.Pin pi4JPin = SystemPinToPi4JPin.getPin(breezyPin);

			GpioPin gpioPin = gpioController.provisionDigitalOutputPin(pi4JPin, PinState.LOW);
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

	private boolean isWindowsEnvironment() {
		return windowsEnvironment;
	}

}
