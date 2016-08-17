package com.windhaven_consulting.breezy.component.lights;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.Component;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;

public abstract class AbstractSemaphore extends Component {
	static final Logger LOG = LoggerFactory.getLogger(AbstractSemaphore.class);

	private static final long serialVersionUID = 1L;

	public void turnOn(UUID digitalOutputPinId) {
		allOff();
		
		DigitalOutputPin digitalOutputPin = getDigitalOutputPin(digitalOutputPinId);
		digitalOutputPin.setHigh();
	}

	public void turnOff(UUID digitalOutputPinId) {
		DigitalOutputPin digitalOutputPin = getDigitalOutputPin(digitalOutputPinId);
		digitalOutputPin.setLow();
	}
	
	public void blink(UUID digitalOutputPinId, long onTime) {
		allOff();
		
		DigitalOutputPin digitalOutputPin = getDigitalOutputPin(digitalOutputPinId);
		digitalOutputPin.blink(onTime);
	}
	
	public void blink(UUID digitalOutputPinId, long onTime, long duration, boolean blockToCompletion) {
		allOff();
		
		DigitalOutputPin digitalOutputPin = getDigitalOutputPin(digitalOutputPinId);
		digitalOutputPin.blink(onTime, duration, PinState.HIGH, blockToCompletion);
	}

	public void pulse(UUID digitalOutputPinId, long duration, boolean blockToCompletion) {
		allOff();

		DigitalOutputPin digitalOutputPin = getDigitalOutputPin(digitalOutputPinId);
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

}
