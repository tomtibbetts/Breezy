package com.windhaven_consulting.breezy.component.motors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.Component;
import com.windhaven_consulting.breezy.component.annotation.ControlledComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledMethod;

@ControlledComponent(value = "Stall Motor", pinNames={"Motor Out"})
public class StallMotor extends Component {
	static final Logger LOG = LoggerFactory.getLogger(StallMotor.class);

	private static final long serialVersionUID = 1L;

	@ControlledMethod("Normal")
	public void normal() {
		getOutputPin().setHigh();
	}
	
	@ControlledMethod("Reverse")
	public void reverse() {
		getOutputPin().setLow();
	}
	
	@ControlledMethod("Toggle")
	public void toggle() {
		getOutputPin().toggle();
	}

	@Override
	public void test() {
		normal();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
		}
		reverse();
	}
}
