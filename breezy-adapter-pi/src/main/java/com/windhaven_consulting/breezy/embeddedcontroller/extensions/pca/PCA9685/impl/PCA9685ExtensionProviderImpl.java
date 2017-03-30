package com.windhaven_consulting.breezy.embeddedcontroller.extensions.pca.PCA9685.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPinProperty;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.pca.PCA9685.BreezyToPCA9685Pin;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.pca.PCA9685.PCA9685Pin;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JPCA9685PWMOutputPinProxyImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JPinProxyImpl;

public class PCA9685ExtensionProviderImpl implements ExtensionProvider<PWMOutputPin> {
	static final Logger LOG = LoggerFactory.getLogger(PCA9685ExtensionProviderImpl.class);
	
	private GpioController gpioController;
	private PCA9685GpioProvider pca9685GpioProvider;

	public PCA9685ExtensionProviderImpl(GpioController gpioController, GpioProvider gpioProvider, GpioPinListenerDigital gpioPinListenerDigital) {
		this.gpioController = gpioController;
	}

	@Override
	public DigitalInputPin provisionDigitalInputPin(String name, String pinName, UUID id, PinPullResistance pinPullResistance, Integer debounce, boolean isEventTrigger) {
		throw new UnsupportedOperationException("The PCA9685 PWM I/O Extender does not have inputs to be configured.");
	}

	@Override
	public PWMOutputPin provisionOutputPin(String name, String pinName, UUID pinId) {
		BreezyPin breezyPin = PCA9685Pin.getByName(pinName);
		com.pi4j.io.gpio.Pin pi4JPin = BreezyToPCA9685Pin.getPin(breezyPin);
		GpioPinPwmOutput gpioPin = gpioController.provisionPwmOutputPin(pca9685GpioProvider, pi4JPin);
		
		gpioPin.setProperty(BreezyPinProperty.NAME.name(), name);
		gpioPin.setProperty(BreezyPinProperty.ID.name(), pinId.toString());
		
		return new Pi4JPCA9685PWMOutputPinProxyImpl(name, pinId, gpioPin, pi4JPin, pca9685GpioProvider);
	}

	@Override
	public void unprovisionPin(BreezyPin breezyPin) {
		Pi4JPinProxyImpl pi4jPinProxyImpl = (Pi4JPinProxyImpl) breezyPin;
		gpioController.unprovisionPin(pi4jPinProxyImpl.getGpioPin());
	}

}
