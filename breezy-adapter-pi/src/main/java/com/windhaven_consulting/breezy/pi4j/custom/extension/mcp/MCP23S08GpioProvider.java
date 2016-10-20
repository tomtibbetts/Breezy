package com.windhaven_consulting.breezy.pi4j.custom.extension.mcp;

import java.io.IOException;

import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.GpioProviderBase;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.PinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.PinListener;
import com.pi4j.io.gpio.exception.InvalidPinException;
import com.pi4j.io.gpio.exception.UnsupportedPinPullResistanceException;
import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: GPIO Extension
 * FILENAME      :  MCP23S17GpioProvider.java  
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
 * The MCP23S08 is connected via SPI connection to the Raspberry Pi and provides 16 GPIO pins that
 * can be used for either digital input or digital output pins.
 * </p>
 * 
 * @author Thomas Tibbetts
 * 
 */
public class MCP23S08GpioProvider extends GpioProviderBase implements GpioProvider {

    public static final String NAME = "com.windhaven_consulting.breezy.pi4j.custom.extension.mcp.MCP23S08GpioProvider";
    public static final String DESCRIPTION = "MCP23S08 GPIO Provider";

    public static final byte ADDRESS_0 = 0b01000000; // 0x40 [0100 0000]
    public static final byte ADDRESS_1 = 0b01000010; // 0x42 [0100 0010]
    public static final byte ADDRESS_2 = 0b01000100; // 0x44 [0100 0100]
    public static final byte ADDRESS_3 = 0b01000110; // 0x46 [0100 0110]
    public static final byte DEFAULT_ADDRESS = ADDRESS_0;

    private static final byte REGISTER_IODIR = 0x00;
//    private static final byte REGISTER_IPOL = 0x01;
    private static final byte REGISTER_GPINTEN = 0x02;
    private static final byte REGISTER_DEFVAL = 0x03;
    private static final byte REGISTER_INTCON = 0x04;
    private static final byte REGISTER_IOCON = 0x05;
    private static final byte REGISTER_GPPU = 0x06;
    private static final byte REGISTER_INTF = 0x07;
    private static final byte REGISTER_INTCAP = 0x08;
    private static final byte REGISTER_GPIO = 0x09;
//    private static final byte REGISTER_OLAT = 0x0a;

//    private static final byte IOCON_UNUSED    = (byte)0x01;
//    private static final byte IOCON_INTPOL    = (byte)0x02;
//    private static final byte IOCON_ODR       = (byte)0x04;
    private static final byte IOCON_HAEN      = (byte)0x08;
//    private static final byte IOCON_DISSLW    = (byte)0x10;
    private static final byte IOCON_SEQOP     = (byte)0x20;
//    private static final byte IOCON_MIRROR    = (byte)0x40;
//    private static final byte IOCON_BANK_MODE = (byte)0x80;

    private int currentStates = 0;
    private int currentDirection = 0;
    private int currentPullup = 0;
    private byte address = DEFAULT_ADDRESS;

    private GpioStateMonitor monitor = null;
    private final SpiDevice spi;
    
    public static final int SPI_SPEED = 1000000;    
    public static final byte WRITE_FLAG = 0b00000000;    // 0x00
    public static final byte READ_FLAG  = 0b00000001;    // 0x01
    
    public MCP23S08GpioProvider(byte spiAddress, int spiChannel) throws IOException {
        this(spiAddress, spiChannel, SPI_SPEED);
    }

    public MCP23S08GpioProvider(byte spiAddress, SpiChannel spiChannel) throws IOException {
        this(spiAddress, spiChannel, SPI_SPEED);
    }

    public MCP23S08GpioProvider(byte spiAddress, int spiChannel, int spiSpeed) throws IOException {
        this(spiAddress, SpiChannel.getByNumber(spiChannel), spiSpeed);
    }

