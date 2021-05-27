package com.fanduel.external.client.btobet.transfer;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class BtoBetFinancialRequest extends BtoBetRequestGeneral {
	@JsonProperty(value = "AuthToken", required = true)
	private String authToken;

	@JsonProperty(value = "ExternalId", required = true)
	private Long externalId;

	@JsonProperty(value = "GameCode", required = true)
	private String gameCode;

	public BtoBetFinancialRequest() {
		super();
	}

	public String getAuthToken() {
		return authToken;
	}

	public String getGameCode() {
		return gameCode;
	}

	public Long getExternalId() {
		return externalId;
	}

	public void setAuthToken( final String authToken ) {
		this.authToken = authToken;
	}

	public void setGameCode( final String gameCode ) {
		this.gameCode = gameCode;
	}

	public void setExternalId( final Long externalId ) {
		this.externalId = externalId;
	}

	@Override
	public MoreObjects.ToStringHelper toStringHelper() {
		return super.toStringHelper()
				.add( "authToken", authToken )
				.add( "gameCode", gameCode )
				.add( "externalId", externalId );
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
		final BtoBetFinancialRequest that = (BtoBetFinancialRequest) o;
		return Objects.equals( authToken, that.authToken ) && Objects.equals( externalId,
				that.externalId ) && Objects.equals( gameCode, that.gameCode );
	}

	@Override
	public int hashCode() {
		return Objects.hash( super.hashCode(), authToken, externalId, gameCode );
	}

	@Override
	public String toString() {
		return toStringHelper().toString();
	}

	BtoBetFinancialRequest( final Builder builder ) {
		super( builder );
		authToken = builder.authToken;
		externalId = builder.externalId;
		gameCode = builder.gameCode;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder extends BtoBetRequestGeneral.Builder {
		private String authToken;
		private Long externalId;
		private String gameCode;

		public Builder authToken( final String authToken ) {
			this.authToken = authToken;
			return this;
		}

		public Builder externalId( final Long externalId ) {
			this.externalId = externalId;
			return this;
		}

		public Builder gameCode( final String gameCode ) {
			this.gameCode = gameCode;
			return this;
		}

		@Override
		public BtoBetFinancialRequest build() {
			return new BtoBetFinancialRequest( this );
		}
	}
}
