package com.fanduel.external.client.btobet.impl.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fanduel.external.client.btobet.transfer.AuthenticateRequest;
import com.fanduel.external.client.btobet.transfer.BtoBetRequestGeneral;

class AuthenticateRequestTest {
	private final String API_USERNAME = "fanduel";
	private final String API_PASSWORD = "fanduel123!";
	private final String AUTH_TOKEN_1 = "authtoken1";
	private final String AUTH_TOKEN_2 = "authtoken2";
	private final String AUTH_TOKEN_3 = "authtoken3";
	private final int CLIENT_ID = 123;
	private final String GAME_CODE = "500";

	private AuthenticateRequest authenticateRequest1;
	private AuthenticateRequest authenticateRequest2;
	private AuthenticateRequest authenticateRequest3;

	@BeforeEach
	void before() {
		authenticateRequest1 = (AuthenticateRequest) AuthenticateRequest.builder()
				.gameCode( GAME_CODE )
				.authToken( AUTH_TOKEN_1 )
				.apiUsername( API_USERNAME )
				.apiPassword( API_PASSWORD )
				.clientId( CLIENT_ID )
				.build();

		authenticateRequest2 = (AuthenticateRequest) AuthenticateRequest.builder()
				.authToken( AUTH_TOKEN_2 )
				.gameCode( GAME_CODE )
				.apiUsername( API_USERNAME )
				.apiPassword( API_PASSWORD )
				.clientId( CLIENT_ID )
				.build();

		authenticateRequest3 = (AuthenticateRequest) AuthenticateRequest.builder()
				.authToken( AUTH_TOKEN_3 )
				.gameCode( GAME_CODE )
				.apiUsername( API_USERNAME )
				.apiPassword( API_PASSWORD )
				.clientId( CLIENT_ID )
				.build();
	}

	@Test
	void testBuild() {
		assertEquals( AUTH_TOKEN_1, authenticateRequest1.getAuthToken() );
		assertEquals( GAME_CODE, authenticateRequest1.getGameCode() );
		assertEquals( API_USERNAME, authenticateRequest1.getApiUsername() );
		assertEquals( API_PASSWORD, authenticateRequest1.getApiPassword() );
		assertEquals( CLIENT_ID, authenticateRequest1.getClientId() );

		assertEquals( AUTH_TOKEN_2, authenticateRequest2.getAuthToken() );
		assertEquals( GAME_CODE, authenticateRequest2.getGameCode() );
		assertEquals( API_USERNAME, authenticateRequest2.getApiUsername() );
		assertEquals( API_PASSWORD, authenticateRequest2.getApiPassword() );
		assertEquals( CLIENT_ID, authenticateRequest2.getClientId() );

		assertEquals( AUTH_TOKEN_3, authenticateRequest3.getAuthToken() );
		assertEquals( GAME_CODE, authenticateRequest3.getGameCode() );
		assertEquals( API_USERNAME, authenticateRequest3.getApiUsername() );
		assertEquals( API_PASSWORD, authenticateRequest3.getApiPassword() );
		assertEquals( CLIENT_ID, authenticateRequest3.getClientId() );
	}

	@Test
	void testHashCodeEqualWhenSame() {
		authenticateRequest2.setAuthToken( AUTH_TOKEN_1 );
		assertEquals( authenticateRequest1.hashCode(), authenticateRequest2.hashCode() );
		assertEquals( authenticateRequest1, authenticateRequest2 );
	}

	@Test
	void testToStringEqualWhenSame() {
		authenticateRequest2.setAuthToken( AUTH_TOKEN_1 );
		assertEquals( authenticateRequest1.toString(), authenticateRequest2.toString() );
		assertEquals( authenticateRequest1, authenticateRequest2 );
	}

	@Test
	void testHashCodeNotEqualWhenDifferent() {
		authenticateRequest3.setGameCode( null );
		assertNotEquals( authenticateRequest1.hashCode(), authenticateRequest3.hashCode() );
		assertNotEquals( authenticateRequest1, authenticateRequest3 );
	}

	@Test
	void testToStringNotEqualWhenDifferent() {
		assertNotEquals( authenticateRequest1.toString(), authenticateRequest3.toString() );
		assertNotEquals( authenticateRequest1, authenticateRequest3 );
	}

	@Test
	void testObjectEquality() {
		assertEquals( authenticateRequest1, authenticateRequest1 );
		assertNotEquals( null, authenticateRequest1 );
		assertNotEquals( authenticateRequest1, new AuthenticateRequest() );
		assertNotEquals( authenticateRequest1, new BtoBetRequestGeneral() );
	}
}
