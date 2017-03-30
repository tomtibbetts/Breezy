package com.windhaven_consulting.breezy.embeddedcontroller.extensions;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class BaseExtensionProvider {
	private GpioController gpioController;
	private GpioPinListenerDigital gpioPinListenerDigital;
	private GpioProvider gpioProvider;
	
	protected GpioController getGpioController() {
		return gpioController;
	}
	
	protected void setGpioController(GpioController gpioController) {
		this.gpioController = gpioController;
	}
	
	protected GpioPinListenerDigital getGpioPinListenerDigital() {
		return gpioPinListenerDigital;
	}
	
	protected void setGpioPinListenerDigital(GpioPinListenerDigital gpioPinListenerDigital) {
		this.gpioPinListenerDigital = gpioPinListenerDigital;
	}
	
	protected GpioProvider getGpioProvider() {
		return gpioProvider;
	}
	
	protected void setGpioProvider(GpioProvider gpioProvider) {
		this.gpioProvider = gpioProvider;
	}

	
}
