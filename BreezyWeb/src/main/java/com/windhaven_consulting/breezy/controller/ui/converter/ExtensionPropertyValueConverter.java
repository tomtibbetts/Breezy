package com.windhaven_consulting.breezy.controller.ui.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;

public class ExtensionPropertyValueConverter implements Converter {

	private List<PropertyValueEnum> propertyValues;

	public ExtensionPropertyValueConverter(List<PropertyValueEnum> propertyValues) {
		this.propertyValues = propertyValues;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		String result = StringUtils.EMPTY;
		
		if(StringUtils.isNotEmpty(value)) {
			for(PropertyValueEnum propertyValueEnum : propertyValues) {
				if(value.equals(propertyValueEnum.getLabel())) {
					result = propertyValueEnum.getValue();
					break;
				}
			}
		}

		return result;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String enumeratedValue = (String) value;
		String result = StringUtils.EMPTY;
		
		for(PropertyValueEnum propertyValueEnum : propertyValues) {
			if(propertyValueEnum.getValue().equals(enumeratedValue)) {
				result = propertyValueEnum.getLabel();
				break;
			}
		}
		
		return result;
	}

}
