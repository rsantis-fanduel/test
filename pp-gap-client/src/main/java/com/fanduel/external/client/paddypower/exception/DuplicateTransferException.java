package com.fanduel.external.client.paddypower.exception;

public class DuplicateTransferException extends PPGapException {
	public DuplicateTransferException( String message, String errorCode ) {
		super( message, errorCode );
	}

	public DuplicateTransferException( String message, String errorCode, Throwable cause ) {
		super( message, errorCode, cause );
	}
}
