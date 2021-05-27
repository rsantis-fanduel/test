package com.fanduel.external.client.btobet.transfer;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class BtoBetRequestGeneral {
	@JsonProperty(value = "ApiUsername", required = true)
	private String apiUsername;

	@JsonProperty(value = "ApiPassword", required = true)
	private String apiPassword;

	@JsonProperty(value = "ClientId", required = true)
	private int clientId;

	public BtoBetRequestGeneral() {}

	public String getApiPassword() {
		return apiPassword;
	}

	public int getClientId() {
		return clientId;
	}

	public String getApiUsername() {
		return apiUsername;
	}

	public void setClientId( final int clientId ) {
		this.clientId = clientId;
	}

	public void setApiUsername( final String apiUsername ) {
		this.apiUsername = apiUsername;
	}

	public void setApiPassword( final String apiPassword ) {
		this.apiPassword = apiPassword;
	}

	public MoreObjects.ToStringHelper toStringHelper() {
		return MoreObjects.toStringHelper( this )
				.add( "apiUsername", apiUsername )
				.add( "apiPassword", apiPassword )
				.add( "clientId", clientId );
	}

	@Override
	public boolean equals( final Object o ) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		final BtoBetRequestGeneral that = (BtoBetRequestGeneral) o;
		return clientId == that.clientId && Objects.equals( apiUsername,
				that.apiUsername ) && Objects.equals( apiPassword, that.apiPassword );
	}

	@Override
	public int hashCode() {
		return Objects.hash( apiUsername, apiPassword, clientId );
	}

	@Override
	public String toString() {
		return toStringHelper().toString();
	}

	BtoBetRequestGeneral( final Builder builder ) {
		apiUsername = builder.apiUsername;
		apiPassword = builder.apiPassword;
		clientId = builder.clientId;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String apiUsername;
		private String apiPassword;
		private int clientId;

		public Builder apiUsername( final String apiUsername ) {
			this.apiUsername = apiUsername;
			return this;
		}

		public Builder apiPassword( final String apiPassword ) {
			this.apiPassword = apiPassword;
			return this;
		}

		public Builder clientId( final int clientId ) {
			this.clientId = clientId;
			return this;
		}

		public BtoBetRequestGeneral build() {
			return new BtoBetRequestGeneral( this );
		}
	}
}
