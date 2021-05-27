package com.fanduel.external.client.btobet.transfer;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticateRequest extends BtoBetRequestGeneral {
	@JsonProperty("AuthToken")
	private String authToken;

	@JsonProperty("GameCode")
	private String gameCode;

	public AuthenticateRequest() {
		super();
	}

	AuthenticateRequest( final Builder builder ) {
		super( builder );
		authToken = builder.authToken;
		gameCode = builder.gameCode;
	}

	public static Builder builder() {
		return new Builder();
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken( final String authToken ) {
		this.authToken = authToken;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode( final String gameCode ) {
		this.gameCode = gameCode;
	}

	@Override
	public boolean equals( final Object o ) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		if ( !super.equals( o ) ) {
			return false;
		}
		final AuthenticateRequest that = (AuthenticateRequest) o;
		return Objects.equals( authToken, that.authToken ) && Objects.equals( gameCode,
				that.gameCode );
	}

	@Override
	public int hashCode() {
		return Objects.hash( super.hashCode(), authToken, gameCode );
	}

	@Override
	public String toString() {
		return super.toStringHelper()
				.add( "authToken", authToken )
				.add( "gameCode", gameCode )
				.toString();
	}

	public static class Builder extends BtoBetRequestGeneral.Builder {
		private String authToken;
		private String gameCode;

		public Builder authToken( String authToken ) {
			this.authToken = authToken;
			return this;
		}

		public Builder gameCode( String gameCode ) {
			this.gameCode = gameCode;
			return this;
		}

		@Override
		public AuthenticateRequest build() {
			return new AuthenticateRequest( this );
		}
	}
}
