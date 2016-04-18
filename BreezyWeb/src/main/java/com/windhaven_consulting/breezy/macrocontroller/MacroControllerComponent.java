package com.windhaven_consulting.breezy.macrocontroller;

import com.windhaven_consulting.breezy.embeddedcontroller.PinState;


public interface MacroControllerComponent {
	public void delay(long waitTime);

	public void waitOn(String mountedBoardId, String inputPinId, PinState inputState);
	
	public void jumpTo(String tag);

	public void jumpOn(String mountedBoardId, String inputPinId, PinState inputState,	String tag);
}
