package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.io.Serializable;
import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;

public class MockDigitalInputPinProxyImpl extends MockPinProxyImpl implements DigitalInputPin, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MockDigitalInputPinProxyImpl(String name, UUID id) {
		super(name, id);
	}

	@Override
	public boolean isHigh() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLow() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isState(PinState state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PinState getState() {
		// TODO Auto-generated method stub
		return PinState.LOW;
	}

}
