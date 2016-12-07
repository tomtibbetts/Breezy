package com.windhaven_consulting.breezy.concurrent.impl;

import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PWMOutputPinPulsateStopTaskImpl implements Runnable {
	static final Logger LOG = LoggerFactory.getLogger(PWMOutputPinPulsateStopTaskImpl.class);

    private final ScheduledFuture<?> pulsateTask;
    
    public PWMOutputPinPulsateStopTaskImpl(ScheduledFuture<?> pulsateTask) {
        this.pulsateTask = pulsateTask;
    }

    public void run() {
    	LOG.debug("Killing pulsate.");
    	
        // cancel the blinking task
    	pulsateTask.cancel(true);
    }

}
