package com.fanduel.external.client.paddypower.impl.exception;

import com.fanduel.external.client.paddypower.exception.CurrencyMismatchException;
import com.fanduel.external.client.paddypower.exception.DuplicateTransferException;
import com.fanduel.external.client.paddypower.exception.InvalidTokenException;
import com.fanduel.external.client.paddypower.exception.OverBalanceException;
import com.fanduel.external.client.paddypower.exception.PPGapException;
import com.fanduel.external.client.paddypower.exception.UserSuspendedException;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.PartnerAPIExceptionFault;

public class PPGapClientExceptionHelper {

	public static PPGapException mapException( PartnerAPIExceptionFault exception ) {

		String errorCode = exception.getFaultInfo().getErrorCode();

		switch ( errorCode ) {
		case "INVALID_TOKEN":
			return new InvalidTokenException( exception.getFaultInfo().getMessage(),
					exception.getFaultInfo().getErrorCode() );
		case "OVER_BALANCE":
			return new OverBalanceException( exception.getFaultInfo().getMessage(),
					exception.getFaultInfo().getErrorCode() );
		case "DUPLICATE_TRANSFER":
			return new DuplicateTransferException( exception.getFaultInfo().getMessage(),
					exception.getFaultInfo().getErrorCode() );
		case "CURRENCY_MISMATCH":
			return new CurrencyMismatchException( exception.getFaultInfo().getMessage(),
					exception.getFaultInfo().getErrorCode() );
		case "KYC_SUSPENDED":
		case "ACCOUNT_INACTIVE":
		case "PENDING_AUTH":
		case "ACCOUNT_LOCKED":
			return new UserSuspendedException( exception.getFaultInfo().getMessage(),
					exception.getFaultInfo().getErrorCode() );
		default:
			return new PPGapException( exception.getFaultInfo().getMessage(),
					exception.getFaultInfo().getErrorCode() );
		}
	}
}
