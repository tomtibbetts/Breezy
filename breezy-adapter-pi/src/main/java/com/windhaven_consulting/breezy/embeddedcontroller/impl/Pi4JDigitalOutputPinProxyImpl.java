package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.UUID;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerRuntimeException;

public class Pi4JDigitalOutputPinProxyImpl extends Pi4JPinProxyImpl implements DigitalOutputPin {

	public Pi4JDigitalOutputPinProxyImpl(String name, UUID id, GpioPin gpioPin) {
		super(name, id, gpioPin);
	}

	@Override
	public void toggle() {
		getDigitalOutputPin().toggle();
	}

	@Override
	public void setLow() {
		getDigitalOutputPin().low();
	}

	@Override
	public void setHigh() {
		getDigitalOutputPin().high();
	}

	@Override
	public void blink(long onTime, long duration) {
		getDigitalOutputPin().blink(onTime, duration);
	}

	@Override
	public void blink(long onTime) {
		getDigitalOutputPin().blink(onTime);
	}

	@Override
	public void blink(long onTime, PinState pinState) {
		getDigitalOutputPin().blink(onTime, BreezyToPi4JPinState.getPinState(pinState));
	}

	@Override
	public void blink(long onTime, long duration, PinState pinState, boolean blockToCompletion) {
		getDigitalOutputPin().blink(onTime, duration, BreezyToPi4JPinState.getPinState(pinState));
		
		if(blockToCompletion) {
			try {
				Thread.sleep(duration);
			} catch (InterruptedException e) {
				throw new EmbeddedControllerRuntimeException("InterruptedException Thrown.", e);
			}
		}
	}

	@Override
	public void pulse(long duration) {
		getDigitalOutputPin().pulse(duration);
	}

	@Override
	public void pulse(long duration, PinState pinState) {
		getDigitalOutputPin().pulse(duration, BreezyToPi4JPinState.getPinState((pinState)));
	}

	@Override
	public void pulse(long duration, boolean blockToCompletion) {
		getDigitalOutputPin().pulse(duration, blockToCompletion);
	}

	@Override
	public void pulse(long duration, PinState pinState, boolean blockToCompletion) {
		getDigitalOutputPin().pulse(duration, BreezyToPi4JPinState.getPinState((pinState)), blockToCompletion);
	}

	private GpioPinDigitalOutput getDigitalOutputPin() {
		return (GpioPinDigitalOutput) getGpioPin();
	}

	@Override
	public PinState getState() {
		return PinState.find(getDigitalOutputPin().getState().getValue());
	}

}
