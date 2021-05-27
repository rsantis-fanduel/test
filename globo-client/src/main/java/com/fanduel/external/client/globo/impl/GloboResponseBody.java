package com.fanduel.external.client.globo.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GloboResponseBody {
	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("expires_in")
	private int expiresIn;

	@JsonProperty("refresh_expires_in")
	private int refreshExpiresIn;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("id_token")
	private String idToken;

	@JsonProperty("not-before-policy")
	private int notBeforePolicy;

	@JsonProperty("session_state")
	private String sessionState;

	@JsonProperty("scope")
	private String scope;

	public String getAccessToken() {
		return accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public int getRefreshExpiresIn() {
		return refreshExpiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public String getIdToken() {
		return idToken;
	}

	public int getNotBeforePolicy() {
		return notBeforePolicy;
	}

	public String getSessionState() {
		return sessionState;
	}

	public String getScope() {
		return scope;
	}
}
