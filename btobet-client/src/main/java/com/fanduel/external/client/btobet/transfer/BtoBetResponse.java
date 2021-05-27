package com.fanduel.external.client.btobet.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class BtoBetResponse {
	@JsonProperty(value = "StatusCode", required = true)
	private int statusCode;

	@JsonProperty(value = "StatusMessage", required = true)
	private String statusMessage;

	public BtoBetResponse() { }

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusCode( final int statusCode ) {
		this.statusCode = statusCode;
	}

	public void setStatusMessage( final String statusMessage ) {
		this.statusMessage = statusMessage;
	}

	public MoreObjects.ToStringHelper toStringHelper() {
		return MoreObjects.toStringHelper( this )
				.add( "statusCode", statusCode )
				.add( "statusMessage", statusMessage );
	}

	@Override
	public boolean equals( final Object o ) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		final BtoBetResponse response = (BtoBetResponse) o;
		return statusCode == response.getStatusCode() && Objects.equal( statusMessage,
				response.getStatusMessage() );
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( statusCode, statusMessage );
	}

	@Override
	public String toString() {
		return toStringHelper().toString();
	}

	BtoBetResponse( final Builder builder ) {
		statusCode = builder.statusCode;
		statusMessage = builder.statusMessage;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private int statusCode;
		private String statusMessage;

		public Builder statusCode( final int statusCode ) {
			this.statusCode = statusCode;
			return this;
		}

		public Builder statusMessage( final String statusMessage ) {
			this.statusMessage = statusMessage;
			return this;
		}

		public BtoBetResponse build() {
			return new BtoBetResponse( this );
		}
	}
}
