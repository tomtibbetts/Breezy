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
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderAbstractFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionType;

public class ExtensionProviderAbstractFactoryImpl implements ExtensionProviderAbstractFactory {

	@Inject
	@Named("systemExtensionProviderFactory")
	private ExtensionProviderFactory systemExtensionProviderFactory;
	
	@Inject
	@Named("mcp23S08ExtensionProviderFactory")
	private ExtensionProviderFactory mcp23S08ExtensionProviderFactory;
	
	@Inject
	@Named("mcp23017ExtensionProviderFactory")
	private ExtensionProviderFactory mcp23017ExtensionProviderFactory;
	
	@Inject
	@Named("mcp23S17ExtensionProviderFactory")
	private ExtensionProviderFactory mcp23S17ExtensionProviderFactory;
	
	@Inject
	@Named("pca9685ExtensionProviderFactory")
	private ExtensionProviderFactory pca9685ExtensionProviderFactory;
	
	private Map<ExtensionType, ExtensionProviderFactory> extensionTypeToProviderFactoryMap = new HashMap<ExtensionType, ExtensionProviderFactory>();;
	
	@PostConstruct
	public void postConstruct() {
		extensionTypeToProviderFactoryMap.put(ExtensionType.SYSTEM, systemExtensionProviderFactory);
		extensionTypeToProviderFactoryMap.put(ExtensionType.MCP23S08, mcp23S08ExtensionProviderFactory);
		extensionTypeToProviderFactoryMap.put(ExtensionType.MCP23017, mcp23017ExtensionProviderFactory);
		extensionTypeToProviderFactoryMap.put(ExtensionType.MCP23S17, mcp23S17ExtensionProviderFactory);
		extensionTypeToProviderFactoryMap.put(ExtensionType.PCA9685, pca9685ExtensionProviderFactory);
	}
	
	@Override
	/**
	 * returns a new instance of an ExtensionProvider (prototype pattern)
	 */
	public ExtensionProvider getNewExtensionProvider(ExtensionType extensionType, Map<String, String> properties) {
		ExtensionProviderFactory extensionProviderFactory = getExtensionProviderFactory(extensionType);
		ExtensionProvider extensionProvider = extensionProviderFactory.get(properties);
		
		return extensionProvider;
	}

	@Override
	public List<String> getPropertyFieldNames(ExtensionType extensionType) {
		ExtensionProviderFactory extensionProviderFactory = getExtensionProviderFactory(extensionType);
		return extensionProviderFactory.getPropertyFieldNames();
	}

	@Override
	public List<BreezyPin> getAvailablePins(ExtensionType extensionType) {
		ExtensionProviderFactory extensionProviderFactory = getExtensionProviderFactory(extensionType);
		return extensionProviderFactory.getAvailablePins();
	}

	@Override
	public List<PropertyValueEnum> getProperties(ExtensionType extensionType) {
		ExtensionProviderFactory extensionProviderFactory = getExtensionProviderFactory(extensionType);
		return extensionProviderFactory.getProperties();
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(ExtensionType extensionType, PropertyValueEnum property) {
		ExtensionProviderFactory extensionProviderFactory = getExtensionProviderFactory(extensionType);
		return extensionProviderFactory.getPropertyValues(property);
	}
	
	@Override
	public List<PropertyValueEnum> getPropertyValues(ExtensionType extensionType, String property) {
		ExtensionProviderFactory extensionProviderFactory = getExtensionProviderFactory(extensionType);
		return extensionProviderFactory.getPropertyValues(property);
	}

	private ExtensionProviderFactory getExtensionProviderFactory(ExtensionType extensionType) {
		ExtensionProviderFactory extensionProviderFactory = extensionTypeToProviderFactoryMap.get(extensionType);

		if(extensionProviderFactory == null) {
			throw new EmbeddedControllerException("No Extension Provider Factory found for '" + extensionType.name() + "'.");
		}
		
		return extensionProviderFactory;
	}
}
