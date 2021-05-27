package com.fanduel.external.client.globo;

import static java.util.Arrays.asList;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

import com.codahale.metrics.InstrumentedExecutorService;
import com.fanduel.external.client.common.res4j.ResilientAwareModule;
import com.fanduel.external.client.common.res4j.TimeLimiterRegistry;
import com.fanduel.external.client.globo.impl.GloboClientImpl;
import com.fanduel.external.client.globo.impl.ResilientGloboClientImpl;
import com.fanduel.external.client.globo.impl.URLEncoder;
import com.fanduel.runtime.config.Config;

public class GloboClientModule extends ResilientAwareModule {
	public final static String GLOBO_AUTHENTICATE = "authenticate";
	public final static String GLOBO_GET_TOKEN = "getToken";
	public final static String GLOBO_LOGOUT = "logout";
	public final static String GLOBO_AUTHORISE = "authorise";
	public final static String GLOBO_REFRESH = "refresh";
	public final static String GLOBO_VERIFY_TOKEN = "verifyToken";

	private static final String CLIENT_SECRET = "ClientSecret";
	private static final String SCOPE = "Scope";
	private static final String DEFAULT_SCOPE = "openid+globoid";
	private static final String CLIENT_ID = "ClientId";
	private static final String ISSUER = "Issuer";
	private static final String REDIRECT_URI = "RedirectUrl";

	public GloboClientModule( final Config config ) {
		super( config, asList( GLOBO_AUTHENTICATE, GLOBO_GET_TOKEN, GLOBO_AUTHORISE, GLOBO_LOGOUT,
				GLOBO_REFRESH ) );
	}

	protected void bindGloboClientModule( final String configPath ) {
		Config section = getConfig().section( configPath );
		GloboClient globoClient = createGloboClient( section );

		if ( section.getBool( RES4J_CIRCUIT_BREAKER_ENABLED, true ) ) {
			final boolean timeLimiterEnabled = section.getBool( RES4J_TIME_LIMITER_ENABLED, true );
			Executor timeLimiterExecutor = null;
			TimeLimiterRegistry timeLimiterRegistry = null;

			if ( timeLimiterEnabled ) {
				timeLimiterExecutor = createTimeLimiterExecutor( configPath, section );
				timeLimiterRegistry = createTimeLimiterRegistry( section );
			}

			CircuitBreakerRegistry circuitBreakerRegistry = createCircuitBreakerRegistry( section );

			globoClient = new ResilientGloboClientImpl( globoClient, circuitBreakerRegistry,
					timeLimiterEnabled, timeLimiterExecutor, timeLimiterRegistry );

		}
		bind( GloboClient.class ).toInstance( globoClient );
	}

	private GloboClient createGloboClient( final Config section ) {
		return new GloboClientImpl( section.get( CLIENT_ID ), section.get( CLIENT_SECRET ),
				section.get( ISSUER ), section.get( REDIRECT_URI ), section.get( SCOPE, DEFAULT_SCOPE ),
				new URLEncoder() );
	}

	private Executor createTimeLimiterExecutor( final String injectedName, final Config section ) {
		return new InstrumentedExecutorService( Executors.newFixedThreadPool(
				section.getInt( RES4J_TIME_LIMITER_EXECUTOR_POOL_SIZE,
						DEFAULT_RES4J_TIME_LIMITER_EXECUTOR_POOL_SIZE ) ), METRICS,
				name( GloboClientModule.class, injectedName, "time-limiter-executor" ) );
	}
}
