package com.fanduel.external.client.common.res4j;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.vavr.control.Try;

public abstract class ResilientClient {
	private static final Logger LOGGER = LoggerFactory.getLogger( ResilientClient.class );
	private final TimeLimiterRegistry timeLimiterRegistry;
	private final CircuitBreakerRegistry circuitBreakerRegistry;
	private final boolean timeLimiterEnabled;
	private final Executor timeLimiterExecutor;

	public ResilientClient( final TimeLimiterRegistry timeLimiterRegistry,
			final CircuitBreakerRegistry circuitBreakerRegistry, final boolean timeLimiterEnabled,
			final Executor timeLimiterExecutor ) {
		this.timeLimiterRegistry = timeLimiterRegistry;
		this.circuitBreakerRegistry = circuitBreakerRegistry;
		this.timeLimiterEnabled = timeLimiterEnabled;
		this.timeLimiterExecutor = timeLimiterExecutor;
	}

	protected <T> T callResiliently( final String key, Callable<T> callable ) {
		final Callable<T> circuitedCallable = CircuitBreaker.decorateCallable(
				circuitBreakerRegistry.circuitBreaker( key ),
				timeLimiterEnabled ? timeLimitedCallable( key, callable ) : callable );

		return Try.ofCallable( circuitedCallable ).getOrElseThrow( this::unwrapBusinessExceptions );
	}

	protected <T> Callable<T> timeLimitedCallable( final String key, Callable<T> callable ) {
		final TimeLimiter timeLimiter = timeLimiterRegistry.timeLimiter( key );
		final Supplier<CompletableFuture<T>> futureSupplier = () -> CompletableFuture.supplyAsync(
				() -> Try.ofCallable( callable ).get(), timeLimiterExecutor );

		return TimeLimiter.decorateFutureSupplier( timeLimiter, futureSupplier );
	}

	protected RuntimeException unwrapBusinessExceptions( final Throwable exception ) {
		LOGGER.error( "Error calling delegate:", exception );
		throw new RuntimeException( exception );
	}
}
