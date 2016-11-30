package com.windhaven_consulting.breezy.embeddedcontroller.extensions.system.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl.BaseExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.system.SystemPin;

@Named("systemExtensionProviderFactory")
@ApplicationScoped
public class SystemExtensionProviderFactoryImpl extends BaseExtensionProviderFactory implements ExtensionProviderFactory {
	
	@Override
	public ExtensionProvider get(Map<String, String> properties) {
		ExtensionProvider extensionProvider = new SystemExtensionProviderImpl(getGpioController(), getInputListener(), properties, isWindowsEnvironment());
		
		return extensionProvider;
	}

	@Override
	public List<String> getPropertyFieldNames() {
		return Collections.emptyList();
	}

	@Override
	public List<BreezyPin> getAvailablePins() {
		return SystemPin.getPins();
	}

	@Override
	public List<PropertyValueEnum> getProperties() {
		return Collections.emptyList();
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(PropertyValueEnum property) {
		return Collections.emptyList();
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(String property) {
		return Collections.emptyList();
	}

}
