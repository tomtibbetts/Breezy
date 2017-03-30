package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.Collection;
import java.util.List;

import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.GpioPinShutdown;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinListener;

public class MockGpioPwmOutput extends BaseMockGpioPin implements GpioPinPwmOutput {

	public MockGpioPwmOutput(GpioProvider provider, Pin pin) {
		super(provider, pin);
	}

	@Override
	public int getPwm() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GpioProvider getProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pin getPin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTag(Object tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getTag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void export(PinMode mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void export(PinMode mode, PinState defaultState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unexport() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isExported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMode(PinMode mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public PinMode getMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMode(PinMode mode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPullResistance(PinPullResistance resistance) {
		// TODO Auto-generated method stub

	}

	@Override
	public PinPullResistance getPullResistance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPullResistance(PinPullResistance resistance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<GpioPinListener> getListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addListener(GpioPinListener... listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addListener(List<? extends GpioPinListener> listeners) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasListener(GpioPinListener... listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(GpioPinListener... listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListener(List<? extends GpioPinListener> listeners) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAllListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	public GpioPinShutdown getShutdownOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShutdownOptions(GpioPinShutdown options) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShutdownOptions(Boolean unexport) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShutdownOptions(Boolean unexport, PinState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShutdownOptions(Boolean unexport, PinState state, PinPullResistance resistance) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShutdownOptions(Boolean unexport, PinState state, PinPullResistance resistance, PinMode mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPwm(int value) {
		// TODO Auto-generated method stub

	}

}
