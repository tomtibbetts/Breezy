package com.windhaven_consulting.breezy.macrocontroller.exception;

public class MacroControllerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MacroControllerException() {
	}

	public MacroControllerException(String message) {
		super(message);
	}

	public MacroControllerException(Throwable cause) {
		super(cause);
	}

	public MacroControllerException(String message, Throwable cause) {
		super(message, cause);
	}

	public MacroControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
