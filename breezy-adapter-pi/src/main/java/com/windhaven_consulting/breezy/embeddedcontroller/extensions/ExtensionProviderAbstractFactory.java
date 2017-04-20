package com.windhaven_consulting.breezy.embeddedcontroller.extensions;

import java.util.List;
import java.util.Map;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;

public interface ExtensionProviderAbstractFactory {
	
	ExtensionProvider<BreezyPin> getNewExtensionProvider(ExtensionType extensionType, Map<String, String> properties) throws EmbeddedControllerException;
	
	List<String> getPropertyFieldNames(ExtensionType extensionType);

	List<BreezyPin> getAvailablePins(ExtensionType extensionType);

	List<PropertyValueEnum> getProperties(ExtensionType extensionType);

	List<PropertyValueEnum> getPropertyValues(ExtensionType extensionType, PropertyValueEnum property);

	List<PropertyValueEnum> getPropertyValues(ExtensionType extensionType, String property);
}
