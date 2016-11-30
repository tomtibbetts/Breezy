package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;

public class MockPWMOutputPinProxyImpl extends MockPinProxyImpl implements PWMOutputPin {

	public MockPWMOutputPinProxyImpl(String name, UUID id) {
		super(name, id);
	}

}
