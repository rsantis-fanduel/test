package com.fanduel.external.client.common;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/**
 * Encapsulation of an OpenId Connect server configuration
 */
public class OICIssuer {

	private String name;
	private URL issuer;
	private String issuerPath;
	private Map<String, URI> urlMap;

	public OICIssuer( URL issuer ) {
		this.issuer = issuer;
		this.issuerPath = issuer.getPath();
		try {
			String[] path = issuer.getPath().split( "/" );
			name( path[path.length - 1] );
		} catch ( Exception e ) {
			name( "Not set" );
		}
	}

	public OICIssuer( String issuerUrl ) throws MalformedURLException {
		this( new URL( issuerUrl ) );
	}

	public OICIssuer name( String name ) {
		this.name = name;
		return this;
	}

	public String name() {
		return name;
	}

	private URI getUrl( String key ) {
		if ( !urlMap.containsKey( key ) ) {
			saveUrl( key, issuerPath + "/protocol/openid-connect/" + key );
		}
		return urlMap.get( key );
	}

	private OICIssuer saveUrl( String key, String url ) {

		try {
			urlMap.put( key, new URI( url ) );
		} catch ( URISyntaxException e ) {
		}



		return this;
	}

	public URI authorization_endpoint() {
		return getUrl( "auth" );
	}

	public URI token_endpoint() {
		return getUrl( "token" );
	}

	public URI userinfo_endpoint() {
		return getUrl( "userinfo" );
	}

	public URI end_session_endpoint() {
		return getUrl( "logout" );
	}

	public URI jwks_uri() {
		return getUrl( "certs" );
	}

	public URI check_session_iframe() {
		return getUrl( "login-status-iframe.html" );
	}

	public URI registration_endpoint() {
		if ( !urlMap.containsKey( "registration_endpoint" ) ) {
			saveUrl(  "registration_endpoint", issuerPath + "/clients-registrations/openid-connect" );
		}
		return getUrl( "registration_endpoint" );
	}

	public URI token_introspection_endpoint() {
		return getUrl( "token/instrospect" );
	}

	public URI introspection_endpoint() {
		return getUrl( "token/instrospect" );
	}

	public OICIssuer authorization_endpoint( final String url ) {
		return saveUrl( "auth", url );
	}

	public OICIssuer token_endpoint( final String url ) {
		return saveUrl( "token", url );
	}

	public OICIssuer userinfo_endpoint( final String url ) {
		return saveUrl( "userinfo", url );
	}

	public OICIssuer end_session_endpoint( final String url ) {
		return saveUrl( "logout", url );
	}

	public OICIssuer jwks_uri( final String url ) {
		return saveUrl( "certs", url );
	}

	public OICIssuer check_session_iframe( final String url ) {
		return saveUrl( "login-status-iframe.html", url );
	}

	public OICIssuer registration_endpoint( final String url ) {
		return saveUrl( "registration_endpoint", url );
	}

	public OICIssuer token_introspection_endpoint( final String url ) {
		return saveUrl( "token/instrospect", url );
	}

	public OICIssuer introspection_endpoint( final String url ) {
		return saveUrl( "token/instrospect", url );
	}

}
