package com.fanduel.external.client.btobet.impl.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fanduel.external.client.btobet.transfer.BetRequest;
import com.fanduel.external.client.btobet.transfer.BetResponse;
import com.fanduel.external.client.btobet.transfer.BtoBetResponse;

public class BetResponseTest {
	private final int STATUS_CODE = 1;
	private final String STATUS_MESSAGE = "";
	private final Long EXTERNAL_TRANSACTION_ID1 = 123456L;
	private final Long EXTERNAL_TRANSACTION_ID2 = 123456L;
	private final Long EXTERNAL_TRANSACTION_ID3 = 789123L;
	private final BigDecimal BALANCE1 = BigDecimal.valueOf( 0 );
	private final BigDecimal BALANCE2 = BigDecimal.valueOf( 0 );
	private final BigDecimal BALANCE3 = BigDecimal.valueOf( 100 );

	private BetResponse betResponse1;
	private BetResponse betResponse2;
	private BetResponse betResponse3;

	private BtoBetResponse.Builder builder1;
	private BtoBetResponse.Builder builder2;
	private BtoBetResponse.Builder builder3;

	@BeforeEach
	void before() {
		builder1 = new BetResponse.Builder()
				.externalTransactionId( EXTERNAL_TRANSACTION_ID1 )
				.balance( BALANCE1 )
				.statusCode( STATUS_CODE )
				.statusMessage( STATUS_MESSAGE );

		betResponse1 = ( ( BetResponse.Builder ) builder1 ).build();

		builder2 = new BetResponse.Builder()
				.externalTransactionId( EXTERNAL_TRANSACTION_ID2 )
				.balance( BALANCE2 )
				.statusCode( STATUS_CODE )
				.statusMessage( STATUS_MESSAGE );

		betResponse2 = ( ( BetResponse.Builder ) builder2 ).build();


		builder3 = new BetResponse.Builder()
				.externalTransactionId( EXTERNAL_TRANSACTION_ID3 )
				.balance( BALANCE3 )
				.statusCode( STATUS_CODE )
				.statusMessage( STATUS_MESSAGE );

		betResponse3 = ( ( BetResponse.Builder ) builder3 ).build();

	}

	@Test
	public void testBuild() {
		assertEquals( betResponse1.getStatusCode(), STATUS_CODE );
		assertEquals( betResponse1.getStatusMessage(), STATUS_MESSAGE );
		assertEquals( betResponse1.getExternalTransactionId(), EXTERNAL_TRANSACTION_ID1 );
		assertEquals( betResponse1.getBalance(), BALANCE1 );

		assertEquals( betResponse2.getStatusCode(), STATUS_CODE );
		assertEquals( betResponse2.getStatusMessage(), STATUS_MESSAGE );
		assertEquals( betResponse2.getExternalTransactionId(), EXTERNAL_TRANSACTION_ID2 );
		assertEquals( betResponse2.getBalance(), BALANCE2 );

		assertEquals( betResponse3.getStatusCode(), STATUS_CODE );
		assertEquals( betResponse3.getStatusMessage(), STATUS_MESSAGE );
		assertEquals( betResponse3.getExternalTransactionId(), EXTERNAL_TRANSACTION_ID3 );
		assertEquals( betResponse3.getBalance(), BALANCE3 );
	}

	@Test
	public void testHashCodeEqualWhenSame() {
		assertEquals( betResponse1.hashCode(), betResponse2.hashCode() );
		assertEquals( betResponse1, betResponse2 );
	}

	@Test
	public void testToStringEqualWhenSame() {
		assertEquals( betResponse1.toString(), betResponse2.toString() );
		assertEquals( betResponse1, betResponse2 );
	}

	@Test
	public void testHashCodeNotEqualWhenDifferent() {
		assertNotEquals( betResponse1.hashCode(), betResponse3.hashCode() );
		assertNotEquals( betResponse1, betResponse3 );
	}

	@Test
	public void testToStringNotEqualWhenDifferent() {
		assertNotEquals( betResponse1.toString(), betResponse3.toString() );
		assertNotEquals( betResponse1, betResponse3 );
	}

	@Test
	void testObjectEquality() {
		betResponse2.setStatusCode( 4 );
		assertTrue( betResponse1.equals( betResponse1 ) );
		assertFalse( betResponse1.equals( null ) );
		assertFalse( betResponse1.equals( betResponse2 ) );
		assertFalse( betResponse1.equals( new BetRequest() ) );
	}
}
