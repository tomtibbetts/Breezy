package com.windhaven_consulting.breezy.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;

public abstract class ComponentGeneric<T extends BreezyPin> implements Comparable<ComponentGeneric>, Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String id;
	
	private List<T> outputPins = new ArrayList<T>();
	private Map<UUID, T> idToDigitalOutputPinMap = new HashMap<UUID, T>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addOutputPin(T pin) {
		outputPins.add(pin);
		idToDigitalOutputPinMap.put(pin.getId(), pin);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<T> getOutputPins() {
		return outputPins;
	}
	
	public T getOutputPin(int index) {
		return outputPins.get(index);
	}
	
	public T getOutputPin() {
		return outputPins.get(0);
	}
	
	public T getDigitalOutputPin(UUID digitalOutputPinId) {
		return idToDigitalOutputPinMap.get(digitalOutputPinId);
	}

	@Override
	public int compareTo(ComponentGeneric o) {
		return this.name.compareTo(o.getName());
	}

	public abstract void test();
}
