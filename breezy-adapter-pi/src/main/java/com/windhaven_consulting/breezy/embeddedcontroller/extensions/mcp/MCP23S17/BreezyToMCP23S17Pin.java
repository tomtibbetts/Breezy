package com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23S17;

import java.util.HashMap;
import java.util.Map;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerRuntimeException;

public class BreezyToMCP23S17Pin {
	private static Map<BreezyPin, com.pi4j.io.gpio.Pin> breezyToMCP23S17Pin = new HashMap<BreezyPin, com.pi4j.io.gpio.Pin>();
	
	static {
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_A0, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_A0);
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_A1, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_A1);
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_A2, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_A2);
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_A3, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_A3);
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_A4, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_A4);
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_A5, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_A5);
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_A6, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_A6);
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_A7, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_A7);
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_B0, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_B0);
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_B1, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_B1);
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_B2, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_B2);
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_B3, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_B3);
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_B4, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_B4);
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_B5, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_B5);
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_B6, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_B6);
		breezyToMCP23S17Pin.put(MCP23S17Pin.GPIO_B7, com.pi4j.gpio.extension.mcp.MCP23S17Pin.GPIO_B7);
	}
	
	public static com.pi4j.io.gpio.Pin getPin(BreezyPin breezyPin) {
		com.pi4j.io.gpio.Pin pin = breezyToMCP23S17Pin.get(breezyPin);
		
		if(pin == null) {
			throw new EmbeddedControllerRuntimeException("Breezy Pin, '" + breezyPin.toString() + "' has no Pi4J (Raspberry Pi) equivalent.");
		}
		
		return pin;
	}

}
