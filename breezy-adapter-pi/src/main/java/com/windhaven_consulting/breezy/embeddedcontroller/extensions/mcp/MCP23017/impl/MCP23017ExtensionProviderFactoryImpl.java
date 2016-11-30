package com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23017.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl.I2CBusExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23017.MCP23017Address;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23017.MCP23017Pin;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23017.MCP23017Property;


@Named("mcp23017ExtensionProviderFactory")
@ApplicationScoped
public class MCP23017ExtensionProviderFactoryImpl extends I2CBusExtensionProviderFactory implements ExtensionProviderFactory {

	@PostConstruct
	public void postConstruct() {
		super.postConstruct();

		addProperties(MCP23017Property.ADDRESS, Arrays.asList(MCP23017Address.values()));
	}
	
	@Override
	public ExtensionProvider get(Map<String, String> properties) {
		return new MCP23017ExtensionProviderImpl(getGpioController(), getInputListener(), properties, isWindowsEnvironment());
	}

	@Override
	public List<BreezyPin> getAvailablePins() {
		return MCP23017Pin.getPins();
	}

}
