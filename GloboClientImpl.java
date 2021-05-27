package com.fanduel.external.client.globo.impl;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import static com.fanduel.external.client.common.exception.ClientErrorCode.GLOBO_CLIENT_EXECUTION_ERROR;
import static com.fanduel.external.client.common.exception.ClientErrorCode.GLOBO_URI_PARSING_ERROR;
import static com.fanduel.external.client.globo.GloboClientModule.GLOBO_AUTHENTICATE;
import static com.fanduel.external.client.globo.GloboClientModule.GLOBO_GET_TOKEN;
import static com.fanduel.external.client.globo.GloboClientModule.GLOBO_REFRESH;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequests;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.fanduel.api.exception.APICodedException;
import com.fanduel.external.client.common.TokenVerifier;
import com.fanduel.external.client.globo.GloboClient;
import com.fanduel.external.client.globo.transfer.GloboAuthenticateResponse;
import com.fanduel.external.client.globo.transfer.GloboAuthoriseResponse;
import com.fanduel.metrics.MetricRegistries;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.nimbusds.jose.JWSAlgorithm;

public class GloboClientImpl implements GloboClient {
	private static final Logger LOGGER = LoggerFactory.getLogger( GloboClientImpl.class );

	private static final MetricRegistry METRICS = MetricRegistries.getRegistry();
	private final Map<String, Timer> timers;

	private static final String TOKEN_PATH = "/protocol/openid-connect/token";
	private static final String AUTHORIZATION_PATH = "/protocol/openid-connect/auth";
	private static final String USERINFO_PATH = "/protocol/openid-connect/userinfo";
	private static final String LOGOUT_PATH = "/protocol/openid-connect/logout";
	private static final String CERT_PATH = "/protocol/openid-connect/certs";

	private static final String USER_AGENT = "CartolaExpress";
	private static final String ACCEPT_HEADER = "application/json;q=0.9";

	private static final String CLIENT_ID_PARAMETER_NAME = "client_id";
	private static final String REDIRECT_URI_PARAMETER_NAME = "redirect_uri";
	private static final String CLIENT_SECRET_PARAMETER_NAME = "client_secret";
	private static final String REFRESH_TOKEN_PARAMETER_NAME = "refresh_token";
	private static final String GRANT_TYPE_PARAMETER_NAME = "grant_type";
	private static final String CODE_PARAMETER_NAME = "code";

	private final String clientId;
	private final String clientSecret;
	private final String issuer;
	private final String tokenEndpoint;
	private final String authorizationEndpoint;
	private final String apiEndpoint;
	private final String logoutEndpoint;
	private final String certsEndpoint;
	private final String redirectUrl;
	private final String scope;
	private final String requiredClaims[];
	private final URLEncoder urlEncoder;
	private final GloboResponseTransformer globoResponseTransformer;
	private final TokenVerifier tokenVerifier;

	public GloboClientImpl( final String clientId, final String clientSecret, final String issuer,
			final String redirectUrl, String scope, final URLEncoder urlEncoder ) {
		this.timers = Stream.of( GLOBO_AUTHENTICATE, GLOBO_GET_TOKEN, GLOBO_REFRESH )
				.collect( toMap( identity(), name -> METRICS.timer(
						MetricRegistry.name( GloboClientImpl.class, name, "timer" ) ) ) );
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.issuer = issuer;
		this.tokenEndpoint = issuer + TOKEN_PATH;
		this.authorizationEndpoint = issuer + AUTHORIZATION_PATH;
		this.apiEndpoint = issuer + USERINFO_PATH;
		this.logoutEndpoint = issuer + LOGOUT_PATH;
		this.certsEndpoint = issuer + CERT_PATH;
		this.redirectUrl = redirectUrl;
		this.scope = scope;
		// TODO: this must match the scope, for now we list the claims being used
		this.requiredClaims = new String[] { "globo_id", "name", "email " };
		this.urlEncoder = urlEncoder;
		globoResponseTransformer = new GloboResponseTransformer( new ObjectMapper() );
		this.tokenVerifier = getTokenVerifier();
		String aa = "a";
		String aa = "a";
		String ab = "a";
		String ac = "a";
		String ad = "a";
		String ae = "a";
		String af = "a";
		String ag = "a";
		String ah = "a";
		String ai = "a";
		String ak = "a";
	}

