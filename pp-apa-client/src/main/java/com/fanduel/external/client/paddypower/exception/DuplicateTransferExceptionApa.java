package com.fanduel.external.client.paddypower.exception;

public class DuplicateTransferExceptionApa extends PPApaException {
	public DuplicateTransferExceptionApa( String message, String errorCode ) {
		super( message, errorCode );
	}

	public DuplicateTransferExceptionApa( String message, String errorCode, Throwable cause ) {
		super( message, errorCode, cause );
	}
}
