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
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", pulse: duration = " + duration + ", startState = " + startState);
	}

	@Override
	public void blink(long onTime, long duration) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", blink: onTime = " + onTime + ", duration = " + duration);
	}

	@Override
	public void toggle() {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", toggle:");
	}

	@Override
	public void setLow() {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", setLow:");
	}

	@Override
	public void setHigh() {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", setHigh:");
	}

	@Override
	public void blink(long onTime) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", blink: onTime = " + onTime);
	}

	@Override
	public void blink(long onTime, PinState pinState) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", blink: onTime = " + onTime + ", pinState = " + pinState);
	}

	@Override
	public void blink(long delay, long duration, PinState pinState, boolean blockToCompletion) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", blink: delay = " + delay + ", duration = " + duration + ", pinState = " + pinState + ", blockToCompletion = " + blockToCompletion);
	}

	@Override
	public void pulse(long onTime) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", pulse: onTime = " + onTime);
	}

	@Override
	public void pulse(long onTime, boolean blockToCompletion) {
		LOG.debug("name: " + getName() + ", pulse: onTime = " + onTime + ", blockToCompletion = " + blockToCompletion);
	}

	@Override
	public void pulse(long onTime, PinState pinState, boolean blockToCompletion) {
		LOG.debug("name: " + getName() + ", pulse: onTime = " + onTime + ", pinState = " + pinState + ", blockToCompletion = " + blockToCompletion);
	}

	@Override
	public PinState getState() {
		LOG.debug("getState: ");
		return PinState.LOW;
	}

}
