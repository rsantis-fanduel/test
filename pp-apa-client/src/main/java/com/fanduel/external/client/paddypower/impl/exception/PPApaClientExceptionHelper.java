package com.fanduel.external.client.paddypower.impl.exception;


import com.fanduel.external.client.paddypower.exception.CurrencyMismatchExceptionApa;
import com.fanduel.external.client.paddypower.exception.DuplicateTransferExceptionApa;
import com.fanduel.external.client.paddypower.exception.InvalidTokenExceptionApa;
import com.fanduel.external.client.paddypower.exception.OverBalanceExceptionApa;
import com.fanduel.external.client.paddypower.exception.PPApaException;
import com.fanduel.external.client.paddypower.exception.UserSuspendedExceptionApa;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.PartnerAPIExceptionFault;

public class PPApaClientExceptionHelper {

	private static final String DEFAULT_MESSAGE = "PPApaException";
	private static final String OVER_BALANCE_MESSAGE = "Insufficient Funds";

	public static PPApaException mapException( PartnerAPIExceptionFault exception ) {

		String errorCode = exception.getFaultInfo().getErrorCode();

		switch ( errorCode ) {
		case "INVALID_TOKEN":
			return new InvalidTokenExceptionApa( DEFAULT_MESSAGE,
					exception.getFaultInfo().getErrorCode() );
		case "OVER_BALANCE":
			return new OverBalanceExceptionApa( OVER_BALANCE_MESSAGE,
					exception.getFaultInfo().getErrorCode() );
		case "DUPLICATE_TRANSFER":
			return new DuplicateTransferExceptionApa( DEFAULT_MESSAGE,
					exception.getFaultInfo().getErrorCode() );
		case "CURRENCY_MISMATCH":
			return new CurrencyMismatchExceptionApa( DEFAULT_MESSAGE,
					exception.getFaultInfo().getErrorCode() );
		case "KYC_SUSPENDED":
		case "ACCOUNT_INACTIVE":
		case "PENDING_AUTH":
		case "ACCOUNT_LOCKED":
			return new UserSuspendedExceptionApa( DEFAULT_MESSAGE,
					exception.getFaultInfo().getErrorCode() );
		default:
			return new PPApaException( DEFAULT_MESSAGE, exception.getFaultInfo().getErrorCode() );
		}
	}
}