    public MCP23S08GpioProvider(byte spiAddress, SpiChannel spiChannel, int spiSpeed) throws IOException {

        // IOCON â€“ I/O EXPANDER CONFIGURATION REGISTER
        //
        // bit 7 BANK: Controls how the registers are addressed
        //     1 = The registers associated with each port are separated into different banks
        //     0 = The registers are in the same bank (addresses are sequential)
        // bit 6 MIRROR: INT Pins Mirror bit
        //     1 = The INT pins are internally connected
        //     0 = The INT pins are not connected. INTA is associated with PortA and INTB is associated with PortB
        // bit 5 SEQOP: Sequential Operation mode bit.
        //     1 = Sequential operation disabled, address pointer does not increment.
        //     0 = Sequential operation enabled, address pointer increments.
        // bit 4 DISSLW: Slew Rate control bit for SDA output.
        //     1 = Slew rate disabled.
        //     0 = Slew rate enabled.
        // bit 3 HAEN: Hardware Address Enable bit (MCP23S08 only).
        //     Address pins are always enabled on MCP23017.
        //     1 = Enables the MCP23S08 address pins.
        //     0 = Disables the MCP23S08 address pins.
        // bit 2 ODR: This bit configures the INT pin as an open-drain output.
        //     1 = Open-drain output (overrides the INTPOL bit).
        //     0 = Active driver output (INTPOL bit sets the polarity).
        // bit 1 INTPOL: This bit sets the polarity of the INT output pin.
        //     1 = Active-high.
        //     0 = Active-low.
        // bit 0 Unimplemented: Read as â€˜0â€™.
        //
        this(spiAddress ,spiChannel ,spiSpeed, (byte) 0x00001000);
    }

    public MCP23S08GpioProvider(byte spiAddress, int spiChannel, int spiSpeed, byte iocon) throws IOException {
        this(spiAddress, SpiChannel.getByNumber(spiChannel), spiSpeed, iocon);
    }

    public MCP23S08GpioProvider(byte spiAddress, SpiChannel spiChannel, int spiSpeed, byte iocon) throws IOException {

        // create SPi object instance SPI for communication
        spi = SpiFactory.getInstance(spiChannel, spiSpeed);

        // set SPI chip address
        this.address = spiAddress;

        // IOCON â€“ I/O EXPANDER CONFIGURATION REGISTER
        //
        // bit 7 BANK: Controls how the registers are addressed
        //     1 = The registers associated with each port are separated into different banks
        //     0 = The registers are in the same bank (addresses are sequential)
        // bit 6 MIRROR: INT Pins Mirror bit
        //     1 = The INT pins are internally connected
        //     0 = The INT pins are not connected. INTA is associated with PortA and INTB is associated with PortB
        // bit 5 SEQOP: Sequential Operation mode bit.
        //     1 = Sequential operation disabled, address pointer does not increment.
        //     0 = Sequential operation enabled, address pointer increments.
        // bit 4 DISSLW: Slew Rate control bit for SDA output.
        //     1 = Slew rate disabled.
        //     0 = Slew rate enabled.
        // bit 3 HAEN: Hardware Address Enable bit (MCP23S08 only).
        //     Address pins are always enabled on MCP23017.
        //     1 = Enables the MCP23S08 address pins.
        //     0 = Disables the MCP23S08 address pins.
        // bit 2 ODR: This bit configures the INT pin as an open-drain output.
        //     1 = Open-drain output (overrides the INTPOL bit).
        //     0 = Active driver output (INTPOL bit sets the polarity).
        // bit 1 INTPOL: This bit sets the polarity of the INT output pin.
        //     1 = Active-high.
        //     0 = Active-low.
        // bit 0 Unimplemented: Read as â€˜0â€™.
        //

        // write IO configuration
        write(REGISTER_IOCON, (byte)(IOCON_SEQOP|IOCON_HAEN));  // enable hardware address

        // read initial GPIO pin states
        // (include the '& 0xFF' to ensure the bits in the unsigned byte are cast properly)
        currentStates = read(REGISTER_GPIO);

        // set all default pins directions
        // (1 = Pin is configured as an input.)
        // (0 = Pin is configured as an output.)
        write(REGISTER_IODIR, (byte) currentDirection);

        // set all default pin states
        write(REGISTER_GPIO, (byte) currentStates);

        // set all default pin pull up resistors
        // (1 = Pull-up enabled.)
        // (0 = Pull-up disabled.)
        write(REGISTER_GPPU, (byte) currentPullup);

        // set all default pin interrupts
        // (if pin direction is input (1), then enable interrupt for pin)
        // (1 = Enable GPIO input pin for interrupt-on-change event.)
        // (0 = Disable GPIO input pin for interrupt-on-change event.)
        write(REGISTER_GPINTEN, (byte) currentDirection);

        // set all default pin interrupt default values
        // (comparison value registers are not used in this implementation)
        write(REGISTER_DEFVAL, (byte) 0x00);

        // set all default pin interrupt comparison behaviors
        // (1 = Controls how the associated pin value is compared for interrupt-on-change.)
        // (0 = Pin value is compared against the previous pin value.)
        write(REGISTER_INTCON, (byte) 0x00);

        // reset/clear interrupt flags
        if(currentDirection > 0)
            read(REGISTER_INTCAP);
    }

