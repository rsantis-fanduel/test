package com.fanduel.external.client.paddypower.impl.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.fanduel.external.client.paddypower.exception.CurrencyMismatchException;
import com.fanduel.external.client.paddypower.exception.DuplicateTransferException;
import com.fanduel.external.client.paddypower.exception.InvalidTokenException;
import com.fanduel.external.client.paddypower.exception.OverBalanceException;
import com.fanduel.external.client.paddypower.exception.PPGapException;
import com.fanduel.external.client.paddypower.exception.UserSuspendedException;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.PartnerAPIExceptionFault;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.PartnerAPIExceptionType;

public class PPGapClientExceptionHelperTest {

	@Test
	public void invalidToken() {
		PartnerAPIExceptionType exception = new PartnerAPIExceptionType();
		exception.setMessage( "Message" );
		exception.setErrorCode( "INVALID_TOKEN" );
		PartnerAPIExceptionFault fault = new PartnerAPIExceptionFault( "Message", exception );

		PPGapException ppGapException = PPGapClientExceptionHelper.mapException( fault );
		assertTrue( ppGapException instanceof InvalidTokenException );
		assertEquals( "Message", ppGapException.getMessage() );
		assertEquals( "INVALID_TOKEN", ppGapException.getErrorCode() );
	}

	@Test
	public void overBalance() {
		PartnerAPIExceptionType exception = new PartnerAPIExceptionType();
		exception.setMessage( "Message" );
		exception.setErrorCode( "OVER_BALANCE" );
		PartnerAPIExceptionFault fault = new PartnerAPIExceptionFault( "Message", exception );

		PPGapException ppGapException = PPGapClientExceptionHelper.mapException( fault );
		assertTrue( ppGapException instanceof OverBalanceException );
		assertEquals( "Message", ppGapException.getMessage() );
		assertEquals( "OVER_BALANCE", ppGapException.getErrorCode() );
	}

	@Test
	public void duplicateTransfer() {
		PartnerAPIExceptionType exception = new PartnerAPIExceptionType();
		exception.setMessage( "Message" );
		exception.setErrorCode( "DUPLICATE_TRANSFER" );
		PartnerAPIExceptionFault fault = new PartnerAPIExceptionFault( "Message", exception );

		PPGapException ppGapException = PPGapClientExceptionHelper.mapException( fault );
		assertTrue( ppGapException instanceof DuplicateTransferException );
		assertEquals( "Message", ppGapException.getMessage() );
		assertEquals( "DUPLICATE_TRANSFER", ppGapException.getErrorCode() );
	}

	@Test
	public void currencyMismatch() {
		PartnerAPIExceptionType exception = new PartnerAPIExceptionType();
		exception.setMessage( "Message" );
		exception.setErrorCode( "CURRENCY_MISMATCH" );
		PartnerAPIExceptionFault fault = new PartnerAPIExceptionFault( "Message", exception );

		PPGapException ppGapException = PPGapClientExceptionHelper.mapException( fault );
		assertTrue( ppGapException instanceof CurrencyMismatchException );
		assertEquals( "Message", ppGapException.getMessage() );
		assertEquals( "CURRENCY_MISMATCH", ppGapException.getErrorCode() );
	}

	@Test
	public void userSuspended() {
		PartnerAPIExceptionType exception = new PartnerAPIExceptionType();
		exception.setMessage( "Message" );
		exception.setErrorCode( "KYC_SUSPENDED" );
		PartnerAPIExceptionFault fault = new PartnerAPIExceptionFault( "Message", exception );

		PPGapException ppGapException = PPGapClientExceptionHelper.mapException( fault );
		assertTrue( ppGapException instanceof UserSuspendedException );
		assertEquals( "Message", ppGapException.getMessage() );
		assertEquals( "KYC_SUSPENDED", ppGapException.getErrorCode() );
	}
}
