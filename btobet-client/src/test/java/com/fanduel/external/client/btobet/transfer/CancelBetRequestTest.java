package com.fanduel.external.client.btobet.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CancelBetRequestTest {
	private final String API_USERNAME = "fanduel";
	private final String API_PASSWORD = "fanduel123!";
	private final String AUTH_TOKEN = "authtoken";
	private final int CLIENT_ID = 123;
	private final String ORIGINAL_PROVIDER = null;
	private final Long EXTERNAL_ID1 = 100L;
	private final Long EXTERNAL_ID2 = 100L;
	private final Long EXTERNAL_ID3 = 101L;
	private final String GAME_CODE = "500";
	private final BigDecimal AMOUNT1 = BigDecimal.valueOf( 0.0 );
	private final BigDecimal AMOUNT2 = BigDecimal.valueOf( 0.0 );
	private final BigDecimal AMOUNT3 = BigDecimal.valueOf( 10.65 );
	private final String CURRENCY = "BRL";
	private final String GAME_ROUND_ID = "123";
	private final String GAME_SUB_ROUND_ID = "456";
	private final String TRANSACTION_ID = "123456789";

	private CancelBetRequest cancelBetRequest1;
	private CancelBetRequest cancelBetRequest2;
	private CancelBetRequest cancelBetRequest3;

	@BeforeEach
	void before() {
		cancelBetRequest1 = (CancelBetRequest) CancelBetRequest.builder()
				.originalProvider( ORIGINAL_PROVIDER )
				.amount( AMOUNT1 )
				.currency( CURRENCY )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.authToken( AUTH_TOKEN )
				.externalId( EXTERNAL_ID1 )
				.gameCode( GAME_CODE )
				.apiUsername( API_USERNAME )
				.apiPassword( API_PASSWORD )
				.clientId( CLIENT_ID )
				.build();

		cancelBetRequest2 = (CancelBetRequest) CancelBetRequest.builder()
				.originalProvider( ORIGINAL_PROVIDER )
				.amount( AMOUNT2 )
				.currency( CURRENCY )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.authToken( AUTH_TOKEN )
				.externalId( EXTERNAL_ID2 )
				.gameCode( GAME_CODE )
				.apiUsername( API_USERNAME )
				.apiPassword( API_PASSWORD )
				.clientId( CLIENT_ID )
				.build();

		cancelBetRequest3 = (CancelBetRequest) CancelBetRequest.builder()
				.originalProvider( ORIGINAL_PROVIDER )
				.amount( AMOUNT3 )
				.currency( CURRENCY )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.authToken( AUTH_TOKEN )
				.externalId( EXTERNAL_ID3 )
				.gameCode( GAME_CODE )
				.apiUsername( API_USERNAME )
				.apiPassword( API_PASSWORD )
				.clientId( CLIENT_ID )
				.build();
	}

	@Test
	public void testBuild() {
		assertEquals( cancelBetRequest1.getApiUsername(), API_USERNAME );
		assertEquals( cancelBetRequest1.getApiPassword(), API_PASSWORD );
		assertEquals( cancelBetRequest1.getAuthToken(), AUTH_TOKEN );
		assertEquals( cancelBetRequest1.getClientId(), CLIENT_ID );
		assertEquals( cancelBetRequest1.getOriginalProvider(), ORIGINAL_PROVIDER );
		assertEquals( cancelBetRequest1.getExternalId(), EXTERNAL_ID1 );
		assertEquals( cancelBetRequest1.getGameCode(), GAME_CODE );
		assertEquals( cancelBetRequest1.getAmount(), AMOUNT1 );
		assertEquals( cancelBetRequest1.getCurrency(), CURRENCY );
		assertEquals( cancelBetRequest1.getGameRoundId(), GAME_ROUND_ID );
		assertEquals( cancelBetRequest1.getTransactionId(), TRANSACTION_ID );

		assertEquals( cancelBetRequest2.getApiUsername(), API_USERNAME );
		assertEquals( cancelBetRequest2.getApiPassword(), API_PASSWORD );
		assertEquals( cancelBetRequest2.getAuthToken(), AUTH_TOKEN );
		assertEquals( cancelBetRequest2.getClientId(), CLIENT_ID );
		assertEquals( cancelBetRequest2.getOriginalProvider(), ORIGINAL_PROVIDER );
		assertEquals( cancelBetRequest2.getExternalId(), EXTERNAL_ID2 );
		assertEquals( cancelBetRequest2.getGameCode(), GAME_CODE );
		assertEquals( cancelBetRequest2.getAmount(), AMOUNT2 );
		assertEquals( cancelBetRequest2.getCurrency(), CURRENCY );
		assertEquals( cancelBetRequest2.getGameRoundId(), GAME_ROUND_ID );
		assertEquals( cancelBetRequest2.getTransactionId(), TRANSACTION_ID );

		assertEquals( cancelBetRequest3.getApiUsername(), API_USERNAME );
		assertEquals( cancelBetRequest3.getApiPassword(), API_PASSWORD );
		assertEquals( cancelBetRequest3.getAuthToken(), AUTH_TOKEN );
		assertEquals( cancelBetRequest3.getClientId(), CLIENT_ID );
		assertEquals( cancelBetRequest3.getOriginalProvider(), ORIGINAL_PROVIDER );
		assertEquals( cancelBetRequest3.getExternalId(), EXTERNAL_ID3 );
		assertEquals( cancelBetRequest3.getGameCode(), GAME_CODE );
		assertEquals( cancelBetRequest3.getAmount(), AMOUNT3 );
		assertEquals( cancelBetRequest3.getCurrency(), CURRENCY );
		assertEquals( cancelBetRequest3.getGameRoundId(), GAME_ROUND_ID );
		assertEquals( cancelBetRequest3.getTransactionId(), TRANSACTION_ID );
	}

	@Test
	public void testHashCodeEqualWhenSame() {
		assertEquals( cancelBetRequest1.hashCode(), cancelBetRequest2.hashCode() );
		assertEquals( cancelBetRequest1, cancelBetRequest2 );
	}

	@Test
	public void testToStringEqualWhenSame() {
		assertEquals( cancelBetRequest1.toString(), cancelBetRequest2.toString() );
		assertEquals( cancelBetRequest1, cancelBetRequest2 );
	}

	@Test
	public void testHashCodeNotEqualWhenDifferent() {
		assertNotEquals( cancelBetRequest1.hashCode(), cancelBetRequest3.hashCode() );
		assertNotEquals( cancelBetRequest1, cancelBetRequest3 );
	}

	@Test
	public void testToStringNotEqualWhenDifferent() {
		assertNotEquals( cancelBetRequest1.toString(), cancelBetRequest3.toString() );
		assertNotEquals( cancelBetRequest1, cancelBetRequest3 );
	}

	@Test
	public void testObjectEquality() {
		assertTrue( cancelBetRequest1.equals( cancelBetRequest1 ) );
		assertFalse( cancelBetRequest1.equals( null ) );
		assertFalse( cancelBetRequest1.equals( new CancelBetResponse() ) );
	}
}
