package com.fanduel.external.client.globo.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static com.fanduel.external.client.common.exception.ClientErrorCode.GLOBO_CLIENT_REQUEST_ERROR;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequests;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fanduel.api.exception.APICodedException;
import com.fanduel.external.client.globo.GloboClient;
import com.fanduel.external.client.globo.transfer.GloboAuthenticateResponse;
import com.fanduel.external.client.globo.transfer.GloboAuthoriseResponse;

public class GloboClientImplTest {

	final String devstackName = System.getenv( "DEV_STACK_NAME" );
	final String issuer = String.format( "http://devstack-%s-api.use1.dev.us.fdbox.net:8081",
			devstackName );
	final String fakeUrl = "http://fakeurl.com/sessions/cartola";
	final String clientId = "anything";
	final String clientSecret = "anything";
	final String scope = "openid";
	private GloboClient globoClient;

	@BeforeEach
	public void before() {
		globoClient = new GloboClientImpl( clientId, clientSecret, issuer, fakeUrl, scope,
				new URLEncoder() );
	}

	@Test
	@Tag("wiremock")
	public void authenticate() throws ExecutionException, InterruptedException {
		GloboAuthenticateResponse globoAuthenticateResponse = globoClient.authenticate( "state" );
		final CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
		client.start();
		SimpleHttpRequest simpleHttpRequest = SimpleHttpRequests.get(
				globoAuthenticateResponse.getAuthenticateUrl() );
		Future<SimpleHttpResponse> futureResponse = client.execute( simpleHttpRequest,
				new FutureCallback<SimpleHttpResponse>() {
					@Override
					public void completed( final SimpleHttpResponse result ) { }

					@Override
					public void failed( final Exception ex ) {}

					@Override
					public void cancelled() {}
				} );
		SimpleHttpResponse simpleHttpResponse = futureResponse.get();
		assertEquals( 200, simpleHttpResponse.getCode() );
	}

	@Test
	@Tag("wiremock")
	public void getToken() {
		GloboAuthoriseResponse globoAuthoriseResponse = globoClient.getToken( "anything" );
		assertNotNull( globoAuthoriseResponse );
		assertNotNull( globoAuthoriseResponse.getGloboUser() );
		assertNotNull( globoAuthoriseResponse.getGloboUser().getGloboId() );
	}

	@Test
	@Tag("wiremock")
	public void getToken_Failure() {
		GloboAuthoriseResponse globoAuthoriseResponse = globoClient.getToken( "failme" );
		assertEquals( globoAuthoriseResponse.getError().getErrorCode(),
				GLOBO_CLIENT_REQUEST_ERROR );
		assertNull( globoAuthoriseResponse.getGloboUser() );
	}

	@Test
	@Tag("wiremock")
	public void authenticate_givenValidState_shouldGenerateAConfidentialStyleUrl() {
		String state = "CAFEBABE";
		GloboAuthenticateResponse r = globoClient.authenticate( state );

		assertNotNull( r );
		String url = r.getAuthenticateUrl();

		// must be present for confidential-style authentication
		assertTrue( url.contains( "response_type=code" ) );
		assertTrue( url.contains( "state=" + state ) );
		assertTrue( url.contains( "scope=" + scope ) );

		// must be present for public-style authentication
		assertFalse( url.contains( "code_challenge=" ) );
		assertFalse( url.contains( "code_challenge_method=" ) );
	}

	@Test
	public void veryfyTokenTest() {
//		GloboClient globoClient = new GloboClientImpl( "", "", "", "", "", new URLEncoder() );
		assertThrows( APICodedException.class, () -> {globoClient.verifyToken( "123" );} );

	}
	@Test
	public void getTokenVerifierTest() throws Exception
	{
		for( Method m : globoClient.getClass().getDeclaredMethods())
		{
			if ("getTokenVerifier".equals(m.getName()))
			{
				m.setAccessible( true );
				Object t = m.invoke(globoClient,(Object[]) null);
			}
		}

	}
}

