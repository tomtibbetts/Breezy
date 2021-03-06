package com.windhaven_consulting.breezy.manager.viewobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.windhaven_consulting.breezy.persistence.domain.PersistentObject;

public class BreezyBoard extends PersistentObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean mounted;
	
	private String description;
	
	private List<InputPinConfiguration> inputPinConfigurations = new ArrayList<InputPinConfiguration>();
	
	private List<ComponentConfiguration> componentConfigurations = new ArrayList<ComponentConfiguration>();
	
	private List<Extension> extensions = new ArrayList<Extension>();

	private boolean restricted = false;

	public boolean isMounted() {
		return mounted;
	}

	public void setMounted(boolean mounted) {
		this.mounted = mounted;
	}

	public List<ComponentConfiguration> getComponentConfigurations() {
		return componentConfigurations;
	}

	public void setComponentConfigurations(List<ComponentConfiguration> componentConfigurations) {
		this.componentConfigurations = componentConfigurations;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<InputPinConfiguration> getInputPinConfigurations() {
		return inputPinConfigurations;
	}

	public void setInputPinConfigurations(List<InputPinConfiguration> inputPinConfigurations) {
		this.inputPinConfigurations = inputPinConfigurations;
	}

	public List<Extension> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<Extension> extensions) {
		this.extensions = extensions;
	}

	public boolean isRestricted() {
		return restricted;
	}

	public void setRestricted(boolean restricted) {
		this.restricted = restricted;
	}
	
}
