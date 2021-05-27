package com.fanduel.external.client.paddypower.exception;

public class CurrencyMismatchExceptionApa extends PPApaException {
	public CurrencyMismatchExceptionApa( String message, String errorCode ) {
		super( message, errorCode );
	}

	public CurrencyMismatchExceptionApa( String message, String errorCode, Throwable cause ) {
		super( message, errorCode, cause );
	}
}
