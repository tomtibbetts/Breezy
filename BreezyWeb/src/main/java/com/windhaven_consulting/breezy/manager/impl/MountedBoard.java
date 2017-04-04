package com.windhaven_consulting.breezy.manager.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.windhaven_consulting.breezy.component.GenericComponent;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;

public class MountedBoard implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;

	private Map<String, GenericComponent<BreezyPin>> componentMap = new TreeMap<String, GenericComponent<BreezyPin>>();
	
	private Map<String, DigitalInputPin> pinNameToInputPinMap = new TreeMap<String, DigitalInputPin>();
	
	private Map<UUID, DigitalInputPin> pinIdToInputPinMap = new HashMap<UUID, DigitalInputPin>();
	
	private Map<UUID, BreezyPin> pinIdToOutputPinMap = new HashMap<UUID, BreezyPin>();
	
	private String description;
	
	private String name;
	
	private String boardTemplate;
	
	private boolean mounted;

	private List<DigitalInputPin> digitalInputPins = new ArrayList<DigitalInputPin>();

	private List<BreezyPin> outputPins = new ArrayList<BreezyPin>();

	public void addInputPin(DigitalInputPin digitalInputPin) {
		pinNameToInputPinMap.put(digitalInputPin.getName(), digitalInputPin);
		pinIdToInputPinMap.put(digitalInputPin.getId(), digitalInputPin);
		digitalInputPins.add(digitalInputPin);
	}
	
	public void addComponent(GenericComponent<BreezyPin> component) {
		componentMap.put(component.getId(), component);
		
		for(BreezyPin outputPin : component.getOutputPins()) {
			pinIdToOutputPinMap.put(outputPin.getId(), outputPin);
			outputPins.add(outputPin);
		}
	}
	
	public List<GenericComponent<BreezyPin>> getComponents() {
		List<GenericComponent<BreezyPin>> components = new ArrayList<GenericComponent<BreezyPin>>(componentMap.values());
		Collections.sort(components);
		return components;
	}
	
	public GenericComponent<BreezyPin> getComponent(String id) {
		return componentMap.get(id);
	}
	
	public List<DigitalInputPin> getInputPins() {
		return digitalInputPins;
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

	public List<BreezyPin> getOutputPins() {
		return outputPins;
	}

	public String toString() {
		String result = this.id;
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MountedBoard other = (MountedBoard) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
