package com.windhaven_consulting.breezy;


public enum SystemConstants {

	SYSTEM_BOARD_ID("382cba6c-1892-428c-a84c-d548bf86cb60"),
	SYSTEM_COMPONENT_ID("382cba6c-188a-428c-a85d-d548bf86cd01");
	
	private String value;
	
	private SystemConstants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
