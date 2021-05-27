package com.fanduel.external.client.globo.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

import com.codahale.metrics.InstrumentedExecutorService;
import com.codahale.metrics.MetricRegistry;
import com.fanduel.api.exception.APICodedException;
import com.fanduel.external.client.common.res4j.TimeLimiterRegistry;
import com.fanduel.external.client.globo.GloboClient;
import com.fanduel.external.client.globo.transfer.GloboAuthenticateResponse;
import com.fanduel.external.client.globo.transfer.GloboAuthoriseResponse;
import com.fanduel.metrics.MetricRegistries;

public class ResilientGloboClientImplTest {

	static final MetricRegistry METRICS = MetricRegistries.getRegistry();
	final String devstackName = System.getenv( "DEV_STACK_NAME" );
	final String issuer = String.format( "http://devstack-%s-api.use1.dev.us.fdbox.net:8081",
			devstackName );
	final String fakeUrl = "http://fakeurl.com/sessions/cartola";
	final String clientId = "anything";
	final String clientSecret = "anything";
	final String scope = "openid";

	GloboClient globoClient = new GloboClientImpl( clientId, clientSecret, issuer, fakeUrl, scope,
			new URLEncoder() );
	final CircuitBreakerConfig defaultCB = CircuitBreakerConfig.ofDefaults();
	final CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of( defaultCB );

	Executor timeLimitedExecutor = new InstrumentedExecutorService(
			Executors.newFixedThreadPool( 1 ), METRICS, "wiremock-test" );
	final TimeLimiterConfig defaultTL = TimeLimiterConfig.ofDefaults();
	final TimeLimiterRegistry timeLimiterRegistry = TimeLimiterRegistry.of( defaultTL );

	GloboClient resilientClient = new ResilientGloboClientImpl( globoClient, circuitBreakerRegistry,
			true, timeLimitedExecutor, timeLimiterRegistry );

	@Test
	@Tag("wiremock")
	public void authenticate() {
		GloboAuthenticateResponse globoAuthenticateResponse = resilientClient.authenticate(
				"state" );
		assertNotNull( globoAuthenticateResponse );
	}

	@Test
	@Tag("wiremock")
	public void getToken() {
		GloboAuthoriseResponse globoAuthoriseResponse = resilientClient.getToken( "anything" );
		assertNotNull( globoAuthoriseResponse );
		assertNotNull( globoAuthoriseResponse.getGloboUser() );
		assertNotNull( globoAuthoriseResponse.getGloboUser().getGloboId() );
	}

	@Test
	public void verifyTokenTest() {
		try {
			resilientClient.verifyToken( "123" );
			//		assertThrows( APICodedException.class, () -> {resilientClient.verifyToken( "123" );} );
		} catch (Throwable e)
		{
		}

	}

}
