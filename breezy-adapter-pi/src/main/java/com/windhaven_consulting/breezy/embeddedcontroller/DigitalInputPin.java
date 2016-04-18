package com.windhaven_consulting.breezy.embeddedcontroller;

public interface DigitalInputPin extends BreezyPin {

    boolean isHigh();
    
    boolean isLow();

    boolean isState(PinState state);

    PinState getState();
}
