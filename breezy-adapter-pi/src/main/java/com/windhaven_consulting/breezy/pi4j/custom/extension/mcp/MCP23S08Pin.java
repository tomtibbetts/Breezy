package com.windhaven_consulting.breezy.pi4j.custom.extension.mcp;

import java.util.EnumSet;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.impl.PinImpl;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: GPIO Extension
 * FILENAME      :  MCP23S17Pin.java  
 * 
 * This file is part of the Pi4J project. More information about 
 * this project can be found here:  http://www.pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2015 Pi4J
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

/**
 * <p>
 * This GPIO provider implements the MCP23S08 SPI GPIO expansion board as native Pi4J GPIO pins.
 * More information about the board can be found here: *
 * http://ww1.microchip.com/downloads/en/DeviceDoc/21919e.pdf
 * </p>
 * 
 * <p>
 * The MCP23S08 is connected via SPI connection to the Raspberry Pi and provides
 * 8 GPIO pins that can be used for either digital input or digital output pins.
 * </p>
 * 
 * @author Thomas Tibbetts
 * 
 */
public class MCP23S08Pin {
    
    public static final Pin GPIO_0 = createDigitalPin(1, "GPIO 0");
    public static final Pin GPIO_1 = createDigitalPin(2, "GPIO 1");
    public static final Pin GPIO_2 = createDigitalPin(4, "GPIO 2");
    public static final Pin GPIO_3 = createDigitalPin(8, "GPIO 3");
    public static final Pin GPIO_4 = createDigitalPin(16, "GPIO 4");
    public static final Pin GPIO_5 = createDigitalPin(32, "GPIO 5");
    public static final Pin GPIO_6 = createDigitalPin(64, "GPIO 6");
    public static final Pin GPIO_7 = createDigitalPin(128, "GPIO 7");
    
    public static Pin[] ALL = { MCP23S08Pin.GPIO_0, MCP23S08Pin.GPIO_1, MCP23S08Pin.GPIO_2, MCP23S08Pin.GPIO_3,
                                MCP23S08Pin.GPIO_4, MCP23S08Pin.GPIO_5, MCP23S08Pin.GPIO_6, MCP23S08Pin.GPIO_7 };
    
    private static Pin createDigitalPin(int address, String name) {
        return new PinImpl(MCP23S08GpioProvider.NAME, address, name, 
                    EnumSet.of(PinMode.DIGITAL_INPUT, PinMode.DIGITAL_OUTPUT),
                    EnumSet.of(PinPullResistance.PULL_UP, PinPullResistance.OFF));    
    }
}
