package com.windhaven_consulting.breezy.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoard;
import com.windhaven_consulting.breezy.manager.viewobject.ComponentConfiguration;
import com.windhaven_consulting.breezy.manager.viewobject.Extension;
import com.windhaven_consulting.breezy.manager.viewobject.InputPinConfiguration;
import com.windhaven_consulting.breezy.manager.viewobject.OutputPinConfiguration;
import com.windhaven_consulting.breezy.persistence.domain.BreezyBoardDO;
import com.windhaven_consulting.breezy.persistence.domain.ComponentConfigurationDO;
import com.windhaven_consulting.breezy.persistence.domain.ExtensionDO;
import com.windhaven_consulting.breezy.persistence.domain.InputPinConfigurationDO;
import com.windhaven_consulting.breezy.persistence.domain.OutputPinConfigurationDO;

public class BreezyBoardViewMapper implements ViewDiskObjectMapper<BreezyBoard, BreezyBoardDO> {

	@Override
	public BreezyBoardDO getAsDiskObject(BreezyBoard breezyBoard) {
		BreezyBoardDO breezyBoardDO = new BreezyBoardDO();
		
		breezyBoardDO.setDescription(breezyBoard.getDescription());
		breezyBoardDO.setId(breezyBoard.getId());
		breezyBoardDO.setName(breezyBoard.getName());
		breezyBoardDO.setReleaseRevisionNumber(breezyBoard.getReleaseRevisionNumber());
		breezyBoardDO.setMounted(breezyBoard.isMounted());
		breezyBoardDO.setRestricted(breezyBoard.isRestricted());
		
		for(Extension extension : breezyBoard.getExtensions()) {
			ExtensionDO extensionDO = getExtensionDO(extension);
			breezyBoardDO.getExtensionDOs().add(extensionDO);
		}
		
		for(InputPinConfiguration inputPinConfiguration : breezyBoard.getInputPinConfigurations()) {
			InputPinConfigurationDO inputPinConfigurationDO = getInputConfigurationDO(inputPinConfiguration);
			breezyBoardDO.getInputPinConfigurationDOs().add(inputPinConfigurationDO);
		}
		
		for(ComponentConfiguration componentConfiguration :	breezyBoard.getComponentConfigurations()) {
			ComponentConfigurationDO componentConfigurationDO = getComponentConfigurationDO(componentConfiguration);
			breezyBoardDO.getComponentConfigurationDOs().add(componentConfigurationDO);
		}
		
		return breezyBoardDO;
	}

	@Override
	public List<BreezyBoardDO> getAsDiskObjects(List<BreezyBoard> sourceCollection) {
		List<BreezyBoardDO> breezyBoardDOs = new ArrayList<BreezyBoardDO>();
		
		for(BreezyBoard breezyBoard : sourceCollection) {
			BreezyBoardDO breezyBoardDO = getAsDiskObject(breezyBoard);
			breezyBoardDOs.add(breezyBoardDO);
		}
		
		return breezyBoardDOs;
	}

	@Override
	public BreezyBoard getAsViewObject(BreezyBoardDO breezyBoardDO) {
		Map<UUID, Extension> extensionIdToExtensionMap = new HashMap<UUID, Extension>();  
		
		BreezyBoard breezyBoard = new BreezyBoard();
		breezyBoard.setName(breezyBoardDO.getName());
		breezyBoard.setDescription(breezyBoardDO.getDescription());
		breezyBoard.setId(breezyBoardDO.getId());
		breezyBoard.setReleaseRevisionNumber(breezyBoardDO.getReleaseRevisionNumber());
		breezyBoard.setMounted(breezyBoardDO.isMounted());
		breezyBoard.setRestricted(breezyBoardDO.isRestricted());
		
		for(ExtensionDO extensionDO : breezyBoardDO.getExtensionDOs()) {
			Extension extension = getExtension(extensionDO);
			
			breezyBoard.getExtensions().add(extension);
			extensionIdToExtensionMap.put(extension.getId(), extension);
		}
		
		for(InputPinConfigurationDO inputPinConfigurationDO : breezyBoardDO.getInputPinConfigurationDOs()) {
			InputPinConfiguration inputPinConfiguration = getInputPinConfiguration(inputPinConfigurationDO);
			
			if(extensionIdToExtensionMap.containsKey(inputPinConfigurationDO.getExtensionId())) {
				inputPinConfiguration.setExtension(extensionIdToExtensionMap.get(inputPinConfigurationDO.getExtensionId()));
			}
			
			breezyBoard.getInputPinConfigurations().add(inputPinConfiguration);
		}
		
		for(ComponentConfigurationDO componentConfigurationDO : breezyBoardDO.getComponentConfigurationDOs()) {
			ComponentConfiguration componentConfiguration = getComponentConfiguration(componentConfigurationDO, extensionIdToExtensionMap);
			
			breezyBoard.getComponentConfigurations().add(componentConfiguration);
		}
		
		return breezyBoard;
	}

