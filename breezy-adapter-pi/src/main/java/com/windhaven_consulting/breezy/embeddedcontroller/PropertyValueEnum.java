package com.windhaven_consulting.breezy.embeddedcontroller;

import java.util.List;

public interface PropertyValueEnum {
	
	String getName();

	String getLabel();
	
	String getValue();
	
	List<PropertyValueEnum> getProperties();
}
