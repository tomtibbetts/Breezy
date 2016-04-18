package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.io.Serializable;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;

public class MockDigitalOutputPinProxyImpl extends MockPinProxyImpl implements DigitalOutputPin, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final Logger LOG = LoggerFactory.getLogger(MockDigitalOutputPinProxyImpl.class);

	public MockDigitalOutputPinProxyImpl(String name, UUID id) {
		super(name, id);
	}

	@Override
	public void pulse(long duration, PinState startState) {
		LOG.debug("pulse: duration = " + duration + ", startState = " + startState);
	}

	@Override
	public void blink(long onTime, long duration) {
		LOG.debug("blink: onTime = " + onTime + ", duration = " + duration);
	}

	@Override
	public void toggle() {
		LOG.debug("toggle:");
	}

	@Override
	public void setLow() {
		LOG.debug("setLow:");
	}

	@Override
	public void setHigh() {
		LOG.debug("setHigh:");
	}

	@Override
	public void blink(long onTime) {
		LOG.debug("blink: onTime = " + onTime);
	}

	@Override
	public void blink(long onTime, PinState pinState) {
		LOG.debug("blink: onTime = " + onTime + ", pinState = " + pinState);
	}

	@Override
	public void blink(long delay, long duration, PinState pinState, boolean blockToCompletion) {
		LOG.debug("blink: delay = " + delay + ", duration = " + duration + ", pinState = " + pinState + ", blockToCompletion = " + blockToCompletion);
	}

	@Override
	public void pulse(long onTime) {
		LOG.debug("pulse: onTime = " + onTime);
	}

	@Override
	public void pulse(long onTime, boolean blockToCompletion) {
		LOG.debug("pulse: onTime = " + onTime + ", blockToCompletion = " + blockToCompletion);
	}

	@Override
	public void pulse(long onTime, PinState pinState, boolean blockToCompletion) {
		LOG.debug("pulse: onTime = " + onTime + ", pinState = " + pinState + ", blockToCompletion = " + blockToCompletion);
	}

}
