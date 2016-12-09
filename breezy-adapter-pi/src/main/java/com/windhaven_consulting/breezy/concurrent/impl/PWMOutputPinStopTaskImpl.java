package com.windhaven_consulting.breezy.concurrent.impl;

import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PWMOutputPinStopTaskImpl implements Runnable {
	static final Logger LOG = LoggerFactory.getLogger(PWMOutputPinStopTaskImpl.class);

    private final ScheduledFuture<?> pulsateTask;
    
    public PWMOutputPinStopTaskImpl(ScheduledFuture<?> pulsateTask) {
        this.pulsateTask = pulsateTask;
    }

    public void run() {
    	LOG.debug("Killing pulsate.");
    	
        // cancel the blinking task
    	pulsateTask.cancel(true);
    }

}
