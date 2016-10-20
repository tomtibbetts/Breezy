package com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23S08;

import java.util.HashMap;
import java.util.Map;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;

public class BreezyToMCP23S08Pin {
	private static Map<BreezyPin, com.pi4j.io.gpio.Pin> breezyToMCP23S08Pin = new HashMap<BreezyPin, com.pi4j.io.gpio.Pin>();
	
	static {
		breezyToMCP23S08Pin.put(MCP23S08Pin.GPIO_0, com.windhaven_consulting.breezy.pi4j.custom.extension.mcp.MCP23S08Pin.GPIO_0);
		breezyToMCP23S08Pin.put(MCP23S08Pin.GPIO_1, com.windhaven_consulting.breezy.pi4j.custom.extension.mcp.MCP23S08Pin.GPIO_1);
		breezyToMCP23S08Pin.put(MCP23S08Pin.GPIO_2, com.windhaven_consulting.breezy.pi4j.custom.extension.mcp.MCP23S08Pin.GPIO_2);
		breezyToMCP23S08Pin.put(MCP23S08Pin.GPIO_3, com.windhaven_consulting.breezy.pi4j.custom.extension.mcp.MCP23S08Pin.GPIO_3);
		breezyToMCP23S08Pin.put(MCP23S08Pin.GPIO_4, com.windhaven_consulting.breezy.pi4j.custom.extension.mcp.MCP23S08Pin.GPIO_4);
		breezyToMCP23S08Pin.put(MCP23S08Pin.GPIO_5, com.windhaven_consulting.breezy.pi4j.custom.extension.mcp.MCP23S08Pin.GPIO_5);
		breezyToMCP23S08Pin.put(MCP23S08Pin.GPIO_6, com.windhaven_consulting.breezy.pi4j.custom.extension.mcp.MCP23S08Pin.GPIO_6);
		breezyToMCP23S08Pin.put(MCP23S08Pin.GPIO_7, com.windhaven_consulting.breezy.pi4j.custom.extension.mcp.MCP23S08Pin.GPIO_7);
	}
	
	public static com.pi4j.io.gpio.Pin getPin(BreezyPin breezyPin) {
		com.pi4j.io.gpio.Pin pin = breezyToMCP23S08Pin.get(breezyPin);
		
		if(pin == null) {
			throw new EmbeddedControllerException("Breezy Pin, '" + breezyPin.toString() + "' has no Pi4J (Raspberry Pi) equivalent.");
		}
		
		return pin;
	}

}