    protected synchronized void write(byte register, byte data) throws IOException {
        // create packet in data buffer
        byte packet[] = new byte[3];
        packet[0] = (byte)(address|WRITE_FLAG);   // address byte
        packet[1] = register;                     // register byte
        packet[2] = data;                         // data byte

        // send data packet
        spi.write(packet);
    }

    protected synchronized int read(byte register) throws IOException {
        // create packet in data buffer
        byte packet[] = new byte[3];
        packet[0] = (byte) (address | READ_FLAG);   // address byte
        packet[1] = register;                    // register byte
        packet[2] = 0b00000000;                  // data byte

        byte[] result = spi.write(packet);

        // (include the '& 0xFF' to ensure the bits in the unsigned byte are cast properly)
        return result[2] & 0xFF;
    }
    
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void export(Pin pin, PinMode mode) {
        // make sure to set the pin mode
        super.export(pin, mode);
        setMode(pin, mode);
    }

    @Override
    public void unexport(Pin pin) {
        super.unexport(pin);
        setMode(pin, PinMode.DIGITAL_OUTPUT);
    }

    @Override
    public void setMode(Pin pin, PinMode mode) {
        super.setMode(pin, mode);

        try {
            // determine register and pin address
            int pinAddress = pin.getAddress();

            // determine update direction value based on mode
            if (mode == PinMode.DIGITAL_INPUT) {
                currentDirection |= pinAddress;
            } else if (mode == PinMode.DIGITAL_OUTPUT) {
                currentDirection &= ~pinAddress;
            }

            // next update direction value
            write(REGISTER_IODIR, (byte) currentDirection);

            // enable interrupts; interrupt on any change from previous state
            write(REGISTER_GPINTEN, (byte) currentDirection);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // if any pins are configured as input pins, then we need to start the interrupt monitoring
        // thread
        if (currentDirection > 0) {
            // if the monitor has not been started, then start it now
            if (monitor == null) {
                // start monitoring thread
                monitor = new GpioStateMonitor(this);
                monitor.start();
            }
        } else {
            // shutdown and destroy monitoring thread since there are no input pins configured
            if (monitor != null) {
                monitor.shutdown();
                monitor = null;
            }
        }
    }

    @Override
    public PinMode getMode(Pin pin) {
        return super.getMode(pin);
    }

    @Override
    public void setState(Pin pin, PinState state) {
        super.setState(pin, state);

        try {
            // determine pin address
            int pinAddress = pin.getAddress();

            // determine state value for pin bit
            if (state.isHigh()) {
                currentStates |= pinAddress;
            } else {
                currentStates &= ~pinAddress;
            }

            // update state value
            write(REGISTER_GPIO, (byte) currentStates);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public PinState getState(Pin pin) {
        // call super method to perform validation on pin
        PinState result  = super.getState(pin);

        // determine pin address
        int pinAddress = pin.getAddress();
        
        // determine pin state
        result = (currentStates & pinAddress) == pinAddress ? PinState.HIGH : PinState.LOW;

        // cache state
        getPinCache(pin).setState(result);
        
        // return pin state
        return result;
    }
    
    @Override
    public void setPullResistance(Pin pin, PinPullResistance resistance) {
        // validate
        if (hasPin(pin) == false) {
            throw new InvalidPinException(pin);
        }
        // validate
        if (!pin.getSupportedPinPullResistance().contains(resistance)) {
            throw new UnsupportedPinPullResistanceException(pin, resistance);
        }
        try {
            // determine pin address
            int pinAddress = pin.getAddress();

            // determine pull up value for pin bit
            if (resistance == PinPullResistance.PULL_UP) {
                currentPullup |= pinAddress;
            } else {
                currentPullup &= ~pinAddress;
            }

            // next update pull up resistor value
            write(REGISTER_GPPU, (byte) currentPullup);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // cache resistance
        getPinCache(pin).setResistance(resistance);
    }

    @Override
    public PinPullResistance getPullResistance(Pin pin) {
        return super.getPullResistance(pin);
    }
    
    
    @Override
    public void shutdown() {
        
        // prevent reentrant invocation
        if(isShutdown())
            return;
        
        // perform shutdown login in base
        super.shutdown();
        
        // if a monitor is running, then shut it down now
        if (monitor != null) {
            // shutdown monitoring thread
            monitor.shutdown();
            monitor = null;
        }
    }   

    
    /**
     * This class/thread is used to to actively monitor for GPIO interrupts
     * 
     * @author Robert Savage
     * 
     */
    private class GpioStateMonitor extends Thread {
        private MCP23S08GpioProvider provider;
        private boolean shuttingDown = false;

        public GpioStateMonitor(MCP23S08GpioProvider provider) {
            this.provider = provider;
        }

        public void shutdown() {
            shuttingDown = true;
        }

        public void run() {
            while (!shuttingDown) {
                try {
                    // only process for interrupts if a pin on port A is configured as an input pin
                    if (currentDirection > 0) {
                        // process interrupts for port A
                        int pinInterruptA = provider.read(REGISTER_INTF);

                        // validate that there is at least one interrupt active on port A
                        if (pinInterruptA > 0) {
                            // read the current pin states on port A
                            int pinInterruptState = provider.read(REGISTER_GPIO);

                            // loop over the available pins on port B
                            for (Pin pin : MCP23S08Pin.ALL) {
//                                int pinAddressA = pin.getAddress();
                                
                                // is there an interrupt flag on this pin?
                                //if ((pinInterruptA & pinAddressA) > 0) {
                                    // System.out.println("INTERRUPT ON PIN [" + pin.getName() + "]");
                                    evaluatePinForChangeA(pin, pinInterruptState);
                                //}
                            }
                        }
                    }

                    // ... lets take a short breather ...
                    Thread.currentThread();
                    Thread.sleep(50);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        private void evaluatePinForChangeA(Pin pin, int state) {
            if (getPinCache(pin).isExported()) {
                // determine pin address
                int pinAddress = pin.getAddress();

                if ((state & pinAddress) != (currentStates & pinAddress)) {
                    PinState newState = (state & pinAddress) == pinAddress ? PinState.HIGH : PinState.LOW;

                    // cache state
                    getPinCache(pin).setState(newState);

                    // determine and cache state value for pin bit
                    if (newState.isHigh()) {
                        currentStates |= pinAddress;
                    } else {
                        currentStates &= ~pinAddress;
                    }

                    // change detected for INPUT PIN
                    // System.out.println("<<< CHANGE >>> " + pin.getName() + " : " + state);
                    dispatchPinChangeEvent(pin.getAddress(), newState);
                }
            }
        }

        private void dispatchPinChangeEvent(int pinAddress, PinState state) {
            // iterate over the pin listeners map
            for (Pin pin : listeners.keySet()) {
                // System.out.println("<<< DISPATCH >>> " + pin.getName() + " : " +
                // state.getName());

                // dispatch this event to the listener
                // if a matching pin address is found
                if (pin.getAddress() == pinAddress) {
                    // dispatch this event to all listener handlers
                    for (PinListener listener : listeners.get(pin)) {
                        listener.handlePinEvent(new PinDigitalStateChangeEvent(this, pin, state));
                    }
                }
            }
        }
    }
}
