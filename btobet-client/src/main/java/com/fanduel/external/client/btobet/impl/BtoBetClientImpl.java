package com.fanduel.external.client.btobet.impl;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import static com.fanduel.api.ApiPreconditions.notNull;
import static com.fanduel.api.ApiPreconditions.notNullOrEmpty;
import static com.fanduel.external.client.btobet.BtoBetClientModule.API_AUTHENTICATE;
import static com.fanduel.external.client.btobet.BtoBetClientModule.API_BET;
import static com.fanduel.external.client.btobet.BtoBetClientModule.API_CANCEL_BET;
import static com.fanduel.external.client.btobet.BtoBetClientModule.API_GENERATE_TOKEN;
import static com.fanduel.external.client.btobet.BtoBetClientModule.API_GET_BALANCE;
import static com.fanduel.external.client.btobet.BtoBetClientModule.API_REFUND;
import static com.fanduel.external.client.btobet.BtoBetClientModule.API_WIN;
import static com.fanduel.external.client.btobet.impl.exception.BtoBetClientExceptionHelper.mapException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.fanduel.api.exception.APICodedException;
import com.fanduel.external.client.btobet.BtoBetClient;
import com.fanduel.external.client.btobet.transfer.AuthenticateRequest;
import com.fanduel.external.client.btobet.transfer.AuthenticateResponse;
import com.fanduel.external.client.btobet.transfer.BetRequest;
import com.fanduel.external.client.btobet.transfer.BetResponse;
import com.fanduel.external.client.btobet.transfer.BtoBetRequestGeneral;
import com.fanduel.external.client.btobet.transfer.BtoBetRequestWrapper;
import com.fanduel.external.client.btobet.transfer.BtoBetResponse;
import com.fanduel.external.client.btobet.transfer.CancelBetRequest;
import com.fanduel.external.client.btobet.transfer.CancelBetResponse;
import com.fanduel.external.client.btobet.transfer.GenerateBtoBetAuthTokenRequest;
import com.fanduel.external.client.btobet.transfer.GenerateBtoBetAuthTokenResponse;
import com.fanduel.external.client.btobet.transfer.GetBalanceRequest;
import com.fanduel.external.client.btobet.transfer.GetBalanceResponse;
import com.fanduel.external.client.btobet.transfer.RefundRequest;
import com.fanduel.external.client.btobet.transfer.RefundResponse;
import com.fanduel.external.client.btobet.transfer.WinRequest;
import com.fanduel.external.client.btobet.transfer.WinResponse;
import com.fanduel.external.client.common.exception.ClientErrorCode;
import com.fanduel.metrics.MetricRegistries;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;

public class BtoBetClientImpl implements BtoBetClient {
	private static final String EXCEPTION_MESSAGE = "msg=Unexpected encountered error in making btobet client call {} exception={} exceptionMessage={}, request={}";
	private static final Logger LOGGER = LoggerFactory.getLogger( BtoBetClientImpl.class );
	private static final MetricRegistry METRICS = MetricRegistries.getRegistry();
	private static final Map<String, Timer> TIMERS = Stream.of( API_AUTHENTICATE,
			API_GENERATE_TOKEN, API_GET_BALANCE, API_BET, API_CANCEL_BET, API_REFUND, API_WIN )
			.collect( toMap( identity(), name -> METRICS.timer(
					MetricRegistry.name( BtoBetClientImpl.class, name, "timer" ) ) ) );
	private static final String POST = "POST";

	private final ObjectMapper objectMapper;
	private final String endpointUrl;
	private final int clientId;
	private final String apiUsername;
	private final String apiPassword;
	private final String gameCode;

	public BtoBetClientImpl( final String endpointUrl, final int clientId, final String apiUsername,
			final String apiPassword, final String gameCode ) {
		this.objectMapper = new ObjectMapper();
		this.endpointUrl = endpointUrl;
		this.clientId = clientId;
		this.apiUsername = apiUsername;
		this.apiPassword = apiPassword;
		this.gameCode = gameCode;
	}

	@Override
	public GenerateBtoBetAuthTokenResponse generateAuthToken(
			final GenerateBtoBetAuthTokenRequest request ) throws IOException {

		request.setApiUsername( this.apiUsername );
		request.setApiPassword( this.apiPassword );
		request.setClientId( this.clientId );

		final BtoBetRequestWrapper<GenerateBtoBetAuthTokenRequest> fullRequest = new BtoBetRequestWrapper<>(
				request );

		try ( Timer.Context ignored = TIMERS.get( API_GENERATE_TOKEN ).time() ) {
			final GenerateBtoBetAuthTokenResponse response = executeBtobetRESTRequest(
					API_GENERATE_TOKEN, fullRequest, GenerateBtoBetAuthTokenResponse.class );
			final int statusCode = response.getStatusCode();
			if ( statusCode != 0 ) {
				throw mapException( statusCode, API_GENERATE_TOKEN, fullRequest.toString() );
			}

			return response;

		} catch ( APICodedException ex ) {
			throw ex;
		} catch ( Exception ex ) {
			// TODO: Improve test coverage in this block (COBRA-366)
			LOGGER.error( EXCEPTION_MESSAGE, API_GENERATE_TOKEN, ex, ex.getMessage(), fullRequest );
			throw ex;
		}
	}

