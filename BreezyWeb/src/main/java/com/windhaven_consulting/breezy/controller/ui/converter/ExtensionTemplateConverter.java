package com.windhaven_consulting.breezy.controller.ui.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.manager.viewobject.ExtensionTemplate;

public class ExtensionTemplateConverter implements Converter {
	
	private Map<String, ExtensionTemplate> nameToExtensionTemplateMap;

	public ExtensionTemplateConverter(Map<String, ExtensionTemplate> nameToExtensionTemplateMap) {
		this.nameToExtensionTemplateMap = nameToExtensionTemplateMap;
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String name) {
		ExtensionTemplate result = null;
		
		if(StringUtils.isNotEmpty(name)) {
			result = nameToExtensionTemplateMap.get(name);
		}
		
		return result;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String extensionTemplateName = StringUtils.EMPTY;
		
		if(value != null && !value.equals("")) {
			extensionTemplateName = ((ExtensionTemplate) value).getName();
		}
		
		return extensionTemplateName;
	}

}
