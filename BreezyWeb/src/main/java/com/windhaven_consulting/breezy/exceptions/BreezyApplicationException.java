package com.windhaven_consulting.breezy.exceptions;

public class BreezyApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BreezyApplicationException() {
	}

	public BreezyApplicationException(String message) {
		super(message);
	}

	public BreezyApplicationException(Throwable cause) {
		super(cause);
	}

	public BreezyApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public BreezyApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}


}
