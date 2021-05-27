package com.fanduel.external.client.btobet.transfer;

import java.util.Objects;

import com.fanduel.transfer.annotation.TransferClass;
import com.fasterxml.jackson.annotation.JsonProperty;

@TransferClass
public class BetRequest extends BtoBetBettingRelatedRequest {
	@JsonProperty(value = "GameSubRoundId")
	private String gameSubRoundId;

	public BetRequest() {
		super();
	}

	BetRequest( final Builder builder ) {
		super( builder );
		gameSubRoundId = builder.gameSubRoundId;
	}

	public String getGameSubRoundId() {
		return gameSubRoundId;
	}

	public void setGameSubRoundId( final String gameSubRoundId ) {
		this.gameSubRoundId = gameSubRoundId;
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
		final BetRequest that = (BetRequest) o;
		return Objects.equals( gameSubRoundId, that.gameSubRoundId );
	}

	@Override
	public int hashCode() {
		return Objects.hash( super.hashCode(), gameSubRoundId );
	}

	@Override
	public String toString() {
		return super.toStringHelper().add( "gameSubRoundId", gameSubRoundId ).toString();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder extends BtoBetBettingRelatedRequest.Builder {
		private String gameSubRoundId;

		public Builder gameSubRoundId( final String gameSubRoundId ) {
			this.gameSubRoundId = gameSubRoundId;
			return this;
		}

		@Override
		public BetRequest build() {
			return new BetRequest( this );
		}
	}
}
