package com.fanduel.external.client.paddypower.exception;

public class UserSuspendedException extends PPGapException {

	public UserSuspendedException( String message, String errorCode ) {
		super( message, errorCode );
	}

	public UserSuspendedException( String message, String errorCode, Throwable cause ) {
		super( message, errorCode );
	}
}