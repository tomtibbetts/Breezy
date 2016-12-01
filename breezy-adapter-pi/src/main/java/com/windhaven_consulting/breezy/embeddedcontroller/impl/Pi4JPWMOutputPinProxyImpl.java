package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.UUID;

import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.Pin;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;

public class Pi4JPWMOutputPinProxyImpl extends Pi4JPinProxyImpl  implements PWMOutputPin {

	private Pin pin;
	private PCA9685GpioProvider pca9685GpioProvider;
	
	public Pi4JPWMOutputPinProxyImpl(String name, UUID id, GpioPin gpioPin, Pin pin, PCA9685GpioProvider pca9685GpioProvider) {
		super(name, id, gpioPin);

		this.pin = pin;
		this.pca9685GpioProvider = pca9685GpioProvider;
	}

	@Override
	public void setAlwaysOn() {
		pca9685GpioProvider.setAlwaysOn(pin);
	}

	@Override
	public void setAlwaysOff() {
		pca9685GpioProvider.setAlwaysOff(pin);
	}

	@Override
	public void setPwm(int duration) {
		pca9685GpioProvider.setPwm(pin, duration);
	}

	@Override
	public void setPwm(int onPosition, int offPosition) {
		pca9685GpioProvider.setPwm(pin, onPosition, offPosition);
	}

}