	@Override
	public AuthenticateResponse authenticate( final AuthenticateRequest request )
			throws IOException {

		notNullOrEmpty( "authToken", request.getAuthToken() );

		request.setApiUsername( this.apiUsername );
		request.setApiPassword( this.apiPassword );
		request.setClientId( this.clientId );
		request.setGameCode( this.gameCode );

		final BtoBetRequestWrapper<AuthenticateRequest> fullRequest = new BtoBetRequestWrapper<>(
				request );

		try ( Timer.Context ignored = TIMERS.get( API_AUTHENTICATE ).time() ) {

			AuthenticateResponse response = executeBtobetRESTRequest( API_AUTHENTICATE, fullRequest,
					AuthenticateResponse.class );
			final int statusCode = response.getStatusCode();
			if ( statusCode != 0 ) {
				throw mapException( statusCode, API_AUTHENTICATE, fullRequest.toString() );
			}
			return response;

		} catch ( APICodedException ex ) {
			throw ex;
		} catch ( Exception ex ) {
			// TODO: Improve test coverage in this block (COBRA-366)
			LOGGER.error( EXCEPTION_MESSAGE, API_AUTHENTICATE, ex, ex.getMessage(), fullRequest );
			throw ex;
		}
	}

	@Override
	public GetBalanceResponse getBalance( final GetBalanceRequest request ) throws IOException {

		notNullOrEmpty( "authToken", request.getAuthToken() );

		request.setApiUsername( this.apiUsername );
		request.setApiPassword( this.apiPassword );
		request.setClientId( this.clientId );
		request.setGameCode( this.gameCode );

		final BtoBetRequestWrapper<GetBalanceRequest> fullRequest = new BtoBetRequestWrapper<>(
				request );

		try ( Timer.Context ignored = TIMERS.get( API_GET_BALANCE ).time() ) {
			GetBalanceResponse response = executeBtobetRESTRequest( API_GET_BALANCE, fullRequest,
					GetBalanceResponse.class );
			final int statusCode = response.getStatusCode();
			if ( statusCode != 0 ) {
				throw mapException( statusCode, API_GET_BALANCE, fullRequest.toString() );
			}

			return response;

		} catch ( APICodedException ex ) {
			throw ex;
		} catch ( Exception ex ) {
			// TODO: Improve test coverage in this block (COBRA-366)
			LOGGER.error( EXCEPTION_MESSAGE, API_GET_BALANCE, ex, ex.getMessage(), fullRequest );
			throw ex;
		}
	}

	@Override
	public BetResponse bet( final BetRequest request ) throws IOException {

		notNullOrEmpty( "authToken", request.getAuthToken() );
		notNullOrEmpty( "currency", request.getCurrency() );
		notNullOrEmpty( "gameRoundId", request.getGameRoundId() );
		notNull( "amount", request.getAmount() );
		notNullOrEmpty( "transactionId", request.getTransactionId() );

		request.setApiUsername( this.apiUsername );
		request.setApiPassword( this.apiPassword );
		request.setClientId( this.clientId );
		request.setGameCode( this.gameCode );

		final BtoBetRequestWrapper<BetRequest> fullRequest = new BtoBetRequestWrapper<>( request );

		try ( final Timer.Context ignored = TIMERS.get( API_BET ).time() ) {
			final BetResponse response = executeBtobetRESTRequest( API_BET, fullRequest,
					BetResponse.class );
			final int statusCode = response.getStatusCode();
			if ( statusCode != 0 ) {
				throw mapException( statusCode, API_BET, fullRequest.toString() );
			}

			return response;

		}
	}

	@Override
	public CancelBetResponse cancelBet( final CancelBetRequest request ) throws IOException {
		notNullOrEmpty( "authToken", request.getAuthToken() );
		notNullOrEmpty( "currency", request.getCurrency() );
		notNullOrEmpty( "gameRoundId", request.getGameRoundId() );
		notNull( "amount", request.getAmount() );
		notNullOrEmpty( "transactionId", request.getTransactionId() );

		request.setApiUsername( this.apiUsername );
		request.setApiPassword( this.apiPassword );
		request.setClientId( this.clientId );
		request.setGameCode( this.gameCode );

		final BtoBetRequestWrapper<CancelBetRequest> fullRequest = new BtoBetRequestWrapper<>(
				request );

		try ( final Timer.Context ignored = TIMERS.get( API_CANCEL_BET ).time() ) {
			final CancelBetResponse response = executeBtobetRESTRequest( API_CANCEL_BET,
					fullRequest, CancelBetResponse.class );
			if ( response.getStatusCode() != 0 ) {
				throw mapException( response.getStatusCode(), API_CANCEL_BET,
						fullRequest.toString() );
			}
			return response;

		} catch ( APICodedException ex ) {
			throw ex;
		} catch ( Exception ex ) {
			// TODO: Improve test coverage in this block (COBRA-366)
			LOGGER.warn( EXCEPTION_MESSAGE, API_CANCEL_BET, ex, ex.getMessage(), fullRequest );
			throw ex;
		}
	}

