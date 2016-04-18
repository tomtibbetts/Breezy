package com.windhaven_consulting.breezy.component.lights;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.Component;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;
import com.windhaven_consulting.breezy.exceptions.BreezyApplicationException;

public abstract class AbstractSemaphore extends Component {
	static final Logger LOG = LoggerFactory.getLogger(AbstractSemaphore.class);

	private static final long serialVersionUID = 1L;

	public void turnOn(int colorIndex) {
		validateColorIndex(colorIndex);
		allOff();
		
		DigitalOutputPin digitalOutputPin = getOutputPin(colorIndex);
		digitalOutputPin.setHigh();
	}
	
	public void turnOff(int colorIndex) {
		validateColorIndex(colorIndex);
		DigitalOutputPin digitalOutputPin = getOutputPin(colorIndex);
		digitalOutputPin.setLow();
	}
	
	public void blink(int colorIndex, long onTime) {
		validateColorIndex(colorIndex);
		allOff();
		
		DigitalOutputPin digitalOutputPin = getOutputPin(colorIndex);
		digitalOutputPin.blink(onTime);
	}
	
	public void blink(int colorIndex, long onTime, long duration, boolean blockToCompletion) {
		validateColorIndex(colorIndex);
		allOff();
		
		DigitalOutputPin digitalOutputPin = getOutputPin(colorIndex);
		digitalOutputPin.blink(onTime, duration, PinState.HIGH, blockToCompletion);
	}

	public void pulse(int colorIndex, long duration, boolean blockToCompletion) {
		validateColorIndex(colorIndex);
		allOff();

		DigitalOutputPin digitalOutputPin = getOutputPin(colorIndex);
		digitalOutputPin.pulse(duration, PinState.HIGH, blockToCompletion);
	}

	public void allOn() {
		for(DigitalOutputPin digitalOutputPin : getOutputPins()) {
			digitalOutputPin.setHigh();
		}
	}
	
	public void allOff() {
		for(DigitalOutputPin digitalOutputPin : getOutputPins()) {
			digitalOutputPin.setLow();
		}
	}

	@Override
	public void test() {
		allOn();
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
		}
		
		allOff();
	}
	
	private void validateColorIndex(int colorIndex) {
		if(colorIndex >= getOutputPins().size()) {
			throw new BreezyApplicationException("Parameter, 'Color Index' is greater than the number of colors in the semaphore.");
		}
	}

}
