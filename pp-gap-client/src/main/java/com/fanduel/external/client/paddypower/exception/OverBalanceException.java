package com.fanduel.external.client.paddypower.exception;

public class OverBalanceException extends PPGapException {

	public OverBalanceException( String message, String errorCode ) {
		super( message, errorCode );
	}

	public OverBalanceException( String message, String errorCode, Throwable cause ) {
		super( message, errorCode );
	}
}
