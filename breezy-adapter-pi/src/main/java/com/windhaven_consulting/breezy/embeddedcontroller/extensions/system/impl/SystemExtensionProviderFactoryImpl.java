package com.windhaven_consulting.breezy.embeddedcontroller.extensions.system.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl.BaseExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.system.SystemPin;

@Named("systemExtensionProviderFactory")
@ApplicationScoped
public class SystemExtensionProviderFactoryImpl extends BaseExtensionProviderFactory<DigitalOutputPin> {
	
	@Override
	public ExtensionProvider<DigitalOutputPin> getExtensionProvider(GpioController gpioController, GpioProvider gpioProvider, GpioPinListenerDigital gpioPinListenerDigital) {
		return new SystemExtensionProviderImpl(gpioController, gpioProvider, gpioPinListenerDigital);
	}

	@Override
	public List<String> getPropertyFieldNames() {
		return Collections.emptyList();
	}

	@Override
	public List<BreezyPin> getAvailablePins() {
		return SystemPin.getPins();
	}

	@Override
	public List<PropertyValueEnum> getProperties() {
		return Collections.emptyList();
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(PropertyValueEnum property) {
		return Collections.emptyList();
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(String property) {
		return Collections.emptyList();
	}


	@Override
	public void validateProperties(Map<String, String> properties) {
		// Do nothing - there are no properties to validate
	}

	@Override
	public GpioProvider getGpioProvider(Map<String, String> properties) {
		// This provider does not have a Raspi provider analog
		return null;
	}

}
