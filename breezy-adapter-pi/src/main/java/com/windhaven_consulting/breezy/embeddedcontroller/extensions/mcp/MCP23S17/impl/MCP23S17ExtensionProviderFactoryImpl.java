package com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23S17.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.SPIBusProperty;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl.SPIBusExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23S17.MCP23S17Address;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23S17.MCP23S17Pin;


@Named("mcp23S17ExtensionProviderFactory")
@ApplicationScoped
public class MCP23S17ExtensionProviderFactoryImpl extends SPIBusExtensionProviderFactory implements ExtensionProviderFactory {

	@PostConstruct
	public void postConstruct() {
		super.postConstruct();
		
		addProperties(SPIBusProperty.ADDRESS, Arrays.asList(MCP23S17Address.values()));
	}
	
	@Override
	public ExtensionProvider get(Map<String, String> properties) {
		return new MCP23S17ExtensionProviderImpl(getGpioController(), getInputListener(), properties, isWindowsEnvironment());
	}

	@Override
	public List<BreezyPin> getAvailablePins() {
		return MCP23S17Pin.getPins();
	}
}
