package com.fanduel.external.client.globo.transfer;

import com.google.common.base.MoreObjects;

public class GloboUser {
	private String globoId;
	private String email;
	private String preferredUsername;
	private String name;
	private String givenName;

	public GloboUser( final String globoId, final String email, final String preferredUsername,
			final String name, final String givenName ) {
		this.globoId = globoId;
		this.email = email;
		this.preferredUsername = preferredUsername;
		this.name = name;
		this.givenName = givenName;
	}

	public String getGloboId() {
		return globoId;
	}

	public void setGloboId( final String globoId ) {
		this.globoId = globoId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( final String email ) {
		this.email = email;
	}

	public String getPreferredUsername() {
		return preferredUsername;
	}

	public void setPreferredUsername( final String preferredUsername ) {
		this.preferredUsername = preferredUsername;
	}

	public String getName() {
		return name;
	}

	public void setName( final String name ) {
		this.name = name;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName( final String givenName ) {
		this.givenName = givenName;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "globoId", globoId )
				.add( "email", email )
				.add( "preferredUsername", preferredUsername )
				.add( "name", name )
				.add( "givenName", givenName )
				.toString();
	}
}
