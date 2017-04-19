package com.windhaven_consulting.breezy.embeddedcontroller.exceptions;

public class EmbeddedControllerRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmbeddedControllerRuntimeException() {
		super();
	}
	
	public EmbeddedControllerRuntimeException(String message) {
		super(message);
	}
	
	public EmbeddedControllerRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
