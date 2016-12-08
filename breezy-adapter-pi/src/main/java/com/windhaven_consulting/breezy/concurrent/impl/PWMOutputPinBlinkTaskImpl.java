package com.windhaven_consulting.breezy.concurrent.impl;

import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;
public class PWMOutputPinBlinkTaskImpl implements Runnable {
	private final PWMOutputPin pwmOutputPin;
	
	public PWMOutputPinBlinkTaskImpl(PWMOutputPin pwmOutputPin) {
		this.pwmOutputPin = pwmOutputPin;
	}

	@Override
	public void run() {
		pwmOutputPin.toggle();
	}

}
