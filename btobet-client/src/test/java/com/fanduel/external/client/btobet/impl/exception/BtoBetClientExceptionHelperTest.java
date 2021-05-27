package com.fanduel.external.client.btobet.impl.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fanduel.api.exception.APICodedException;

public class BtoBetClientExceptionHelperTest {

	@Test
	void playerNotFound() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 1, "", "" );

		// Assert
		assertEquals( "BTOBET.2001.Player Not Found", exception.getErrorCode().getCode() );
	}

	@Test
	void invalidPlayer() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 2, "", "" );

		// Assert
		assertEquals( "BTOBET.2002.Invalid Player", exception.getErrorCode().getCode() );

	}

	@Test
	void tokenExpired() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 3, "", "" );

		// Assert
		assertEquals( "BTOBET.2003.Token Expired", exception.getErrorCode().getCode() );

	}

	@Test
	void invalidToken() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 4, "", "" );

		// Assert
		assertEquals( "BTOBET.2004.Invalid Token", exception.getErrorCode().getCode() );

	}

	@Test
	void systemError() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 5, "", "" );

		// Assert
		assertEquals( "BTOBET.2005.System Error", exception.getErrorCode().getCode() );

	}

	@Test
	void ipBlocked() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 6, "", "" );

		// Assert
		assertEquals( "BTOBET.2006.IP Blocked", exception.getErrorCode().getCode() );

	}

	@Test
	void badParameters() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 7, "", "" );

		// Assert
		assertEquals( "BTOBET.2007.Bad Parameters", exception.getErrorCode().getCode() );

	}

	@Test
	void accountBlocked() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 8, "", "" );

		// Assert
		assertEquals( "BTOBET.2008.Account Blocked", exception.getErrorCode().getCode() );

	}

	@Test
	void accountInactive() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 9, "", "" );

		// Assert
		assertEquals( "BTOBET.2009.Account Inactive", exception.getErrorCode().getCode() );

	}

	@Test
	void accountLocked() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 10, "", "" );

		// Assert
		assertEquals( "BTOBET.2010.Account Locked", exception.getErrorCode().getCode() );

	}

	@Test
	void invalidCredentials() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 11, "", "" );

		// Assert
		assertEquals( "BTOBET.2011.Invalid Credentials", exception.getErrorCode().getCode() );

	}

	@Test
	void invalidTransaction() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 12, "", "" );

		// Assert
		assertEquals( "BTOBET.2012.Invalid Transaction", exception.getErrorCode().getCode() );

	}

	@Test
	void invalidAmount() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 13, "", "" );

		// Assert
		assertEquals( "BTOBET.2013.Invalid Amount", exception.getErrorCode().getCode() );

	}

	@Test
	void playerBetLimitExceeded() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 14, "", "" );

		// Assert
		assertEquals( "BTOBET.2014.Player Bet Limit Exceeded", exception.getErrorCode().getCode() );

	}

	@Test
	void playerTimeLimitExceeded() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 15, "", "" );

		// Assert
		assertEquals( "BTOBET.2015.Player Time Limit Exceeded",
				exception.getErrorCode().getCode() );

	}

	@Test
	void invalidGame() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 16, "", "" );

		// Assert
		assertEquals( "BTOBET.2016.Invalid Game", exception.getErrorCode().getCode() );

	}

	@Test
	void notEnoughMoney() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 17, "", "" );

		// Assert
		assertEquals( "BTOBET.2017.Not Enough Money", exception.getErrorCode().getCode() );

	}

	@Test
	void genericError() {
		// Act
		APICodedException exception = BtoBetClientExceptionHelper.mapException( 50, "", "" );

		// Assert
		assertEquals( "BTOBET.2100.Unexpected BtoBet Error", exception.getErrorCode().getCode() );

	}

}
