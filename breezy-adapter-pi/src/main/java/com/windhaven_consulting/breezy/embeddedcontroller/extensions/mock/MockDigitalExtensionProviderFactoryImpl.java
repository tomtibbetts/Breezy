package com.windhaven_consulting.breezy.embeddedcontroller.extensions.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl.BaseExtensionProviderFactory;

public class MockDigitalExtensionProviderFactoryImpl extends BaseExtensionProviderFactory<BreezyPin> {

	private List<BreezyPin> pins = new ArrayList<BreezyPin>();
	
	public MockDigitalExtensionProviderFactoryImpl(ExtensionProviderFactory<BreezyPin> extensionProviderFactory) {
		
		for(PropertyValueEnum propertyValueEnum : extensionProviderFactory.getProperties()) {
			addProperties(propertyValueEnum, extensionProviderFactory.getPropertyValues(propertyValueEnum));
		}
		
		pins.addAll(extensionProviderFactory.getAvailablePins());
	}

	@Override
	public ExtensionProvider<BreezyPin> getExtensionProvider(GpioController gpioController, GpioProvider gpioProvider, GpioPinListenerDigital gpioPinListenerDigital) {
		return new MockDigitalExtensionProviderImpl();
	}

	@Override
	public List<BreezyPin> getAvailablePins() {
		return pins;
	}

	@Override
	public void validateProperties(Map<String, String> properties) throws EmbeddedControllerException {
		for(String propertyKey : properties.keySet()) {
			if(StringUtils.isEmpty(properties.get(propertyKey))) {
				throw new EmbeddedControllerException("MockDigitalExtensionProviderFactory:: Property value for key: '" + propertyKey + "' is not provided.");
			}
		}
	}

	@Override
	public GpioProvider getGpioProvider(Map<String, String> properties) {
		return null;
	}


}
