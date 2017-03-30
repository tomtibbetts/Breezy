package com.windhaven_consulting.breezy.embeddedcontroller.extensions.mock;

import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JDigitalInputPinProxyImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JDigitalOutputPinProxyImpl;

public class MockDigitalOutputExtensionProviderImpl implements ExtensionProvider<BreezyPin> {

	@Override
	public DigitalInputPin provisionDigitalInputPin(String name, String pinName, UUID pinId, PinPullResistance pinPullResistance, Integer debounce, boolean isEventTrigger) {
		return new Pi4JDigitalInputPinProxyImpl(name, pinId, null);
	}


	@Override
	public void unprovisionPin(BreezyPin breezyPin) {
		// Do nothing
	}


	@Override
	public DigitalOutputPin provisionOutputPin(String name, String pinName, UUID pinId) {
		// TODO Auto-generated method stub
		return new Pi4JDigitalOutputPinProxyImpl(name, pinId, null);
	}

}
