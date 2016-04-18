package com.windhaven_consulting.breezy.persistence.exceptions;

public class BreezyPersistenceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BreezyPersistenceException() {
	}

	public BreezyPersistenceException(String message) {
		super(message);
	}

	public BreezyPersistenceException(Throwable cause) {
		super(cause);
	}

	public BreezyPersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public BreezyPersistenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
