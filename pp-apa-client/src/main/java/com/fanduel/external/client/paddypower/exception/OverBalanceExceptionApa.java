package com.fanduel.external.client.paddypower.exception;

public class OverBalanceExceptionApa extends PPApaException {

	public OverBalanceExceptionApa( String message, String errorCode ) {
		super( message, errorCode );
	}

	public OverBalanceExceptionApa( String message, String errorCode, Throwable cause ) {
		super( message, errorCode );
	}
}
