package com.fanduel.external.client.btobet.transfer;

import java.math.BigDecimal;
import java.util.Objects;

import com.fanduel.transfer.annotation.TransferClass;
import com.fasterxml.jackson.annotation.JsonProperty;

@TransferClass
public class RefundResponse extends BtoBetResponse {
	@JsonProperty(value = "ExternalTransactionId")
	private Long externalTransactionId;

	@JsonProperty(value = "Balance", required = true)
	private BigDecimal balance;

	public RefundResponse() {
		super();
	}

	RefundResponse( final Builder builder ) {
		super( builder );
		externalTransactionId = builder.externalTransactionId;
		balance = builder.balance;
	}

	public static Builder builder() {
		return new Builder();
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance( final BigDecimal balance ) {
		this.balance = balance;
	}

	public Long getExternalTransactionId() {
		return externalTransactionId;
	}

	public void setExternalTransactionId( final Long externalTransactionId ) {
		this.externalTransactionId = externalTransactionId;
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
		final RefundResponse that = (RefundResponse) o;
		return Objects.equals( externalTransactionId,
				that.externalTransactionId ) && Objects.equals( balance, that.balance );
	}

	@Override
	public int hashCode() {
		return Objects.hash( super.hashCode(), externalTransactionId, balance );
	}

	@Override
	public String toString() {
		return super.toStringHelper()
				.add( "externalTransactionId", externalTransactionId )
				.add( "balance", balance )
				.toString();
	}

	public static class Builder extends BtoBetResponse.Builder {
		private Long externalTransactionId;
		private BigDecimal balance;

		public Builder externalTransactionId( final Long externalTransactionId ) {
			this.externalTransactionId = externalTransactionId;
			return this;
		}

		public Builder balance( final BigDecimal balance ) {
			this.balance = balance;
			return this;
		}

		@Override
		public RefundResponse build() {
			return new RefundResponse( this );
		}
	}
}