	/**
	 * From COBRA-364, force OAUTH Confidential Client flow https://fanduel.atlassian.net/wiki/spaces/PP/pages/2986149361/Globo+API
	 *
	 * @param state
	 * @return
	 */
	@Override
	public GloboAuthenticateResponse authenticate( final String state ) {
		final GloboAuthenticateResponse authenticateResponse = new GloboAuthenticateResponse();
		try ( final Timer.Context ignored = timers.get( GLOBO_AUTHENTICATE ).time() ) {
			URIBuilder uri = new URIBuilder( authorizationEndpoint );
			uri.addParameter( CLIENT_ID_PARAMETER_NAME, clientId )
					.addParameter( REDIRECT_URI_PARAMETER_NAME, redirectUrl )
					.addParameter( "scope", scope )
					.addParameter( "response_type", "code" )
					.addParameter( "state", state );
			authenticateResponse.setAuthenticateUrl( uri.toString() );
			authenticateResponse.setState( state );
		} catch ( URISyntaxException uriSyntaxException ) {
			LOGGER.error( uriSyntaxException.getMessage() );
			authenticateResponse.setError( new APICodedException( GLOBO_URI_PARSING_ERROR ) );
		}
		return authenticateResponse;

	}

	@Override
	public GloboAuthoriseResponse getToken( final String code ) {
		final List<BasicNameValuePair> formValues = ImmutableList.of(
				new BasicNameValuePair( CLIENT_ID_PARAMETER_NAME, clientId ),
				new BasicNameValuePair( REDIRECT_URI_PARAMETER_NAME, redirectUrl ),
				new BasicNameValuePair( CLIENT_SECRET_PARAMETER_NAME, clientSecret ),
				new BasicNameValuePair( CODE_PARAMETER_NAME, code ),
				new BasicNameValuePair( GRANT_TYPE_PARAMETER_NAME, "authorization_code" ) );

		try ( final Timer.Context ignored = timers.get( GLOBO_GET_TOKEN ).time() ) {
			final URIBuilder uri = new URIBuilder( tokenEndpoint );
			final GloboResponse response = openUrl( uri.build(), formValues );

			return globoResponseTransformer.transform( response );
		} catch ( final URISyntaxException e ) {
			LOGGER.error( "Problem generating the request URI for code {}, {}", code, e );

			return GloboAuthoriseResponse.builder()
					.error( new APICodedException( GLOBO_URI_PARSING_ERROR ) )
					.build();
		} catch ( final APICodedException e ) {
			LOGGER.warn( "Problem getting Globo Token for code {}: {}", code, e );

			return GloboAuthoriseResponse.builder().error( e ).build();
		} catch ( final Exception e ) {
			LOGGER.warn( "Unexpected error getting Globo Token for code {}: {}", code, e );

			return GloboAuthoriseResponse.builder()
					.error( new APICodedException( GLOBO_CLIENT_EXECUTION_ERROR ) )
					.build();
		}
	}

