package com.windhaven_consulting.breezy.concurrent.impl;

import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;

public class PWMOutputPinPulsateAttackTaskImpl implements Runnable {
	static final Logger LOG = LoggerFactory.getLogger(PWMOutputPinPulsateAttackTaskImpl.class);

	private PWMOutputPin pwmOutputPin;
	
	private long maxCount;
	
	private long count = 0;

	private double increment;

	private ScheduledFuture<?> pulsateTask;

	public PWMOutputPinPulsateAttackTaskImpl(PWMOutputPin pin, long maxCount, ScheduledFuture<?> pulsateTask) {
		this.pwmOutputPin = pin;
		this.maxCount = maxCount;
		this.pulsateTask = pulsateTask;
		
		increment = 100.0 / (double) maxCount;
		
		LOG.debug("starting attack: maxCount = " + maxCount + ", increment = " + increment);
	}

	@Override
	public void run() {
		int brightness = (int) Math.round(count * increment);
		brightness = (brightness == 0 ? 1 : brightness);
		
		LOG.debug("brightness = " + brightness);
		pwmOutputPin.setBrightness(brightness);
		
		if(count++ >= maxCount) {
			LOG.debug("need to kill this task");
//			pulsateTask.cancel(true);
		}
	}

}
