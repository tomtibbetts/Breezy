package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.UUID;

import com.pi4j.io.gpio.GpioPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;

public class Pi4JPWMOutputPinProxyImpl extends Pi4JPinProxyImpl  implements PWMOutputPin {

	public Pi4JPWMOutputPinProxyImpl(String name, UUID id, GpioPin gpioPin) {
		super(name, id, gpioPin);
	}

}
