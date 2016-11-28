package com.windhaven_consulting.breezy.embeddedcontroller.extensions.pca.PCA9685.impl;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderBuilder;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl.BaseExtensionProviderBuilder;

@Named("pca9685ExtensionProviderBuilder")
@ApplicationScoped
public class PCA9685ExtensionProviderBuilderImpl extends BaseExtensionProviderBuilder implements ExtensionProviderBuilder {

	@Override
	public ExtensionProvider get(Map<String, String> properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getPropertyFieldNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BreezyPin> getAvailablePins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PropertyValueEnum> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(PropertyValueEnum property) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(String property) {
		// TODO Auto-generated method stub
		return null;
	}

}
