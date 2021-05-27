package com.fanduel.external.client.btobet.impl.res4j;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

public class TimeLimiterRegistry {
	private final TimeLimiterConfig defaultTimeLimiterConfig;

	private final ConcurrentMap<String, TimeLimiter> timeLimiters;

	public static TimeLimiterRegistry of( TimeLimiterConfig timeLimiterConfig ) {
		return new TimeLimiterRegistry( timeLimiterConfig );
	}

	public static TimeLimiterRegistry ofDefaults() {
		return new TimeLimiterRegistry( TimeLimiterConfig.ofDefaults() );
	}

	public TimeLimiterRegistry() {
		this.defaultTimeLimiterConfig = TimeLimiterConfig.ofDefaults();
		this.timeLimiters = new ConcurrentHashMap<>();
	}

	public TimeLimiterRegistry( TimeLimiterConfig defaultTimeLimiterConfig ) {
		this.defaultTimeLimiterConfig = Objects.requireNonNull( defaultTimeLimiterConfig,
				"TimeLimiterConfig must not be null" );
		this.timeLimiters = new ConcurrentHashMap<>();
	}

	public TimeLimiter timeLimiter( String name ) {
		return timeLimiters.computeIfAbsent(
				Objects.requireNonNull( name, "Name must not be null" ),
				( k ) -> TimeLimiter.of( defaultTimeLimiterConfig ) );
	}

	public TimeLimiter timeLimiter( String name, TimeLimiterConfig customTimeLimiterConfig ) {
		return timeLimiters.computeIfAbsent(
				Objects.requireNonNull( name, "Name must not be null" ),
				( k ) -> TimeLimiter.of( customTimeLimiterConfig ) );
	}

	public TimeLimiter timeLimiter( String name,
			Supplier<TimeLimiterConfig> timeLimiterConfigSupplier ) {
		return timeLimiters.computeIfAbsent(
				Objects.requireNonNull( name, "Name must not be null" ),
				( k ) -> TimeLimiter.of( timeLimiterConfigSupplier.get() ) );
	}
}

