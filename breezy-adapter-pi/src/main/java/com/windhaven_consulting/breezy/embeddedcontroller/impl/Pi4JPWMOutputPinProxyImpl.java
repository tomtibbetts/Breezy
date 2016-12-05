package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.UUID;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.Pin;
import com.windhaven_consulting.breezy.concurrent.PWMScheduledExecutor;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMPinState;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;

public class Pi4JPWMOutputPinProxyImpl extends Pi4JPinProxyImpl  implements PWMOutputPin {
	static final Logger LOG = LoggerFactory.getLogger(Pi4JPWMOutputPinProxyImpl.class);

	private Pin pin;
	private PCA9685GpioProvider pca9685GpioProvider;
	private PWMPinState pwmPinState = PWMPinState.INDETERMINATE;
	
	public Pi4JPWMOutputPinProxyImpl(String name, UUID id, GpioPin gpioPin, Pin pin, PCA9685GpioProvider pca9685GpioProvider) {
		super(name, id, gpioPin);

		this.pin = pin;
		this.pca9685GpioProvider = pca9685GpioProvider;
	}

	@Override
	public void setAlwaysOn() {
		pwmPinState = PWMPinState.HIGH;
		pca9685GpioProvider.setAlwaysOn(pin);
	}

	@Override
	public void setAlwaysOff() {
		pwmPinState = PWMPinState.LOW;
		pca9685GpioProvider.setAlwaysOff(pin);
	}

	@Override
	public void setPwm(int duration) {
		pwmPinState = PWMPinState.INDETERMINATE;
		pca9685GpioProvider.setPwm(pin, duration);
	}

	@Override
	public void setPwm(int onPosition, int offPosition) {
		pwmPinState = PWMPinState.INDETERMINATE;
		pca9685GpioProvider.setPwm(pin, onPosition, offPosition);
	}

	@Override
	public void high() {
		setAlwaysOn();
	}

	@Override
	public void low() {
		setAlwaysOff();
	}

	@Override
	public void toggle() {
		LOG.debug("toggle, current state is: " + pwmPinState);
		PWMPinState inverseState = PWMPinState.getInverseState(this.pwmPinState);
		
		LOG.debug("toggle, inverted state is: " + inverseState);

		switch(inverseState) {
			case HIGH :
				setAlwaysOn();
				break;
			case LOW :
				setAlwaysOff();
				break;
			case INDETERMINATE :
				break;
			default :
				break;
		}
	}

	@Override
	public Future<?> blink(long delay) {
        return blink(delay, PWMPinState.HIGH);
	}

	@Override
	public Future<?> blink(long delay, PWMPinState blinkState) {
        // NOTE: a value of 0 milliseconds for delay will stop the blinking
        return blink(delay, 0, blinkState);
	}

	@Override
	public Future<?> blink(long delay, long duration) {
        return blink(delay, duration, PWMPinState.HIGH);
	}

	@Override
	public Future<?> blink(long delay, long duration, PWMPinState blinkState) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", blink: delay = " + delay + ", duration = " + duration + ", pwmPinState = " + pwmPinState);

		// NOTE: a value of 0 milliseconds for delay will stop the blinking
        return PWMScheduledExecutor.blink(this, delay, duration, blinkState);
	}

	@Override
	public void blink(long delay, long duration, PWMPinState pwmPinState, boolean blockToCompletion) {
		LOG.debug("name: " + getName() + ", id: " + getId().toString() + ", blink: delay = " + delay + ", duration = " + duration + ", pwmPinState = " + pwmPinState + ", blockToCompletion = " + blockToCompletion);

		blink(delay, duration, pwmPinState);
		
		if(blockToCompletion) {
			try {
				Thread.sleep(duration);
			} catch (InterruptedException e) {
				throw new EmbeddedControllerException("InterruptedException Thrown.", e);
			}
		}
	}

	@Override
	public void setState(PWMPinState pwmPinstate) {
		this.pwmPinState = pwmPinstate;
	}

}
