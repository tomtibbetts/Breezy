package com.windhaven_consulting.breezy.embeddedcontroller.extensions.MCP23S17.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezySPIChannel;
import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderBuilder;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.MCP23S17.MCP23S17Address;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.MCP23S17.MCP23S17Pin;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.MCP23S17.MCP23S17Property;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl.BaseExtensionProviderBuilder;


@Named("mcp23S17ExtensionProviderBuilder")
@ApplicationScoped
public class MCP23S17ExtensionProviderBuilderImpl extends BaseExtensionProviderBuilder implements ExtensionProviderBuilder {

	private Map<MCP23S17Property, List<PropertyValueEnum>> propertyValueByPropertyMap = new HashMap<MCP23S17Property, List<PropertyValueEnum>>();

	@PostConstruct
	public void postConstruct() {
		super.postConstruct();
		
		propertyValueByPropertyMap.put(MCP23S17Property.ADDRESS, Arrays.asList(MCP23S17Address.values()));
		propertyValueByPropertyMap.put(MCP23S17Property.CHANNEL, Arrays.asList(BreezySPIChannel.values()));
	}
	
	@Override
	public ExtensionProvider get(Map<String, String> properties) {
		return new MCP23S17ExtensionProviderImpl(getGpioController(), getInputListener(), properties, isWindowsEnvironment());
	}

	@Override
	public List<String> getPropertyFieldNames() {
		List<String> propertyFieldNames = new ArrayList<String>();
		
		for(MCP23S17Property mcp23S17Property : MCP23S17Property.values()) {
			propertyFieldNames.add(mcp23S17Property.name());
		}
		
		return propertyFieldNames;
	}

	@Override
	public List<BreezyPin> getAvailablePins() {
		return MCP23S17Pin.getPins();
	}

	@Override
	public List<PropertyValueEnum> getProperties() {
		return Arrays.asList(MCP23S17Property.values());
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(PropertyValueEnum property) {
		MCP23S17Property mcp23S17Property = (MCP23S17Property) property;
		return propertyValueByPropertyMap.get(mcp23S17Property);
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(String property) {
		MCP23S17Property mcp23S17Property = MCP23S17Property.valueOf(property);
		return propertyValueByPropertyMap.get(mcp23S17Property);
	}

}
