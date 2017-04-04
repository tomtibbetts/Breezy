package com.windhaven_consulting.breezy.embeddedcontroller.extensions;

import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;

public interface ExtensionProvider<O extends BreezyPin> {

	DigitalInputPin provisionDigitalInputPin(String name, String pinName, UUID pinId, PinPullResistance pinPullResistance, Integer debounce, boolean isEventTrigger);
	
	O provisionOutputPin(String name, String pinName, UUID pinId);

	void unprovisionPin(BreezyPin breezyPin);

}
