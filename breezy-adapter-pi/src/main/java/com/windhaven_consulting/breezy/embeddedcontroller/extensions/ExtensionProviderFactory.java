package com.windhaven_consulting.breezy.embeddedcontroller.extensions;

import java.util.List;
import java.util.Map;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;

public interface ExtensionProviderFactory<T extends BreezyPin> {

	ExtensionProvider<T> getExtensionProvider(GpioController gpioController, GpioProvider gpioProvider, GpioPinListenerDigital gpioPinListenerDigital);
	
	List<String> getPropertyFieldNames();

	List<BreezyPin> getAvailablePins();

	List<PropertyValueEnum> getProperties();

	List<PropertyValueEnum> getPropertyValues(PropertyValueEnum property);

	List<PropertyValueEnum> getPropertyValues(String property);

	void validateProperties(Map<String, String> properties);

	GpioProvider getGpioProvider(Map<String, String> properties);

}
