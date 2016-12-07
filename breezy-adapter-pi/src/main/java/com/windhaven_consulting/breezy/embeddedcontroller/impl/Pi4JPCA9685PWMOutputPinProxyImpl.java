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

public class Pi4JPCA9685PWMOutputPinProxyImpl extends Pi4JPinProxyImpl  implements PWMOutputPin {
	static final Logger LOG = LoggerFactory.getLogger(Pi4JPCA9685PWMOutputPinProxyImpl.class);

	private Pin pin;
	private PCA9685GpioProvider pca9685GpioProvider;
	private PWMPinState pwmPinState = PWMPinState.INDETERMINATE;
	
	public Pi4JPCA9685PWMOutputPinProxyImpl(String name, UUID id, GpioPin gpioPin, Pin pin, PCA9685GpioProvider pca9685GpioProvider) {
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
	public void setBrightness(int brightness) {
		if(brightness < 0 || brightness > 100) {
			throw new IllegalArgumentException("Range of brightness must be >= 0 and <= 100");
		}
		
		pwmPinState = PWMPinState.INDETERMINATE;

		int duration = ((pca9685GpioProvider.getPeriodDurationMicros() - 1) / 100) * brightness;
		pca9685GpioProvider.setPwm(pin, duration);
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
		PWMPinState inverseState = PWMPinState.getInverseState(this.pwmPinState);

		switch(inverseState) {
			case HIGH:
				setAlwaysOn();
				break;
			case LOW:
				setAlwaysOff();
				break;
			case INDETERMINATE:
				break;
			default:
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
		// NOTE: a value of 0 milliseconds for delay will stop the blinking
        return PWMScheduledExecutor.blink(this, delay, duration, blinkState);
	}

	@Override
	public void blink(long delay, long duration, PWMPinState pwmPinState, boolean blockToCompletion) {
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
		switch(pwmPinstate) {
			case HIGH : 
				setAlwaysOn();
				break;
			case LOW:
				setAlwaysOff();
				break;
			case INDETERMINATE:
				break;
			default:
				break;
		}
	}

	@Override
	public Future<?> pulse(long duration) {
		return pulse(duration, PWMPinState.HIGH, false);
	}

	@Override
	public Future<?> pulse(long duration, boolean blocking) {
		return pulse(duration, PWMPinState.HIGH, blocking);
	}

	@Override
	public Future<?> pulse(long duration, PWMPinState pulseState) {
		return pulse(duration, pulseState, false);
	}

	@Override
	public Future<?> pulse(long duration, PWMPinState pulseState, boolean blocking) {
        // validate duration argument
        if(duration <= 0)
            throw new IllegalArgumentException("Pulse duration must be greater than 0 milliseconds.");
        
        // if this is a blocking pulse, then execute the pulse 
        // and sleep the caller's thread to block the operation 
        // until the pulse is complete
        if(blocking) {
            // start the pulse state
            setState(pulseState);
            
            // block the current thread for the pulse duration 
            try {
                Thread.sleep(duration);
            }
            catch (InterruptedException e) {
                throw new RuntimeException("Pulse blocking thread interrupted.", e);
            }

            // end the pulse state
            setState(PWMPinState.getInverseState(pulseState));
            
            // we are done; no future is returned for blocking pulses
            return null;
        }
        else {            
            // if this is not a blocking call, then setup the pulse 
            // instruction to be completed in a background worker
            // thread pool using a scheduled executor 
            return PWMScheduledExecutor.pulse(this, duration, pulseState);
        }
	}

	@Override
	public Future<?> pulsate(long attack, long sustain, long release) {
		return PWMScheduledExecutor.pulsate(this, attack, sustain, release);
	}

}
