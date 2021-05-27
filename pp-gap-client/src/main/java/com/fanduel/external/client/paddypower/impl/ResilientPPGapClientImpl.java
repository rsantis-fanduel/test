package com.fanduel.external.client.paddypower.impl;

import static com.fanduel.external.client.paddypower.PPGapClientModule.CANCEL_TRANSFER;
import static com.fanduel.external.client.paddypower.PPGapClientModule.KEEP_ALIVE;
import static com.fanduel.external.client.paddypower.PPGapClientModule.RETRIEVE_BALANCE;
import static com.fanduel.external.client.paddypower.PPGapClientModule.RETRIEVE_USER_SESSION_DETAILS;
import static com.fanduel.external.client.paddypower.PPGapClientModule.TRANSFER;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.metrics.CircuitBreakerMetrics;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.vavr.control.Try;

import com.codahale.metrics.MetricRegistry;
import com.fanduel.external.client.paddypower.PPGapClient;
import com.fanduel.external.client.paddypower.exception.PPGapException;
import com.fanduel.external.client.paddypower.res4j.TimeLimiterRegistry;
import com.fanduel.external.client.paddypower.transfer.TransferRequest;
import com.fanduel.external.client.paddypower.transfer.TransferResponse;
import com.fanduel.external.client.paddypower.transfer.UserSessionDetails;
import com.fanduel.external.client.paddypower.transfer.UserSessionDetailsRequest;
import com.fanduel.metrics.MetricRegistries;

public class ResilientPPGapClientImpl implements PPGapClient {
	private static final MetricRegistry METRICS = MetricRegistries.getRegistry();

	private final PPGapClient delegate;
	private final CircuitBreakerRegistry circuitBreakerRegistry;
	private final boolean timeLimiterEnabled;
	private final Executor timeLimiterExecutor;
	private final TimeLimiterRegistry timeLimiterRegistry;

	public ResilientPPGapClientImpl( final PPGapClient delegate,
			final CircuitBreakerRegistry circuitBreakerRegistry, final boolean timeLimiterEnabled,
			final Executor timeLimiterExecutor, final TimeLimiterRegistry timeLimiterRegistry ) {

		METRICS.registerAll( CircuitBreakerMetrics.ofCircuitBreakerRegistry( "pp-gap-client",
				circuitBreakerRegistry ) );

		this.delegate = delegate;
		this.circuitBreakerRegistry = circuitBreakerRegistry;
		this.timeLimiterEnabled = timeLimiterEnabled;
		this.timeLimiterExecutor = timeLimiterExecutor;
		this.timeLimiterRegistry = timeLimiterRegistry;
	}

	@Override
	public UserSessionDetails retrieveUserSessionDetails(
			final UserSessionDetailsRequest request ) {
		return callResiliently( RETRIEVE_USER_SESSION_DETAILS,
				() -> delegate.retrieveUserSessionDetails( request ) );
	}

	@Override
	public String keepAlive( final String token ) {
		return callResiliently( KEEP_ALIVE, () -> delegate.keepAlive( token ) );
	}

	@Override
	public TransferResponse transfer( final TransferRequest request ) {
		return callResiliently( TRANSFER, () -> delegate.transfer( request ) );
	}

	@Override
	public void cancelTransfer( final TransferRequest request ) {
		callResiliently( CANCEL_TRANSFER, () -> {
			delegate.cancelTransfer( request );
			return null; // to make it callable
		} );
	}

	@Override
	public BigDecimal retrieveBalance( final long accountId ) {
		return callResiliently( RETRIEVE_BALANCE, () -> delegate.retrieveBalance( accountId ) );
	}

	private <T> T callResiliently( final String key, Callable<T> callable ) {
		final Callable<T> circuitedCallable = CircuitBreaker.decorateCallable(
				circuitBreakerRegistry.circuitBreaker( key ),
				timeLimiterEnabled ? timeLimitedCallable( key, callable ) : callable );

		return Try.ofCallable( circuitedCallable ).getOrElseThrow( this::unwrapBusinessExceptions );
	}

	private <T> Callable<T> timeLimitedCallable( final String key, Callable<T> callable ) {
		final TimeLimiter timeLimiter = timeLimiterRegistry.timeLimiter( key );
		final Supplier<CompletableFuture<T>> futureSupplier = () -> CompletableFuture.supplyAsync(
				() -> Try.ofCallable( callable ).get(), timeLimiterExecutor );

		return TimeLimiter.decorateFutureSupplier( timeLimiter, futureSupplier );
	}

	private RuntimeException unwrapBusinessExceptions( final Throwable exception ) {
		if ( exception instanceof PPGapException ) {
			return (PPGapException) exception;
		}

		if ( exception.getCause() instanceof PPGapException ) {
			return (PPGapException) exception.getCause();
		}

		throw new RuntimeException( exception );
	}
}