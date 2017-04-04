package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import com.pi4j.io.gpio.GpioController;

public class MockGpioControllerFactory {

	private static GpioController mockGpioController;

	public static GpioController getController() {
		if(mockGpioController == null) {
			mockGpioController = new MockGpioControllerImpl();
		}
		
		return mockGpioController;
	}

}
