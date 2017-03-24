package com.windhaven_consulting.breezy.concurrent.impl;

import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PWMOutputPinStopTaskImpl implements Runnable {
	static final Logger LOG = LoggerFactory.getLogger(PWMOutputPinStopTaskImpl.class);

    private final ScheduledFuture<?> task;
    
    public PWMOutputPinStopTaskImpl(ScheduledFuture<?> task) {
        this.task = task;
    }

    public void run() {
        // cancel the blinking task
    	task.cancel(true);
    }

}
