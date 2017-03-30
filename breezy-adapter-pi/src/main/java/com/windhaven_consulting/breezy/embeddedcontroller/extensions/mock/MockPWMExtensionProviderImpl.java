package com.windhaven_consulting.breezy.embeddedcontroller.extensions.mock;

import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JPCA9685PWMOutputPinProxyImpl;

public class MockPWMExtensionProviderImpl implements ExtensionProvider<BreezyPin> {

	@Override
	public DigitalInputPin provisionDigitalInputPin(String name, String pinName, UUID pinId, PinPullResistance pinPullResistance, Integer debounce, boolean isEventTrigger) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BreezyPin provisionOutputPin(String name, String pinName, UUID pinId) {
		return new Pi4JPCA9685PWMOutputPinProxyImpl(name, pinId, null, null, null);
	}

	@Override
	public void unprovisionPin(BreezyPin breezyPin) {
		// TODO Auto-generated method stub

	}

}
