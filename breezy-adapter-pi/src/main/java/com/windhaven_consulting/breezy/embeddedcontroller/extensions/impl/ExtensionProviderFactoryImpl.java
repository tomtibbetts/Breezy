package com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderBuilder;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionType;

public class ExtensionProviderFactoryImpl implements ExtensionProviderFactory {

	@Inject
	@Named("systemExtensionProviderBuilder")
	private ExtensionProviderBuilder systemExtensionProviderBuilder;
	
	@Inject
	@Named("mcp23S08ExtensionProviderBuilder")
	private ExtensionProviderBuilder mcp23S08ExtensionProviderBuilder;
	
	@Inject
	@Named("mcp23017ExtensionProviderBuilder")
	private ExtensionProviderBuilder mcp23017ExtensionProviderBuilder;
	
	@Inject
	@Named("mcp23S17ExtensionProviderBuilder")
	private ExtensionProviderBuilder mcp23S17ExtensionProviderBuilder;
	
	@Inject
	@Named("pca9685ExtensionProviderBuilder")
	private ExtensionProviderBuilder pca9685ExtensionProviderBuilder;
	
	private Map<ExtensionType, ExtensionProviderBuilder> extensionTypeToProviderBuilderMap = new HashMap<ExtensionType, ExtensionProviderBuilder>();;
	
	@PostConstruct
	public void postConstruct() {
		extensionTypeToProviderBuilderMap.put(ExtensionType.SYSTEM, systemExtensionProviderBuilder);
		extensionTypeToProviderBuilderMap.put(ExtensionType.MCP23S08, mcp23S08ExtensionProviderBuilder);
		extensionTypeToProviderBuilderMap.put(ExtensionType.MCP23017, mcp23017ExtensionProviderBuilder);
		extensionTypeToProviderBuilderMap.put(ExtensionType.MCP23S17, mcp23S17ExtensionProviderBuilder);
		extensionTypeToProviderBuilderMap.put(ExtensionType.PCA9685, pca9685ExtensionProviderBuilder);
	}
	
	@Override
	/**
	 * returns a new instance of an ExtensionProvider (prototype pattern)
	 */
	public ExtensionProvider getNewExtensionProvider(ExtensionType extensionType, Map<String, String> properties) {
		ExtensionProviderBuilder extensionProviderBuilder = getExtensionProviderBuilder(extensionType);
		ExtensionProvider extensionProvider = extensionProviderBuilder.get(properties);
		
		return extensionProvider;
	}

	@Override
	public List<String> getPropertyFieldNames(ExtensionType extensionType) {
		ExtensionProviderBuilder extensionProviderBuilder = getExtensionProviderBuilder(extensionType);
		return extensionProviderBuilder.getPropertyFieldNames();
	}

	@Override
	public List<BreezyPin> getAvailablePins(ExtensionType extensionType) {
		ExtensionProviderBuilder extensionProviderBuilder = getExtensionProviderBuilder(extensionType);
		return extensionProviderBuilder.getAvailablePins();
	}

	@Override
	public List<PropertyValueEnum> getProperties(ExtensionType extensionType) {
		ExtensionProviderBuilder extensionProviderBuilder = getExtensionProviderBuilder(extensionType);
		return extensionProviderBuilder.getProperties();
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(ExtensionType extensionType, PropertyValueEnum property) {
		ExtensionProviderBuilder extensionProviderBuilder = getExtensionProviderBuilder(extensionType);
		return extensionProviderBuilder.getPropertyValues(property);
	}
	
	@Override
	public List<PropertyValueEnum> getPropertyValues(ExtensionType extensionType, String property) {
		ExtensionProviderBuilder extensionProviderBuilder = getExtensionProviderBuilder(extensionType);
		return extensionProviderBuilder.getPropertyValues(property);
	}

	private ExtensionProviderBuilder getExtensionProviderBuilder(ExtensionType extensionType) {
		ExtensionProviderBuilder extensionProviderBuilder = extensionTypeToProviderBuilderMap.get(extensionType);

		if(extensionProviderBuilder == null) {
			throw new EmbeddedControllerException("No Extension Provider Builder found for '" + extensionType.name() + "'.");
		}
		
		return extensionProviderBuilder;
	}
}
