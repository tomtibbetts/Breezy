package com.windhaven_consulting.breezy.embeddedcontroller.extensions.pca.PCA9685.impl;

import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;

public class PCA9685ExtensionProviderImpl implements ExtensionProvider {

	@Override
	public DigitalInputPin provisionDigitalInputPin(String name, String pinName, UUID id, PinPullResistance pinPullResistance, Integer debounce,
			boolean isEventTrigger) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DigitalOutputPin provisionDigitalOutputPin(String name, String pinName, UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unprovisionPin(BreezyPin breezyPin) {
		// TODO Auto-generated method stub

	}

}
