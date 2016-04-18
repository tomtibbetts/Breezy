package com.windhaven_consulting.breezy.controller.ui.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;

public class ExtensionPropertyKeyConverter implements Converter {

	private List<PropertyValueEnum> propertyValues;

	public ExtensionPropertyKeyConverter(List<PropertyValueEnum> propertyValues) {
		this.propertyValues = propertyValues;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String key = (String) value;
		String result = StringUtils.EMPTY;
		
		for(PropertyValueEnum propertyValueEnum : propertyValues) {
			if(propertyValueEnum.getName().equals(key)) {
				result = propertyValueEnum.getLabel();
				break;
			}
		}
		
		return result;
	}

}
