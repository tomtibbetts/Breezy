package com.windhaven_consulting.breezy.persistence.domain;

import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionType;

public class ExtensionTemplateDO extends PersistentObject {

	private ExtensionType extensionType;
	
	public ExtensionType getExtensionType() {
		return extensionType;
	}

	public void setExtensionType(ExtensionType extensionType) {
		this.extensionType = extensionType;
	}
}
