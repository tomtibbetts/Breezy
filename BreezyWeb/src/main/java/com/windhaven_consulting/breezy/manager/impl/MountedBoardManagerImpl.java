package com.windhaven_consulting.breezy.manager.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.Component;
import com.windhaven_consulting.breezy.component.library.ComponentTemplate;
import com.windhaven_consulting.breezy.component.library.ComponentTemplateLibraryManager;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderAbstractFactory;
import com.windhaven_consulting.breezy.exceptions.BreezyApplicationException;
import com.windhaven_consulting.breezy.macrocontroller.MacroExecutor;
import com.windhaven_consulting.breezy.manager.MountedBoardManager;
import com.windhaven_consulting.breezy.manager.MountedComponent;
import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoard;
import com.windhaven_consulting.breezy.manager.viewobject.ComponentConfiguration;
import com.windhaven_consulting.breezy.manager.viewobject.Extension;
import com.windhaven_consulting.breezy.manager.viewobject.InputPinConfiguration;
import com.windhaven_consulting.breezy.manager.viewobject.OutputPinConfiguration;

@ApplicationScoped
public class MountedBoardManagerImpl implements MountedBoardManager, Serializable {
	static final Logger LOG = LoggerFactory.getLogger(MacroExecutor.class);
	
	private static final long serialVersionUID = 1L;

	private Map<String, MountedBoard> mountedBoardByNameMap = new HashMap<String, MountedBoard>();

	private List<MountedBoard> mountedBoards = new ArrayList<MountedBoard>();
	
	private Map<String, MountedBoard> mountedBoardMap = new HashMap<String, MountedBoard>();
	
	@Inject
	private ComponentTemplateLibraryManager componentLibraryManager;
	
	@Inject
	private ExtensionProviderAbstractFactory extensionProviderFactory;
	
	@Deprecated
	public Collection<MountedComponent> getAllMountedComponents() {
		List<MountedComponent> mountedComponents = new ArrayList<MountedComponent>();
		Collection<Component> components = mountedBoards.get(0).getComponents();
		
		for(Component component : components) {
			ComponentTemplate componentDescriptor = componentLibraryManager.getComponentTemplateFor(component);
			MountedComponent mountedComponent = new MountedComponent(componentDescriptor, component);
			
			mountedComponents.add(mountedComponent);
		}
		
		return mountedComponents;
	}
	
	@Override
	public MountedBoard getById(String id) {
		return mountedBoardMap.get(id);
	}

	@Override
	public MountedBoard getByName(String mountedBoardName) {
		return mountedBoardByNameMap.get(mountedBoardName);
	}

	@Override
	public List<MountedBoard> getAllMountedBoards() {
		return mountedBoards;
	}

	@Override
	public void mount(BreezyBoard breezyBoard) {
		MountedBoard mountedBoard = getMountedBoard(breezyBoard);

		mountedBoards.add(mountedBoard);
		mountedBoardMap.put(mountedBoard.getId(), mountedBoard);
		mountedBoardByNameMap.put(mountedBoard.getName(), mountedBoard);
	}

