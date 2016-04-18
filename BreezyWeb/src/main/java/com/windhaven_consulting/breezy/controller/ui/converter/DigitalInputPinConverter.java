package com.windhaven_consulting.breezy.controller.ui.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;

public class DigitalInputPinConverter implements Converter {
	
	private Map<UUID, DigitalInputPin> digitalInputPinIdToDigitalInputPinMap = new HashMap<UUID, DigitalInputPin>();

	public DigitalInputPinConverter(List<DigitalInputPin> digitalInputPins) {
		for(DigitalInputPin digitalInputPin : digitalInputPins) {
			digitalInputPinIdToDigitalInputPinMap.put(digitalInputPin.getId(), digitalInputPin);
		}
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String id) {
		DigitalInputPin result = null;
		
		if(StringUtils.isNotEmpty(id)) {
			result = digitalInputPinIdToDigitalInputPinMap.get(UUID.fromString(id));
		}
		
		return result;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String digitalInputPinName = StringUtils.EMPTY;
		
		if(value != null && !value.equals("")) {
			UUID uuid = UUID.fromString((String) value);
			DigitalInputPin digitalInputPin = digitalInputPinIdToDigitalInputPinMap.get(uuid);
			
			if(digitalInputPin != null) {
				digitalInputPinName = digitalInputPin.getName();
			}
		}
		
		return digitalInputPinName;
	}

}
