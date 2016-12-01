package com.windhaven_consulting.breezy.embeddedcontroller;

public interface PWMOutputPin extends BreezyPin {

	void setAlwaysOn();
	
	void setAlwaysOff();
	
	void setPwm(int duration);
	
	void setPwm(int onPosition, int offPosition);
}
