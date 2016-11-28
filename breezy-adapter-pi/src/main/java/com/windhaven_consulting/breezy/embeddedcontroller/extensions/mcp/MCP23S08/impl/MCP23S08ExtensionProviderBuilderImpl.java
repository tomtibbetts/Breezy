package com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23S08.impl;

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
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl.BaseExtensionProviderBuilder;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23S08.MCP23S08Address;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23S08.MCP23S08Pin;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mcp.MCP23S08.MCP23S08Property;


@Named("mcp23S08ExtensionProviderBuilder")
@ApplicationScoped
public class MCP23S08ExtensionProviderBuilderImpl extends BaseExtensionProviderBuilder implements ExtensionProviderBuilder {

	private Map<MCP23S08Property, List<PropertyValueEnum>> propertyValueByPropertyMap = new HashMap<MCP23S08Property, List<PropertyValueEnum>>();

	@PostConstruct
	public void postConstruct() {
		super.postConstruct();
		
		propertyValueByPropertyMap.put(MCP23S08Property.ADDRESS, Arrays.asList(MCP23S08Address.values()));
		propertyValueByPropertyMap.put(MCP23S08Property.CHANNEL, Arrays.asList(BreezySPIChannel.values()));
	}
	
	@Override
	public ExtensionProvider get(Map<String, String> properties) {
		return new MCP23S08ExtensionProviderImpl(getGpioController(), getInputListener(), properties, isWindowsEnvironment());
	}

	@Override
	public List<String> getPropertyFieldNames() {
		List<String> propertyFieldNames = new ArrayList<String>();
		
		for(MCP23S08Property mcp23S08Property : MCP23S08Property.values()) {
			propertyFieldNames.add(mcp23S08Property.name());
		}
		
		return propertyFieldNames;
	}

	@Override
	public List<BreezyPin> getAvailablePins() {
		return MCP23S08Pin.getPins();
	}

	@Override
	public List<PropertyValueEnum> getProperties() {
		return Arrays.asList(MCP23S08Property.values());
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(PropertyValueEnum property) {
		MCP23S08Property mcp23S08Property = (MCP23S08Property) property;
		return propertyValueByPropertyMap.get(mcp23S08Property);
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(String property) {
		MCP23S08Property mcp23S08Property = MCP23S08Property.valueOf(property);
		return propertyValueByPropertyMap.get(mcp23S08Property);
	}

}
