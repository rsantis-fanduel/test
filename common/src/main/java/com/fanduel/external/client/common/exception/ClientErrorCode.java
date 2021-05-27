package com.fanduel.external.client.common.exception;

import com.fanduel.exception.ErrorCode;
import com.fanduel.external.client.common.ExternalClient;

public enum ClientErrorCode implements ErrorCode {

	GLOBO_TOKEN_PARSING_ERROR( ExternalClient.GLOBO, 1000, null ),
	GLOBO_URI_PARSING_ERROR( ExternalClient.GLOBO, 1001, null ),
	GLOBO_RESPONSE_DESERIALIZATION_ERROR( ExternalClient.GLOBO, 1002, null ),
	GLOBO_CLIENT_REQUEST_ERROR( ExternalClient.GLOBO, 1003, null ),
	GLOBO_CLIENT_EXECUTION_ERROR( ExternalClient.GLOBO, 1004, null ),
	JWT_SIGNATURE_VERIFICATION_ERROR( ExternalClient.COMMON, 1011, null ),
	BTOBET_CLIENT_PLAYER_NOT_FOUND( ExternalClient.BTOBET, 2001, "Player Not Found" ),
	BTOBET_CLIENT_INVALID_PLAYER( ExternalClient.BTOBET, 2002, "Invalid Player" ),
	BTOBET_CLIENT_TOKEN_EXPIRED( ExternalClient.BTOBET, 2003, "Token Expired" ),
	BTOBET_CLIENT_INVALID_TOKEN( ExternalClient.BTOBET, 2004, "Invalid Token" ),
	BTOBET_CLIENT_SYSTEM_ERROR( ExternalClient.BTOBET, 2005, "System Error" ),
	BTOBET_CLIENT_IP_BLOCKED( ExternalClient.BTOBET, 2006, "IP Blocked" ),
	BTOBET_CLIENT_BAD_PARAMETERS( ExternalClient.BTOBET, 2007, "Bad Parameters" ),
	BTOBET_CLIENT_ACCOUNT_BLOCKED( ExternalClient.BTOBET, 2008, "Account Blocked" ),
	BTOBET_CLIENT_ACCOUNT_INACTIVE( ExternalClient.BTOBET, 2009, "Account Inactive" ),
	BTOBET_CLIENT_ACCOUNT_LOCKED( ExternalClient.BTOBET, 2010, "Account Locked" ),
	BTOBET_CLIENT_INVALID_CREDENTIALS( ExternalClient.BTOBET, 2011, "Invalid Credentials" ),
	BTOBET_CLIENT_INVALID_TRANSACTION( ExternalClient.BTOBET, 2012, "Invalid Transaction" ),
	BTOBET_CLIENT_INVALID_AMOUNT( ExternalClient.BTOBET, 2013, "Invalid Amount" ),
	BTOBET_CLIENT_PLAYER_BET_LIMIT_EXCEEDED( ExternalClient.BTOBET, 2014,
			"Player Bet Limit Exceeded" ),
	BTOBET_CLIENT_PLAYER_TIME_LIMIT_EXCEEDED( ExternalClient.BTOBET, 2015,
			"Player Time Limit Exceeded" ),
	BTOBET_CLIENT_INVALID_GAME( ExternalClient.BTOBET, 2016, "Invalid Game" ),
	BTOBET_CLIENT_NOT_ENOUGH_MONEY( ExternalClient.BTOBET, 2017, "Not Enough Money" ),
	BTOBET_CLIENT_GENERIC_ERROR( ExternalClient.BTOBET, 2100, "Unexpected BtoBet Error" );

	private final int code;
	private final ExternalClient clientName;
	private final String message;

	ClientErrorCode( final ExternalClient clientName, final int code, final String message ) {
		this.code = code;
		this.clientName = clientName;
		this.message = message;
	}

	@Override
	public String getPrefix() {
		return this.clientName.toString();
	}

	@Override
	public Integer getErrorNumber() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String getCode() {
		return String.format( "%s.%d.%s", getPrefix(), getErrorNumber(), getMessage() );
	}

}
