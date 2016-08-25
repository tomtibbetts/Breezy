package com.windhaven_consulting.breezy.embeddedcontroller;

public interface DigitalOutputPin extends BreezyPin {

	void toggle();

	void setLow();

	void setHigh();

    void blink(long onTime);
	
	void blink(long onTime, long duration);

	void blink(long onTime, PinState pinState);

    void blink(long delay, long duration, PinState pinState, boolean blockToCompletion);

    void pulse(long onTime);

    void pulse(long onTime, PinState pulseState);

    void pulse(long onTime, boolean blockToCompletion);

    void pulse(long onTime, PinState pinState, boolean blockToCompletion);

	PinState getState();

}
