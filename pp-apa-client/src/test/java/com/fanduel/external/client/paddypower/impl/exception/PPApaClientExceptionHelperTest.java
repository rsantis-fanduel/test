package com.fanduel.external.client.paddypower.impl.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.fanduel.external.client.paddypower.exception.CurrencyMismatchExceptionApa;
import com.fanduel.external.client.paddypower.exception.DuplicateTransferExceptionApa;
import com.fanduel.external.client.paddypower.exception.InvalidTokenExceptionApa;
import com.fanduel.external.client.paddypower.exception.OverBalanceExceptionApa;
import com.fanduel.external.client.paddypower.exception.PPApaException;
import com.fanduel.external.client.paddypower.exception.UserSuspendedExceptionApa;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.PartnerAPIExceptionFault;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.PartnerAPIExceptionType;

public class PPApaClientExceptionHelperTest {

	@Test
	public void invalidToken() {
		PartnerAPIExceptionType exception = new PartnerAPIExceptionType();
		exception.setErrorCode( "INVALID_TOKEN" );
		PartnerAPIExceptionFault fault = new PartnerAPIExceptionFault( "Message", exception );

		PPApaException ppApaException = PPApaClientExceptionHelper.mapException( fault );
		assertTrue( ppApaException instanceof InvalidTokenExceptionApa );
		assertErrorCode( "INVALID_TOKEN", ppApaException.getErrorCode() );
	}

	@Test
	public void overBalance() {
		PartnerAPIExceptionType exception = new PartnerAPIExceptionType();
		exception.setErrorCode( "OVER_BALANCE" );
		PartnerAPIExceptionFault fault = new PartnerAPIExceptionFault( "Message", exception );

		PPApaException ppApaException = PPApaClientExceptionHelper.mapException( fault );
		assertTrue( ppApaException instanceof OverBalanceExceptionApa );
		assertErrorCode( "OVER_BALANCE", ppApaException.getErrorCode() );
	}

	@Test
	public void duplicateTransfer() {
		PartnerAPIExceptionType exception = new PartnerAPIExceptionType();
		exception.setErrorCode( "DUPLICATE_TRANSFER" );
		PartnerAPIExceptionFault fault = new PartnerAPIExceptionFault( "Message", exception );

		PPApaException ppApaException = PPApaClientExceptionHelper.mapException( fault );
		assertTrue( ppApaException instanceof DuplicateTransferExceptionApa );
		assertErrorCode( "DUPLICATE_TRANSFER", ppApaException.getErrorCode() );
	}

	@Test
	public void currencyMismatch() {
		PartnerAPIExceptionType exception = new PartnerAPIExceptionType();
		exception.setErrorCode( "CURRENCY_MISMATCH" );
		PartnerAPIExceptionFault fault = new PartnerAPIExceptionFault( "Message", exception );

		PPApaException ppApaException = PPApaClientExceptionHelper.mapException( fault );
		assertTrue( ppApaException instanceof CurrencyMismatchExceptionApa );
		assertErrorCode( "CURRENCY_MISMATCH", ppApaException.getErrorCode() );
	}

	@Test
	public void userSuspended() {
		PartnerAPIExceptionType exception = new PartnerAPIExceptionType();
		exception.setErrorCode( "KYC_SUSPENDED" );
		PartnerAPIExceptionFault fault = new PartnerAPIExceptionFault( "Message", exception );

		PPApaException ppApaException = PPApaClientExceptionHelper.mapException( fault );
		assertTrue( ppApaException instanceof UserSuspendedExceptionApa );
		assertErrorCode( "KYC_SUSPENDED", ppApaException.getErrorCode() );
	}

	private void assertErrorCode(String expected, String actual) {
		assertEquals( expected, actual );
	}
}
