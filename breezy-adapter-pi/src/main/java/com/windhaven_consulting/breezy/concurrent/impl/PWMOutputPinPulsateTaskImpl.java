package com.windhaven_consulting.breezy.concurrent.impl;

import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;

public class PWMOutputPinPulsateTaskImpl implements Runnable {
	private PWMOutputPin pwmOutputPin;
	
	private long attack;

	private long sustain;

	private long release;
	
	private long interval;

	private int maxBrightness;

	private long attackCount = 0;
	
	private long sustainCount = 0;
	
	private long releaseCount = 0;
	
	private long intervalCount = 0;
	
	private double attackIncrement;
	
	private double releaseIncrement;
	
	private int currentBrightness;
	
	private PWMPulsateTaskState pulsateTaskState;

	public PWMOutputPinPulsateTaskImpl(PWMOutputPin pin, long attack, long sustain, long release, long interval, int maxBrightness) {
		this.pwmOutputPin = pin;
		this.attack = attack;
		this.sustain = sustain;
		this.release = release;
		this.interval = interval;
		this.maxBrightness = maxBrightness;
		
		attackIncrement = 100.0 / (double) attack;
		releaseIncrement = 100.0 / (double) release;
		pulsateTaskState = PWMPulsateTaskState.ATTACK;
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
			case INTERVAL:
				doInterval();
			case DONE:
				break;
			default:
				break;
		}
	}

	private void doAttack() {
		int calculatedBrightness = getCalculatedBrightness(attackCount, attackIncrement);
		
		if(currentBrightness != calculatedBrightness) {
			pwmOutputPin.setBrightness(calculatedBrightness);
			currentBrightness = calculatedBrightness;
		}
		
		if(attackCount++ >= attack) {
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
		int calculatedBrightness = getCalculatedBrightness(releaseCount, releaseIncrement);

		if(currentBrightness != calculatedBrightness) {
			pwmOutputPin.setBrightness(calculatedBrightness);
			currentBrightness = calculatedBrightness;
		}
		
		if(releaseCount-- <= 0) {
			pwmOutputPin.setAlwaysOff();
			
			if(interval > 0) {
				intervalCount = interval;
				pulsateTaskState = PWMPulsateTaskState.INTERVAL;
			}
			else {
				attackCount = 0;
				pulsateTaskState = PWMPulsateTaskState.ATTACK;
			}
		}
	}

	private void doInterval() {
		if(intervalCount-- <= 0) {
			attackCount = 0;
			pulsateTaskState = PWMPulsateTaskState.ATTACK;
		}
	}

	private int getCalculatedBrightness(long count, double delta) {
		int calculatedBrightness = (int) Math.round(count * delta);
		calculatedBrightness = (calculatedBrightness == 0 ? 1 : calculatedBrightness);
		
		if(calculatedBrightness > maxBrightness) {
			calculatedBrightness = maxBrightness;
		}

		return calculatedBrightness;
	}
	
	private enum PWMPulsateTaskState {
		ATTACK,
		SUSTAIN,
		RELEASE,
		INTERVAL,
		DONE;
	}
}
