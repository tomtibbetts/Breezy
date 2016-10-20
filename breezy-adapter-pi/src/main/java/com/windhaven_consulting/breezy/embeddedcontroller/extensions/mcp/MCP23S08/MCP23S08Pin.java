package com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23S08;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;

public class MCP23S08Pin implements BreezyPin {

	private final static Map<String, BreezyPin> pinMap = new LinkedHashMap<String, BreezyPin>();

	private String name;

	private UUID id;

	public static final BreezyPin GPIO_0 = createPin("GPIO_0");
	public static final BreezyPin GPIO_1 = createPin("GPIO_1");
	public static final BreezyPin GPIO_2 = createPin("GPIO_2");
	public static final BreezyPin GPIO_3 = createPin("GPIO_3");
	public static final BreezyPin GPIO_4 = createPin("GPIO_4");
	public static final BreezyPin GPIO_5 = createPin("GPIO_5");
	public static final BreezyPin GPIO_6 = createPin("GPIO_6");
	public static final BreezyPin GPIO_7 = createPin("GPIO_7");
	
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

	private MCP23S08Pin(String pinName) {
		this.name = pinName;
	}

	private static BreezyPin createPin(String pinName) {
		BreezyPin breezyPin = new MCP23S08Pin(pinName);
		pinMap.put(pinName, breezyPin);
		
		return breezyPin;
	}

}
