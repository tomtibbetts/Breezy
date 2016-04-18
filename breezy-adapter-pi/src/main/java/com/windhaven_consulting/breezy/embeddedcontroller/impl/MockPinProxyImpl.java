package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;

public class MockPinProxyImpl implements BreezyPin {

	private String name;
	private UUID id;

	public MockPinProxyImpl(String name, UUID id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public UUID getId() {
		return id;
	}

}
