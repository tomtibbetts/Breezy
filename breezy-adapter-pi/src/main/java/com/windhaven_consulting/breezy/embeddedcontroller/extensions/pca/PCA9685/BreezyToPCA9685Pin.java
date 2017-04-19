package com.windhaven_consulting.breezy.embeddedcontroller.extensions.pca.PCA9685;

import java.util.HashMap;
import java.util.Map;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerRuntimeException;

public class BreezyToPCA9685Pin {
	private static Map<BreezyPin, com.pi4j.io.gpio.Pin> breezyToPCA9685Pin = new HashMap<BreezyPin, com.pi4j.io.gpio.Pin>();
	
	static {
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_00, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_00);
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_01, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_01);
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_02, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_02);
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_03, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_03);
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_04, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_04);
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_05, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_05);
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_06, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_06);
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_07, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_07);
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_08, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_08);
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_09, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_09);
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_10, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_10);
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_11, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_11);
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_12, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_12);
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_13, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_13);
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_14, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_14);
		breezyToPCA9685Pin.put(PCA9685Pin.PWM_15, com.pi4j.gpio.extension.pca.PCA9685Pin.PWM_15);
	}
	
	public static com.pi4j.io.gpio.Pin getPin(BreezyPin breezyPin) {
		com.pi4j.io.gpio.Pin pin = breezyToPCA9685Pin.get(breezyPin);
		
		if(pin == null) {
			throw new EmbeddedControllerRuntimeException("Breezy Pin, '" + breezyPin.toString() + "' has no Pi4J (Raspberry Pi) equivalent.");
		}
		
		return pin;
	}

}