	@Override
	public GloboAuthoriseResponse getTokenSync( final String code ) {
		final List<BasicNameValuePair> requestBody = ImmutableList.of(
				new BasicNameValuePair( CLIENT_ID_PARAMETER_NAME, this.clientId ),
				new BasicNameValuePair( CLIENT_SECRET_PARAMETER_NAME, this.clientSecret ),
				new BasicNameValuePair( REDIRECT_URI_PARAMETER_NAME, this.redirectUrl ),
				new BasicNameValuePair( CODE_PARAMETER_NAME, code ),
				new BasicNameValuePair( GRANT_TYPE_PARAMETER_NAME, "authorization_code" ) );

		try ( final Timer.Context ignored = timers.get( GLOBO_GET_TOKEN ).time() ) {
			final URIBuilder uri = new URIBuilder( tokenEndpoint );
			final GloboResponse response = openUrlSync( uri.build(), requestBody );

			return globoResponseTransformer.transform( response );
		} catch ( final URISyntaxException e ) {
			LOGGER.error( "Problem generating the request URI for code {}, {}", code, e );

			return GloboAuthoriseResponse.builder()
					.error( new APICodedException( GLOBO_URI_PARSING_ERROR ) )
					.build();
		} catch ( final APICodedException e ) {
			LOGGER.warn( "Problem getting Globo Token for code {}: {}", code, e );

			return GloboAuthoriseResponse.builder().error( e ).build();
		} catch ( final Exception e ) {
			LOGGER.warn( "Unexpected error getting Globo Token for code {}: {}", code, e );

			return GloboAuthoriseResponse.builder()
					.error( new APICodedException( GLOBO_CLIENT_EXECUTION_ERROR ) )
					.build();
		}
	}

	@Override
	public GloboAuthoriseResponse refresh( final String refreshToken ) {
		final List<BasicNameValuePair> formValues = ImmutableList.of(
				new BasicNameValuePair( CLIENT_ID_PARAMETER_NAME, clientId ),
				new BasicNameValuePair( CLIENT_SECRET_PARAMETER_NAME, clientSecret ),
				new BasicNameValuePair( REFRESH_TOKEN_PARAMETER_NAME, refreshToken ),
				new BasicNameValuePair( GRANT_TYPE_PARAMETER_NAME, "refresh_token" ) );

		try ( final Timer.Context ignored = timers.get( GLOBO_REFRESH ).time() ) {
			final URIBuilder uri = new URIBuilder( tokenEndpoint );
			final GloboResponse response = openUrl( uri.build(), formValues );

			return globoResponseTransformer.transform( response );
		} catch ( final URISyntaxException e ) {
			LOGGER.error( "Problem generating the request URI for refreshToken {}, {}",
					refreshToken, e );

			return GloboAuthoriseResponse.builder()
					.error( new APICodedException( GLOBO_URI_PARSING_ERROR ) )
					.build();
		} catch ( final APICodedException e ) {
			LOGGER.warn( "Problem getting Globo Token for refreshToken {}: {}", refreshToken, e );

			return GloboAuthoriseResponse.builder().error( e ).build();
		} catch ( final Exception e ) {
			LOGGER.warn( "Unexpected error getting Globo Token for refreshToken {}: {}",
					refreshToken, e );

			return GloboAuthoriseResponse.builder()
					.error( new APICodedException( GLOBO_CLIENT_EXECUTION_ERROR ) )
					.build();
		}
	}

	@Override
	public GloboAuthoriseResponse refreshSync( final String refreshToken ) {
		final List<BasicNameValuePair> requestBody = ImmutableList.of(
				new BasicNameValuePair( CLIENT_ID_PARAMETER_NAME, clientId ),
				new BasicNameValuePair( CLIENT_SECRET_PARAMETER_NAME, clientSecret ),
				new BasicNameValuePair( REFRESH_TOKEN_PARAMETER_NAME, refreshToken ),
				new BasicNameValuePair( GRANT_TYPE_PARAMETER_NAME, "refresh_token" ) );

		try ( final Timer.Context ignored = timers.get( GLOBO_REFRESH ).time() ) {
			final URIBuilder uri = new URIBuilder( tokenEndpoint );
			final GloboResponse response = openUrlSync( uri.build(), requestBody );

			return globoResponseTransformer.transform( response );
		} catch ( final URISyntaxException e ) {
			LOGGER.error( "Problem generating the request URI for refreshToken {}, {}",
					refreshToken, e );

			return GloboAuthoriseResponse.builder()
					.error( new APICodedException( GLOBO_URI_PARSING_ERROR ) )
					.build();
		} catch ( final APICodedException e ) {
			LOGGER.warn( "Problem getting Globo Token for refreshToken {}: {}", refreshToken, e );

			return GloboAuthoriseResponse.builder().error( e ).build();
		} catch ( final Exception e ) {
			LOGGER.warn( "Unexpected error getting Globo Token for refreshToken {}: {}",
					refreshToken, e );

			return GloboAuthoriseResponse.builder()
					.error( new APICodedException( GLOBO_CLIENT_EXECUTION_ERROR ) )
					.build();
		}
	}

