package com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23017;

import java.util.HashMap;
import java.util.Map;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;

public class BreezyToMCP23017Pin {
	private static Map<BreezyPin, com.pi4j.io.gpio.Pin> breezyToMCP23017Pin = new HashMap<BreezyPin, com.pi4j.io.gpio.Pin>();
	
	static {
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_A0, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_A0);
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_A1, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_A1);
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_A2, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_A2);
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_A3, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_A3);
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_A4, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_A4);
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_A5, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_A5);
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_A6, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_A6);
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_A7, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_A7);
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_B0, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_B0);
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_B1, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_B1);
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_B2, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_B2);
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_B3, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_B3);
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_B4, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_B4);
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_B5, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_B5);
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_B6, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_B6);
		breezyToMCP23017Pin.put(MCP23017Pin.GPIO_B7, com.pi4j.gpio.extension.mcp.MCP23017Pin.GPIO_B7);
	}
	
	public static com.pi4j.io.gpio.Pin getPin(BreezyPin breezyPin) {
		com.pi4j.io.gpio.Pin pin = breezyToMCP23017Pin.get(breezyPin);
		
		if(pin == null) {
			throw new EmbeddedControllerException("Breezy Pin, '" + breezyPin.toString() + "' has no Pi4J (Raspberry Pi) equivalent.");
		}
		
		return pin;
	}

}
