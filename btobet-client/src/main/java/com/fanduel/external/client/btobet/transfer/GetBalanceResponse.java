package com.fanduel.external.client.btobet.transfer;

import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetBalanceResponse extends BtoBetResponse {
	@JsonProperty(value = "Balance", required = true)
	private BigDecimal balance;

	public GetBalanceResponse() {
		super();
	}

	GetBalanceResponse( final Builder builder ) {
		super( builder );
		balance = builder.balance;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance( final BigDecimal balance ) {
		this.balance = balance;
	}

	@Override
	public boolean equals( final Object o ) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		final GetBalanceResponse response = (GetBalanceResponse) o;
		if( !super.equals(response) ) {
			return false;
		}
		return Objects.equals( balance, response.balance );
	}

	@Override
	public int hashCode() {
		return Objects.hash( balance );
	}

	@Override
	public String toString() {
		return super.toStringHelper().add( "balance", balance )
				.toString();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder extends BtoBetResponse.Builder {
		private BigDecimal balance;

		public Builder balance( BigDecimal balance ) {
			this.balance = balance;
			return this;
		}

		public GetBalanceResponse build() {
			return new GetBalanceResponse( this );
		}
	}
}
