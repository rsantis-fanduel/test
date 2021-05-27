package com.fanduel.external.client.paddypower.impl;

import static java.util.stream.IntStream.rangeClosed;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static com.fanduel.external.client.paddypower.PPGapClientModule.createCircuitBreakerRegistry;
import static com.fanduel.external.client.paddypower.PPGapClientModule.createTimeLimiterRegistry;

import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import io.vavr.control.Try;

import com.fanduel.external.client.paddypower.PPGapClient;
import com.fanduel.runtime.config.Config;
import com.fanduel.runtime.config.MapConfig;
import com.google.common.collect.ImmutableMap;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CircuitedTimeLimitedPPGapClientImplTest extends ResilientPPGapClientImplTest {

	private static final String SLOW_TOKEN = "slowToken";

	@Override
	ResilientPPGapClientImpl createClient( final PPGapClient delegate, final Config config ) {
		return new ResilientPPGapClientImpl( delegate, createCircuitBreakerRegistry( config ), true,
				Executors.newFixedThreadPool( 5 ), createTimeLimiterRegistry( config ) );
	}

	@Test
	void openCircuitOnTimeOuts() {
		// keepAlive calls will timeout if it takes more than 100ms
		// Circuit breaker ring size is 10 calls, and fail threshold is 50%. So 5 failure calls should short circuit.
		final Config config = new MapConfig(
				ImmutableMap.of( "Res4J.TimeLimiter.Method.keepAlive.TimeoutDurationMS", 100,
						"Res4J.CircuitBreaker.Method.keepAlive.MaxFailureThresholdPercent", 50,
						"Res4J.CircuitBreaker.Method.keepAlive.RingBufferSizeInHalfClosedState",
						10 ) );

		when( delegate.keepAlive( SLOW_TOKEN ) ).then( invocation -> {
			Thread.sleep( 150 );
			return SLOW_TOKEN;
		} );

		final ResilientPPGapClientImpl client = createClient( delegate, config );

		rangeClosed( 1, 20 ).forEach( ( x ) -> Try.of( () -> client.keepAlive( GOOD_TOKEN ) ) );

		// Verify Circuit is still closed.
		verify( delegate, times( 20 ) ).keepAlive( GOOD_TOKEN );

		// let's Induce ~50% timeout errors
		rangeClosed( 1, 6 ).forEach( ( x ) -> Try.of( () -> client.keepAlive( SLOW_TOKEN ) ) );

		// Circuit should be OPEN, more calls will be short circuited now
		rangeClosed( 1, 30 ).forEach( ( x ) -> Try.of( () -> client.keepAlive( GOOD_TOKEN ) ) );

		// Count should not have gone up.
		verify( delegate, times( 20 ) ).keepAlive( GOOD_TOKEN );
	}
}