	@Override
	public void unmount(BreezyBoard breezyBoard) {
//		LOG.debug("unmount for " + breezyBoard.getName());
		
		Map<UUID, ExtensionProvider<BreezyPin>> extensionProviderMap = new HashMap<UUID, ExtensionProvider<BreezyPin>>();

		for(Extension extension : breezyBoard.getExtensions()) {
			ExtensionProvider<BreezyPin> extensionProvider = extensionProviderFactory.getNewExtensionProvider(extension.getExtensionType(), extension.getProperties());
			
			extensionProviderMap.put(extension.getId(), extensionProvider);
		}
		
		MountedBoard mountedBoard =  mountedBoardMap.get(breezyBoard.getId().toString());
		
		for(InputPinConfiguration inputPinConfiguration : breezyBoard.getInputPinConfigurations()) {
			ExtensionProvider<BreezyPin> extensionProvider = extensionProviderMap.get(inputPinConfiguration.getExtension().getId());

			BreezyPin breezyPin = mountedBoard.getInputPinById(inputPinConfiguration.getId());
			extensionProvider.unprovisionPin(breezyPin);
		}
		
		for(ComponentConfiguration componentConfiguration : breezyBoard.getComponentConfigurations()) {
			for(OutputPinConfiguration outputPinConfiguration : componentConfiguration.getOutputPinConfigurations()) {
				ExtensionProvider<BreezyPin> extensionProvider = extensionProviderMap.get(outputPinConfiguration.getExtension().getId());
				
				BreezyPin breezyPin = mountedBoard.getOutputPinById(outputPinConfiguration.getId());
				extensionProvider.unprovisionPin(breezyPin);
			}
		}
		
		mountedBoards.remove(mountedBoard);
		mountedBoardMap.remove(mountedBoard.getId());
		mountedBoardByNameMap.remove(mountedBoard.getName());
	}
	
	private MountedBoard getMountedBoard(BreezyBoard breezyBoard) {
		LOG.debug("getMountedBoard for " + breezyBoard.getName());
		
		Map<UUID, ExtensionProvider<BreezyPin>> extensionProviderMap = new HashMap<UUID, ExtensionProvider<BreezyPin>>();

		for(Extension extension : breezyBoard.getExtensions()) {
			LOG.debug("Using extension properties: " + extension.getProperties().toString());
			ExtensionProvider<BreezyPin> extensionProvider = extensionProviderFactory.getNewExtensionProvider(extension.getExtensionType(), extension.getProperties());
			
			extensionProviderMap.put(extension.getId(), extensionProvider);
		}
		
		MountedBoard mountedBoard = new MountedBoard();
		mountedBoard.setId(breezyBoard.getId().toString());
		mountedBoard.setName(breezyBoard.getName());
		mountedBoard.setDescription(breezyBoard.getDescription());
		
		LOG.debug("********* Initializing input pins for breezy board: " + breezyBoard.getName());
		for(InputPinConfiguration inputPinConfiguration : breezyBoard.getInputPinConfigurations()) {
			ExtensionProvider<BreezyPin> extensionProvider = extensionProviderMap.get(inputPinConfiguration.getExtension().getId());
			
			DigitalInputPin digitalInputPin = extensionProvider.provisionDigitalInputPin(
					inputPinConfiguration.getName(),
					inputPinConfiguration.getExtensionMappedPin(), 
					inputPinConfiguration.getId(),
					inputPinConfiguration.getPinPullResistance(),
					inputPinConfiguration.getDebounce(),
					inputPinConfiguration.isEventTrigger());
			mountedBoard.addInputPin(digitalInputPin);
		}
		
		LOG.debug("********* Initializing components for breezy board: " + breezyBoard.getName());
		for(ComponentConfiguration componentConfiguration : breezyBoard.getComponentConfigurations()) {
			try {
				Component component = componentLibraryManager.getNewComponentByType(componentConfiguration.getComponentType());
				component.setName(componentConfiguration.getName());
				component.setId(componentConfiguration.getId().toString());
				
				// TODO: put in a check for digital/analog/PWM
				for(OutputPinConfiguration outputPinConfiguration : componentConfiguration.getOutputPinConfigurations()) {
					ExtensionProvider<BreezyPin> extensionProvider = extensionProviderMap.get(outputPinConfiguration.getExtension().getId());
					
					DigitalOutputPin digitalOutputPin = extensionProvider.provisionDigitalOutputPin(
							outputPinConfiguration.getName(), 
							outputPinConfiguration.getExtensionMappedPin(),
							outputPinConfiguration.getId());
					component.addOutputPin(digitalOutputPin);
				}
				
				mountedBoard.addComponent(component);
			} catch (Exception e) {
				throw new BreezyApplicationException("Unable to register breezy board components.", e);
			}
			
		}

		return mountedBoard;
	}

}
