package com.windhaven_consulting.breezy.embeddedcontroller;

import java.util.concurrent.Future;

public interface PWMOutputPin extends BreezyPin {

	void setAlwaysOn();
	
	void setAlwaysOff();
	
	void setBrightness(int brightness);
	
	int getBrightness();
	
	void setPwm(int duration);
	
	void setPwm(int onPosition, int offPosition);
	
    void high();
    
    void low();    
    
    void toggle();

    Future<?> blink(long delay);
    
    Future<?> blink(long delay, PWMPinState pwmPinState);
    
    Future<?> blink(long delay, long duration);
    
    Future<?> blink(long delay, long duration, PWMPinState pwmPinState);

    void blink(long delay, long duration, PWMPinState pwmPinState, boolean blockToCompletion);
    
    Future<?> pulse(long duration);

    Future<?> pulse(long duration, boolean blockToCompletion);
    
    Future<?> pulse(long duration, PWMPinState pulseState);
    
    Future<?> pulse(long duration, PWMPinState pulseState, boolean blocking);
	
    Future<?> pulsate(long attack, long sustain, long release, long interval, int brightness);
    
    void setState(PWMPinState pwmPinstate);

	void stop();

	void dimTo(long attack, int brightness, boolean blockToCompletion);
    
//    Future<?> pulse(long duration, Callable<Void> callback);
//    Future<?> pulse(long duration, boolean blocking, Callable<Void> callback);
//    Future<?> pulse(long duration, PinState pulseState, Callable<Void> callback);
//    Future<?> pulse(long duration, PinState pulseState, boolean blocking, Callable<Void> callback);


//    void setState(boolean state);
	
}
