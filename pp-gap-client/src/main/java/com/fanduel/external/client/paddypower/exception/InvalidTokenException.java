package com.fanduel.external.client.paddypower.exception;

public class InvalidTokenException extends PPGapException {

	public InvalidTokenException( String message, String errorCode ) {
		super( message, errorCode );
	}

	public InvalidTokenException( String message, String errorCode, Throwable cause ) {
		super( message, errorCode, cause );
	}
}
