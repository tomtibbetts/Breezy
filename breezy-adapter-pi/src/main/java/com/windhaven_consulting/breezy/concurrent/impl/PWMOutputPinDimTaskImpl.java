package com.windhaven_consulting.breezy.concurrent.impl;

import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;

public class PWMOutputPinDimTaskImpl implements Runnable {

	private PWMOutputPin pwmOutputPin;
	private long attack;
	private long attackCount;
	private int brightness;
	private double attackIncrement;
	private DimDirectionState dimDirectionState;
	private int currentBrightness;

	public PWMOutputPinDimTaskImpl(PWMOutputPin pwmOutputPin, long attack, int brightness) {
		this.pwmOutputPin = pwmOutputPin;
		this.attack = attack;
		this.brightness = brightness;
		
		attackIncrement = 100.0 / (double) attack;
		
		if(pwmOutputPin.getBrightness() > brightness) {
			// dim down
			dimDirectionState = DimDirectionState.DIM_DOWN;
			attackCount = attack;
		}
		else {
			// dim up
			dimDirectionState = DimDirectionState.DIM_UP;
			attackCount = 0;
		}
	}

	@Override
	public void run() {
		int calculatedBrightness = (int) Math.round(attackCount * attackIncrement);
		calculatedBrightness = (calculatedBrightness == 0 ? 1 : calculatedBrightness);
		
		if(dimDirectionState == DimDirectionState.DIM_UP && attackCount < attack) {
			if(calculatedBrightness > brightness) {
				calculatedBrightness = brightness;
			}

			if(currentBrightness != calculatedBrightness) {
				pwmOutputPin.setBrightness(calculatedBrightness);
				currentBrightness = calculatedBrightness;
			}
			
			attackCount++;
		}
		else if(dimDirectionState == DimDirectionState.DIM_DOWN && attackCount > 0) {
			if(calculatedBrightness < brightness) {
				calculatedBrightness = brightness;
			}

			if(currentBrightness != calculatedBrightness) {
				pwmOutputPin.setBrightness(calculatedBrightness);
				currentBrightness = calculatedBrightness;
			}
			
			attackCount--;
		}
	}

	private enum DimDirectionState {
		DIM_UP,
		DIM_DOWN;
	}
}
 