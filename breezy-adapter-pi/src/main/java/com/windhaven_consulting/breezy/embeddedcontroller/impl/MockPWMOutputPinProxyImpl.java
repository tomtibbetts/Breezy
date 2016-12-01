package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;

public class MockPWMOutputPinProxyImpl extends MockPinProxyImpl implements PWMOutputPin {

	static final Logger LOG = LoggerFactory.getLogger(MockPWMOutputPinProxyImpl.class);

	public MockPWMOutputPinProxyImpl(String name, UUID id) {
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
	public void setPwm(int duration) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", setPwm: duration = " + duration);
	}

	@Override
	public void setPwm(int onPosition, int offPosition) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", setPwm: onPosition = " + onPosition + ", offPosition = " + offPosition);
	}

}
