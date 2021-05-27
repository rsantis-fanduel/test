package com.fanduel.external.client.globo.impl;

import static com.fanduel.external.client.globo.GloboClientModule.GLOBO_AUTHENTICATE;
import static com.fanduel.external.client.globo.GloboClientModule.GLOBO_GET_TOKEN;
import static com.fanduel.external.client.globo.GloboClientModule.GLOBO_REFRESH;
import static com.fanduel.external.client.globo.GloboClientModule.GLOBO_VERIFY_TOKEN;

import java.util.concurrent.Executor;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

import com.fanduel.external.client.common.res4j.ResilientClient;
import com.fanduel.external.client.common.res4j.TimeLimiterRegistry;
import com.fanduel.external.client.globo.GloboClient;
import com.fanduel.external.client.globo.transfer.GloboAuthenticateResponse;
import com.fanduel.external.client.globo.transfer.GloboAuthoriseResponse;

public class ResilientGloboClientImpl extends ResilientClient implements GloboClient {
	private final GloboClient globoClient;

	public ResilientGloboClientImpl( final GloboClient globoClient,
			final CircuitBreakerRegistry circuitBreakerRegistry, final boolean timeLimiterEnabled,
			final Executor timeLimiterExecutor, final TimeLimiterRegistry timeLimiterRegistry

	) {
		super( timeLimiterRegistry, circuitBreakerRegistry, timeLimiterEnabled,
				timeLimiterExecutor );
		this.globoClient = globoClient;
	}

	@Override
	public GloboAuthenticateResponse authenticate( final String state ) {
		return this.callResiliently( GLOBO_AUTHENTICATE, () -> globoClient.authenticate( state ) );
	}

	@Override
	public GloboAuthoriseResponse getToken( final String code ) {
		return this.callResiliently( GLOBO_GET_TOKEN, () -> globoClient.getTokenSync( code ) );
	}

	@Override
	public GloboAuthoriseResponse refresh( final String refreshToken ) {
		return this.callResiliently( GLOBO_REFRESH, () -> globoClient.refreshSync( refreshToken ) );
	}

	@Override
	public GloboAuthoriseResponse getTokenSync( final String code ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GloboAuthoriseResponse refreshSync( final String refreshToken ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean verifyToken( final String jwtToken ) {
		return this.callResiliently( GLOBO_VERIFY_TOKEN,
				() -> globoClient.verifyToken( jwtToken ) );

	}

}
