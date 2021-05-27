package com.fanduel.external.client.globo.impl;

import static com.fanduel.external.client.common.exception.ClientErrorCode.GLOBO_CLIENT_REQUEST_ERROR;
import static com.fanduel.external.client.common.exception.ClientErrorCode.GLOBO_RESPONSE_DESERIALIZATION_ERROR;
import static com.fanduel.external.client.common.exception.ClientErrorCode.GLOBO_TOKEN_PARSING_ERROR;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanduel.api.exception.APICodedException;
import com.fanduel.external.client.globo.transfer.GloboAuthoriseResponse;
import com.fanduel.external.client.globo.transfer.GloboUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSObject;

import net.minidev.json.JSONObject;

public class GloboResponseTransformer {
	private static final Logger LOGGER = LoggerFactory.getLogger( GloboResponseTransformer.class );
	private final ObjectMapper objectMapper;

	public GloboResponseTransformer( final ObjectMapper objectMapper ) {
		this.objectMapper = objectMapper;
	}

	public GloboAuthoriseResponse transform( final GloboResponse response ) {
		// exit early on an HTTP Errors
		if ( isHTTPErrorResponse( response ) ) {
			return GloboAuthoriseResponse.builder()
					.error( new APICodedException( GLOBO_CLIENT_REQUEST_ERROR ) )
					.build();
		}

		final GloboResponseBody body;
		try {
			body = objectMapper.readValue( response.getBody(), GloboResponseBody.class );
		} catch ( final Exception e ) {
			LOGGER.error( "Error deserializing response {}: {}", response, e );
			return GloboAuthoriseResponse.builder()
					.error( new APICodedException( GLOBO_RESPONSE_DESERIALIZATION_ERROR ) )
					.build();
		}

		// standard OIDC
		final GloboUser user;
		try {
			final JWSObject token = JWSObject.parse( body.getAccessToken() );
			final JSONObject json = token.getPayload().toJSONObject();
			// @formatter:off
			user = new GloboUser(
					Objects.requireNonNull( json.getAsString( "globo_id" ) ),
					json.getOrDefault( "email", "" ).toString(),
					json.getOrDefault( "preferred_name", "" ).toString(),
					json.getOrDefault( "name", "" ).toString(),
					json.getOrDefault( "given_name ", "" ).toString()
			);
			// @formatter:on
		} catch ( final Exception e ) {
			LOGGER.error( "Error deserializing the Globo user from response {}: {}", body, e );
			return GloboAuthoriseResponse.builder()
					.error( new APICodedException( GLOBO_TOKEN_PARSING_ERROR ) )
					.build();
		}

		return GloboAuthoriseResponse.builder()
				.accessToken( body.getAccessToken() )
				.refreshToken( body.getRefreshToken() )
				.idToken( body.getIdToken() )
				.globoUser( user )
				.build();
	}

	private static boolean isHTTPErrorResponse( final GloboResponse response ) {
		return response.getCode() >= 400;
	}
}
