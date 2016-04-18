package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.UUID;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;

public class Pi4JDigitalInputPinProxyImpl extends Pi4JPinProxyImpl implements DigitalInputPin {

	public Pi4JDigitalInputPinProxyImpl(String name, UUID id, GpioPin gpioPin) {
		super(name, id, gpioPin);
	}

	@Override
	public PinState getState() {
		GpioPinDigitalInput gpioPinDigitalInput = (GpioPinDigitalInput) getGpioPin();
		
		PinState pinState = PinState.find(gpioPinDigitalInput.getState().getValue());
		
		return pinState;
	}

	@Override
	public boolean isHigh() {
		return PinState.HIGH == getState();
	}

	@Override
	public boolean isLow() {
		return PinState.LOW == getState();
	}

	@Override
	public boolean isState(PinState state) {
		return getState() == state;
	}

}
