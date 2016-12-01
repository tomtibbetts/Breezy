package com.windhaven_consulting.breezy.component.motors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.GenericComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledMethod;
import com.windhaven_consulting.breezy.embeddedcontroller.OutputType;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;

@ControlledComponent(value="Simple Servo Motor",
numberOfOutputs=1,
pinNames={"PWM 1"},
outputType=OutputType.PWM_OUTPUT)
public class SimpleServoMotor extends GenericComponent<PWMOutputPin> {
	private static final Logger LOG = LoggerFactory.getLogger(SimpleServoMotor.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int MINIMUM_SERVO_DURATION = 900;
	private static final int NEUTRAL_SERVO_DURATION = 1500;
	private static final int MAXIMUM_SERVO_DURATION = 2100;

	@ControlledMethod("Move to Minimum Position")
	public void moveToMinimum() {
		PWMOutputPin pwmOutputPin = getOutputPin();
		pwmOutputPin.setPwm(MINIMUM_SERVO_DURATION);
	}

	@ControlledMethod("Move to Maximum Position")
	public void moveToMaximum() {
		PWMOutputPin pwmOutputPin = getOutputPin();
		pwmOutputPin.setPwm(MAXIMUM_SERVO_DURATION);
	}
	
	@ControlledMethod("Move to Neutral Position")
	public void moveToNeutral() {
		PWMOutputPin pwmOutputPin = getOutputPin();
		pwmOutputPin.setPwm(NEUTRAL_SERVO_DURATION);
	}
	
	@Override
	public void test() {
		try {
			moveToNeutral();
			Thread.sleep(1000);
			
			moveToMaximum();
			Thread.sleep(1000);

			moveToMinimum();
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
		}
	}

}
