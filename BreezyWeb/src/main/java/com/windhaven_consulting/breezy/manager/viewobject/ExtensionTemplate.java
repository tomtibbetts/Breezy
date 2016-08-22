package com.windhaven_consulting.breezy.manager.viewobject;

import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionType;
import com.windhaven_consulting.breezy.persistence.domain.PersistentObject;

public class ExtensionTemplate extends PersistentObject {

	private ExtensionType extensionType;
	
	public ExtensionType getExtensionType() {
		return extensionType;
	}

	public void setExtensionType(ExtensionType extensionType) {
		this.extensionType = extensionType;
	}
}
