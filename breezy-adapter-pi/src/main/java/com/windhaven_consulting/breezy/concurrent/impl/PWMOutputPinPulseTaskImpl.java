package com.windhaven_consulting.breezy.concurrent.impl;

import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMPinState;

public class PWMOutputPinPulseTaskImpl implements Runnable {

	private PWMOutputPin pwmOutputPin;
	private PWMPinState inactiveState;

	public PWMOutputPinPulseTaskImpl(PWMOutputPin pin, PWMPinState inactiveState) {
		this.pwmOutputPin = pin;
		this.inactiveState = inactiveState;
	}

	@Override
	public void run() {
		pwmOutputPin.setState(inactiveState);
	}

}
