package com.fanduel.external.client.paddypower;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import static com.codahale.metrics.MetricRegistry.name;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

import javax.xml.namespace.QName;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

import com.codahale.metrics.InstrumentedExecutorService;
import com.codahale.metrics.MetricRegistry;
import com.fanduel.external.client.paddypower.exception.PPApaException;
import com.fanduel.external.client.paddypower.impl.PPApaClientImpl;
import com.fanduel.external.client.paddypower.impl.ResilientPPApaClientImpl;
import com.fanduel.external.client.paddypower.res4j.TimeLimiterRegistry;
import com.fanduel.metrics.MetricRegistries;
import com.fanduel.runtime.ConfigAwareModule;
import com.fanduel.runtime.config.Config;
import com.google.common.annotations.VisibleForTesting;

public class PPApaClientModule extends ConfigAwareModule {
	private static final MetricRegistry METRICS = MetricRegistries.getRegistry();

	public final static String RETRIEVE_USER_SESSION_DETAILS = "retrieveUserSessionDetails";
	public final static String KEEP_ALIVE = "keepAlive";
	public final static String TRANSFER = "transfer";
	public final static String CANCEL_TRANSFER = "cancelTransfer";
	public final static String RETRIEVE_BALANCE = "retrieveBalance";
	public final static String RETRIEVE_BALANCE_INFO = "retrieveBalanceInfo";

	private static final String WSDL_URL_KEY = "WsdlUrl";
	private static final String WS_NAMESPACE_KEY = "Namespace";
	private static final String WS_SERVICE_NAME_KEY = "ServiceName";
	private static final String CLIENT_ID_HEADER = "ClientId";

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
	private static final String DEFAULT_WS_NAMESPACE = "http://www.betfair.com/serviceapi/v1.0/PartnerAPI/";
	private static final String DEFAULT_WS_SERVICE_NAME = "PartnerAPIService";
	private static final String DEFAULT_CLIENT_ID_HEADER = "ppfantasy";

	public PPApaClientModule( final Config config ) {
		super( config );
	}

	protected void bindPPApaClient( String injectedName ) throws MalformedURLException {
		PPApaClient ppApaClient = createBareClient( injectedName );

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

			ppApaClient = new ResilientPPApaClientImpl( ppApaClient, circuitBreakerRegistry,
					timeLimiterEnabled, timeLimiterExecutor, timeLimiterRegistry );

		}

		bind( PPApaClient.class ).toInstance( ppApaClient );
	}

	@VisibleForTesting
	public static TimeLimiterRegistry createTimeLimiterRegistry( final Config section ) {
		final TimeLimiterConfig defaultTL = TimeLimiterConfig.ofDefaults();
		final TimeLimiterRegistry timeLimiterRegistry = TimeLimiterRegistry.of( defaultTL );

		for ( String method : asList( RETRIEVE_USER_SESSION_DETAILS, KEEP_ALIVE, TRANSFER,
				CANCEL_TRANSFER, RETRIEVE_BALANCE ) ) {
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

		for ( String method : asList( RETRIEVE_USER_SESSION_DETAILS, KEEP_ALIVE, TRANSFER,
				CANCEL_TRANSFER, RETRIEVE_BALANCE ) ) {

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

	private PPApaClient createBareClient( String injectedName ) throws MalformedURLException {
		Config section = getConfig().section( injectedName );

		URL wsdlUrl = new URL( section.get( WSDL_URL_KEY ) );
		String namespace = section.get( WS_NAMESPACE_KEY, DEFAULT_WS_NAMESPACE );
		String serviceName = section.get( WS_SERVICE_NAME_KEY, DEFAULT_WS_SERVICE_NAME );
		String clientId = section.get( CLIENT_ID_HEADER, DEFAULT_CLIENT_ID_HEADER );

		QName qName = new QName( namespace, serviceName );

		return new PPApaClientImpl( wsdlUrl, qName, clientId );
	}

	private Executor createTimeLimiterExecutor( final String injectedName, final Config section ) {
		return new InstrumentedExecutorService( Executors.newFixedThreadPool(
				section.getInt( RES4J_TIME_LIMITER_EXECUTOR_POOL_SIZE,
						DEFAULT_RES4J_TIME_LIMITER_EXECUTOR_POOL_SIZE ) ), METRICS,
				name( PPApaClientModule.class, injectedName, "time-limiter-executor" ) );
	}

	// Predicate when circuit breaker counts errors
	// Ignore Business exceptions here.
	@VisibleForTesting
	public static Predicate<Throwable> recordFailure() {
		return ( exception ) -> {
			if ( exception instanceof PPApaException ) {
				return false;
			}
			if ( exception.getCause() instanceof PPApaException ) {
				return false;
			}
			return true;
		};
	}

}
