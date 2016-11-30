package com.windhaven_consulting.breezy.embeddedcontroller.extensions;

import java.util.List;
import java.util.Map;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;

public interface ExtensionProviderFactory {

	ExtensionProvider get(Map<String, String> properties);
	
	List<String> getPropertyFieldNames();

	List<BreezyPin> getAvailablePins();

	List<PropertyValueEnum> getProperties();

	List<PropertyValueEnum> getPropertyValues(PropertyValueEnum property);

	List<PropertyValueEnum> getPropertyValues(String property);
}
