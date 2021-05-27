package com.fanduel.external.client.paddypower;

import java.math.BigDecimal;

import com.fanduel.external.client.paddypower.exception.DuplicateTransferExceptionApa;
import com.fanduel.external.client.paddypower.exception.InvalidTokenExceptionApa;
import com.fanduel.external.client.paddypower.exception.OverBalanceExceptionApa;
import com.fanduel.external.client.paddypower.exception.UserSuspendedExceptionApa;
import com.fanduel.external.client.paddypower.exception.PPApaException;
import com.fanduel.external.client.paddypower.transfer.BalanceInfo;
import com.fanduel.external.client.paddypower.transfer.TransferRequest;
import com.fanduel.external.client.paddypower.transfer.TransferResponse;
import com.fanduel.external.client.paddypower.transfer.UserSessionDetails;
import com.fanduel.external.client.paddypower.transfer.UserSessionDetailsRequest;

public interface PPApaClient {
	/**
	 * Validates whether a user is allowed to act(play, bet) and retrieves their profile within
	 * context of their current session.
	 *
	 * @param request
	 * @return user profile details
	 * @throws InvalidTokenExceptionApa                              if token is invalid
	 * @throws UserSuspendedExceptionApa                             if the account is suspended
	 *                                                               awaiting KYC checking
	 * @throws PPApaException                                        if PP APA returns any other
	 *                                                               error code
	 * @throws com.fanduel.api.exception.APIIllegalArgumentException if request is invalid
	 */
	UserSessionDetails retrieveUserSessionDetails( UserSessionDetailsRequest request );

	/**
	 * Keeps alive the Betfair’s user session. The user session duration is
	 * around 20 minutes long, so use this operation to keep the session active,
	 * in case that no other token-based operation has been call within 20
	 * minutes. Every time application calls the operation, it is returned a
	 * sessionToken and the session timeout is reset to 20 minutes. Call
	 * keepAlive to obtain a new session token and reset the session timeout.
	 *
	 * @param token
	 * @throws InvalidTokenExceptionApa
	 *             if token is invalid
	 * @throws PPApaException
	 *             if PP APA returns any other error code
	 * @throws com.fanduel.api.exception.APIIllegalArgumentException
	 *             if token is null or empty
	 * @return the refreshed token (same or a new one)
	 */
	String keepAlive( String token );

	/**
	 * Transfers funds to/from the BF
	 *
	 * @param request
	 * @return the transfer results
	 * @throws InvalidTokenExceptionApa                              if token is invalid
	 * @throws OverBalanceExceptionApa                               if the transfer tries to go
	 *                                                               over the current user balance
	 * @throws DuplicateTransferExceptionApa                            if the transfer reference is a
	 *                                                               duplicate
	 * @throws PPApaException                                        if PP APA returns any other
	 *                                                               error code
	 * @throws com.fanduel.api.exception.APIIllegalArgumentException if the request is invalid
	 */
	TransferResponse transfer( TransferRequest request );

	/**
	 * Cancels the funds transfer depicted by the details provided as input
	 * data. The input data values must be exactly the same as the ones sent on
	 * the transfer that is wanted to be cancelled.
	 *
	 * @param request
	 * @throws DuplicateTransferExceptionApa
	 *             if the transfer reference is a duplicate
	 * @throws PPApaException
	 *             if PP APA returns any other error code
	 * @throws com.fanduel.api.exception.APIIllegalArgumentException
	 *             if the request is invalid
	 */
	void cancelTransfer( TransferRequest request );

	/**
	 * Retrieves the Betfair user’s available-to-bet balance.
	 *
	 * @param accountId
	 * @throws PPApaException
	 *             if PP APA returns any error
	 * @throws com.fanduel.api.exception.APIIllegalArgumentException
	 *             if the request is invalid
	 * @return the current balance
	 */
	BigDecimal retrieveBalance( long accountId );

	/**
	 * Retrieves the different Betfair user's balances: main, bonus, total
	 * @param accountId
	 * @throws PPApaException
	 *             if PP APA returns any error
	 * @throws com.fanduel.api.exception.APIIllegalArgumentException
	 *             if the request is invalid
	 * @return the current balance
	 */
	BalanceInfo retrieveBalanceInfo( long accountId );
}
