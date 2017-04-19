package com.windhaven_consulting.breezy.embeddedcontroller.exceptions;

public class EmbeddedControllerException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmbeddedControllerException() {
		super();
	}
	
	public EmbeddedControllerException(String message) {
		super(message);
	}
	
	public EmbeddedControllerException(String message, Throwable cause) {
		super(message, cause);
	}

}
