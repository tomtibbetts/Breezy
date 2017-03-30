package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.Collection;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinAnalog;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.GpioPinAnalogOutput;
import com.pi4j.io.gpio.GpioPinDigital;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalMultipurpose;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioPinInput;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.GpioPinShutdown;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;

public class MockGpioControllerImpl implements GpioController {

	@Override
	public void export(PinMode mode, PinState defaultState, GpioPin... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void export(PinMode mode, GpioPin... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public boolean isExported(GpioPin... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void unexport(GpioPin... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void unexportAll() {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void setMode(PinMode mode, GpioPin... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public PinMode getMode(GpioPin pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public boolean isMode(PinMode mode, GpioPin... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void setPullResistance(PinPullResistance resistance, GpioPin... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public PinPullResistance getPullResistance(GpioPin pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public boolean isPullResistance(PinPullResistance resistance, GpioPin... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void high(GpioPinDigitalOutput... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public boolean isHigh(GpioPinDigital... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void low(GpioPinDigitalOutput... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public boolean isLow(GpioPinDigital... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void setState(PinState state, GpioPinDigitalOutput... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void setState(boolean state, GpioPinDigitalOutput... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public boolean isState(PinState state, GpioPinDigital... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public PinState getState(GpioPinDigital pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void toggle(GpioPinDigitalOutput... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void pulse(long milliseconds, GpioPinDigitalOutput... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void setValue(double value, GpioPinAnalogOutput... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public double getValue(GpioPinAnalog pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void addListener(GpioPinListener listener, GpioPinInput... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void addListener(GpioPinListener[] listeners, GpioPinInput... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void removeListener(GpioPinListener listener, GpioPinInput... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void removeListener(GpioPinListener[] listeners, GpioPinInput... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void removeAllListeners() {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void addTrigger(GpioTrigger trigger, GpioPinInput... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void addTrigger(GpioTrigger[] triggers, GpioPinInput... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void removeTrigger(GpioTrigger trigger, GpioPinInput... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void removeTrigger(GpioTrigger[] triggers, GpioPinInput... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void removeAllTriggers() {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalMultipurpose provisionDigitalMultipurposePin(GpioProvider provider, Pin pin, String name, PinMode mode, PinPullResistance resistance) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalMultipurpose provisionDigitalMultipurposePin(GpioProvider provider, Pin pin, PinMode mode, PinPullResistance resistance) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalMultipurpose provisionDigitalMultipurposePin(GpioProvider provider, Pin pin, String name, PinMode mode) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalMultipurpose provisionDigitalMultipurposePin(GpioProvider provider, Pin pin, PinMode mode) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalMultipurpose provisionDigitalMultipurposePin(Pin pin, String name, PinMode mode, PinPullResistance resistance) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalMultipurpose provisionDigitalMultipurposePin(Pin pin, PinMode mode, PinPullResistance resistance) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalMultipurpose provisionDigitalMultipurposePin(Pin pin, String name, PinMode mode) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalMultipurpose provisionDigitalMultipurposePin(Pin pin, PinMode mode) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalInput provisionDigitalInputPin(GpioProvider provider, Pin pin, String name, PinPullResistance resistance) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	/**
	 * called from providerImpl
	 */
	@Override
	public GpioPinDigitalInput provisionDigitalInputPin(GpioProvider provider, Pin pin, PinPullResistance resistance) {
		GpioPinDigitalInput gpioPinDigitalInput = new MockGpioPinDigitalInput(provider, pin, resistance);

		return gpioPinDigitalInput; 
	}

	@Override
	public GpioPinDigitalInput provisionDigitalInputPin(GpioProvider provider, Pin pin, String name) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalInput provisionDigitalInputPin(GpioProvider provider, Pin pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalInput provisionDigitalInputPin(Pin pin, String name, PinPullResistance resistance) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalInput provisionDigitalInputPin(Pin pin, PinPullResistance resistance) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalInput provisionDigitalInputPin(Pin pin, String name) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalInput provisionDigitalInputPin(Pin pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalOutput provisionDigitalOutputPin(GpioProvider provider, Pin pin, String name, PinState defaultState) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	/**
	 * 
	 */
	@Override
	public GpioPinDigitalOutput provisionDigitalOutputPin(GpioProvider provider, Pin pin, PinState defaultState) {
		return new MockGpioPinDigitalOutput(provider, pin, defaultState);
	}

	@Override
	public GpioPinDigitalOutput provisionDigitalOutputPin(GpioProvider provider, Pin pin, String name) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalOutput provisionDigitalOutputPin(GpioProvider provider, Pin pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalOutput provisionDigitalOutputPin(Pin pin, String name, PinState defaultState) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalOutput provisionDigitalOutputPin(Pin pin, PinState defaultState) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalOutput provisionDigitalOutputPin(Pin pin, String name) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinDigitalOutput provisionDigitalOutputPin(Pin pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinAnalogInput provisionAnalogInputPin(GpioProvider provider, Pin pin, String name) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinAnalogInput provisionAnalogInputPin(GpioProvider provider, Pin pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinAnalogInput provisionAnalogInputPin(Pin pin, String name) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinAnalogInput provisionAnalogInputPin(Pin pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinAnalogOutput provisionAnalogOutputPin(GpioProvider provider, Pin pin, String name, double defaultValue) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinAnalogOutput provisionAnalogOutputPin(GpioProvider provider, Pin pin, double defaultValue) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinAnalogOutput provisionAnalogOutputPin(GpioProvider provider, Pin pin, String name) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinAnalogOutput provisionAnalogOutputPin(GpioProvider provider, Pin pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinAnalogOutput provisionAnalogOutputPin(Pin pin, String name, double defaultValue) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinAnalogOutput provisionAnalogOutputPin(Pin pin, double defaultValue) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinAnalogOutput provisionAnalogOutputPin(Pin pin, String name) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinAnalogOutput provisionAnalogOutputPin(Pin pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinPwmOutput provisionPwmOutputPin(GpioProvider provider, Pin pin, String name, int defaultValue) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinPwmOutput provisionPwmOutputPin(GpioProvider provider, Pin pin, int defaultValue) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinPwmOutput provisionPwmOutputPin(GpioProvider provider, Pin pin, String name) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	/**
	 * 
	 */
	@Override
	public GpioPinPwmOutput provisionPwmOutputPin(GpioProvider provider, Pin pin) {
		return new MockGpioPwmOutput(provider, pin);
	}

	@Override
	public GpioPinPwmOutput provisionPwmOutputPin(Pin pin, String name, int defaultValue) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinPwmOutput provisionPwmOutputPin(Pin pin, int defaultValue) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinPwmOutput provisionPwmOutputPin(Pin pin, String name) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPinPwmOutput provisionPwmOutputPin(Pin pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPin provisionPin(GpioProvider provider, Pin pin, String name, PinMode mode, PinState defaultState) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPin provisionPin(GpioProvider provider, Pin pin, String name, PinMode mode) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPin provisionPin(GpioProvider provider, Pin pin, PinMode mode) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPin provisionPin(Pin pin, String name, PinMode mode) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public GpioPin provisionPin(Pin pin, PinMode mode) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void setShutdownOptions(GpioPinShutdown options, GpioPin... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void setShutdownOptions(Boolean unexport, GpioPin... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void setShutdownOptions(Boolean unexport, PinState state, GpioPin... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void setShutdownOptions(Boolean unexport, PinState state, PinPullResistance resistance, GpioPin... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void setShutdownOptions(Boolean unexport, PinState state, PinPullResistance resistance, PinMode mode, GpioPin... pin) {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public Collection<GpioPin> getProvisionedPins() {
		throw new UnsupportedOperationException("Method not supported for MockGpioControllerImpl");
	}

	@Override
	public void unprovisionPin(GpioPin... pin) {
		// do nothing
	}

	@Override
	public boolean isShutdown() {
		return false;
	}

	@Override
	public void shutdown() {
		// do nothing
	}

}
