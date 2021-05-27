package com.fanduel.external.client.btobet.transfer;

import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class BtoBetBettingRelatedRequest extends BtoBetFinancialRequest {
	@JsonProperty(value = "OriginalProvider")
	private String originalProvider;

	@JsonProperty(value = "Amount", required = true)
	private BigDecimal amount;

	@JsonProperty(value = "Currency", required = true)
	private String currency;

	@JsonProperty(value = "GameRoundId", required = true)
	private String gameRoundId;

	@JsonProperty(value = "TransactionId", required = true)
	private String transactionId;

	public BtoBetBettingRelatedRequest() {
		super();
	}

	public String getOriginalProvider() {
		return originalProvider;
	}

	public void setOriginalProvider( final String originalProvider ) {
		this.originalProvider = originalProvider;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount( final BigDecimal amount ) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency( final String currency ) {
		this.currency = currency;
	}

	public String getGameRoundId() {
		return gameRoundId;
	}

	public void setGameRoundId( final String gameRoundId ) {
		this.gameRoundId = gameRoundId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId( final String transactionId ) {
		this.transactionId = transactionId;
	}

	@Override
	public MoreObjects.ToStringHelper toStringHelper() {
		return super.toStringHelper()
				.add( "originalProvider", originalProvider )
				.add( "amount", amount )
				.add( "currency", currency )
				.add( "gameRoundId", gameRoundId )
				.add( "transactionId", transactionId );
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
		final BtoBetBettingRelatedRequest that = (BtoBetBettingRelatedRequest) o;
		return Objects.equals( originalProvider, that.originalProvider ) && Objects.equals( amount,
				that.amount ) && Objects.equals( currency, that.currency ) && Objects.equals(
				gameRoundId, that.gameRoundId ) && Objects.equals( transactionId,
				that.transactionId );
	}

	@Override
	public int hashCode() {
		return Objects.hash( super.hashCode(), originalProvider, amount, currency, gameRoundId,
				transactionId );
	}

	@Override
	public String toString() {
		return toStringHelper().toString();
	}

	BtoBetBettingRelatedRequest( final Builder builder ) {
		super( builder );
		originalProvider = builder.originalProvider;
		amount = builder.amount;
		currency = builder.currency;
		gameRoundId = builder.gameRoundId;
		transactionId = builder.transactionId;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder extends BtoBetFinancialRequest.Builder {
		private String originalProvider;
		private BigDecimal amount;
		private String currency;
		private String gameRoundId;
		private String transactionId;

		public Builder originalProvider( final String originalProvider ) {
			this.originalProvider = originalProvider;
			return this;
		}

		public Builder amount( final BigDecimal amount ) {
			this.amount = amount;
			return this;
		}

		public Builder currency( final String currency ) {
			this.currency = currency;
			return this;
		}

		public Builder gameRoundId( final String gameRoundId ) {
			this.gameRoundId = gameRoundId;
			return this;
		}

		public Builder transactionId( final String transactionId ) {
			this.transactionId = transactionId;
			return this;
		}

		@Override
		public BtoBetBettingRelatedRequest build() {
			return new BtoBetBettingRelatedRequest( this );
		}
	}
}
