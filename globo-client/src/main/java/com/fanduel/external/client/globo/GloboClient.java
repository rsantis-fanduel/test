package com.fanduel.external.client.globo;

import com.fanduel.external.client.globo.transfer.GloboAuthenticateResponse;
import com.fanduel.external.client.globo.transfer.GloboAuthoriseResponse;

public interface GloboClient {
	/**
	 * Authenticate response.
	 *
	 * @param state
	 * 		the request
	 *
	 * @return the response
	 */
	GloboAuthenticateResponse authenticate( String state );

	/**
	 * Gets token.
	 *
	 * @param code
	 * 		the code
	 * @return the token
	 */
	GloboAuthoriseResponse getToken( String code );

	/**
	 * Refresh globo auth token
	 *
	 * @param refreshToken
	 * 		the refresh request
	 *
	 * @return the globo authorise response
	 */
	GloboAuthoriseResponse refresh( String refreshToken );

	/**
	 * Gets token in a synchronous manner.  Admittedly ugly, it permits the use of resilient4j which
	 * will run these requests on its own managed executor threads.
	 *
	 * @param code
	 * 		the code
	 * @return the token
	 */
	GloboAuthoriseResponse getTokenSync( String code );

	/**
	 * Refresh globo auth token synchronously
	 *
	 * @param refreshToken
	 * 		the refresh request
	 *
	 * @return the globo authorise response
	 */
	GloboAuthoriseResponse refreshSync( String refreshToken );

	/**
	 * Verifies if given token is signed properly
	 *
	 * @param refreshToken to be verified
	 * @return true: when valid, false: when valid but expired
	 * @thows APICodedException(unchecked) if JWT syntax, signature, claims or issue  data is invalid
	 */
	boolean verifyToken( String jwtToken );
}
