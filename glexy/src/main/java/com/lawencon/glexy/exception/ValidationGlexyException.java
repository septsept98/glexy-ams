package com.lawencon.glexy.exception;

public class ValidationGlexyException extends RuntimeException {

	private static final long serialVersionUID = 3305452510816552485L;

	public ValidationGlexyException(Throwable cause) {

		super(cause);
	}

	public ValidationGlexyException(String message) {

		super(message);
	}

}
