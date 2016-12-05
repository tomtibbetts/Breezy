package com.windhaven_consulting.breezy.concurrent.impl;

import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMPinState;

public class PWMOutputPinBlinkStopTaskImpl implements Runnable {
	static final Logger LOG = LoggerFactory.getLogger(PWMOutputPinBlinkStopTaskImpl.class);

    private final PWMOutputPin pin;
    private final PWMPinState stopState;
    private final ScheduledFuture<?> blinkTask;
    
    public PWMOutputPinBlinkStopTaskImpl(PWMOutputPin pin, PWMPinState stopState, ScheduledFuture<?> blinkTask) {
        this.pin = pin;    
        this.stopState = stopState;
        this.blinkTask = blinkTask;
    }

    public void run() {
    	LOG.debug("Killing blink");
        // cancel the blinking task
        blinkTask.cancel(true);
        
        // set the pin to the stop blinking state
        pin.setState(stopState);
    }

}
