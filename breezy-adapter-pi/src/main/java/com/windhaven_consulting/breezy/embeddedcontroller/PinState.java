package com.windhaven_consulting.breezy.embeddedcontroller;

public enum PinState {

	LOW(0, "Low", false),
	HIGH(1, "High", true);
	
	private int value;
	private String state;
	private boolean logicState;

	private PinState(int value, String state, boolean logicState) {
		this.value = value;
		this.state = state;
		this.logicState = logicState;
	}

	public int getValue() {
		return value;
	}

	public String getState() {
		return state;
	}

	public boolean isLogicState() {
		return logicState;
	}

	public static PinState find(int value) {
		PinState returnValue = null;
		
		for(PinState pinState : values()) {
			if(pinState.value == value) {
				returnValue = pinState;
				break;
			}
		}
		
		return returnValue;
	}
	
}
