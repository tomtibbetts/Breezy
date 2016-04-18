package com.windhaven_consulting.breezy.manager.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionType;
import com.windhaven_consulting.breezy.manager.ExtensionProviderManager;

@ApplicationScoped
public class ExtensionProviderManagerImpl implements ExtensionProviderManager {

	@Inject
	private ExtensionProviderFactory extensionProviderFactory;
	
	@Override
	public List<String> getPropertyFieldNames(ExtensionType extensionType) {
		return extensionProviderFactory.getPropertyFieldNames(extensionType);
	}

	@Override
	public List<BreezyPin> getAvailablePins(ExtensionType extensionType) {
		return extensionProviderFactory.getAvailablePins(extensionType);
	}

	@Override
	public List<PropertyValueEnum> getProperties(ExtensionType extensionType) {
		return extensionProviderFactory.getProperties(extensionType);
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(ExtensionType extensionType, PropertyValueEnum property) {
		return extensionProviderFactory.getPropertyValues(extensionType, property);
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(ExtensionType extensionType, String property) {
		return extensionProviderFactory.getPropertyValues(extensionType, property);
	}

}
