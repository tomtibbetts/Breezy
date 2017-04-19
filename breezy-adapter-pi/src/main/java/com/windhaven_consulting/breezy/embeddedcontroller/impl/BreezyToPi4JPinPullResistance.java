package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.HashMap;
import java.util.Map;

import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerRuntimeException;

public class BreezyToPi4JPinPullResistance {

	private static Map<PinPullResistance, com.pi4j.io.gpio.PinPullResistance> breezyToPi4JPinPullResistanceMap = new HashMap<PinPullResistance, com.pi4j.io.gpio.PinPullResistance>();
	
	static {
		breezyToPi4JPinPullResistanceMap.put(PinPullResistance.NONE, com.pi4j.io.gpio.PinPullResistance.OFF);
		breezyToPi4JPinPullResistanceMap.put(PinPullResistance.PULL_DOWN, com.pi4j.io.gpio.PinPullResistance.PULL_DOWN);
		breezyToPi4JPinPullResistanceMap.put(PinPullResistance.PULL_UP, com.pi4j.io.gpio.PinPullResistance.PULL_UP);
	}
	
	public static com.pi4j.io.gpio.PinPullResistance getPinPullResistance(PinPullResistance pinPullResistance) {
		com.pi4j.io.gpio.PinPullResistance result = breezyToPi4JPinPullResistanceMap.get(pinPullResistance);
		
		if(result == null) {
			throw new EmbeddedControllerRuntimeException("Breezy PinPullResistance, '" + pinPullResistance.toString() + "' has no Pi4J (Raspberry Pi) equivalent.");
		}
		
		return result;
	}
}
