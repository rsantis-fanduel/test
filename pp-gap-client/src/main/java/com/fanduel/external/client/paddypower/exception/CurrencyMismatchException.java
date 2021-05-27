package com.fanduel.external.client.paddypower.exception;

public class CurrencyMismatchException extends PPGapException {
	public CurrencyMismatchException( String message, String errorCode ) {
		super( message, errorCode );
	}

	public CurrencyMismatchException( String message, String errorCode, Throwable cause ) {
		super( message, errorCode, cause );
	}
}
