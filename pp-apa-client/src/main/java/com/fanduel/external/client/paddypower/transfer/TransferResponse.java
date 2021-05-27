package com.fanduel.external.client.paddypower.transfer;

import java.math.BigDecimal;
import java.util.Objects;

import com.google.common.base.MoreObjects;

public class TransferResponse {

	private long transferId;
	private BigDecimal balance;
	private String token;

	public long getTransferId() {
		return transferId;
	}

	public void setTransferId( final long transferId ) {
		this.transferId = transferId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance( final BigDecimal balance ) {
		this.balance = balance;
	}

	public String getToken() {
		return token;
	}

	public void setToken( final String token ) {
		this.token = token;
	}

	@Override
	public boolean equals( final Object o ) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		final TransferResponse that = (TransferResponse) o;
		return transferId == that.transferId && Objects.equals( balance,
				that.balance ) && Objects.equals( token, that.token );
	}

	@Override
	public int hashCode() {
		return Objects.hash( transferId, balance, token );
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "transferId", transferId )
				.toString();
	}

	public static final class Builder {
		private long transferId;
		private BigDecimal balance;
		private String token;

		private Builder() {}

		public static Builder aTransferResponse() { return new Builder(); }

		public Builder transferId( long transferId ) {
			this.transferId = transferId;
			return this;
		}

		public Builder balance( BigDecimal balance ) {
			this.balance = balance;
			return this;
		}

		public Builder token( String token ) {
			this.token = token;
			return this;
		}

		public TransferResponse build() {
			TransferResponse transferResponse = new TransferResponse();
			transferResponse.setTransferId( transferId );
			transferResponse.setBalance( balance );
			transferResponse.setToken( token );
			return transferResponse;
		}
	}
}
