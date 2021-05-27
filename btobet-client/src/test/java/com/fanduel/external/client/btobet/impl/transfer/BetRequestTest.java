package com.fanduel.external.client.btobet.impl.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fanduel.external.client.btobet.transfer.BetRequest;

public class BetRequestTest {
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

	private BetRequest betRequest1;
	private BetRequest betRequest2;
	private BetRequest betRequest3;

	@BeforeEach
	void before() {
		betRequest1 = (BetRequest) BetRequest.builder()
				.gameSubRoundId( GAME_SUB_ROUND_ID )
				.amount( AMOUNT1 )
				.currency( CURRENCY )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.originalProvider( ORIGINAL_PROVIDER )
				.authToken( AUTH_TOKEN )
				.externalId( EXTERNAL_ID1 )
				.gameCode( GAME_CODE )
				.apiUsername( API_USERNAME )
				.apiPassword( API_PASSWORD )
				.clientId( CLIENT_ID )
				.build();

		betRequest2 = (BetRequest) BetRequest.builder()
				.gameSubRoundId( GAME_SUB_ROUND_ID )
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

		betRequest3 = (BetRequest) BetRequest.builder()
				.gameSubRoundId( GAME_SUB_ROUND_ID )
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
		assertEquals( betRequest1.getApiUsername(), API_USERNAME );
		assertEquals( betRequest1.getApiPassword(), API_PASSWORD );
		assertEquals( betRequest1.getAuthToken(), AUTH_TOKEN );
		assertEquals( betRequest1.getClientId(), CLIENT_ID );
		assertEquals( betRequest1.getOriginalProvider(), ORIGINAL_PROVIDER );
		assertEquals( betRequest1.getExternalId(), EXTERNAL_ID1 );
		assertEquals( betRequest1.getGameCode(), GAME_CODE );
		assertEquals( betRequest1.getAmount(), AMOUNT1 );
		assertEquals( betRequest1.getCurrency(), CURRENCY );
		assertEquals( betRequest1.getGameRoundId(), GAME_ROUND_ID );
		assertEquals( betRequest1.getGameSubRoundId(), GAME_SUB_ROUND_ID );
		assertEquals( betRequest1.getTransactionId(), TRANSACTION_ID );

		assertEquals( betRequest2.getExternalId(), EXTERNAL_ID2 );
		assertEquals( betRequest2.getAmount(), AMOUNT2 );

		assertEquals( betRequest3.getExternalId(), EXTERNAL_ID3 );
		assertEquals( betRequest3.getAmount(), AMOUNT3 );
	}

	@Test
	public void testHashCodeEqualWhenSame() {
		assertEquals( betRequest1.hashCode(), betRequest2.hashCode() );
		assertEquals( betRequest1, betRequest2 );
	}

	@Test
	public void testToStringEqualWhenSame() {
		assertEquals( betRequest1.toString(), betRequest2.toString() );
		assertEquals( betRequest1, betRequest2 );
	}

	@Test
	public void testHashCodeNotEqualWhenDifferent() {
		betRequest1.setGameSubRoundId( null );
		assertNotEquals( betRequest1.hashCode(), betRequest3.hashCode() );
		assertNotEquals( betRequest1, betRequest3 );
	}

	@Test
	public void testToStringNotEqualWhenDifferent() {
		assertNotEquals( betRequest1.toString(), betRequest3.toString() );
		assertNotEquals( betRequest1, betRequest3 );
	}

	@Test
	public void testObjectEquality() {
		assertEquals( betRequest1, betRequest1 );
		assertNotEquals( null, betRequest1 );
		assertNotEquals( betRequest1, new BetRequest() );
	}
}
