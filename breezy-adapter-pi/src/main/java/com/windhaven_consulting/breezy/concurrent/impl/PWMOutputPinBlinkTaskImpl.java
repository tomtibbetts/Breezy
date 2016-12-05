package com.windhaven_consulting.breezy.concurrent.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;
public class PWMOutputPinBlinkTaskImpl implements Runnable {
	static final Logger LOG = LoggerFactory.getLogger(PWMOutputPinBlinkTaskImpl.class);

	private final PWMOutputPin pwmOutputPin;
	
	public PWMOutputPinBlinkTaskImpl(PWMOutputPin pwmOutputPin) {
		this.pwmOutputPin = pwmOutputPin;
	}

	@Override
	public void run() {
		LOG.debug("running toggle");
		pwmOutputPin.toggle();
	}

}
