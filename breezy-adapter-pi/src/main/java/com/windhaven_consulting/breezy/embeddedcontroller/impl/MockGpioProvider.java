package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.PinListener;

public class MockGpioProvider implements GpioProvider {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPin(Pin pin) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void export(Pin pin, PinMode mode, PinState defaultState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void export(Pin pin, PinMode mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isExported(Pin pin) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unexport(Pin pin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMode(Pin pin, PinMode mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public PinMode getMode(Pin pin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPullResistance(Pin pin, PinPullResistance resistance) {
		// TODO Auto-generated method stub

	}

	@Override
	public PinPullResistance getPullResistance(Pin pin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setState(Pin pin, PinState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public PinState getState(Pin pin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(Pin pin, double value) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getValue(Pin pin) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPwm(Pin pin, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getPwm(Pin pin) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addListener(Pin pin, PinListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListener(Pin pin, PinListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAllListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isShutdown() {
		// TODO Auto-generated method stub
		return false;
	}

}
