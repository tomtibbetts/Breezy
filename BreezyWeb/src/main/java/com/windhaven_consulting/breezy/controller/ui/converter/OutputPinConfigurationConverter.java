package com.windhaven_consulting.breezy.controller.ui.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.manager.viewobject.OutputPinConfiguration;

public class OutputPinConfigurationConverter implements Converter {

	private Map<UUID, OutputPinConfiguration> outputPinConfigurationIdToOutputPinConfigurationMap = new HashMap<UUID, OutputPinConfiguration>();
	
	public OutputPinConfigurationConverter(List<OutputPinConfiguration> outputPinConfigurations) {
		for(OutputPinConfiguration outputPinConfiguration : outputPinConfigurations) {
			outputPinConfigurationIdToOutputPinConfigurationMap.put(outputPinConfiguration.getId(), outputPinConfiguration);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		OutputPinConfiguration result = null;
		
		if(StringUtils.isNotEmpty(value)) {
			result = outputPinConfigurationIdToOutputPinConfigurationMap.get(UUID.fromString(value));
		}
		
		return result;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String outputPinConfigurationName = StringUtils.EMPTY;
		
		if(value != null && !value.equals("")) {
			UUID uuid = UUID.fromString((String) value);
			OutputPinConfiguration outputPinConfiguration = outputPinConfigurationIdToOutputPinConfigurationMap.get(uuid);
			
			if(outputPinConfiguration != null) {
				outputPinConfigurationName = outputPinConfiguration.getName();
			}
		}
		
		return outputPinConfigurationName;
	}

}
