package com.fanduel.external.client.btobet.impl;

import static java.util.stream.IntStream.rangeClosed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import io.vavr.control.Try;

import com.fanduel.api.exception.APICodedException;
import com.fanduel.external.client.btobet.BtoBetClient;
import com.fanduel.external.client.btobet.transfer.AuthenticateRequest;
import com.fanduel.external.client.btobet.transfer.AuthenticateResponse;
import com.fanduel.runtime.config.Config;
import com.fanduel.runtime.config.EmptyConfig;

public abstract class ResilientBtoBetClientImplTest {

	protected final static String API_PASSWORD = "apipassword";
	protected final static String API_USERNAME = "apiusername";
	protected final static String AUTH_TOKEN = "authtoken";
	protected final static String GAME_CODE = "gamecode";
	protected final static int CLIENT_ID = 1;

	private final AuthenticateRequest goodRequest = goodAuthenticateRequest();
	private final AuthenticateRequest badRequest = badAuthenticateRequest();
	private final AuthenticateResponse goodResponse = goodAuthenticateResponse();
	private final AuthenticateRequest errorRequest = errorAuthenticateRequest();

	protected ResilientBtoBetClientImpl client;

	@Mock
	protected BtoBetClient delegate;

	abstract ResilientBtoBetClientImpl createClient( BtoBetClient delegate, Config config );

	@BeforeEach
	void before() throws IOException {
		client = createClient( delegate, new EmptyConfig() );

		when( delegate.authenticate( goodRequest ) ).thenReturn( goodResponse );
		doThrow( APICodedException.class ).when( delegate ).authenticate( badRequest );
		doThrow( RuntimeException.class ).when( delegate ).authenticate( errorRequest );
	}

	@Test
	void testDelegation() throws IOException {
		// Default calls
		client.authenticate( null );

		verify( delegate, times( 1 ) ).authenticate( null );

		// Expected returns
		assertEquals( goodResponse, client.authenticate( goodRequest ) );
		assertThrows( RuntimeException.class, () -> client.authenticate( badRequest ) );
		assertThrows( RuntimeException.class, () -> client.authenticate( errorRequest ) );
	}

	@Test
	void testDelegation_Parallel() throws InterruptedException, IOException {
		final ExecutorService executor = Executors.newFixedThreadPool( 5 );

		rangeClosed( 1, 200 ).forEach(
				( x ) -> executor.execute( () -> client.authenticate( goodRequest ) ) );

		executor.shutdown();
		executor.awaitTermination( 10, TimeUnit.SECONDS );

		verify( delegate, times( 200 ) ).authenticate( goodRequest );
	}

	@Test
	void openCircuitOnSystemErrors() throws IOException {
		// Default circuit breaker config, opens circuit on 50% errors. The size of the ring in closed state = 100.
		rangeClosed( 1, 200 ).forEach(
				( x ) -> Try.of( () -> client.authenticate( goodRequest ) ) );
		rangeClosed( 1, 200 ).forEach( ( x ) -> Try.of( () -> client.authenticate( badRequest ) ) );

		// Verify Circuit is still closed.
		verify( delegate, times( 200 ) ).authenticate( goodRequest );
		verify( delegate, times( 200 ) ).authenticate( badRequest );

		// Induce ~50% errors
		rangeClosed( 1, 60 ).forEach(
				( x ) -> Try.of( () -> client.authenticate( errorRequest ) ) );

		// Circuit should be OPEN, more calls will be short circuited now
		rangeClosed( 1, 30 ).forEach( ( x ) -> Try.of( () -> client.authenticate( goodRequest ) ) );
		rangeClosed( 1, 40 ).forEach( ( x ) -> Try.of( () -> client.authenticate( goodRequest ) ) );

		// Count should not have gone up.
		verify( delegate, times( 200 ) ).authenticate( goodRequest );
		verify( delegate, times( 200 ) ).authenticate( badRequest );
	}

	@Test
	void openCircuitOnSystemErrors_Parallel() throws InterruptedException, IOException {
		// Default circuit breaker config, opens circuit on 50% errors. The size of the ring in closed state = 100.
		final ExecutorService executor = Executors.newFixedThreadPool( 5 );
		rangeClosed( 1, 200 ).forEach( ( x ) -> executor.execute( () -> {
			Try.of( () -> client.authenticate( goodRequest ) );
			Try.of( () -> client.authenticate( badRequest ) );
			Try.of( () -> client.authenticate( errorRequest ) );
		} ) );
		executor.shutdown();
		executor.awaitTermination( 10, TimeUnit.SECONDS );

		verify( delegate, atMost( 100 ) ).authenticate( goodRequest );
		verify( delegate, atMost( 100 ) ).authenticate( badRequest );
	}

	protected static AuthenticateResponse goodAuthenticateResponse() {
		return AuthenticateResponse.Builder.anAuthenticateResponse()
				.statusCode( 0 )
				.currency( "BRL" )
				.language( "PT" )
				.nickname( "Fanduel" )
				.externalId( 1 )
				.country( "BR" )
				.gender( "M" )
				.birthdate( "1989-01-01T00:00:00" )
				.balance( new BigDecimal( 1500.00 ) )
				.build();
	}

	protected static AuthenticateRequest goodAuthenticateRequest() {
		return (AuthenticateRequest) AuthenticateRequest.builder()
				.authToken( AUTH_TOKEN )
				.gameCode( GAME_CODE )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();
	}

	protected AuthenticateRequest badAuthenticateRequest() {
		return (AuthenticateRequest) AuthenticateRequest.builder()
				.authToken( AUTH_TOKEN + "SLOW" )
				.gameCode( GAME_CODE )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();
	}

	protected AuthenticateRequest errorAuthenticateRequest() {
		return (AuthenticateRequest) AuthenticateRequest.builder()
				.gameCode( GAME_CODE )
				.authToken( AUTH_TOKEN + "ERROR" )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();
	}

}
