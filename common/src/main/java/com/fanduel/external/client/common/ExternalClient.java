package com.fanduel.external.client.common;

public enum ExternalClient {
	PADDYPOWER( "PADDYPOWER" ),
	GLOBO( "GLOBO" ),
	BTOBET( "BTOBET" ),
	COMMON( "COMMON" );

	private final String client;

	ExternalClient( final String client ) {
		this.client = client;
	}

	@Override
	public String toString() {
		return client;
	}
}