	@Override
	public RefundResponse refund( final RefundRequest request ) throws IOException {
		notNullOrEmpty( "authToken", request.getAuthToken() );
		notNullOrEmpty( "gameRoundId", request.getGameRoundId() );
		notNullOrEmpty( "transactionId", request.getTransactionId() );

		request.setApiUsername( this.apiUsername );
		request.setApiPassword( this.apiPassword );
		request.setClientId( this.clientId );
		request.setGameCode( this.gameCode );

		final BtoBetRequestWrapper<RefundRequest> fullRequest = new BtoBetRequestWrapper<>(
				request );

		try ( final Timer.Context ignored = TIMERS.get( API_REFUND ).time() ) {
			final RefundResponse response = executeBtobetRESTRequest( API_REFUND, fullRequest,
					RefundResponse.class );
			final int statusCode = response.getStatusCode();
			if ( statusCode != 0 ) {
				throw mapException( statusCode, API_REFUND, fullRequest.toString() );
			}
			return response;
		} catch ( APICodedException e ) {
			throw e;
		} catch ( Exception e ) {
			// TODO: Improve test coverage in this block (COBRA-366)
			LOGGER.error( EXCEPTION_MESSAGE, API_REFUND, e, e.getMessage(), fullRequest );
			throw e;
		}
	}

	@Override
	public WinResponse win( final WinRequest request ) throws IOException {
		notNullOrEmpty( "authToken", request.getAuthToken() );
		notNullOrEmpty( "currency", request.getCurrency() );
		notNullOrEmpty( "gameRoundId", request.getGameRoundId() );
		notNull( "amount", request.getAmount() );
		notNullOrEmpty( "transactionId", request.getTransactionId() );

		request.setApiUsername( this.apiUsername );
		request.setApiPassword( this.apiPassword );
		request.setClientId( this.clientId );
		request.setGameCode( this.gameCode );

		final BtoBetRequestWrapper<WinRequest> fullRequest = new BtoBetRequestWrapper<>( request );

		try ( final Timer.Context ignored = TIMERS.get( API_WIN ).time() ) {
			final WinResponse response = executeBtobetRESTRequest( API_WIN, fullRequest,
					WinResponse.class );
			if ( response.getStatusCode() != 0 ) {
				throw mapException( response.getStatusCode(), API_WIN, fullRequest.toString() );
			}

			return response;

		} catch ( APICodedException ex ) {
			throw ex;
		} catch ( Exception ex ) {
			LOGGER.error( EXCEPTION_MESSAGE, API_WIN, ex, ex.getMessage(), fullRequest );
			throw ex;
		}
	}

	@VisibleForTesting
	public <T extends BtoBetResponse> T executeBtobetRESTRequest( final String endpoint,
			final BtoBetRequestWrapper<? extends BtoBetRequestGeneral> request,
			final Class<T> classToConvert ) throws IOException {
		final String url = this.endpointUrl + "/" + endpoint;
		final String body = this.objectMapper.writeValueAsString( request );
		final String serializedResponse = executeHTTPRequest( POST, new URL( url ), body );

		try {
			return this.objectMapper.readValue( serializedResponse, classToConvert );
		} catch ( final Exception e ) {
			LOGGER.error( "msg='Failure to generate response object' message='{}'",
					e.getMessage() );
			throw e;
		}
	}

	@VisibleForTesting
	public String executeHTTPRequest( final String method, final URL url, final String body )
			throws IOException {
		LOGGER.debug( "Sending request: {} {} with {}", method, url, body );

		final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod( method );
		connection.setRequestProperty( "Content-Type", "application/json" );
		connection.setDoOutput( true );

		final OutputStream os = connection.getOutputStream();
		os.write( body.getBytes() );
		os.flush();
		os.close();

		int responseCode = connection.getResponseCode();

		if ( responseCode == 200 ) {
			BufferedReader in = new BufferedReader(
					new InputStreamReader( connection.getInputStream() ) );
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ( ( inputLine = in.readLine() ) != null ) {
				response.append( inputLine );
			}
			in.close();

			/* The responseString contains the response, wrapped around another object.
				The next two lines remove that outer object.
			 */
			// TODO - COBRA-249
			String responseString = response.toString();
			LOGGER.debug( "Got response: {}", responseString );

			String conversionString = responseString.substring(
					response.toString().indexOf( ":" ) + 1, responseString.length() - 1 );

			return conversionString;

		} else {
			LOGGER.warn( "msg=getResponse error httpmethod={} url={} body={}", method, url, body );

			throw new APICodedException( ClientErrorCode.BTOBET_CLIENT_GENERIC_ERROR );
		}
	}

}
