package com.fanduel.external.client.globo.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import static com.fanduel.external.client.common.exception.ClientErrorCode.GLOBO_CLIENT_REQUEST_ERROR;
import static com.fanduel.external.client.common.exception.ClientErrorCode.GLOBO_RESPONSE_DESERIALIZATION_ERROR;
import static com.fanduel.external.client.common.exception.ClientErrorCode.GLOBO_TOKEN_PARSING_ERROR;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fanduel.external.client.globo.transfer.GloboAuthoriseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;

class GloboReponseTransformerTest {
	private String validResponseValidUser;
	private String validResponseInvalidUser;

	private GloboResponseTransformer transformer;

	@BeforeEach
	void setUp() throws Exception {
		transformer = new GloboResponseTransformer( new ObjectMapper() );

		// created and validated using https://www.jsonwebtoken.io/
		validResponseValidUser = Resources.toString(
				GloboClientImplTest.class.getResource( "/globo_valid_response.json" ),
				StandardCharsets.UTF_8 );
		validResponseInvalidUser = Resources.toString(
				GloboClientImplTest.class.getResource( "/globo_valid_response_invalid_user.json" ),
				StandardCharsets.UTF_8 );
	}

	@Test
	void transform_givenValidResponseAndValidUser_shouldReturnAGloboResponse() {
		final GloboResponse response = new GloboResponse();
		response.setBody( validResponseValidUser );
		response.setCode( 200 );

		final GloboAuthoriseResponse authoriseResponse = transformer.transform( response );

		assertNull( authoriseResponse.getError() );
		assertNotNull( authoriseResponse.getGloboUser() );
		assertEquals( "ceb4af90-dfe8-49e1-b5c8-505864187e68",
				authoriseResponse.getGloboUser().getGloboId() );
	}

	@Test
	void transform_givenValidResponseAndInvalidUser_shouldReturnAnError() {
		final GloboResponse response = new GloboResponse();
		response.setBody( validResponseInvalidUser );
		response.setCode( 200 );

		final GloboAuthoriseResponse authoriseResponse = transformer.transform( response );

		assertNotNull( authoriseResponse.getError() );
		assertEquals( GLOBO_TOKEN_PARSING_ERROR, authoriseResponse.getError().getErrorCode() );
	}

	@Test
	void transform_givenAnInvalidResponse_shouldReturnAnError() {
		final GloboResponse response = new GloboResponse();
		response.setBody( "COMPLETE_GARBAGE" );
		response.setCode( 200 );

		final GloboAuthoriseResponse authoriseResponse = transformer.transform( response );

		assertNotNull( authoriseResponse.getError() );
		assertEquals( GLOBO_RESPONSE_DESERIALIZATION_ERROR,
				authoriseResponse.getError().getErrorCode() );
	}

	@Test
	void transform_givenAnErrorResponse_shouldReturnAnError() {
		final GloboResponse response = new GloboResponse();
		response.setBody( "" );
		response.setCode( 400 );

		final GloboAuthoriseResponse authoriseResponse = transformer.transform( response );

		assertNotNull( authoriseResponse.getError() );
		assertEquals( GLOBO_CLIENT_REQUEST_ERROR, authoriseResponse.getError().getErrorCode() );
	}

}
