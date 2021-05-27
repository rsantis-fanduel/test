package com.fanduel.external.client.btobet.impl.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanduel.api.exception.APICodedException;
import com.fanduel.external.client.common.exception.ClientErrorCode;

public class BtoBetClientExceptionHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger( BtoBetClientExceptionHelper.class );

	private final static Map<Integer, ClientErrorCode> EXCEPTION_MAP;

	static {
		EXCEPTION_MAP = new HashMap<>();
		EXCEPTION_MAP.put( 1, ClientErrorCode.BTOBET_CLIENT_PLAYER_NOT_FOUND );
		EXCEPTION_MAP.put( 2, ClientErrorCode.BTOBET_CLIENT_INVALID_PLAYER );
		EXCEPTION_MAP.put( 3, ClientErrorCode.BTOBET_CLIENT_TOKEN_EXPIRED );
		EXCEPTION_MAP.put( 4, ClientErrorCode.BTOBET_CLIENT_INVALID_TOKEN );
		EXCEPTION_MAP.put( 5, ClientErrorCode.BTOBET_CLIENT_SYSTEM_ERROR );
		EXCEPTION_MAP.put( 6, ClientErrorCode.BTOBET_CLIENT_IP_BLOCKED );
		EXCEPTION_MAP.put( 7, ClientErrorCode.BTOBET_CLIENT_BAD_PARAMETERS );
		EXCEPTION_MAP.put( 8, ClientErrorCode.BTOBET_CLIENT_ACCOUNT_BLOCKED );
		EXCEPTION_MAP.put( 9, ClientErrorCode.BTOBET_CLIENT_ACCOUNT_INACTIVE );
		EXCEPTION_MAP.put( 10, ClientErrorCode.BTOBET_CLIENT_ACCOUNT_LOCKED );
		EXCEPTION_MAP.put( 11, ClientErrorCode.BTOBET_CLIENT_INVALID_CREDENTIALS );
		EXCEPTION_MAP.put( 12, ClientErrorCode.BTOBET_CLIENT_INVALID_TRANSACTION );
		EXCEPTION_MAP.put( 13, ClientErrorCode.BTOBET_CLIENT_INVALID_AMOUNT );
		EXCEPTION_MAP.put( 14, ClientErrorCode.BTOBET_CLIENT_PLAYER_BET_LIMIT_EXCEEDED );
		EXCEPTION_MAP.put( 15, ClientErrorCode.BTOBET_CLIENT_PLAYER_TIME_LIMIT_EXCEEDED );
		EXCEPTION_MAP.put( 16, ClientErrorCode.BTOBET_CLIENT_INVALID_GAME );
		EXCEPTION_MAP.put( 17, ClientErrorCode.BTOBET_CLIENT_NOT_ENOUGH_MONEY );
	}

	public static APICodedException mapException( final int responseStatusCode, final String call,
			final String fullRequest ) {

		LOGGER.error(
				"msg=Encountered error with code {} in making btobet client call {} request={}",
				responseStatusCode, call, fullRequest );

		if ( EXCEPTION_MAP.containsKey( responseStatusCode ) ) {
			return new APICodedException( EXCEPTION_MAP.get( responseStatusCode ) );
		} else {
			return new APICodedException( ClientErrorCode.BTOBET_CLIENT_GENERIC_ERROR );
		}
	}

}