	private GloboResponse openUrl( final URI uri, final List<BasicNameValuePair> formValues )
			throws APICodedException {
		final String encodedForm = urlEncoder.encode( formValues );
		GloboResponse globoResponse = new GloboResponse();
		try {
			final CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
			client.start();
			SimpleHttpRequest simpleHttpRequest = SimpleHttpRequests.post( uri );
			simpleHttpRequest.setHeader( "User-Agent", USER_AGENT );
			simpleHttpRequest.setHeader( "Accept", ACCEPT_HEADER );
			simpleHttpRequest.setBody( encodedForm, ContentType.APPLICATION_FORM_URLENCODED );
			Future<SimpleHttpResponse> futureResponse = client.execute( simpleHttpRequest,
					new FutureCallback<SimpleHttpResponse>() {
						@Override
						public void completed( final SimpleHttpResponse result ) { }

						@Override
						public void failed( final Exception ex ) {
							LOGGER.error( ex.getMessage() );
						}

						@Override
						public void cancelled() {}
					} );

			SimpleHttpResponse simpleHttpResponse = futureResponse.get();
			globoResponse.setCode( simpleHttpResponse.getCode() );
			globoResponse.setBody( simpleHttpResponse.getBodyText() );
		} catch ( Exception e ) {
			LOGGER.error( e.getMessage() );
			throw new APICodedException( GLOBO_CLIENT_EXECUTION_ERROR );
		}
		return globoResponse;
	}

	private GloboResponse openUrlSync( final URI uri, final List<BasicNameValuePair> formValues )
			throws APICodedException {
		GloboResponse globoResponse = new GloboResponse();
		try {
			final CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httppost = new HttpPost( uri );
			httppost.setHeader( "User-Agent", USER_AGENT );
			httppost.setHeader( "Accept", ACCEPT_HEADER );
			HttpEntity httpEntity = new UrlEncodedFormEntity( formValues );
			httppost.setEntity( httpEntity );
			CloseableHttpResponse response = client.execute( httppost );

			InputStream inputStream = response.getEntity().getContent();
			String result = new BufferedReader(
					new InputStreamReader( inputStream, StandardCharsets.UTF_8 ) ).lines()
					.collect( Collectors.joining( "\n" ) );
			globoResponse.setBody( result );
			globoResponse.setCode( response.getCode() );
		} catch ( Exception e ) {
			LOGGER.error( e.getMessage() );
			throw new APICodedException( GLOBO_CLIENT_EXECUTION_ERROR );
		}
		return globoResponse;
	}

	private TokenVerifier getTokenVerifier() {
		try {
			return new TokenVerifier( certsEndpoint, JWSAlgorithm.RS256, issuer,
					new HashSet<>( Arrays.asList( requiredClaims ) ) );
		} catch ( MalformedURLException e ) {
			LOGGER.error( "Failed to create Token " + e.getMessage() );
			return null; // will cause NPE later
		}
	}

	@Override
	public boolean verifyToken( String jwtToken ) {
		return tokenVerifier.verifyTokenSignature( jwtToken );
	}

}
