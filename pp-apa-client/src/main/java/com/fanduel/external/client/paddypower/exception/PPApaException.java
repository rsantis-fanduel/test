package com.fanduel.external.client.paddypower.exception;

public class PPApaException extends RuntimeException {

	protected String errorCode;

	public PPApaException( String message, String errorCode ) {
		super( message );
		this.errorCode = errorCode;
	}

	public PPApaException( String message, String errorCode, Throwable cause ) {
		super( message, cause );
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
