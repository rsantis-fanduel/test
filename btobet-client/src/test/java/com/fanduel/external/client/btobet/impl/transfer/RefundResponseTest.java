package com.fanduel.external.client.btobet.impl.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fanduel.external.client.btobet.transfer.BtoBetRequestGeneral;
import com.fanduel.external.client.btobet.transfer.RefundResponse;

class RefundResponseTest {
	private final int STATUS_CODE = 1;
	private final String STATUS_MESSAGE = "";
	private final Long EXTERNAL_TRANSACTION_ID1 = 123456L;
	private final Long EXTERNAL_TRANSACTION_ID2 = 123456L;
	private final Long EXTERNAL_TRANSACTION_ID3 = 789123L;
	private final BigDecimal BALANCE = BigDecimal.valueOf( 200.00 );
	private final BigDecimal BALANCE2 = BigDecimal.valueOf( 100.00 );

	private RefundResponse response1;
	private RefundResponse response2;
	private RefundResponse response3;

	@BeforeEach
	void before() {
		response1 = (RefundResponse) RefundResponse.builder()
				.externalTransactionId( EXTERNAL_TRANSACTION_ID1 )
				.balance( BALANCE )
				.statusCode( STATUS_CODE )
				.statusMessage( STATUS_MESSAGE )
				.build();

		response2 = (RefundResponse) RefundResponse.builder()
				.externalTransactionId( EXTERNAL_TRANSACTION_ID2 )
				.balance( BALANCE )
				.statusCode( STATUS_CODE )
				.statusMessage( STATUS_MESSAGE )
				.build();

		response3 = (RefundResponse) RefundResponse.builder()
				.externalTransactionId( EXTERNAL_TRANSACTION_ID3 )
				.balance( BALANCE )
				.statusCode( STATUS_CODE )
				.statusMessage( STATUS_MESSAGE )
				.build();
	}

	@Test
	void testBuild() {
		assertEquals( STATUS_CODE, response1.getStatusCode() );
		assertEquals( STATUS_MESSAGE, response1.getStatusMessage() );
		assertEquals( EXTERNAL_TRANSACTION_ID1, response1.getExternalTransactionId() );
		assertEquals( BALANCE, response1.getBalance() );

		assertEquals( STATUS_CODE, response2.getStatusCode() );
		assertEquals( STATUS_MESSAGE, response2.getStatusMessage() );
		assertEquals( EXTERNAL_TRANSACTION_ID2, response2.getExternalTransactionId() );
		assertEquals( BALANCE, response2.getBalance() );

		assertEquals( STATUS_CODE, response3.getStatusCode() );
		assertEquals( STATUS_MESSAGE, response3.getStatusMessage() );
		assertEquals( EXTERNAL_TRANSACTION_ID3, response3.getExternalTransactionId() );
		assertEquals( BALANCE, response3.getBalance() );
	}

	@Test
	void testHashCodeEqualWhenSame() {
		response2.setExternalTransactionId( EXTERNAL_TRANSACTION_ID1 );
		assertEquals( response1.hashCode(), response2.hashCode() );
		assertEquals( response1, response2 );
	}

	@Test
	void testToStringEqualWhenSame() {
		response2.setExternalTransactionId( EXTERNAL_TRANSACTION_ID1 );
		assertEquals( response1.toString(), response2.toString() );
		assertEquals( response1, response2 );
	}

	@Test
	void testHashCodeNotEqualWhenDifferent() {
		response3.setBalance( BALANCE2 );
		assertNotEquals( response1.hashCode(), response3.hashCode() );
		assertNotEquals( response1, response3 );
	}

	@Test
	void testToStringNotEqualWhenDifferent() {
		assertNotEquals( response1.toString(), response3.toString() );
		assertNotEquals( response1, response3 );
	}

	@Test
	void testObjectEquality() {
		assertEquals( response1, response1 );
		assertNotEquals( null, response1 );
		assertNotEquals( response1, new RefundResponse() );
		assertNotEquals( response1, new BtoBetRequestGeneral() );
	}
}
