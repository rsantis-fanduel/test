package com.fanduel.external.client.btobet;

import java.io.IOException;

import com.fanduel.external.client.btobet.transfer.AuthenticateRequest;
import com.fanduel.external.client.btobet.transfer.AuthenticateResponse;
import com.fanduel.external.client.btobet.transfer.BetRequest;
import com.fanduel.external.client.btobet.transfer.BetResponse;
import com.fanduel.external.client.btobet.transfer.CancelBetRequest;
import com.fanduel.external.client.btobet.transfer.CancelBetResponse;
import com.fanduel.external.client.btobet.transfer.GenerateBtoBetAuthTokenRequest;
import com.fanduel.external.client.btobet.transfer.GenerateBtoBetAuthTokenResponse;
import com.fanduel.external.client.btobet.transfer.GetBalanceRequest;
import com.fanduel.external.client.btobet.transfer.GetBalanceResponse;
import com.fanduel.external.client.btobet.transfer.RefundRequest;
import com.fanduel.external.client.btobet.transfer.RefundResponse;
import com.fanduel.external.client.btobet.transfer.WinRequest;
import com.fanduel.external.client.btobet.transfer.WinResponse;

public interface BtoBetClient {

	/**
	 * Authenticate user and generate auth token using globo id.
	 *
	 * @param request - Must contain valid globo id.
	 * @return GenerateBtoBetAuthTokenRequest - Response object that contains status, authtoken and
	 * user information.
	 * @throws IOException
	 */
	GenerateBtoBetAuthTokenResponse generateAuthToken( GenerateBtoBetAuthTokenRequest request )
			throws IOException;

	/**
	 * Authenticate user using authtoken. This should be done after the generateAuthToken call to
	 * authenticate user.
	 *
	 * @param request - Must contain valid authtoken.
	 * @return AuthenticateRequest - Response object that contains status and user information.
	 * @throws IOException
	 */
	AuthenticateResponse authenticate( AuthenticateRequest request ) throws IOException;

	/**
	 * Get current player balance available to transfer to specified game provider.
	 *
	 * @param request - Must contain valid authtoken.
	 * @return GetBalanceResponse - Response object that contains status and user balance.
	 * @throws IOException
	 */
	GetBalanceResponse getBalance( GetBalanceRequest request ) throws IOException;

	/**
	 * This call is used for placing a bet/playing a game round on the game. The call checks the
	 * player balance and makes a bet transaction for the specific game of the game provider in the
	 * casino platform. Place bet.
	 * <p>
	 * If selected game supports additional bets within the same game round, several Bet requests
	 * can be called for that game round. Normally, these additional calls, need to have separate
	 * Transaction Ids (Game hand ids)
	 *
	 * @param request - Must contain valid authtoken
	 * @return BetResponse - Response object that contains ExternalTransactionId and balance.
	 * @throws IOException
	 */
	BetResponse bet( final BetRequest request ) throws IOException;

	/**
	 * The win transaction is closing the placed bet in the game. It can be a real win or a win with
	 * an amount 0 which is used to close the game hand / bet as lost.
	 * <p>
	 * The Win call should be performed for every Bet call (except CancelBet call), no matter if
	 * placed bet is win or lost. If placed bet is lost, then Win call should be performed with
	 * Amount parameter set to 0, if placed bet is win, just set this parameter to the win amount.
	 * <p>
	 * Free Spin Promotion For the game providers and games that support free spins, the PromoCode
	 * parameter should be implemented. If promotion code parameter [PromoCode query string
	 * parameter], received on game launch is not empty, and there is a free spin win on game
	 * provider side, then Win call should be performed with BonusPromotionCode equals to received
	 * value.
	 *
	 * @param request - must contain valid authToken
	 * @return WinResponse - Response object that contains ExternalTransactionId and balance.
	 * @throws IOException
	 */
	WinResponse win( final WinRequest request ) throws IOException;

	/**
	 * If a Bet call fails, a CancelBet should be sent with the transactionId of the Bet call. When
	 * received, that transactionId will be canceled. If there is no transactionId, a positive
	 * response will be sent together with an empty externalTransactionId. CancelBet should be
	 * resent until status ok (0) is returned.
	 *
	 * @param request - must contain valid authtoken.
	 * @return CancelBetResponse - Response object that contains status and user balance.
	 * @throws IOException
	 */
	CancelBetResponse cancelBet( final CancelBetRequest request ) throws IOException;

	/**
	 * Refund call should be performed in case when bet need to be refunded.
	 *
	 * @param request - must contain valid externalId and authtoken
	 * @return RefundResponse - Response object that contains status and user balance.
	 * @throws IOException
	 */
	RefundResponse refund( final RefundRequest request ) throws IOException;
}
