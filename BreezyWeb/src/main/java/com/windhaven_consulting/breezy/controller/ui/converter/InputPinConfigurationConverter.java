package com.windhaven_consulting.breezy.controller.ui.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.manager.viewobject.InputPinConfiguration;

public class InputPinConfigurationConverter implements Converter {

	private Map<UUID, InputPinConfiguration> inputPinConfigurationIdToInputPinConfigurationMap = new HashMap<UUID, InputPinConfiguration>();
	
	public InputPinConfigurationConverter(List<InputPinConfiguration> inputPinConfigurations) {
		for(InputPinConfiguration inputPinConfiguration : inputPinConfigurations) {
			inputPinConfigurationIdToInputPinConfigurationMap.put(inputPinConfiguration.getId(), inputPinConfiguration);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String id) {
		InputPinConfiguration result = null;
		
		if(StringUtils.isNotEmpty(id)) {
			result = inputPinConfigurationIdToInputPinConfigurationMap.get(UUID.fromString(id));
		}
		
		return result;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String inputPinConfigurationName = StringUtils.EMPTY;
		
		if(value != null && !value.equals("")) {
			UUID uuid = UUID.fromString((String) value);
			InputPinConfiguration inputPinConfiguration = inputPinConfigurationIdToInputPinConfigurationMap.get(uuid);
			
			if(inputPinConfiguration != null) {
				inputPinConfigurationName = inputPinConfiguration.getName();
			}
		}
		
		return inputPinConfigurationName;
	}

}
