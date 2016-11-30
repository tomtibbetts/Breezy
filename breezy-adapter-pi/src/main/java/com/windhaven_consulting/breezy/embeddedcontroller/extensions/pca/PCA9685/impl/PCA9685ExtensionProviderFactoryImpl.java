package com.windhaven_consulting.breezy.embeddedcontroller.extensions.pca.PCA9685.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.I2CBusProperty;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl.I2CBusExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.pca.PCA9685.PCA9685Address;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.pca.PCA9685.PCA9685Pin;

@Named("pca9685ExtensionProviderFactory")
@ApplicationScoped
public class PCA9685ExtensionProviderFactoryImpl extends I2CBusExtensionProviderFactory implements ExtensionProviderFactory {
	
	@PostConstruct
	public void postConstruct() {
		super.postConstruct();
		
		addProperties(I2CBusProperty.ADDRESS, Arrays.asList(PCA9685Address.values()));
	}

	@Override
	public ExtensionProvider get(Map<String, String> properties) {
		return new PCA9685ExtensionProviderImpl(getGpioController(), getInputListener(), properties, isWindowsEnvironment());
	}

	@Override
	public List<BreezyPin> getAvailablePins() {
		return PCA9685Pin.getPins();
	}

}
