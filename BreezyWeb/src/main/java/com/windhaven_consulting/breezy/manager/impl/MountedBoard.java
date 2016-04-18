package com.windhaven_consulting.breezy.manager.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.windhaven_consulting.breezy.component.Component;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;

public class MountedBoard implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;

	private Map<String, Component> componentMap = new TreeMap<String, Component>();
	
	private Map<String, DigitalInputPin> pinNameToInputPinMap = new TreeMap<String, DigitalInputPin>();
	
	private Map<UUID, DigitalInputPin> pinIdToInputPinMap = new HashMap<UUID, DigitalInputPin>();
	
	private Map<UUID, DigitalOutputPin> pinIdToOutputPinMap = new HashMap<UUID, DigitalOutputPin>();
	
	private String description;
	
	private String name;
	
	private String boardTemplate;
	
	private boolean mounted;

	private List<DigitalInputPin> inputPins = new ArrayList<DigitalInputPin>();

	private List<DigitalOutputPin> outputPins = new ArrayList<DigitalOutputPin>();

	public void addInputPin(DigitalInputPin digitalInputPin) {
		pinNameToInputPinMap.put(digitalInputPin.getName(), digitalInputPin);
		pinIdToInputPinMap.put(digitalInputPin.getId(), digitalInputPin);
		inputPins.add(digitalInputPin);
	}
	
	public void addComponent(Component component) {
		componentMap.put(component.getId(), component);
		
		for(DigitalOutputPin digitalOutputPin : component.getOutputPins()) {
			pinIdToOutputPinMap.put(digitalOutputPin.getId(), digitalOutputPin);
			outputPins.add(digitalOutputPin);
		}
	}
	
	public List<Component> getComponents() {
		List<Component> components = new ArrayList<Component>(componentMap.values());
		Collections.sort(components);
		return components;
	}
	
	public Component getComponent(String id) {
		return componentMap.get(id);
	}
	
	public List<DigitalInputPin> getInputPins() {
		return inputPins;
	}
	
	public Map<String, DigitalInputPin> getInputPinMap() {
		return pinNameToInputPinMap;
	}
	
	public DigitalInputPin getInputPinById(UUID id) {
		return pinIdToInputPinMap.get(id);
	}

	public BreezyPin getOutputPinById(UUID id) {
		return pinIdToOutputPinMap.get(id);
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBoardTemplate() {
		return boardTemplate;
	}

	public void setBoardTemplate(String boardTemplate) {
		this.boardTemplate = boardTemplate;
	}

	public boolean isMounted() {
		return mounted;
	}

	public void setMounted(boolean mounted) {
		this.mounted = mounted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<DigitalOutputPin> getOutputPins() {
		return outputPins;
	}

	public String toString() {
		String result = this.id;
		return result;
	}
}
