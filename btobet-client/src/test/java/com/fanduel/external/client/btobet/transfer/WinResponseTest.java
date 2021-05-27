package com.fanduel.external.client.btobet.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WinResponseTest {
	private final int STATUS_CODE = 1;
	private final String STATUS_MESSAGE = "";
	private final Long EXTERNAL_TRANSACTION_ID_1 = 123456L;
	private final Long EXTERNAL_TRANSACTION_ID_2 = 123456L;
	private final Long EXTERNAL_TRANSACTION_ID_3 = 789123L;
	private final BigDecimal BALANCE_1 = BigDecimal.valueOf( 0 );
	private final BigDecimal BALANCE_2 = BigDecimal.valueOf( 0 );
	private final BigDecimal BALANCE_3 = BigDecimal.valueOf( 100 );

	private BtoBetResponse.Builder builder1;
	private BtoBetResponse.Builder builder2;
	private BtoBetResponse.Builder builder3;

	private WinResponse winResponse1;
	private WinResponse winResponse2;
	private WinResponse winResponse3;

	@BeforeEach
	void before() {
		builder1 = new WinResponse.Builder().externalTransactionId( EXTERNAL_TRANSACTION_ID_1 )
				.balance( BALANCE_1 )
				.statusCode( STATUS_CODE )
				.statusMessage( STATUS_MESSAGE );

		winResponse1 = ( (WinResponse.Builder) builder1 ).build();

		builder2 = new WinResponse.Builder().externalTransactionId( EXTERNAL_TRANSACTION_ID_2 )
				.balance( BALANCE_2 )
				.statusCode( STATUS_CODE )
				.statusMessage( STATUS_MESSAGE );

		winResponse2 = ( (WinResponse.Builder) builder2 ).build();

		builder3 = new WinResponse.Builder().externalTransactionId( EXTERNAL_TRANSACTION_ID_3 )
				.balance( BALANCE_3 )
				.statusCode( STATUS_CODE )
				.statusMessage( STATUS_MESSAGE );

		winResponse3 = ( (WinResponse.Builder) builder3 ).build();
	}

	@AfterEach
	void after() { }

	@Test
	public void testBuild() {
		assertEquals( winResponse1.getStatusCode(), STATUS_CODE );
		assertEquals( winResponse1.getStatusMessage(), STATUS_MESSAGE );
		assertEquals( winResponse1.getExternalTransactionId(), EXTERNAL_TRANSACTION_ID_1 );
		assertEquals( winResponse1.getBalance(), BALANCE_1 );

		assertEquals( winResponse2.getStatusCode(), STATUS_CODE );
		assertEquals( winResponse2.getStatusMessage(), STATUS_MESSAGE );
		assertEquals( winResponse2.getExternalTransactionId(), EXTERNAL_TRANSACTION_ID_2 );
		assertEquals( winResponse2.getBalance(), BALANCE_2 );

		assertEquals( winResponse3.getStatusCode(), STATUS_CODE );
		assertEquals( winResponse3.getStatusMessage(), STATUS_MESSAGE );
		assertEquals( winResponse3.getExternalTransactionId(), EXTERNAL_TRANSACTION_ID_3 );
		assertEquals( winResponse3.getBalance(), BALANCE_3 );
	}

	@Test
	public void testHashCodeEqualWhenSame() {
		assertEquals( winResponse1.hashCode(), winResponse2.hashCode() );
		assertEquals( winResponse1, winResponse2 );
	}

	@Test
	public void testToStringEqualWhenSame() {
		assertEquals( winResponse1.toString(), winResponse2.toString() );
		assertEquals( winResponse1, winResponse2 );
	}

	@Test
	public void testHashCodeNotEqualWhenDifferent() {
		assertNotEquals( winResponse1.hashCode(), winResponse3.hashCode() );
		assertNotEquals( winResponse1, winResponse3 );
	}

	@Test
	public void testToStringNotEqualWhenDifferent() {
		assertNotEquals( winResponse1.toString(), winResponse3.toString() );
		assertNotEquals( winResponse1, winResponse3 );
	}

	@Test
	public void testEquals() {
		assertTrue( winResponse1.equals( winResponse1 ) );
		assertFalse( winResponse1.equals( null ) );
		assertTrue( winResponse1.equals( winResponse2 ) );
		assertFalse( winResponse1.equals( winResponse3 ) );
	}
}
