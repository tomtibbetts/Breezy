package com.windhaven_consulting.breezy.manager;

import java.util.List;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionType;

public interface ExtensionProviderManager {

	List<String> getPropertyFieldNames(ExtensionType extensionType);
	
	List<BreezyPin> getAvailablePins(ExtensionType extensionType);

	List<PropertyValueEnum> getProperties(ExtensionType extensionType);

	List<PropertyValueEnum> getPropertyValues(ExtensionType extensionType, PropertyValueEnum property);

	List<PropertyValueEnum> getPropertyValues(ExtensionType extensionType, String property);
}
