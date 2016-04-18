package com.windhaven_consulting.breezy.embeddedcontroller.extensions.MCP23017.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyI2CBus;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderBuilder;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.MCP23017.MCP23017Address;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.MCP23017.MCP23017Pin;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.MCP23017.MCP23017Property;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl.BaseExtensionProviderBuilder;


@Named("mcp23017ExtensionProviderBuilder")
@ApplicationScoped
public class MCP23017ExtensionProviderBuilderImpl extends BaseExtensionProviderBuilder implements ExtensionProviderBuilder {

	private Map<MCP23017Property, List<PropertyValueEnum>> propertyValueByPropertyMap = new HashMap<MCP23017Property, List<PropertyValueEnum>>();

	@PostConstruct
	public void postConstruct() {
		propertyValueByPropertyMap.put(MCP23017Property.ADDRESS, Arrays.asList(MCP23017Address.values()));
		propertyValueByPropertyMap.put(MCP23017Property.BUS_NUMBER, Arrays.asList(BreezyI2CBus.values()));
	}
	
	@Override
	public ExtensionProvider get(Map<String, String> properties) {
		return new MCP23017ExtensionProviderImpl(getGpioController(), getInputListener(), properties, isWindowsEnvironment());
	}

	@Override
	public List<String> getPropertyFieldNames() {
		List<String> propertyFieldNames = new ArrayList<String>();
		
		for(MCP23017Property mcp23017Property : MCP23017Property.values()) {
			propertyFieldNames.add(mcp23017Property.name());
		}
		
		return propertyFieldNames;
	}

	@Override
	public List<BreezyPin> getAvailablePins() {
		return MCP23017Pin.getPins();
	}

	@Override
	public List<PropertyValueEnum> getProperties() {
		return Arrays.asList(MCP23017Property.values());
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(PropertyValueEnum property) {
		MCP23017Property mcp23017Property = (MCP23017Property) property;
		return propertyValueByPropertyMap.get(mcp23017Property);
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(String property) {
		MCP23017Property mcp23017Property = MCP23017Property.valueOf(property);
		return propertyValueByPropertyMap.get(mcp23017Property);
	}

}
