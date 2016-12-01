package com.windhaven_consulting.breezy.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.annotation.ControlledMethod;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;

//@ControlledComponent(value="Mini Panel", numberOfOutputs=7)
public class MiniPanelProxy extends  GenericComponent<DigitalOutputPin> {
	static final Logger LOG = LoggerFactory.getLogger(MiniPanelProxy.class);

	private static final long serialVersionUID = 1L;
	
	private static final int FORWARD_PIN = 0;
	private static final int REVERSE_PIN = 1;
	private static final int BELL_ON_PIN = 2;
	private static final int BELL_OFF_PIN = 3;
	private static final int HORN_ON_PIN = 4;
	private static final int HORN_OFF_PIN = 5;
	private static final int STOP_PIN = 6;

	@ControlledMethod("Forward")
	public void forward() {
		getOutputPin(FORWARD_PIN).pulse(50, true);
	}

	@ControlledMethod("Reverse")
	public void reverse() {
		getOutputPin(REVERSE_PIN).pulse(50, true);
	}

	@ControlledMethod("Bell On")
	public void bellOn() {
		getOutputPin(BELL_ON_PIN).pulse(50, true);
	}

	@ControlledMethod("Bell Off")
	public void bellOff() {
		getOutputPin(BELL_OFF_PIN).pulse(50, true);
	}

	@ControlledMethod("Horn On")
	public void hornOn() {
		getOutputPin(HORN_ON_PIN).pulse(50, true);
	}

	@ControlledMethod("Horn Off")
	public void hornOff() {
		getOutputPin(HORN_OFF_PIN).pulse(50, true);
	}

	@ControlledMethod("Stop")
	public void stop() {
		getOutputPin(STOP_PIN).pulse(50, true);
	}

	
	@Override
	public void test() {
		bellOn();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
		}
		bellOff();
	}
}
