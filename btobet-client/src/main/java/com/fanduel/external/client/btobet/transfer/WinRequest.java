package com.fanduel.external.client.btobet.transfer;

import java.util.Objects;

import com.fanduel.transfer.annotation.TransferClass;
import com.fasterxml.jackson.annotation.JsonProperty;

@TransferClass
public class WinRequest extends BtoBetBettingRelatedRequest {
	@JsonProperty(value = "GameSubRoundId")
	private String gameSubRoundId;

	@JsonProperty(value = "BonusPromotionCode")
	private String bonusPromotionCode;

	@JsonProperty(value = "ExternalTransactionId")
	private Long externalTransactionId;

	public WinRequest() {
		super();
	}

	WinRequest( final Builder builder ) {
		super( builder );
		gameSubRoundId = builder.gameSubRoundId;
		bonusPromotionCode = builder.bonusPromotionCode;
		externalTransactionId = builder.externalTransactionId;
	}

	public String getGameSubRoundId() { return gameSubRoundId; }

	public void setGameSubRoundId(
			final String gameSubRoundId ) { this.gameSubRoundId = gameSubRoundId; }

	public String getBonusPromotionCode() { return bonusPromotionCode; }

	public void setBonusPromotionCode(
			final String bonusPromotionCode ) { this.bonusPromotionCode = bonusPromotionCode; }

	public Long getExternalTransactionId() { return externalTransactionId; }

	public void setExternalTransactionId(
			final Long externalTransactionId ) { this.externalTransactionId = externalTransactionId; }

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
		final WinRequest that = (WinRequest) o;
		return Objects.equals( gameSubRoundId, that.gameSubRoundId ) && Objects.equals(
				bonusPromotionCode, that.bonusPromotionCode ) && Objects.equals(
				externalTransactionId, that.externalTransactionId );
	}

	@Override
	public int hashCode() {
		return Objects.hash( super.hashCode(), gameSubRoundId, bonusPromotionCode,
				externalTransactionId );
	}

	@Override
	public String toString() {
		return super.toStringHelper()
				.add( "gameSubRoundId", gameSubRoundId )
				.add( "bonusPromotionCode", bonusPromotionCode )
				.add( "externalTransactionId", externalTransactionId )
				.toString();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder extends BtoBetBettingRelatedRequest.Builder {
		private String gameSubRoundId;
		private String bonusPromotionCode;
		private Long externalTransactionId;

		public Builder gameSubRoundId( final String gameSubRoundId ) {
			this.gameSubRoundId = gameSubRoundId;
			return this;
		}

		public Builder bonusPromotionCode( final String bonusPromotionCode ) {
			this.bonusPromotionCode = bonusPromotionCode;
			return this;
		}

		public Builder externalTransactionId( final Long externalTransactionId ) {
			this.externalTransactionId = externalTransactionId;
			return this;
		}

		@Override
		public WinRequest build() {
			return new WinRequest( this );
		}
	}
}
