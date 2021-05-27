package com.fanduel.external.client.paddypower;

import java.math.BigDecimal;

import com.fanduel.external.client.paddypower.exception.DuplicateTransferException;
import com.fanduel.external.client.paddypower.exception.InvalidTokenException;
import com.fanduel.external.client.paddypower.exception.OverBalanceException;
import com.fanduel.external.client.paddypower.exception.UserSuspendedException;
import com.fanduel.external.client.paddypower.exception.PPGapException;
import com.fanduel.external.client.paddypower.transfer.TransferRequest;
import com.fanduel.external.client.paddypower.transfer.TransferResponse;
import com.fanduel.external.client.paddypower.transfer.UserSessionDetails;
import com.fanduel.external.client.paddypower.transfer.UserSessionDetailsRequest;

public interface PPGapClient {
	/**
	 * Validates whether a user is allowed to act(play, bet) and retrieves their
	 * profile within context of their current session.
	 * 
	 * @param request
	 * @throws InvalidTokenException
	 *             if token is invalid
	 * @throws UserSuspendedException
	 *             if the account is suspended awaiting KYC checking
	 * @throws PPGapException
	 *             if PP GAP returns any other error code
	 * @throws com.fanduel.api.exception.APIIllegalArgumentException
	 *             if request is invalid
	 *
	 * @return user profile details
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
	 * @throws InvalidTokenException
	 *             if token is invalid
	 * @throws PPGapException
	 *             if PP GAP returns any other error code
	 * @throws com.fanduel.api.exception.APIIllegalArgumentException
	 *             if token is null or empty
	 * @return the refreshed token (same or a new one)
	 */
	String keepAlive( String token );

	/**
	 * Transfers funds to/from the BF
	 *
	 * @param request
	 * @throws InvalidTokenException
	 *             if token is invalid
	 * @throws OverBalanceException
	 *             if the transfer tries to go over the current user balance
	 * @throws DuplicateTransferException
	 *             if the transfer reference is a duplicate
	 * @throws PPGapException
	 *             if PP GAP returns any other error code
	 * @throws com.fanduel.api.exception.APIIllegalArgumentException
	 *             if the request is invalid
	 * 
	 * @return the transfer results
	 */
	TransferResponse transfer( TransferRequest request );

	/**
	 * Cancels the funds transfer depicted by the details provided as input
	 * data. The input data values must be exactly the same as the ones sent on
	 * the transfer that is wanted to be cancelled.
	 *
	 * @param request
	 * @throws DuplicateTransferException
	 *             if the transfer reference is a duplicate
	 * @throws PPGapException
	 *             if PP GAP returns any other error code
	 * @throws com.fanduel.api.exception.APIIllegalArgumentException
	 *             if the request is invalid
	 */
	void cancelTransfer( TransferRequest request );

	/**
	 * Retrieves the Betfair user’s available-to-bet balance.
	 *
	 * @param accountId
	 * @throws PPGapException
	 *             if PP GAP returns any error
	 * @throws com.fanduel.api.exception.APIIllegalArgumentException
	 *             if the request is invalid
	 * @return the current balance
	 */
	BigDecimal retrieveBalance( long accountId );
}
