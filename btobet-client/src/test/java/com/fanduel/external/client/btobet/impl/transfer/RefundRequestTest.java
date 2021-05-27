package com.fanduel.external.client.btobet.impl.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fanduel.external.client.btobet.transfer.RefundRequest;

class RefundRequestTest {
	private final static String API_USERNAME = "fanduel";
	private final static String API_PASSWORD = "fanduel123!";
	private final static String AUTH_TOKEN = "authtoken";
	private final static int CLIENT_ID = 123;
	private final static Long EXTERNAL_ID_1 = 12345l;
	private final static Long EXTERNAL_ID_2 = 54321l;
	private final static Long EXTERNAL_ID_3 = 13579l;
	private final static String ORIGINAL_PROVIDER = "ORIGINAL PROVIDER";
	private final static String GAME_CODE = "FanDuel";
	private final static BigDecimal AMOUNT = new BigDecimal( "10.0" );
	private final static String CURRENCY_CODE = "BRL";
	private final static String GAME_ROUND_ID = "123";
	private final static String TRANSACTION_ID = "321";

	private RefundRequest request1;
	private RefundRequest request2;
	private RefundRequest request3;

	@BeforeEach
	void before() {
		request1 = (RefundRequest) RefundRequest.builder()
				.originalProvider( ORIGINAL_PROVIDER )
				.amount( AMOUNT )
				.currency( CURRENCY_CODE )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.authToken( AUTH_TOKEN )
				.externalId( EXTERNAL_ID_1 )
				.gameCode( GAME_CODE )
				.apiUsername( API_USERNAME )
				.apiPassword( API_PASSWORD )
				.clientId( CLIENT_ID )
				.build();

		request2 = (RefundRequest) RefundRequest.builder()
				.originalProvider( ORIGINAL_PROVIDER )
				.amount( AMOUNT )
				.currency( CURRENCY_CODE )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.authToken( AUTH_TOKEN )
				.externalId( EXTERNAL_ID_2 )
				.gameCode( GAME_CODE )
				.apiUsername( API_USERNAME )
				.apiPassword( API_PASSWORD )
				.clientId( CLIENT_ID )
				.build();

		request3 = (RefundRequest) RefundRequest.builder()
				.originalProvider( ORIGINAL_PROVIDER )
				.amount( AMOUNT )
				.currency( CURRENCY_CODE )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.authToken( AUTH_TOKEN )
				.externalId( EXTERNAL_ID_3 )
				.gameCode( GAME_CODE )
				.apiUsername( API_USERNAME )
				.apiPassword( API_PASSWORD )
				.clientId( CLIENT_ID )
				.build();
	}

	@Test
	void testBuild() {
		assertEquals( API_USERNAME, request1.getApiUsername() );
		assertEquals( API_PASSWORD, request1.getApiPassword() );
		assertEquals( AUTH_TOKEN, request1.getAuthToken() );
		assertEquals( CLIENT_ID, request1.getClientId() );
		assertEquals( ORIGINAL_PROVIDER, request1.getOriginalProvider() );
		assertEquals( EXTERNAL_ID_1, request1.getExternalId() );
		assertEquals( GAME_CODE, request1.getGameCode() );
		assertEquals( AMOUNT, request1.getAmount() );
		assertEquals( CURRENCY_CODE, request1.getCurrency() );
		assertEquals( GAME_ROUND_ID, request1.getGameRoundId() );
		assertEquals( TRANSACTION_ID, request1.getTransactionId() );

		assertEquals( EXTERNAL_ID_2, request2.getExternalId() );

		assertEquals( EXTERNAL_ID_3, request3.getExternalId() );
	}

	@Test
	void testHashCodeEqualWhenSame() {
		request2.setExternalId( EXTERNAL_ID_1 );
		assertEquals( request1.hashCode(), request2.hashCode() );
		assertEquals( request1, request2 );
	}

	@Test
	void testToStringEqualWhenSame() {
		request2.setExternalId( EXTERNAL_ID_1 );
		assertEquals( request1.toString(), request2.toString() );
		assertEquals( request1, request2 );
	}

	@Test
	void testHashCodeNotEqualWhenDifferent() {
		assertNotEquals( request1.hashCode(), request3.hashCode() );
		assertNotEquals( request1, request3 );
	}

	@Test
	void testToStringNotEqualWhenDifferent() {
		assertNotEquals( request1.toString(), request3.toString() );
		assertNotEquals( request1, request3 );
	}

	@Test
	void testObjectEquality() {
		assertEquals( request1, request1 );
		assertNotEquals( null, request1 );
		assertNotEquals( request1, new RefundRequest() );
	}
}
