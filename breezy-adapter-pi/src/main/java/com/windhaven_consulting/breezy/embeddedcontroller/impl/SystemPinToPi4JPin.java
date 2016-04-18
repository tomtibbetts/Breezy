package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.HashMap;
import java.util.Map;

import com.pi4j.io.gpio.RaspiPin;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.system.SystemPin;


public class SystemPinToPi4JPin {

	private static Map<BreezyPin, com.pi4j.io.gpio.Pin> breezyPinToPi4JPinMap = new HashMap<BreezyPin, com.pi4j.io.gpio.Pin>();
	
	static {
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_00, RaspiPin.GPIO_00);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_01, RaspiPin.GPIO_01);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_02, RaspiPin.GPIO_02);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_03, RaspiPin.GPIO_03);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_04, RaspiPin.GPIO_04);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_05, RaspiPin.GPIO_05);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_06, RaspiPin.GPIO_06);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_07, RaspiPin.GPIO_07);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_08, RaspiPin.GPIO_08);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_09, RaspiPin.GPIO_09);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_10, RaspiPin.GPIO_10);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_11, RaspiPin.GPIO_11);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_12, RaspiPin.GPIO_12);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_13, RaspiPin.GPIO_13);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_14, RaspiPin.GPIO_14);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_15, RaspiPin.GPIO_15);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_16, RaspiPin.GPIO_16);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_17, RaspiPin.GPIO_17);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_18, RaspiPin.GPIO_18);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_19, RaspiPin.GPIO_19);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_20, RaspiPin.GPIO_20);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_21, RaspiPin.GPIO_21);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_22, RaspiPin.GPIO_22);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_23, RaspiPin.GPIO_23);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_24, RaspiPin.GPIO_24);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_25, RaspiPin.GPIO_25);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_26, RaspiPin.GPIO_26);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_27, RaspiPin.GPIO_27);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_28, RaspiPin.GPIO_28);
		breezyPinToPi4JPinMap.put(SystemPin.GPIO_29, RaspiPin.GPIO_29);
	}
	
//	@PostConstruct
//	public void postConstruct() {
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_00, RaspiPin.GPIO_00);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_01, RaspiPin.GPIO_01);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_02, RaspiPin.GPIO_02);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_03, RaspiPin.GPIO_03);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_04, RaspiPin.GPIO_04);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_05, RaspiPin.GPIO_05);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_06, RaspiPin.GPIO_06);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_07, RaspiPin.GPIO_07);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_08, RaspiPin.GPIO_08);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_09, RaspiPin.GPIO_09);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_10, RaspiPin.GPIO_10);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_11, RaspiPin.GPIO_11);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_12, RaspiPin.GPIO_12);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_13, RaspiPin.GPIO_13);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_14, RaspiPin.GPIO_14);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_15, RaspiPin.GPIO_15);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_16, RaspiPin.GPIO_16);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_17, RaspiPin.GPIO_17);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_18, RaspiPin.GPIO_18);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_19, RaspiPin.GPIO_19);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_20, RaspiPin.GPIO_20);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_21, RaspiPin.GPIO_21);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_22, RaspiPin.GPIO_22);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_23, RaspiPin.GPIO_23);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_24, RaspiPin.GPIO_24);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_25, RaspiPin.GPIO_25);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_26, RaspiPin.GPIO_26);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_27, RaspiPin.GPIO_27);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_28, RaspiPin.GPIO_28);
//		breezyPinToPi4JPinMap.put(SystemPin.GPIO_29, RaspiPin.GPIO_29);
//	}

	public static com.pi4j.io.gpio.Pin getPin(BreezyPin breezyPin) {
		com.pi4j.io.gpio.Pin pin = breezyPinToPi4JPinMap.get(breezyPin);
		
		if(pin == null) {
			throw new EmbeddedControllerException("Breezy Pin, '" + breezyPin.toString() + "' has no Pi4J (Raspberry Pi) equivalent.");
		}
		
		return pin;
	}
	
}
