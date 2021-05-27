package com.fanduel.external.client.paddypower.exception;

public class UserSuspendedExceptionApa extends PPApaException {

	public UserSuspendedExceptionApa( String message, String errorCode ) {
		super( message, errorCode );
	}

	public UserSuspendedExceptionApa( String message, String errorCode, Throwable cause ) {
		super( message, errorCode );
	}
}