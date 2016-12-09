package com.windhaven_consulting.breezy.component.lights;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.GenericComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledMethod;
import com.windhaven_consulting.breezy.component.annotation.ControlledParameter;
import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;
import com.windhaven_consulting.breezy.embeddedcontroller.OutputType;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMPinState;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;

@ControlledComponent(value="PWM LED",
numberOfOutputs=1,
pinNames={"PWM LED"},
outputType=OutputType.PWM_OUTPUT)
public class PWMLed extends GenericComponent<PWMOutputPin> {

	private static final Logger LOG = LoggerFactory.getLogger(PWMLed.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ControlledMethod("Turn On")
	public void turnOn() {
		PWMOutputPin pwmOutputPin = getOutputPin();
		pwmOutputPin.setAlwaysOn();
	}

	@ControlledMethod("Turn Off")
	public void turnOff() {
		PWMOutputPin pwmOutputPin = getOutputPin();
		pwmOutputPin.setAlwaysOff();
	}
	
	@ControlledMethod("Blink Forever")
	public void blink(@ControlledParameter(name = "On Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long onTime,
			@ControlledParameter(name = "Start State", parameterFieldType = ParameterFieldType.LOGIC_STATE) PinState startState) {
		PWMPinState pwmPinState = (startState == null ? PWMPinState.HIGH : PWMPinState.LOW);

		getOutputPin().blink(onTime, pwmPinState);
	}
	
	@ControlledMethod("Blink Timed")
	public void blink(@ControlledParameter(name = "On Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long onTime,
			@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration,
			@ControlledParameter(name = "Start State", parameterFieldType = ParameterFieldType.PIN_STATE) PinState startState,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {
		PWMPinState pwmPinState = (startState == null ? PWMPinState.HIGH : PWMPinState.LOW);
		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);
		
		getOutputPin().blink(onTime, duration, pwmPinState, blockToCompletion);
	}
	
	@ControlledMethod("Pulse")
	public void pulse(@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration,
			@ControlledParameter(name = "Start State", parameterFieldType = ParameterFieldType.PIN_STATE) PinState startState,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {
		PWMPinState pwmPinState = (startState == null ? PWMPinState.HIGH : PWMPinState.LOW);
		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);

		getOutputPin().pulse(duration, pwmPinState, blockToCompletion);
	}
	
	@ControlledMethod("Pulsate")
	public void pulsate(@ControlledParameter(name = "Attack (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long attack,
			@ControlledParameter(name = "Sustain (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long sustain,
			@ControlledParameter(name = "Release (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long release,
			@ControlledParameter(name = "Interval (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER) Long interval,
			@ControlledParameter(name = "Max Brightness (0% - 100%)", parameterFieldType = ParameterFieldType.NUMBER) Integer maxBrightness) {

		interval = (interval == null ? 0 : interval);
		maxBrightness = (maxBrightness == null ? 100 : maxBrightness);
		
		getOutputPin().pulsate(attack, sustain, release, interval, maxBrightness);
	}
	
	// use percentage and change to setBrightness
	@ControlledMethod("Set Brightness")  // pulses the LED in microseconds each cycle effectively setting brightness
	public void setBrightness(@ControlledParameter(name = "Brightness (0% - 100%)", parameterFieldType = ParameterFieldType.NUMBER, required = true) Integer brightness) {
		brightness = (brightness == null ? 100 : brightness);

		PWMOutputPin pwmOutputPin = getOutputPin();
		
		if(brightness == 0) {
			pwmOutputPin.setAlwaysOff();
		}
		else if(brightness == 100) {
			pwmOutputPin.setAlwaysOn();
		}
		else {
			pwmOutputPin.setBrightness(brightness);
		}
	}

	/**
	 * Stops all blinking and pulsating
	 */
	@ControlledMethod("Stop")
	public void stop() {
		getOutputPin().stop();
	}
	
	/**
	 * ramp brightness up to/down to a specific level over the specified attack time
	 * If the current brightness is less than the new value, ramp up.
	 * If the current brightness is more than the new value, ramp down.
	 * Otherwise, leave it unchanged
	 * 
	 * TODO: add block to completion
	 */
	@ControlledMethod("Dim to")
	public void dimTo(@ControlledParameter(name = "Attack (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long attack,
			@ControlledParameter(name = "Brightness (0% - 100%)", parameterFieldType = ParameterFieldType.NUMBER, required = true) Integer brightness,
			@ControlledParameter(name = "Wait Until Done", parameterFieldType = ParameterFieldType.LOGIC_STATE) Boolean blockToCompletion) {

		blockToCompletion = (blockToCompletion == null ? false : blockToCompletion);
		getOutputPin().dimTo(attack, brightness, blockToCompletion);
	}
	
	@Override
	public void test() {
		try {
			turnOn();
			Thread.sleep(1000);
			turnOff();
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
		}
	}

}
