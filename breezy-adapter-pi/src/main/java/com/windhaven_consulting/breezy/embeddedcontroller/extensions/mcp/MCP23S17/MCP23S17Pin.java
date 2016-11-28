package com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23S17;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;

public class MCP23S17Pin implements BreezyPin {

	private final static Map<String, BreezyPin> pinMap = new LinkedHashMap<String, BreezyPin>();

	private String name;

	private UUID id;

	public static final BreezyPin GPIO_A0 = createPin("GPIO_A0");
	public static final BreezyPin GPIO_A1 = createPin("GPIO_A1");
	public static final BreezyPin GPIO_A2 = createPin("GPIO_A2");
	public static final BreezyPin GPIO_A3 = createPin("GPIO_A3");
	public static final BreezyPin GPIO_A4 = createPin("GPIO_A4");
	public static final BreezyPin GPIO_A5 = createPin("GPIO_A5");
	public static final BreezyPin GPIO_A6 = createPin("GPIO_A6");
	public static final BreezyPin GPIO_A7 = createPin("GPIO_A7");
	public static final BreezyPin GPIO_B0 = createPin("GPIO_B0");
	public static final BreezyPin GPIO_B1 = createPin("GPIO_B1");
	public static final BreezyPin GPIO_B2 = createPin("GPIO_B2");
	public static final BreezyPin GPIO_B3 = createPin("GPIO_B3");
	public static final BreezyPin GPIO_B4 = createPin("GPIO_B4");
	public static final BreezyPin GPIO_B5 = createPin("GPIO_B5");
	public static final BreezyPin GPIO_B6 = createPin("GPIO_B6");
	public static final BreezyPin GPIO_B7 = createPin("GPIO_B7");
	
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

	private MCP23S17Pin(String pinName) {
		this.name = pinName;
	}

	private static BreezyPin createPin(String pinName) {
		BreezyPin breezyPin = new MCP23S17Pin(pinName);
		pinMap.put(pinName, breezyPin);
		
		return breezyPin;
	}

}
