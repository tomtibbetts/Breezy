package com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl;

import java.util.Arrays;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyI2CBus;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.I2CBusProperty;

public abstract class I2CBusExtensionProviderBuilder extends BaseExtensionProviderBuilder {
	
	public void postConstruct() {
		super.postConstruct();
		addProperties(I2CBusProperty.BUS_NUMBER, Arrays.asList(BreezyI2CBus.values()));
	}

}
