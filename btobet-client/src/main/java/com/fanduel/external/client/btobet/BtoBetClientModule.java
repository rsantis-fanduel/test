package com.fanduel.external.client.btobet;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

import com.codahale.metrics.InstrumentedExecutorService;
import com.codahale.metrics.MetricRegistry;
import com.fanduel.api.exception.APICodedException;
import com.fanduel.external.client.btobet.impl.BtoBetClientImpl;
import com.fanduel.external.client.btobet.impl.ResilientBtoBetClientImpl;
import com.fanduel.external.client.common.res4j.TimeLimiterRegistry;
import com.fanduel.metrics.MetricRegistries;
import com.fanduel.runtime.ConfigAwareModule;
import com.fanduel.runtime.config.Config;
import com.google.common.annotations.VisibleForTesting;

public class BtoBetClientModule extends ConfigAwareModule {
	private static final MetricRegistry METRICS = MetricRegistries.getRegistry();

	public static final String API_AUTHENTICATE = "authenticate";
	public static final String API_BET = "bet";
	public static final String API_CANCEL_BET = "cancelBet";
	public static final String API_GENERATE_TOKEN = "globoGenerateToken";
	public static final String API_GET_BALANCE = "getBalance";
	public static final String API_REFUND = "refund";
	public static final String API_WIN = "win";

	private static final String ENDPOINT_URL_KEY = "BaseUrl";
	private static final String API_USERNAME_JSON_KEY = "Username";
	private static final String API_PASSWORD_JSON_KEY = "Password";
	private static final String CLIENT_ID_JSON_KEY = "ClientId";
	private static final String GAME_CODE_JSON_KEY = "GameCode";

	private static final String RES4J_TIME_LIMITER_ENABLED = "Res4j.TimeLimiter.Enabled";
	private static final String RES4J_TIME_LIMITER_EXECUTOR_POOL_SIZE = "Res4j.TimeLimiter.ExecutorPoolSize";
	private static final String RES4J_TIME_LIMITER_METHOD_TIMEOUT_DURATION_MS_FORMAT = "Res4j.TimeLimiter.Method.%s.TimeoutDurationMS";
	private static final String RES4J_TIME_LIMITER_METHOD_CANCEL_RUNNING_FUTURE_FORMAT = "Res4j.TimeLimiter.Method.%s.CancelRunningFuture";

	private static final String RES4J_CIRCUIT_BREAKER_ENABLED = "Res4j.CircuitBreaker.Enabled";
	private static final String RES4J_CIRCUIT_BREAKER_METHOD_MAX_FAILURE_THRESHOLD_PERCENT_FORMAT = "Res4j.CircuitBreaker.Method.%s.MaxFailureThresholdPercent";
	private static final String RES4J_CIRCUIT_BREAKER_METHOD_WAIT_DURATION_IN_OPEN_STATE_MS_FORMAT = "Res4j.CircuitBreaker.Method.%s.WaitDurationInOpenStateMS";
	private static final String RES4J_CIRCUIT_BREAKER_METHOD_RING_BUFFER_SIZE_IN_HALF_OPEN_STATE_FORMAT = "Res4j.CircuitBreaker.Method.%s.RingBufferSizeInHalfOpenState";
	private static final String RES4J_CIRCUIT_BREAKER_METHOD_RING_BUFFER_SIZE_IN_CLOSED_STATE_FORMAT = "Res4j.CircuitBreaker.Method.%s.RingBufferSizeInHalfClosedState";

	private static final int DEFAULT_RES4J_TIME_LIMITER_EXECUTOR_POOL_SIZE = Runtime.getRuntime()
			.availableProcessors() * 2;

	public BtoBetClientModule( final Config config ) {
		super( config );
	}

	protected void bindBtoBetClient( String injectedName ) {
		BtoBetClient btoBetClient = createBareClient( injectedName );

		Config section = getConfig().section( injectedName );

		if ( section.getBool( RES4J_CIRCUIT_BREAKER_ENABLED, true ) ) {
			final boolean timeLimiterEnabled = section.getBool( RES4J_TIME_LIMITER_ENABLED, true );
			Executor timeLimiterExecutor = null;
			TimeLimiterRegistry timeLimiterRegistry = null;

			if ( timeLimiterEnabled ) {
				timeLimiterExecutor = createTimeLimiterExecutor( injectedName, section );
				timeLimiterRegistry = createTimeLimiterRegistry( section );
			}

			CircuitBreakerRegistry circuitBreakerRegistry = createCircuitBreakerRegistry( section );

			btoBetClient = new ResilientBtoBetClientImpl( btoBetClient, circuitBreakerRegistry,
					timeLimiterEnabled, timeLimiterExecutor, timeLimiterRegistry );

		}

		bind( BtoBetClient.class ).toInstance( btoBetClient );
	}

