package ru.school21.swingy.exception;

public class SwingyRuntimeException extends RuntimeException {
	public SwingyRuntimeException() {
	}

	public SwingyRuntimeException(String message) {
		super(message);
	}

	public SwingyRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SwingyRuntimeException(Throwable cause) {
		super(cause);
	}

	public SwingyRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
