package com.fanduel.external.client.paddypower.transfer;

import java.util.Objects;

import com.google.common.base.MoreObjects;

public class UserSessionDetailsRequest {

	private String token;

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
		final UserSessionDetailsRequest that = (UserSessionDetailsRequest) o;
		return Objects.equals( token, that.token );
	}

	@Override
	public int hashCode() {
		return Objects.hash( token );
	}

	public static final class Builder {
		private String token;

		private Builder() {}

		public static Builder anUserSessionDetailsRequest() { return new Builder(); }

		public Builder token( String token ) {
			this.token = token;
			return this;
		}

		public UserSessionDetailsRequest build() {
			UserSessionDetailsRequest userSessionDetailsRequest = new UserSessionDetailsRequest();
			userSessionDetailsRequest.setToken( token );
			return userSessionDetailsRequest;
		}
	}
}
