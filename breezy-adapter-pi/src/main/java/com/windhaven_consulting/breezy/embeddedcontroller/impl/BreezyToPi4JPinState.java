package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.HashMap;
import java.util.Map;

import com.windhaven_consulting.breezy.embeddedcontroller.PinState;

public class BreezyToPi4JPinState {

	private static Map<PinState, com.pi4j.io.gpio.PinState> breezyToPi4JPinStateMap = new HashMap<PinState, com.pi4j.io.gpio.PinState>();
	
	static {
		breezyToPi4JPinStateMap.put(PinState.HIGH, com.pi4j.io.gpio.PinState.HIGH);
		breezyToPi4JPinStateMap.put(PinState.LOW, com.pi4j.io.gpio.PinState.LOW);
	}
	
	public static com.pi4j.io.gpio.PinState getPinState(PinState pinState) {
		return breezyToPi4JPinStateMap.get(pinState);
	}
}
