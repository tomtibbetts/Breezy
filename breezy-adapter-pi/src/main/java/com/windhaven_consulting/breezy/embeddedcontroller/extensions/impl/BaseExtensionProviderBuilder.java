package com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPinProperty;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;
import com.windhaven_consulting.breezy.embeddedcontroller.StateChange;
import com.windhaven_consulting.breezy.embeddedcontroller.StateChangeEvent;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JControllerProxyImpl;

public class BaseExtensionProviderBuilder {
	@Resource
	private Boolean windowsEnvironment;
	
	@Inject
	private Pi4JControllerProxyImpl pi4JControllerProxy;

	@Inject
	@StateChange
	private Event<StateChangeEvent> events;

	private GpioPinListenerDigital gpioPinListenerDigital;

	@PostConstruct
	public void postConstruct() {
		initializeInputListener();
	}
	
	private void initializeInputListener() {
		gpioPinListenerDigital = new GpioPinListenerDigital() {
		
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				GpioPin gpioPin = event.getPin();
				com.pi4j.io.gpio.PinState pinState = event.getState();
				
				StateChangeEvent stateChangeEvent = new StateChangeEvent();
				stateChangeEvent.setName(gpioPin.getProperty(BreezyPinProperty.NAME.name()));
				stateChangeEvent.setId(gpioPin.getProperty(BreezyPinProperty.ID.name()));
				stateChangeEvent.setPinState(PinState.find(pinState.getValue()));

				events.fire(stateChangeEvent);
			}
		}; 
	}

	protected boolean isWindowsEnvironment() {
		return windowsEnvironment != null && windowsEnvironment == true;
	}
	
	protected GpioPinListenerDigital getInputListener() {
		return gpioPinListenerDigital;
	}
	
	protected GpioController getGpioController() {
		return pi4JControllerProxy.getGpioController();
	}
	
}