	@Override
	public List<BreezyBoard> getAsViewObjects(List<BreezyBoardDO> sourceCollection) {
		List<BreezyBoard> breezyBoards = new ArrayList<BreezyBoard>();
		
		for(BreezyBoardDO breezyBoardDO : sourceCollection) {
			BreezyBoard breezyBoard = getAsViewObject(breezyBoardDO);
			breezyBoards.add(breezyBoard);
		}
		
		return breezyBoards;
	}

	private ExtensionDO getExtensionDO(Extension extension) {
		if(extension.getId() == null) {
			extension.setId(UUID.randomUUID());
		}
		
		ExtensionDO extensionDO = new ExtensionDO();
		extensionDO.setId(extension.getId());
		extensionDO.setName(extension.getName());
		extensionDO.setExtensionType(extension.getExtensionType());
		extensionDO.setProperties(extension.getProperties());
		
		return extensionDO;
	}

	private InputPinConfigurationDO getInputConfigurationDO(InputPinConfiguration inputPinConfiguration) {
		if(inputPinConfiguration.getId() == null) {
			inputPinConfiguration.setId(UUID.randomUUID());
		}
		
		InputPinConfigurationDO inputPinConfigurationDO = new InputPinConfigurationDO();
		inputPinConfigurationDO.setId(inputPinConfiguration.getId());
		inputPinConfigurationDO.setName(inputPinConfiguration.getName());
		inputPinConfigurationDO.setExtensionMappedPin(inputPinConfiguration.getExtensionMappedPin());
		inputPinConfigurationDO.setPinPullResistance(inputPinConfiguration.getPinPullResistance());
		inputPinConfigurationDO.setDebounce(inputPinConfiguration.getDebounce());
		inputPinConfigurationDO.setEventTrigger(inputPinConfiguration.isEventTrigger());
		inputPinConfigurationDO.setDescription(inputPinConfiguration.getDescription());
		inputPinConfigurationDO.setProvisionable(inputPinConfiguration.isProvisionable());
		inputPinConfigurationDO.setExtensionId(inputPinConfiguration.getExtension().getId());
		
		return inputPinConfigurationDO;
	}

	private ComponentConfigurationDO getComponentConfigurationDO(ComponentConfiguration componentConfiguration) {
		if(componentConfiguration.getId() == null) {
			componentConfiguration.setId(UUID.randomUUID());
		}
		
		ComponentConfigurationDO componentConfigurationDO = new ComponentConfigurationDO();
		componentConfigurationDO.setComponentType(componentConfiguration.getComponentType());
		componentConfigurationDO.setId(componentConfiguration.getId());
		componentConfigurationDO.setName(componentConfiguration.getName());
		componentConfigurationDO.setDescription(componentConfiguration.getDescription());
		componentConfigurationDO.setForwardLatency(componentConfiguration.getForwardLatency());
		componentConfigurationDO.setTrailingLatency(componentConfiguration.getTrailingLatency());
		componentConfigurationDO.setProvisionable(componentConfiguration.isProvisionable());
		componentConfigurationDO.setInverted(componentConfiguration.isInverted());
		
		for(OutputPinConfiguration outputPinConfiguration : componentConfiguration.getOutputPinConfigurations()) {
			OutputPinConfigurationDO outputPinConfigurationDO = getOutputPinConfigurationDO(outputPinConfiguration);
			componentConfigurationDO.getOutputPinConfigurationDOs().add(outputPinConfigurationDO);
			
			outputPinConfigurationDO.setExtensionId(outputPinConfiguration.getExtension().getId());
			outputPinConfigurationDO.setDescription(outputPinConfiguration.getDescription());
			outputPinConfigurationDO.setExtensionMappedPin(outputPinConfiguration.getExtensionMappedPin());
			outputPinConfigurationDO.setName(outputPinConfiguration.getName());
			outputPinConfigurationDO.setId(outputPinConfiguration.getId());
		}
		
		return componentConfigurationDO;
	}

