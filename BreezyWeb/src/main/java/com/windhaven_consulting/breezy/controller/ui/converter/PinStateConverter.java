package com.windhaven_consulting.breezy.controller.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.embeddedcontroller.PinState;

public class PinStateConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return PinState.valueOf(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String result = StringUtils.EMPTY;
		String pinStateValue = (String) value;
		
		if(StringUtils.isNotEmpty(pinStateValue)) {
			PinState pinState = PinState.valueOf(pinStateValue);
			result = pinState.getState();
		}
		
		return result;
	}

}
