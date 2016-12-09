package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.UUID;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMPinState;

public class MockPCA9685PWMOutputPinProxyImpl extends MockPinProxyImpl implements PWMOutputPin {

	static final Logger LOG = LoggerFactory.getLogger(MockPCA9685PWMOutputPinProxyImpl.class);

	public MockPCA9685PWMOutputPinProxyImpl(String name, UUID id) {
		super(name, id);
	}

	@Override
	public void setAlwaysOn() {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", setAlwaysOn");
	}

	@Override
	public void setAlwaysOff() {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", setAlwaysOff");
	}

	@Override
	public void setBrightness(int duration) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", setBrightness: duration = " + duration);
	}

	@Override
	public void setPwm(int onPosition, int offPosition) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", setPwm: onPosition = " + onPosition + ", offPosition = " + offPosition);
	}

	@Override
	public void high() {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", high");
	}

	@Override
	public void low() {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", low");
	}

	@Override
	public void toggle() {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", toggle");
	}

	@Override
	public Future<?> blink(long delay) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", blink: delay = " + delay);
		return null;
	}

	@Override
	public Future<?> blink(long delay, long duration) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", blink: delay = " + delay + ", duration = " + duration);
		return null;
	}

	@Override
	public void setState(PWMPinState pwmPinstate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Future<?> blink(long delay, PWMPinState blinkState) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", blink: blinkState = " + blinkState);
		return null;
	}

	@Override
	public Future<?> blink(long delay, long duration, PWMPinState blinkState) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", blink: delay = " + delay + ", duration = " + duration + ", pwmPinState = " + blinkState);
		return null;
	}

	@Override
	public void blink(long delay, long duration, PWMPinState pwmPinState, boolean blockToCompletion) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", blink: delay = " + delay + ", duration = " + duration + ", pwmPinState = " + pwmPinState + ", blockToCompletion = " + blockToCompletion);
	}

	@Override
	public Future<?> pulse(long duration) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", pulse: duration = " + duration);
		return null;
	}

	@Override
	public Future<?> pulse(long duration, boolean blocking) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", pulse: duration = " + duration + ", blocking = " + blocking);
		return null;
	}

	@Override
	public Future<?> pulse(long duration, PWMPinState pulseState) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", pulse: duration = " + duration + ", pwmPinState = " + pulseState);
		return null;
	}

	@Override
	public Future<?> pulse(long duration, PWMPinState pulseState, boolean blocking) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", pulse: duration = " + duration + ", pulseState = " + pulseState + ", blocking = " + blocking);
		return null;
	}

	@Override
	public Future<?> pulsate(long attack, long sustain, long release, long interval, int maxBrightness) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", pulsate: attack = " + attack + ", sustain = " + sustain + ", release = " + release + ", interval = " + interval + ", maxBrightness = " + maxBrightness);
		
		return null;
	}

	@Override
	public void setPwm(int brightness) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", setPwm: brightness = " + brightness);
	}

	@Override
	public void stop() {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", stop");
	}

	@Override
	public void dimTo(long attack, int brightness, boolean blockToCompletion) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", pulsate: attack = " + attack + ", brightness = " + brightness + ", blockToCompletion = " + blockToCompletion);
	}

	@Override
	public int getBrightness() {
		// TODO Auto-generated method stub
		return 0;
	}

}
