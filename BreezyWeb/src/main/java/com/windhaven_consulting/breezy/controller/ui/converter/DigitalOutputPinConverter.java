package com.windhaven_consulting.breezy.controller.ui.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;

public class DigitalOutputPinConverter implements Converter {
	
	private Map<UUID, DigitalOutputPin> digitalOutputPinIdToDigitalOutputPinMap = new HashMap<UUID, DigitalOutputPin>();

	public DigitalOutputPinConverter(List<DigitalOutputPin> digitalOutputPins) {
		for(DigitalOutputPin digitalOutputPin : digitalOutputPins) {
			digitalOutputPinIdToDigitalOutputPinMap.put(digitalOutputPin.getId(), digitalOutputPin);
		}
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String id) {
		DigitalOutputPin result = null;
		
		if(StringUtils.isNotEmpty(id)) {
			result = digitalOutputPinIdToDigitalOutputPinMap.get(UUID.fromString(id));
		}
		
		return result;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String digitalOutputPinName = StringUtils.EMPTY;
		
		if(value != null && !value.equals("")) {
			UUID uuid = UUID.fromString((String) value);
			DigitalOutputPin digitalOutputPin = digitalOutputPinIdToDigitalOutputPinMap.get(uuid);
			
			if(digitalOutputPin != null) {
				digitalOutputPinName = digitalOutputPin.getName();
			}
		}
		
		return digitalOutputPinName;
	}

}
