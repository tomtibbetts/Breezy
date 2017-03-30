package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioPinShutdown;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinListener;

public class MockGpioPinDigitalOutput extends BaseMockGpioPin implements GpioPinDigitalOutput {

	private PinState defaultState;

	public MockGpioPinDigitalOutput(GpioProvider provider, Pin pin, PinState defaultState) {
		super(provider, pin);
		
		this.defaultState = defaultState;
	}

	@Override
	public boolean isHigh() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLow() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PinState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isState(PinState state) {
		// TODO Auto-generated method stub
		return false;
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
	public void high() {
		// TODO Auto-generated method stub

	}

	@Override
	public void low() {
		// TODO Auto-generated method stub

	}

	@Override
	public void toggle() {
		// TODO Auto-generated method stub

	}

	@Override
	public Future<?> blink(long delay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> blink(long delay, PinState blinkState) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> blink(long delay, long duration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> blink(long delay, long duration, PinState blinkState) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> pulse(long duration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> pulse(long duration, Callable<Void> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> pulse(long duration, boolean blocking) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> pulse(long duration, boolean blocking, Callable<Void> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> pulse(long duration, PinState pulseState) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> pulse(long duration, PinState pulseState, Callable<Void> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> pulse(long duration, PinState pulseState, boolean blocking) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> pulse(long duration, PinState pulseState, boolean blocking, Callable<Void> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setState(PinState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setState(boolean state) {
		// TODO Auto-generated method stub

	}

}