	@VisibleForTesting
	public static TimeLimiterRegistry createTimeLimiterRegistry( final Config section ) {
		final TimeLimiterConfig defaultTL = TimeLimiterConfig.ofDefaults();
		final TimeLimiterRegistry timeLimiterRegistry = TimeLimiterRegistry.of( defaultTL );

		for ( String method : asList( API_GENERATE_TOKEN, API_AUTHENTICATE ) ) {
			final TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
					.timeoutDuration( section.getDuration(
							format( RES4J_TIME_LIMITER_METHOD_TIMEOUT_DURATION_MS_FORMAT, method ),
							defaultTL.getTimeoutDuration() ) )
					.cancelRunningFuture( section.getBool(
							format( RES4J_TIME_LIMITER_METHOD_CANCEL_RUNNING_FUTURE_FORMAT,
									method ), defaultTL.shouldCancelRunningFuture() ) )
					.build();

			timeLimiterRegistry.timeLimiter( method, timeLimiterConfig );
		}

		return timeLimiterRegistry;
	}

	@VisibleForTesting
	public static CircuitBreakerRegistry createCircuitBreakerRegistry( final Config section ) {
		final CircuitBreakerConfig defaultCB = CircuitBreakerConfig.ofDefaults();
		final CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(
				defaultCB );

		for ( String method : asList( API_GENERATE_TOKEN, API_AUTHENTICATE ) ) {

			final CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
					.failureRateThreshold( (float) section.getDouble(
							format( RES4J_CIRCUIT_BREAKER_METHOD_MAX_FAILURE_THRESHOLD_PERCENT_FORMAT,
									method ), defaultCB.getFailureRateThreshold() ) )
					.waitDurationInOpenState( section.getDuration(
							format( RES4J_CIRCUIT_BREAKER_METHOD_WAIT_DURATION_IN_OPEN_STATE_MS_FORMAT,
									method ), defaultCB.getWaitDurationInOpenState() ) )
					.ringBufferSizeInHalfOpenState( section.getInt(
							format( RES4J_CIRCUIT_BREAKER_METHOD_RING_BUFFER_SIZE_IN_HALF_OPEN_STATE_FORMAT,
									method ), defaultCB.getRingBufferSizeInHalfOpenState() ) )
					.ringBufferSizeInClosedState( section.getInt(
							format( RES4J_CIRCUIT_BREAKER_METHOD_RING_BUFFER_SIZE_IN_CLOSED_STATE_FORMAT,
									method ), defaultCB.getRingBufferSizeInClosedState() ) )
					.enableAutomaticTransitionFromOpenToHalfOpen()
					.recordFailure( recordFailure() )
					.build();

			circuitBreakerRegistry.circuitBreaker( method, circuitBreakerConfig );
		}

		return circuitBreakerRegistry;
	}

	public BtoBetClient createBareClient( String injectedName ) {
		Config section = getConfig().section( injectedName );

		return new BtoBetClientImpl( section.get( ENDPOINT_URL_KEY ),
				section.getInt( CLIENT_ID_JSON_KEY ), section.get( API_USERNAME_JSON_KEY ),
				section.get( API_PASSWORD_JSON_KEY ), section.get( GAME_CODE_JSON_KEY ) );
	}

	private Executor createTimeLimiterExecutor( final String injectedName, final Config section ) {
		return new InstrumentedExecutorService( Executors.newFixedThreadPool(
				section.getInt( RES4J_TIME_LIMITER_EXECUTOR_POOL_SIZE,
						DEFAULT_RES4J_TIME_LIMITER_EXECUTOR_POOL_SIZE ) ), METRICS,
				name( BtoBetClientModule.class, injectedName, "time-limiter-executor" ) );
	}

	// Predicate when circuit breaker counts errors
	// Ignore Business exceptions here.
	@VisibleForTesting
	public static Predicate<Throwable> recordFailure() {
		return ( exception ) -> {
			if ( exception instanceof APICodedException ) {
				return false;
			}
			if ( exception.getCause() instanceof APICodedException ) {
				return false;
			}
			return true;
		};
	}
}
