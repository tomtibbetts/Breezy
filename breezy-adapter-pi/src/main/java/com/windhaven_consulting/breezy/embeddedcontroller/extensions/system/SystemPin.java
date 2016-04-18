package com.windhaven_consulting.breezy.embeddedcontroller.extensions.system;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;

public class SystemPin implements BreezyPin {
	private static final Map<String, BreezyPin> pinMap = new LinkedHashMap<String, BreezyPin>();

	private String name;

	private UUID id;

	public static final BreezyPin GPIO_00 = createPin("GPIO_00");
	public static final BreezyPin GPIO_01 = createPin("GPIO_01");
	public static final BreezyPin GPIO_02 = createPin("GPIO_02");
	public static final BreezyPin GPIO_03 = createPin("GPIO_03");
	public static final BreezyPin GPIO_04 = createPin("GPIO_04");
	public static final BreezyPin GPIO_05 = createPin("GPIO_05");
	public static final BreezyPin GPIO_06 = createPin("GPIO_06");
	public static final BreezyPin GPIO_07 = createPin("GPIO_07");
	public static final BreezyPin GPIO_08 = createPin("GPIO_08");
	public static final BreezyPin GPIO_09 = createPin("GPIO_09");
	public static final BreezyPin GPIO_10 = createPin("GPIO_10");
	public static final BreezyPin GPIO_11 = createPin("GPIO_11");
	public static final BreezyPin GPIO_12 = createPin("GPIO_12");
	public static final BreezyPin GPIO_13 = createPin("GPIO_13");
	public static final BreezyPin GPIO_14 = createPin("GPIO_14");
	public static final BreezyPin GPIO_15 = createPin("GPIO_15");
	public static final BreezyPin GPIO_16 = createPin("GPIO_16");
	public static final BreezyPin GPIO_17 = createPin("GPIO_17");
	public static final BreezyPin GPIO_18 = createPin("GPIO_18");
	public static final BreezyPin GPIO_19 = createPin("GPIO_19");
	public static final BreezyPin GPIO_20 = createPin("GPIO_20");
	public static final BreezyPin GPIO_21 = createPin("GPIO_21");
	public static final BreezyPin GPIO_22 = createPin("GPIO_22");
	public static final BreezyPin GPIO_23 = createPin("GPIO_23");
	public static final BreezyPin GPIO_24 = createPin("GPIO_24");
	public static final BreezyPin GPIO_25 = createPin("GPIO_25");
	public static final BreezyPin GPIO_26 = createPin("GPIO_26");
	public static final BreezyPin GPIO_27 = createPin("GPIO_27");
	public static final BreezyPin GPIO_28 = createPin("GPIO_28");
	public static final BreezyPin GPIO_29 = createPin("GPIO_29");

	public static List<BreezyPin> getPins() {
		return new ArrayList<BreezyPin>(pinMap.values());
	}

	public static BreezyPin getByName(String name) {
		return pinMap.get(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public UUID getId() {
		return id;
	}
	
	private SystemPin(String name) {
		this.name = name;
	}

	private static BreezyPin createPin(String pinName) {
		BreezyPin breezyPin = new SystemPin(pinName);
		pinMap.put(pinName, breezyPin);
		
		return breezyPin;
	}
	
}
