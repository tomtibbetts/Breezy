package com.windhaven_consulting.breezy.embeddedcontroller;

public class StateChangeEvent {

	private PinState pinState;
	private String name;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PinState getPinState() {
		return pinState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPinState(PinState pinState) {
		this.pinState = pinState;
	}

}
