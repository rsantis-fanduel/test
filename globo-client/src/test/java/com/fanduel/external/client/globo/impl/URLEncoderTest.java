package com.fanduel.external.client.globo.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

class URLEncoderTest {

	private URLEncoder encoder;

	@BeforeEach
	void setUp() {
		encoder = new URLEncoder();
	}

	@Test
	void test_givenEmptyObject_shouldReturnEmptyString() {
		final String queryParams = encoder.encode( Collections.emptyList() );

		assertEquals( "", queryParams );
	}

	@Test
	void test_givenFullObject_shouldReturnQueryParamsWithAllNonNullFields() {
		final List<BasicNameValuePair> body = ImmutableList.of(
				new BasicNameValuePair( "client_id", "client_id" ),
				new BasicNameValuePair( "client_secret", "client_secret" ),
				new BasicNameValuePair( "code", "code" ),
				new BasicNameValuePair( "redirect_uri", null ) );

		final String queryParams = encoder.encode( body );

		assertNotEquals( "&", queryParams.substring( 0, 1 ) );
		assertTrue( queryParams.contains( "client_id=client_id" ) );
		assertTrue( queryParams.contains( "client_secret=client_secret" ) );
		assertTrue( queryParams.contains( "code=code" ) );
		assertFalse( queryParams.contains( "=redirect_uri" ) );
	}
}
