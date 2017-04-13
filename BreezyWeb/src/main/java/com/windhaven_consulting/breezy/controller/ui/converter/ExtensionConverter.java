package com.windhaven_consulting.breezy.controller.ui.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.manager.viewobject.Extension;

public class ExtensionConverter implements Converter {
	private Map<String, Extension> nameToExtensionMap;

	public ExtensionConverter(Map<String, Extension> nameToExtensionMap) {
		this.nameToExtensionMap = nameToExtensionMap;
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String name) {
		Extension result = null;

		if(StringUtils.isNotEmpty(name)) {
			result = nameToExtensionMap.get(name);
		}
		
		return result;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String extensionName = StringUtils.EMPTY;
		
		if(value != null && !value.equals("")) {
			extensionName = ((Extension) value).getName();
		}
		
		return extensionName;
	}

}
