package com.windhaven_consulting.breezy.embeddedcontroller;


public enum PWMPinState {

	LOW(0, "Low"),
	HIGH(1, "High"),
	INDETERMINATE(-1, "Indeterminate");
	
	private int value;
	private String state;

	private PWMPinState(int value, String state) {
		this.value = value;
		this.state = state;
	}

	public int getValue() {
		return value;
	}

	public String getState() {
		return state;
	}

	public static PWMPinState find(int value) {
		PWMPinState returnValue = null;
		
		for(PWMPinState pinState : values()) {
			if(pinState.value == value) {
				returnValue = pinState;
				break;
			}
		}
		
		return returnValue;
	}
	
    public static PWMPinState getInverseState(PWMPinState pwmPinstate) {
    	PWMPinState result = INDETERMINATE;
    	
    	if(pwmPinstate != null && pwmPinstate != INDETERMINATE) {
            result = (pwmPinstate == HIGH ? LOW : HIGH);
    	}
    	
    	return result;
    }

}
