package com.fanduel.external.client.btobet.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CancelBetResponseTest {
	private final int STATUS_CODE = 1;
	private final String STATUS_MESSAGE = "";
	private final Long EXTERNAL_TRANSACTION_ID1 = 123456L;
	private final Long EXTERNAL_TRANSACTION_ID2 = 123456L;
	private final Long EXTERNAL_TRANSACTION_ID3 = 789123L;
	private final BigDecimal BALANCE1 = BigDecimal.valueOf( 0 );
	private final BigDecimal BALANCE2 = BigDecimal.valueOf( 0 );
	private final BigDecimal BALANCE3 = BigDecimal.valueOf( 100 );

	private CancelBetResponse cancelBetResponse1;
	private CancelBetResponse cancelBetResponse2;
	private CancelBetResponse cancelBetResponse3;

	private BtoBetResponse.Builder builder1;
	private BtoBetResponse.Builder builder2;
	private BtoBetResponse.Builder builder3;

	@BeforeEach
	void before() {
		builder1 = new CancelBetResponse.Builder().externalTransactionId( EXTERNAL_TRANSACTION_ID1 )
				.balance( BALANCE1 )
				.statusCode( STATUS_CODE )
				.statusMessage( STATUS_MESSAGE );

		cancelBetResponse1 = ( (CancelBetResponse.Builder) builder1 ).build();

		builder2 = new CancelBetResponse.Builder().externalTransactionId( EXTERNAL_TRANSACTION_ID2 )
				.balance( BALANCE2 )
				.statusCode( STATUS_CODE )
				.statusMessage( STATUS_MESSAGE );

		cancelBetResponse2 = ( (CancelBetResponse.Builder) builder2 ).build();

		builder3 = new CancelBetResponse.Builder().externalTransactionId( EXTERNAL_TRANSACTION_ID3 )
				.balance( BALANCE3 )
				.statusCode( STATUS_CODE )
				.statusMessage( STATUS_MESSAGE );

		cancelBetResponse3 = ( (CancelBetResponse.Builder) builder3 ).build();

	}

	@Test
	public void testBuild() {
		assertEquals( cancelBetResponse1.getStatusCode(), STATUS_CODE );
		assertEquals( cancelBetResponse1.getStatusMessage(), STATUS_MESSAGE );
		assertEquals( cancelBetResponse1.getExternalTransactionId(), EXTERNAL_TRANSACTION_ID1 );
		assertEquals( cancelBetResponse1.getBalance(), BALANCE1 );

		assertEquals( cancelBetResponse2.getStatusCode(), STATUS_CODE );
		assertEquals( cancelBetResponse2.getStatusMessage(), STATUS_MESSAGE );
		assertEquals( cancelBetResponse2.getExternalTransactionId(), EXTERNAL_TRANSACTION_ID2 );
		assertEquals( cancelBetResponse2.getBalance(), BALANCE2 );

		assertEquals( cancelBetResponse3.getStatusCode(), STATUS_CODE );
		assertEquals( cancelBetResponse3.getStatusMessage(), STATUS_MESSAGE );
		assertEquals( cancelBetResponse3.getExternalTransactionId(), EXTERNAL_TRANSACTION_ID3 );
		assertEquals( cancelBetResponse3.getBalance(), BALANCE3 );
	}

	@Test
	public void testHashCodeEqualWhenSame() {
		assertEquals( cancelBetResponse1.hashCode(), cancelBetResponse2.hashCode() );
		assertEquals( cancelBetResponse1, cancelBetResponse2 );
	}

	@Test
	public void testToStringEqualWhenSame() {
		assertEquals( cancelBetResponse1.toString(), cancelBetResponse2.toString() );
		assertEquals( cancelBetResponse1, cancelBetResponse2 );
	}

	@Test
	public void testHashCodeNotEqualWhenDifferent() {
		assertNotEquals( cancelBetResponse1.hashCode(), cancelBetResponse3.hashCode() );
		assertNotEquals( cancelBetResponse1, cancelBetResponse3 );
	}

	@Test
	public void testToStringNotEqualWhenDifferent() {
		assertNotEquals( cancelBetResponse1.toString(), cancelBetResponse3.toString() );
		assertNotEquals( cancelBetResponse1, cancelBetResponse3 );
	}

	@Test
	void testObjectEquality() {
		cancelBetResponse2.setStatusCode( 4 );
		assertTrue( cancelBetResponse1.equals( cancelBetResponse1 ) );
		assertFalse( cancelBetResponse1.equals( null ) );
		assertFalse( cancelBetResponse1.equals( cancelBetResponse2 ) );
		assertFalse( cancelBetResponse1.equals( new CancelBetRequest() ) );
	}
}
