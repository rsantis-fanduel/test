package com.fanduel.external.client.paddypower.exception;

public class InvalidTokenExceptionApa extends PPApaException {

	public InvalidTokenExceptionApa( String message, String errorCode ) {
		super( message, errorCode );
	}

	public InvalidTokenExceptionApa( String message, String errorCode, Throwable cause ) {
		super( message, errorCode, cause );
	}
}
