package com.windhaven_consulting.breezy.concurrent.impl;

import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;

public class PWMOutputPinDimmerTaskImpl implements Runnable {
	private PWMOutputPin pwmOutputPin;
	private long attack;
	private long attackCount;
	private int brightnessThreshold;
	private double attackIncrement;
	private DimmerDirectionState dimDirectionState;
	private double lastCalculatedBrightness;

	public PWMOutputPinDimmerTaskImpl(PWMOutputPin pwmOutputPin, long attack, int brightnessThreshold) {
		this.pwmOutputPin = pwmOutputPin;
		this.attack = attack;
		this.brightnessThreshold = brightnessThreshold;
		this.lastCalculatedBrightness = pwmOutputPin.getBrightness();
		
		initialize();
	}

	@Override
	public void run() {
		if(dimDirectionState == DimmerDirectionState.UP && attackCount <= attack) {
			lastCalculatedBrightness = lastCalculatedBrightness + attackIncrement;
			int calculatedBrightness = (int) (Math.round(lastCalculatedBrightness));
			
			if(calculatedBrightness > brightnessThreshold) {
				calculatedBrightness = brightnessThreshold;
			}
			
			if(pwmOutputPin.getBrightness() != calculatedBrightness) {
				pwmOutputPin.setBrightness(calculatedBrightness);
			}

			attackCount++;
		}
		else if(dimDirectionState == DimmerDirectionState.DOWN && attackCount >= 0) {
			lastCalculatedBrightness = lastCalculatedBrightness - attackIncrement;
			int calculatedBrightness = (int) (Math.round(lastCalculatedBrightness));

			if(calculatedBrightness < brightnessThreshold) {
				calculatedBrightness = brightnessThreshold;
			}
	
			if(pwmOutputPin.getBrightness() != calculatedBrightness) {
				pwmOutputPin.setBrightness(calculatedBrightness);
			}
			
			attackCount--;
		}
	}

	private void initialize() {
		int brightnessDelta = Math.abs(pwmOutputPin.getBrightness() - brightnessThreshold);
		attackIncrement = ((double) brightnessDelta) / ((double) attack);
		
		if(pwmOutputPin.getBrightness() > brightnessThreshold) {
			// dim down
			dimDirectionState = DimmerDirectionState.DOWN;
			attackCount = attack;
		}
		else {
			// dim up
			dimDirectionState = DimmerDirectionState.UP;
			attackCount = 0;
		}
	}

	private enum DimmerDirectionState {
		UP,
		DOWN;
	}
}
 