package com.windhaven_consulting.breezy.manager;

import com.windhaven_consulting.breezy.component.Component;
import com.windhaven_consulting.breezy.component.library.ComponentTemplate;

public class MountedComponent {

	private ComponentTemplate componentTemplate;
	
	private Component component;

	public MountedComponent(ComponentTemplate componentTemplate, Component component) {
		this.componentTemplate = componentTemplate;
		this.component = component;
	}
	
	public ComponentTemplate getComponentTemplate() {
		return componentTemplate;
	}

	public Component getComponent() {
		return component;
	}
	
}
