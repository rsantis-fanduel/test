package com.fanduel.external.client.common.res4j;

import static java.lang.String.format;

import java.util.Collection;
import java.util.function.Predicate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

import com.codahale.metrics.MetricRegistry;
import com.fanduel.metrics.MetricRegistries;
import com.fanduel.runtime.ConfigAwareModule;
import com.fanduel.runtime.config.Config;
import com.google.common.annotations.VisibleForTesting;

public abstract class ResilientAwareModule extends ConfigAwareModule {
	protected static final MetricRegistry METRICS = MetricRegistries.getRegistry();
	protected static final String RES4J_TIME_LIMITER_ENABLED = "Res4j.TimeLimiter.Enabled";
	protected static final String RES4J_TIME_LIMITER_EXECUTOR_POOL_SIZE = "Res4j.TimeLimiter.ExecutorPoolSize";
	protected static final String RES4J_TIME_LIMITER_METHOD_TIMEOUT_DURATION_MS_FORMAT = "Res4j.TimeLimiter.Method.%s.TimeoutDurationMS";
	protected static final String RES4J_TIME_LIMITER_METHOD_CANCEL_RUNNING_FUTURE_FORMAT = "Res4j.TimeLimiter.Method.%s.CancelRunningFuture";

	protected static final String RES4J_CIRCUIT_BREAKER_ENABLED = "Res4j.CircuitBreaker.Enabled";
	protected static final String RES4J_CIRCUIT_BREAKER_METHOD_MAX_FAILURE_THRESHOLD_PERCENT_FORMAT = "Res4j.CircuitBreaker.Method.%s.MaxFailureThresholdPercent";
	protected static final String RES4J_CIRCUIT_BREAKER_METHOD_WAIT_DURATION_IN_OPEN_STATE_MS_FORMAT = "Res4j.CircuitBreaker.Method.%s.WaitDurationInOpenStateMS";
	protected static final String RES4J_CIRCUIT_BREAKER_METHOD_RING_BUFFER_SIZE_IN_HALF_OPEN_STATE_FORMAT = "Res4j.CircuitBreaker.Method.%s.RingBufferSizeInHalfOpenState";
	protected static final String RES4J_CIRCUIT_BREAKER_METHOD_RING_BUFFER_SIZE_IN_CLOSED_STATE_FORMAT = "Res4j.CircuitBreaker.Method.%s.RingBufferSizeInHalfClosedState";
	protected static final int DEFAULT_RES4J_TIME_LIMITER_EXECUTOR_POOL_SIZE = Runtime.getRuntime()
			.availableProcessors() * 2;
	private final Collection<String> methods;

	public ResilientAwareModule( final Config config, final Collection<String> methods ) {
		super( config );
		this.methods = methods;
	}

	@VisibleForTesting
	public TimeLimiterRegistry createTimeLimiterRegistry( final Config section ) {
		final TimeLimiterConfig defaultTL = TimeLimiterConfig.ofDefaults();
		final TimeLimiterRegistry timeLimiterRegistry = TimeLimiterRegistry.of( defaultTL );

		for ( String method : this.methods ) {
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
	public CircuitBreakerRegistry createCircuitBreakerRegistry( final Config section ) {
		final CircuitBreakerConfig defaultCB = CircuitBreakerConfig.ofDefaults();
		final CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(
				defaultCB );

		for ( String method : this.methods ) {
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

	@VisibleForTesting
	public Predicate<Throwable> recordFailure() {
		return ( exception ) -> {
			return true;
		};
	}
}
