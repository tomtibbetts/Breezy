package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinShutdown;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;

public class MockGpioPinDigitalInput extends BaseMockGpioPin implements GpioPinDigitalInput {

	private PinPullResistance pinPullResistance;
	private int debounce;
	private List<GpioPinListener> listeners = new ArrayList<GpioPinListener>();

	public MockGpioPinDigitalInput(GpioProvider provider, Pin pin, PinPullResistance pinPullResistance) {
		super(provider, pin);
		
		this.pinPullResistance = pinPullResistance;
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

	/**
	 * 
	 * @param pinPullResistance
	 */
	@Override
	public void setPullResistance(PinPullResistance pinPullResistance) {
		this.pinPullResistance = pinPullResistance;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public PinPullResistance getPullResistance() {
		return pinPullResistance;
	}

	/**
	 * 
	 * @param pinPullResistance
	 * @return
	 */
	@Override
	public boolean isPullResistance(PinPullResistance pinPullResistance) {
		return this.pinPullResistance == pinPullResistance;
	}

	@Override
	public Collection<GpioPinListener> getListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addListener(GpioPinListener... listeners) {
		for(GpioPinListener listener : listeners) {
			this.listeners.add(listener);
		}
	}

	@Override
	public void addListener(List<? extends GpioPinListener> listeners) {
		this.listeners.addAll(listeners);
	}

	@Override
	public boolean hasListener(GpioPinListener... listener) {
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
	public Collection<GpioTrigger> getTriggers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTrigger(GpioTrigger... trigger) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTrigger(List<? extends GpioTrigger> triggers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTrigger(GpioTrigger... trigger) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTrigger(List<? extends GpioTrigger> triggers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAllTriggers() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasDebounce(PinState state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getDebounce(PinState state) {
		return debounce;
	}

	@Override
	public void setDebounce(int debounce) {
		this.debounce = debounce;
	}

	@Override
	public void setDebounce(int debounce, PinState... states) {
		this.debounce = debounce;
	}

}
