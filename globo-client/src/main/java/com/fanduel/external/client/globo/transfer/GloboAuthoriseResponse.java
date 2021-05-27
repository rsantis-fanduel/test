package com.fanduel.external.client.globo.transfer;

import com.fanduel.api.exception.APICodedException;
import com.google.common.base.MoreObjects;

public class GloboAuthoriseResponse {
	private String accessToken;
	private String refreshToken;
	private String idToken;
	private APICodedException error;
	private GloboUser globoUser;

	private GloboAuthoriseResponse( final Builder builder ) {
		accessToken = builder.accessToken;
		refreshToken = builder.refreshToken;
		idToken = builder.idToken;
		error = builder.error;
		globoUser = builder.globoUser;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken( final String accessToken ) {
		this.accessToken = accessToken;
	}

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken( final String idToken ) {
		this.idToken = idToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken( final String refreshToken ) {
		this.refreshToken = refreshToken;
	}

	public APICodedException getError() {
		return error;
	}

	public void setError( final APICodedException error ) {
		this.error = error;
	}

	public GloboUser getGloboUser() {
		return globoUser;
	}

	public void setGloboUser( final GloboUser globoUser ) {
		this.globoUser = globoUser;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "accessToken", accessToken )
				.add( "refreshToken", refreshToken )
				.add( "idToken", idToken )
				.add( "userSession", globoUser )
				.add( "error", error )
				.toString();
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private String accessToken;
		private String refreshToken;
		private String idToken;
		private APICodedException error;
		private GloboUser globoUser;

		public Builder accessToken( final String accessToken ) {
			this.accessToken = accessToken;
			return this;
		}

		public Builder refreshToken( final String refreshToken ) {
			this.refreshToken = refreshToken;
			return this;
		}

		public Builder idToken( final String idToken ) {
			this.idToken = idToken;
			return this;
		}

		public Builder error( final APICodedException error ) {
			this.error = error;
			return this;
		}

		public Builder globoUser( final GloboUser globoUser ) {
			this.globoUser = globoUser;
			return this;
		}

		public GloboAuthoriseResponse build() {
			return new GloboAuthoriseResponse( this );
		}
	}
}
