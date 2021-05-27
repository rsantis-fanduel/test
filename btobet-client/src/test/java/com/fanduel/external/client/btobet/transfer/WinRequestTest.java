package com.fanduel.external.client.btobet.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WinRequestTest {
	private final String API_USERNAME = "fanduel";
	private final String API_PASSWORD = "fanduel123!";
	private final String AUTH_TOKEN = "authtoken";
	private final int CLIENT_ID = 301;
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
	private final String BONUS_PROMOTION_CODE = null;
	private final Long EXTERNAL_TRANSACTION_ID1 = 123456L;
	private final Long EXTERNAL_TRANSACTION_ID2 = 123456L;
	private final Long EXTERNAL_TRANSACTION_ID3 = 789123L;

	private final WinRequest.Builder builder = new WinRequest.Builder();

	private WinRequest winRequest1;
	private WinRequest winRequest2;
	private WinRequest winRequest3;

	@BeforeEach
	void before() {
		winRequest1 = (WinRequest) builder.gameSubRoundId( GAME_SUB_ROUND_ID )
				.bonusPromotionCode( BONUS_PROMOTION_CODE )
				.externalTransactionId( EXTERNAL_TRANSACTION_ID1 )
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

		winRequest2 = (WinRequest) builder.gameSubRoundId( GAME_SUB_ROUND_ID )
				.bonusPromotionCode( BONUS_PROMOTION_CODE )
				.externalTransactionId( EXTERNAL_TRANSACTION_ID2 )
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

		winRequest3 = (WinRequest) builder.gameSubRoundId( GAME_SUB_ROUND_ID )
				.bonusPromotionCode( BONUS_PROMOTION_CODE )
				.externalTransactionId( EXTERNAL_TRANSACTION_ID3 )
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

	@AfterEach
	void after() {

	}

	@Test
	public void testBuild() {
		assertEquals( winRequest1.getApiUsername(), API_USERNAME );
		assertEquals( winRequest1.getApiPassword(), API_PASSWORD );
		assertEquals( winRequest1.getAuthToken(), AUTH_TOKEN );
		assertEquals( winRequest1.getClientId(), CLIENT_ID );
		assertEquals( winRequest1.getOriginalProvider(), ORIGINAL_PROVIDER );
		assertEquals( winRequest1.getExternalId(), EXTERNAL_ID1 );
		assertEquals( winRequest1.getGameCode(), GAME_CODE );
		assertEquals( winRequest1.getAmount(), AMOUNT1 );
		assertEquals( winRequest1.getCurrency(), CURRENCY );
		assertEquals( winRequest1.getGameRoundId(), GAME_ROUND_ID );
		assertEquals( winRequest1.getGameSubRoundId(), GAME_SUB_ROUND_ID );
		assertEquals( winRequest1.getTransactionId(), TRANSACTION_ID );
		assertEquals( winRequest1.getBonusPromotionCode(), BONUS_PROMOTION_CODE );
		assertEquals( winRequest1.getExternalTransactionId(), EXTERNAL_TRANSACTION_ID1 );

		assertEquals( winRequest2.getApiUsername(), API_USERNAME );
		assertEquals( winRequest2.getApiPassword(), API_PASSWORD );
		assertEquals( winRequest2.getAuthToken(), AUTH_TOKEN );
		assertEquals( winRequest2.getClientId(), CLIENT_ID );
		assertEquals( winRequest2.getOriginalProvider(), ORIGINAL_PROVIDER );
		assertEquals( winRequest2.getExternalId(), EXTERNAL_ID2 );
		assertEquals( winRequest2.getGameCode(), GAME_CODE );
		assertEquals( winRequest2.getAmount(), AMOUNT2 );
		assertEquals( winRequest2.getCurrency(), CURRENCY );
		assertEquals( winRequest2.getGameRoundId(), GAME_ROUND_ID );
		assertEquals( winRequest2.getGameSubRoundId(), GAME_SUB_ROUND_ID );
		assertEquals( winRequest2.getTransactionId(), TRANSACTION_ID );
		assertEquals( winRequest2.getBonusPromotionCode(), BONUS_PROMOTION_CODE );
		assertEquals( winRequest2.getExternalTransactionId(), EXTERNAL_TRANSACTION_ID2 );

		assertEquals( winRequest3.getApiUsername(), API_USERNAME );
		assertEquals( winRequest3.getApiPassword(), API_PASSWORD );
		assertEquals( winRequest3.getAuthToken(), AUTH_TOKEN );
		assertEquals( winRequest3.getClientId(), CLIENT_ID );
		assertEquals( winRequest3.getOriginalProvider(), ORIGINAL_PROVIDER );
		assertEquals( winRequest3.getExternalId(), EXTERNAL_ID3 );
		assertEquals( winRequest3.getGameCode(), GAME_CODE );
		assertEquals( winRequest3.getAmount(), AMOUNT3 );
		assertEquals( winRequest3.getCurrency(), CURRENCY );
		assertEquals( winRequest3.getGameRoundId(), GAME_ROUND_ID );
		assertEquals( winRequest3.getGameSubRoundId(), GAME_SUB_ROUND_ID );
		assertEquals( winRequest3.getTransactionId(), TRANSACTION_ID );
		assertEquals( winRequest3.getBonusPromotionCode(), BONUS_PROMOTION_CODE );
		assertEquals( winRequest3.getExternalTransactionId(), EXTERNAL_TRANSACTION_ID3 );
	}

	@Test
	public void testHashCodeEqualWhenSame() {
		assertEquals( winRequest1.hashCode(), winRequest2.hashCode() );
		assertEquals( winRequest1, winRequest2 );
	}

	@Test
	public void testToStringEqualWhenSame() {
		assertEquals( winRequest1.toString(), winRequest2.toString() );
		assertEquals( winRequest1, winRequest2 );
	}

	@Test
	public void testHashCodeNotEqualWhenDifferent() {
		assertNotEquals( winRequest1.hashCode(), winRequest3.hashCode() );
		assertNotEquals( winRequest1, winRequest3 );
	}

	@Test
	public void testToStringNotEqualWhenDifferent() {
		assertNotEquals( winRequest1.toString(), winRequest3.toString() );
		assertNotEquals( winRequest1, winRequest3 );
	}

	@Test
	public void testEquals() {
		assertTrue( winRequest1.equals( winRequest1 ) );
		assertFalse( winRequest1.equals( null ) );
		assertTrue( winRequest1.equals( winRequest2 ) );
		assertFalse( winRequest1.equals( winRequest3 ) );
	}
}