	private OutputPinConfigurationDO getOutputPinConfigurationDO(OutputPinConfiguration outputPinConfiguration) {
		if(outputPinConfiguration.getId() == null) {
			outputPinConfiguration.setId(UUID.randomUUID());
		}
		
		OutputPinConfigurationDO outputPinConfigurationDO = new OutputPinConfigurationDO();
		outputPinConfigurationDO.setId(outputPinConfiguration.getId());
		outputPinConfigurationDO.setName(outputPinConfiguration.getName());
		outputPinConfigurationDO.setExtensionMappedPin(outputPinConfiguration.getExtensionMappedPin());
		
		return outputPinConfigurationDO;
	}

	private Extension getExtension(ExtensionDO extensionDO) {
		Extension extension = new Extension();
		extension.setName(extensionDO.getName());
		extension.setId(extensionDO.getId());
		extension.setExtensionType(extensionDO.getExtensionType());
		extension.setProperties(extensionDO.getProperties());
		
		return extension;
	}

	private InputPinConfiguration getInputPinConfiguration(InputPinConfigurationDO inputPinConfigurationDO) {
		InputPinConfiguration inputPinConfiguration = new InputPinConfiguration();
		inputPinConfiguration.setName(inputPinConfigurationDO.getName());
		inputPinConfiguration.setId(inputPinConfigurationDO.getId());
		inputPinConfiguration.setExtensionMappedPin(inputPinConfigurationDO.getExtensionMappedPin());
		inputPinConfiguration.setPinPullResistance(inputPinConfigurationDO.getPinPullResistance());
		inputPinConfiguration.setDebounce(inputPinConfigurationDO.getDebounce());
		inputPinConfiguration.setDescription(inputPinConfigurationDO.getDescription());
		inputPinConfiguration.setEventTrigger(inputPinConfigurationDO.isEventTrigger());
		inputPinConfiguration.setProvisionable(inputPinConfigurationDO.isProvisionable());
		
		return inputPinConfiguration;
	}

	private ComponentConfiguration getComponentConfiguration(ComponentConfigurationDO componentConfigurationDO, Map<UUID, Extension> extensionIdToExtensionMap) {
		ComponentConfiguration componentConfiguration = new ComponentConfiguration();
		componentConfiguration.setComponentType(componentConfigurationDO.getComponentType());
		componentConfiguration.setId(componentConfigurationDO.getId());
		componentConfiguration.setName(componentConfigurationDO.getName());
		componentConfiguration.setDescription(componentConfigurationDO.getDescription());
		componentConfiguration.setForwardLatency(componentConfigurationDO.getForwardLatency());
		componentConfiguration.setTrailingLatency(componentConfigurationDO.getTrailingLatency());
		componentConfiguration.setInverted(componentConfigurationDO.isInverted());
		componentConfiguration.setProvisionable(componentConfigurationDO.isProvisionable());
		
		for(OutputPinConfigurationDO outputPinConfigurationDO : componentConfigurationDO.getOutputPinConfigurationDOs()) {
			OutputPinConfiguration outputPinConfiguration = new OutputPinConfiguration();
			outputPinConfiguration.setId(outputPinConfigurationDO.getId());
			outputPinConfiguration.setName(outputPinConfigurationDO.getName());
			outputPinConfiguration.setExtensionMappedPin(outputPinConfigurationDO.getExtensionMappedPin());
			outputPinConfiguration.setDescription(outputPinConfigurationDO.getDescription());
			outputPinConfiguration.setExtension(extensionIdToExtensionMap.get(outputPinConfigurationDO.getExtensionId()));
			
			if(extensionIdToExtensionMap.containsKey(outputPinConfigurationDO.getExtensionId())) {
				outputPinConfiguration.setExtension(extensionIdToExtensionMap.get(outputPinConfigurationDO.getExtensionId()));
			}
			
			componentConfiguration.getOutputPinConfigurations().add(outputPinConfiguration);
		}
		
		return componentConfiguration;
	}

}
