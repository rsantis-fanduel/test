package com.fanduel.external.client.btobet.transfer;

import java.math.BigDecimal;

import com.fanduel.transfer.annotation.TransferClass;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

@TransferClass
public class WinResponse extends BtoBetResponse {
	@JsonProperty(value = "ExternalTransactionId")
	private Long externalTransactionId;

	@JsonProperty(value = "Balance", required = true)
	private BigDecimal balance;

	public Long getExternalTransactionId() { return externalTransactionId; }

	public void setExternalTransactionId(
			final Long externalTransactionId ) { this.externalTransactionId = externalTransactionId; }

	public BigDecimal getBalance() { return balance; }

	public void setBalance( final BigDecimal balance ) { this.balance = balance; }

	@Override
	public boolean equals( final Object o ) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		final WinResponse that = (WinResponse) o;
		if ( !super.equals( that ) ) {
			return false;
		}
		return Objects.equal( externalTransactionId, that.externalTransactionId ) && Objects.equal(
				balance, that.balance );
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( super.hashCode(), externalTransactionId, balance );
	}

	@Override
	public String toString() {
		return super.toStringHelper()
				.add( "externalTransactionId", externalTransactionId )
				.add( "balance", balance )
				.toString();
	}

	public static Builder builder() {
		return new Builder();
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

		public WinResponse build() {
			return new WinResponse( this );
		}
	}

	public WinResponse() {}

	public WinResponse( final Builder builder ) {
		super( builder );
		externalTransactionId = builder.externalTransactionId;
		balance = builder.balance;
	}
}
