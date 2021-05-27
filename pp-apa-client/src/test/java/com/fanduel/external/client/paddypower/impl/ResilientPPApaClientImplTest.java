package com.fanduel.external.client.paddypower.impl;

import static java.util.stream.IntStream.rangeClosed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import io.vavr.control.Try;

import com.fanduel.external.client.paddypower.PPApaClient;
import com.fanduel.external.client.paddypower.exception.PPApaException;
import com.fanduel.runtime.config.Config;
import com.fanduel.runtime.config.EmptyConfig;

public abstract class ResilientPPApaClientImplTest {

	protected static final String GOOD_TOKEN = "goodToken";
	protected static final String INVALID_TOKEN = "invalidToken";
	protected static final String SYSTEM_ERROR_TOKEN = "systemErrorToken";

	protected ResilientPPApaClientImpl client;

	@Mock
	protected PPApaClient delegate;

	abstract ResilientPPApaClientImpl createClient( PPApaClient delegate, Config config );

	@BeforeEach
	void before() {
		client = createClient( delegate, new EmptyConfig() );

		when( delegate.keepAlive( GOOD_TOKEN ) ).thenReturn( GOOD_TOKEN );
		doThrow( PPApaException.class ).when( delegate ).keepAlive( INVALID_TOKEN );
		doThrow( RuntimeException.class ).when( delegate ).keepAlive( SYSTEM_ERROR_TOKEN );
	}

	@Test
	void testDelegation() {
		// Default calls
		client.retrieveUserSessionDetails( null );
		client.keepAlive( null );
		client.transfer( null );
		client.cancelTransfer( null );
		client.retrieveBalance( 0L );

		verify( delegate, times( 1 ) ).retrieveUserSessionDetails( null );
		verify( delegate, times( 1 ) ).keepAlive( null );
		verify( delegate, times( 1 ) ).transfer( null );
		verify( delegate, times( 1 ) ).cancelTransfer( null );
		verify( delegate, times( 1 ) ).retrieveBalance( 0L );

		// Expected returns
		assertEquals( GOOD_TOKEN, client.keepAlive( GOOD_TOKEN ) );
		assertThrows( PPApaException.class, () -> client.keepAlive( INVALID_TOKEN ) );
		assertThrows( RuntimeException.class, () -> client.keepAlive( SYSTEM_ERROR_TOKEN ) );
	}

	@Test
	void testDelegation_Parallel() throws InterruptedException {
		final ExecutorService executor = Executors.newFixedThreadPool( 5 );

		rangeClosed( 1, 200 ).forEach(
				( x ) -> executor.execute( () -> client.keepAlive( GOOD_TOKEN ) ) );

		executor.shutdown();
		executor.awaitTermination( 10, TimeUnit.SECONDS );

		verify( delegate, times( 200 ) ).keepAlive( GOOD_TOKEN );
	}

	@Test
	void openCircuitOnSystemErrors() {
		// Default circuit breaker config, opens circuit on 50% errors. The size of the ring in closed state = 100.
		rangeClosed( 1, 200 ).forEach( ( x ) -> Try.of( () -> client.keepAlive( GOOD_TOKEN ) ) );
		rangeClosed( 1, 200 ).forEach( ( x ) -> Try.of( () -> client.keepAlive( INVALID_TOKEN ) ) );

		// Verify Circuit is still closed.
		verify( delegate, times( 200 ) ).keepAlive( GOOD_TOKEN );
		verify( delegate, times( 200 ) ).keepAlive( INVALID_TOKEN );

		// Induce ~50% errors
		rangeClosed( 1, 60 ).forEach(
				( x ) -> Try.of( () -> client.keepAlive( SYSTEM_ERROR_TOKEN ) ) );

		// Circuit should be OPEN, more calls will be short circuited now
		rangeClosed( 1, 30 ).forEach( ( x ) -> Try.of( () -> client.keepAlive( GOOD_TOKEN ) ) );
		rangeClosed( 1, 40 ).forEach( ( x ) -> Try.of( () -> client.keepAlive( GOOD_TOKEN ) ) );

		// Count should not have gone up.
		verify( delegate, times( 200 ) ).keepAlive( GOOD_TOKEN );
		verify( delegate, times( 200 ) ).keepAlive( INVALID_TOKEN );
	}

	@Test
	void openCircuitOnSystemErrors_Parallel() throws InterruptedException {
		// Default circuit breaker config, opens circuit on 50% errors. The size of the ring in closed state = 100.
		final ExecutorService executor = Executors.newFixedThreadPool( 5 );
		rangeClosed( 1, 200 ).forEach( ( x ) -> executor.execute( () -> {
			Try.of( () -> client.keepAlive( GOOD_TOKEN ) );
			Try.of( () -> client.keepAlive( INVALID_TOKEN ) );
			Try.of( () -> client.keepAlive( SYSTEM_ERROR_TOKEN ) );
		} ) );
		executor.shutdown();
		executor.awaitTermination( 10, TimeUnit.SECONDS );

		verify( delegate, atMost( 100 ) ).keepAlive( GOOD_TOKEN );
		verify( delegate, atMost( 100 ) ).keepAlive( INVALID_TOKEN );
	}
}
