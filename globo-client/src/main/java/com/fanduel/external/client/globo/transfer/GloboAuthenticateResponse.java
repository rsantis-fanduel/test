package com.fanduel.external.client.globo.transfer;

import com.fanduel.api.exception.APICodedException;
import com.google.common.base.MoreObjects;

public class GloboAuthenticateResponse {
	private String userSessionId;
	private String authenticateUrl;
	private String codeVerifier;
	private String state;
	private APICodedException error;

	public String getCodeVerifier() {
		return codeVerifier;
	}

	public void setCodeVerifier( final String codeVerifier ) {
		this.codeVerifier = codeVerifier;
	}

	public String getState() {
		return state;
	}

	public void setState( final String state ) {
		this.state = state;
	}

	public String getAuthenticateUrl() {
		return authenticateUrl;
	}

	public void setAuthenticateUrl( final String authenticateUrl ) {
		this.authenticateUrl = authenticateUrl;
	}

	public String getUserSessionId() {
		return userSessionId;
	}

	public void setUserSessionId( final String userSessionId ) {
		this.userSessionId = userSessionId;
	}

	public APICodedException getError() {
		return error;
	}

	public void setError( final APICodedException error ) {
		this.error = error;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "userSessionId", userSessionId )
				.add( "authenticateUrl", authenticateUrl )
				.add( "codeVerifier", codeVerifier )
				.add( "state", state )
				.toString();
	}
}
