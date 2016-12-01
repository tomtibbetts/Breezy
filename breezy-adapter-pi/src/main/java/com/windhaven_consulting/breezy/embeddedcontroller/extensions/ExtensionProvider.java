package com.windhaven_consulting.breezy.embeddedcontroller.extensions;

import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;

public interface ExtensionProvider<T extends BreezyPin> {

	DigitalInputPin provisionDigitalInputPin(String name, String pinName, UUID pinId, PinPullResistance pinPullResistance, Integer debounce, boolean isEventTrigger);
	
	DigitalOutputPin provisionDigitalOutputPin(String name, String pinName, UUID pinId);
	
	T provisionOutputPin(String name, String pinName, UUID pinId);

	void unprovisionPin(BreezyPin breezyPin);

}
