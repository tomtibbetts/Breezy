package com.windhaven_consulting.breezy.manager;

import com.windhaven_consulting.breezy.component.GenericComponent;
import com.windhaven_consulting.breezy.component.library.ComponentTemplate;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;

public class MountedComponent {

	private ComponentTemplate componentTemplate;
	
	private GenericComponent<BreezyPin> component;

	public MountedComponent(ComponentTemplate componentTemplate, GenericComponent<BreezyPin> component) {
		this.componentTemplate = componentTemplate;
		this.component = component;
	}
	
	public ComponentTemplate getComponentTemplate() {
		return componentTemplate;
	}

	public GenericComponent<BreezyPin> getComponent() {
		return component;
	}
	
}
