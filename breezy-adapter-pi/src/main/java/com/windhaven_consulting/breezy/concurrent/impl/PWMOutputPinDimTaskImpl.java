package com.windhaven_consulting.breezy.concurrent.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;

public class PWMOutputPinDimTaskImpl implements Runnable {
	static final Logger LOG = LoggerFactory.getLogger(PWMOutputPinDimTaskImpl.class);

	private PWMOutputPin pwmOutputPin;
	private long attack;
	private long attackCount;
	private int brightnessThreshold;
	private double attackIncrement;
	private DimDirectionState dimDirectionState;
	private double lastCalculatedBrightness;

	private int brightnessDelta;

	public PWMOutputPinDimTaskImpl(PWMOutputPin pwmOutputPin, long attack, int brightnessThreshold) {
		this.pwmOutputPin = pwmOutputPin;
		this.attack = attack;
		this.brightnessThreshold = brightnessThreshold;
		
		this.lastCalculatedBrightness = pwmOutputPin.getBrightness();
		
		this.brightnessDelta = Math.abs(pwmOutputPin.getBrightness() - brightnessThreshold);
		
		attackIncrement = ((double) brightnessDelta) / ((double) attack);
		
		if(pwmOutputPin.getBrightness() > brightnessThreshold) {
			// dim down
			dimDirectionState = DimDirectionState.DIM_DOWN;
			attackCount = attack;
		}
		else {
			// dim up
			dimDirectionState = DimDirectionState.DIM_UP;
			attackCount = 0;
		}
		
		LOG.debug("PWMOutputPinDimTaskImpl: lastCalculatedBrightness = " + lastCalculatedBrightness + ", attack = " + attack + ", attackIncrement = " + attackIncrement + ", maxBrightness = " + brightnessThreshold + ", brightnessDelta = " + brightnessDelta + ", dimDirectionState - " + dimDirectionState);
	}

	@Override
	public void run() {
		if(dimDirectionState == DimDirectionState.DIM_UP && attackCount < attack) {
			lastCalculatedBrightness = lastCalculatedBrightness + attackIncrement;
			
			int calculatedBrightness = (int) (Math.round(lastCalculatedBrightness));

			if(calculatedBrightness > brightnessThreshold) {
				calculatedBrightness = brightnessThreshold;
			}
			
			if(pwmOutputPin.getBrightness() != calculatedBrightness) {
				LOG.debug("Setting brightness: old = " + pwmOutputPin.getBrightness() + ", new = " + calculatedBrightness);
				pwmOutputPin.setBrightness(calculatedBrightness);
			}

			attackCount++;
		}
		else if(dimDirectionState == DimDirectionState.DIM_DOWN && attackCount > 0) {
			lastCalculatedBrightness = lastCalculatedBrightness - attackIncrement;
			
			int calculatedBrightness = (int) (Math.round(lastCalculatedBrightness));

			if(calculatedBrightness < brightnessThreshold) {
				calculatedBrightness = brightnessThreshold;
			}
	
			if(pwmOutputPin.getBrightness() != calculatedBrightness) {
				LOG.debug("Setting brightness: old = " + pwmOutputPin.getBrightness() + ", new = " + calculatedBrightness);
				pwmOutputPin.setBrightness(calculatedBrightness);
			}
			
			attackCount--;
		}
	}

	private enum DimDirectionState {
		DIM_UP,
		DIM_DOWN;
	}
}
 