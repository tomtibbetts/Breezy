package com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl;

import java.util.Arrays;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezySPIChannel;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.SPIBusProperty;

public abstract class SPIBusExtensionProviderFactory extends BaseExtensionProviderFactory {
	
	public void postConstruct() {
		super.postConstruct();
		addProperties(SPIBusProperty.CHANNEL, Arrays.asList(BreezySPIChannel.values()));
	}

}
