package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.HashMap;
import java.util.Map;

import com.pi4j.io.i2c.I2CBus;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyI2CBus;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerRuntimeException;

public class BreezyToPi4JI2CBus {

	private static Map<BreezyI2CBus, Integer> breezyToPi4JI2CBusMap = new HashMap<BreezyI2CBus, Integer>();

	static {
		breezyToPi4JI2CBusMap.put(BreezyI2CBus.BUS_0, I2CBus.BUS_0);
		breezyToPi4JI2CBusMap.put(BreezyI2CBus.BUS_1, I2CBus.BUS_1);
	}
	
	public static Integer getBusAsInteger(BreezyI2CBus breezyI2CBus) {
		Integer result = breezyToPi4JI2CBusMap.get(breezyI2CBus);
		
		if(result == null) {
			throw new EmbeddedControllerRuntimeException("Breezy I2C Bus, '" + breezyI2CBus.toString() + "' has no Pi4J (Raspberry Pi) equivalent.");
		}
		
		return result;
	}
}
