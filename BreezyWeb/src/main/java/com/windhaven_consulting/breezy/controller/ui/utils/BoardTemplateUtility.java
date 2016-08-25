package com.windhaven_consulting.breezy.controller.ui.utils;

import java.util.HashMap;
import java.util.UUID;

import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoardTemplate;
import com.windhaven_consulting.breezy.manager.viewobject.ComponentConfigurationTemplate;
import com.windhaven_consulting.breezy.manager.viewobject.ExtensionTemplate;
import com.windhaven_consulting.breezy.manager.viewobject.InputConfigurationTemplate;
import com.windhaven_consulting.breezy.manager.viewobject.OutputConfigurationTemplate;

public class BoardTemplateUtility {

	public static BreezyBoardTemplate cloneNew(BreezyBoardTemplate breezyBoardTemplate) {
		BreezyBoardTemplate newBreezyBoardTemplate = new BreezyBoardTemplate();
		
		newBreezyBoardTemplate.setDescription(breezyBoardTemplate.getDescription());
		newBreezyBoardTemplate.setInactive(breezyBoardTemplate.isInactive());
		newBreezyBoardTemplate.setName(breezyBoardTemplate.getName());
		newBreezyBoardTemplate.setReleaseRevisionNumber(breezyBoardTemplate.getReleaseRevisionNumber());
		
		HashMap<UUID, ExtensionTemplate> idToExtensionTemplateMap = new HashMap<UUID, ExtensionTemplate>();
		
		for(ExtensionTemplate extensionTemplate : breezyBoardTemplate.getExtensionTemplates()) {
			ExtensionTemplate newExtensionTemplate = new ExtensionTemplate();
			
			newExtensionTemplate.setExtensionType(extensionTemplate.getExtensionType());
			newExtensionTemplate.setName(extensionTemplate.getName());
			
			newBreezyBoardTemplate.getExtensionTemplates().add(newExtensionTemplate);
			idToExtensionTemplateMap.put(extensionTemplate.getId(), newExtensionTemplate);
		}
		
		for(InputConfigurationTemplate inputConfigurationTemplate : breezyBoardTemplate.getInputConfigurationTemplates()) {
			InputConfigurationTemplate newInputConfigurationTemplate = new InputConfigurationTemplate();
			
			newInputConfigurationTemplate.setExtensionTemplate(idToExtensionTemplateMap.get(inputConfigurationTemplate.getExtensionTemplate().getId()));
			newInputConfigurationTemplate.setMappedPin(inputConfigurationTemplate.getMappedPin());
			newInputConfigurationTemplate.setName(inputConfigurationTemplate.getName());
			newInputConfigurationTemplate.setPinPullResistance(inputConfigurationTemplate.getPinPullResistance());
			
			newBreezyBoardTemplate.getInputConfigurationTemplates().add(newInputConfigurationTemplate);
		}
		
		for(ComponentConfigurationTemplate componentConfigurationTemplate : breezyBoardTemplate.getComponentConfigurationTemplates()) {
			ComponentConfigurationTemplate newComponentConfigurationTemplate = new ComponentConfigurationTemplate();
			
			newComponentConfigurationTemplate.setComponentType(componentConfigurationTemplate.getComponentType());
			newComponentConfigurationTemplate.setName(componentConfigurationTemplate.getName());
			
			for(OutputConfigurationTemplate outputConfigurationTemplate : componentConfigurationTemplate.getOutputConfigurationTemplates()) {
				OutputConfigurationTemplate newOutputConfigurationTemplate = new OutputConfigurationTemplate();
				
				newOutputConfigurationTemplate.setExtensionTemplate(idToExtensionTemplateMap.get(outputConfigurationTemplate.getExtensionTemplate().getId()));
				newOutputConfigurationTemplate.setMappedPin(outputConfigurationTemplate.getMappedPin());
				newOutputConfigurationTemplate.setName(outputConfigurationTemplate.getName());
				
				newComponentConfigurationTemplate.getOutputConfigurationTemplates().add(newOutputConfigurationTemplate);
			}
			
			newBreezyBoardTemplate.getComponentConfigurationTemplates().add(newComponentConfigurationTemplate);
		}
		
		return newBreezyBoardTemplate;
	}
}
