package com.windhaven_consulting.breezy.concurrent.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;

public class PWMOutputPinPulsateTaskImpl implements Runnable {
	static final Logger LOG = LoggerFactory.getLogger(PWMOutputPinPulsateTaskImpl.class);

	private PWMOutputPin pwmOutputPin;
	
	private long attack;

	private long sustain;

	private long release;
	
	private long attackCount = 0;
	
	private long sustainCount = 0;
	
	private long releaseCount = 0;
	
	private double attackIncrement;
	
	private double releaseIncrement;
	
	private int currentBrightness;
	
	private PWMPulsateTaskState pulsateTaskState;

	public PWMOutputPinPulsateTaskImpl(PWMOutputPin pin, long attack, long sustain, long release) {
		this.pwmOutputPin = pin;
		this.attack = attack;
		this.sustain = sustain;
		this.release = release;
		
		attackIncrement = 100.0 / (double) attack;
		releaseIncrement = 100.0 / (double) release;
		pulsateTaskState = PWMPulsateTaskState.ATTACK;
		LOG.debug("Creating Task");
	}

	@Override
	public void run() {
		switch (pulsateTaskState) {
			case ATTACK:
				doAttack();
				break;
			case SUSTAIN:
				doSustain();
				break;
			case RELEASE:
				doRelease();
				break;
			case DONE:
				LOG.debug("Task complete, need to kill");
			default:
				break;
		}
	}

	private void doAttack() {
		int brightness = (int) Math.round(attackCount * attackIncrement);
		brightness = (brightness == 0 ? 1 : brightness);

		if(currentBrightness != brightness) {
			pwmOutputPin.setBrightness(brightness);
			currentBrightness = brightness;
		}
		
		if(attackCount++ >= attack) {
			pwmOutputPin.setAlwaysOn();
			sustainCount = sustain;
			pulsateTaskState = PWMPulsateTaskState.SUSTAIN;
		}
	}

	private void doSustain() {
		sustainCount--;
		
		if(sustainCount == 0) {
			releaseCount = release;
			pulsateTaskState = PWMPulsateTaskState.RELEASE;
		}
	}

	private void doRelease() {
		int brightness = (int) Math.round(releaseCount * releaseIncrement);
		brightness = (brightness == 0 ? 1 : brightness);

		if(currentBrightness != brightness) {
			pwmOutputPin.setBrightness(brightness);
			currentBrightness = brightness;
		}
		
		if(releaseCount-- <= 0) {
			pwmOutputPin.setAlwaysOff();
			attackCount = 0;
			pulsateTaskState = PWMPulsateTaskState.ATTACK;
		}
	}

	private enum PWMPulsateTaskState {
		ATTACK,
		SUSTAIN,
		RELEASE,
		DONE;
	}
}
