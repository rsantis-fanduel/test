package com.fanduel.external.client.paddypower.transfer;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import com.google.common.base.MoreObjects;

public class TransferRequest {

	private long accountId;
	private String token;
	private String reference;
	private BigDecimal amount;
	private Currency currency;
	private String description;

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId( final long accountId ) {
		this.accountId = accountId;
	}

	public String getToken() {
		return token;
	}

	public void setToken( final String token ) {
		this.token = token;
	}

	public String getReference() {
		return reference;
	}

	public void setReference( final String reference ) {
		this.reference = reference;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount( final BigDecimal amount ) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency( final Currency currency ) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( final String description ) {
		this.description = description;
	}

	@Override
	public boolean equals( final Object o ) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		final TransferRequest that = (TransferRequest) o;
		return accountId == that.accountId && Objects.equals( token, that.token ) && Objects.equals(
				reference, that.reference ) && Objects.equals( amount,
				that.amount ) && Objects.equals( currency, that.currency ) && Objects.equals(
				description, that.description );
	}

	@Override
	public int hashCode() {
		return Objects.hash( accountId, token, reference, amount, currency, description );
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "accountId", accountId )
				.add( "reference", reference )
				.add( "amount", amount )
				.add( "currency", currency )
				.add( "description", description )
				.toString();
	}

	public static final class Builder {
		private long accountId;
		private String token;
		private String reference;
		private BigDecimal amount;
		private Currency currency;
		private String description;

		private Builder() {}

		public static Builder aTransferRequest() { return new Builder(); }

		public Builder accountId( long accountId ) {
			this.accountId = accountId;
			return this;
		}

		public Builder token( String token ) {
			this.token = token;
			return this;
		}

		public Builder reference( String reference ) {
			this.reference = reference;
			return this;
		}

		public Builder amount( BigDecimal amount ) {
			this.amount = amount;
			return this;
		}

		public Builder currency( Currency currency ) {
			this.currency = currency;
			return this;
		}

		public Builder description( String description ) {
			this.description = description;
			return this;
		}

		public TransferRequest build() {
			TransferRequest transferRequest = new TransferRequest();
			transferRequest.setAccountId( accountId );
			transferRequest.setToken( token );
			transferRequest.setReference( reference );
			transferRequest.setAmount( amount );
			transferRequest.setCurrency( currency );
			transferRequest.setDescription( description );
			return transferRequest;
		}
	}
}
