package com.fanduel.external.client.btobet.impl;

import static java.util.stream.IntStream.rangeClosed;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static com.fanduel.external.client.btobet.BtoBetClientModule.createCircuitBreakerRegistry;
import static com.fanduel.external.client.btobet.BtoBetClientModule.createTimeLimiterRegistry;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import io.vavr.control.Try;

import com.fanduel.external.client.btobet.BtoBetClient;
import com.fanduel.external.client.btobet.transfer.AuthenticateRequest;
import com.fanduel.external.client.btobet.transfer.AuthenticateResponse;
import com.fanduel.runtime.config.Config;
import com.fanduel.runtime.config.MapConfig;
import com.google.common.collect.ImmutableMap;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CircuitedTimeLimitedBtoBetClientImplTest extends ResilientBtoBetClientImplTest {

	private static final AuthenticateRequest GOOD_REQUEST = goodAuthenticateRequest();
	private static final AuthenticateRequest SLOW_REQUEST = slowAuthenticateRequest();
	private static final AuthenticateResponse SLOW_RESPONSE = slowAuthenticateResponse();

	@Override
	ResilientBtoBetClientImpl createClient( final BtoBetClient delegate, final Config config ) {
		return new ResilientBtoBetClientImpl( delegate, createCircuitBreakerRegistry( config ),
				true, Executors.newFixedThreadPool( 5 ), createTimeLimiterRegistry( config ) );
	}

	@Test
	void openCircuitOnTimeOuts() throws IOException {
		// authenticate calls will timeout if it takes more than 100ms
		// Circuit breaker ring size is 10 calls, and fail threshold is 50%. So 5 failure calls should short circuit.
		final Config config = new MapConfig(
				ImmutableMap.of( "Res4J.TimeLimiter.Method.keepAlive.TimeoutDurationMS", 100,
						"Res4J.CircuitBreaker.Method.keepAlive.MaxFailureThresholdPercent", 50,
						"Res4J.CircuitBreaker.Method.keepAlive.RingBufferSizeInHalfClosedState",
						10 ) );

		when( delegate.authenticate( SLOW_REQUEST ) ).then( invocation -> {
			Thread.sleep( 1500 );
			return SLOW_RESPONSE;
		} );

		final ResilientBtoBetClientImpl client = createClient( delegate, config );

		rangeClosed( 1, 20 ).forEach(
				( x ) -> Try.of( () -> client.authenticate( GOOD_REQUEST ) ) );

		// Verify Circuit is still closed.
		verify( delegate, times( 20 ) ).authenticate( GOOD_REQUEST );

		// let's Induce ~50% timeout errors
		rangeClosed( 1, 6 ).forEach( ( x ) -> Try.of( () -> client.authenticate( SLOW_REQUEST ) ) );

		// Count should not have gone up.
		verify( delegate, times( 20 ) ).authenticate( GOOD_REQUEST );
	}

	protected static AuthenticateRequest slowAuthenticateRequest() {
		return (AuthenticateRequest) AuthenticateRequest.builder()
				.authToken( "SLOW" )
				.gameCode( GAME_CODE )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();
	}

	private static AuthenticateResponse slowAuthenticateResponse() {
		return AuthenticateResponse.Builder.anAuthenticateResponse()
				.statusCode( 0 )
				.externalId( 10 )
				.balance( new BigDecimal( 1500.00 ) )
				.build();
	}

}
