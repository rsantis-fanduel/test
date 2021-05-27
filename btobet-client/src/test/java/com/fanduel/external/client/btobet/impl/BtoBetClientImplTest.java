package com.fanduel.external.client.btobet.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.fanduel.api.exception.APICodedException;
import com.fanduel.external.client.btobet.transfer.AuthenticateRequest;
import com.fanduel.external.client.btobet.transfer.AuthenticateResponse;
import com.fanduel.external.client.btobet.transfer.BetRequest;
import com.fanduel.external.client.btobet.transfer.BetResponse;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BtoBetClientImplTest {

	private static final String ENDPOINT_URL = "http://some.btobet.endpoint/";
	private static final int CLIENT_ID = 301;
	private static final String API_USERNAME = "fanduel";
	private static final String API_PASSWORD = "fanduel123!";
	private static final String AUTH_TOKEN = "authtoken";
	private static final String INTEGRATION_TEST_AUTH_TOKEN_2 = "BtobetUser2";
	private static final String GAME_CODE = "FanDuel";
	private static final long ERROR_EXTERNAL_ID = 0l;
	private static final long EXTERNAL_ID_1 = 12345l;
	private static final String NULL_VALUE = null;
	private static final BigDecimal BET_AMOUNT = new BigDecimal( "10.0" );
	private static final BigDecimal BET_BALANCE_2 = new BigDecimal( "1000.00" );
	private static final String BET_GAME_ROUND_ID = "123";
	private static final String BET_TRANSACTION_ID = "123456";
	private static final Long BET_EXTERNAL_TRANSACTION_ID = 12345678l;
	private static final String GLOBO_ID = "12345";
	private static final String CURRENCY_CODE_1 = "BRL";
	private static final int HAPPY_PATH_STATUS_CODE = 0;
	private static final int SYSTEM_ERROR_STATUS_CODE = 5;
	private static final String FAIL_STATUS_MESSAGE = "System Error. Contact Casino Operatior For" + " Help";
	private static final Long EXTERNAL_TRANSACTION_ID_1 = 5877470l;
	private static final String ORGINAL_PROVIDER = null;
	private static final BigDecimal WIN_AMOUNT_1 = new BigDecimal( "138.1" );
	private static final BigDecimal CANCEL_AMOUNT_1 = new BigDecimal( "200.0" );
	private static final BigDecimal REFUND_AMOUNT_1 = new BigDecimal( "200.0" );
	private static final BigDecimal CANCEL_BALANCE_1 = new BigDecimal( "200.0" );
	private static final BigDecimal REFUND_BALANCE_1 = new BigDecimal( "200.0" );
	private static final String GAME_ROUND_ID = "45822468";
	private static final String GAME_SUB_ROUND_ID = null;
	private static final String TRANSACTION_ID = "69856774";
	private static final String BONUS_PROMOTION_CODE = null;

	private BtoBetClientImpl client;
	private BtoBetClientImpl spyImpl;

	@BeforeEach
	void before() {
		client = new BtoBetClientImpl( ENDPOINT_URL, CLIENT_ID, API_USERNAME, API_PASSWORD,
				GAME_CODE );
		spyImpl = Mockito.spy( client );
	}

	@Test
	void authenticate() throws IOException {
		// Arrange
		Mockito.doReturn( authenticateResponse() )
				.when( spyImpl )
				.executeHTTPRequest( any(), any( URL.class ), any() );

		// Act
		AuthenticateResponse response = spyImpl.authenticate( authenticateRequest() );

		// Assert
		Mockito.verify( spyImpl, Mockito.times( 1 ) ).authenticate( any() );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeBtobetRESTRequest( any(), any(), any() );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeHTTPRequest( any(), any( URL.class ), any() );
		assertEquals( HAPPY_PATH_STATUS_CODE, response.getStatusCode() );
		assertEquals( new BigDecimal( "1000.00" ), response.getBalance() );
		assertEquals( "1989-01-01T00:00:00", response.getBirthdate() );
		assertEquals( "BR", response.getCountry() );
		assertEquals( CURRENCY_CODE_1, response.getCurrency() );
		assertEquals( EXTERNAL_ID_1, response.getExternalId() );
		assertEquals( "M", response.getGender() );
		assertEquals( "ES", response.getLanguage() );
		assertEquals( "Fanduel", response.getNickname() );
	}

	@Test
	void authenticateException() throws IOException {
		// Arrange
		Mockito.doReturn( authenticateResponseError() )
				.when( spyImpl )
				.executeHTTPRequest( any(), any( URL.class ), any() );

		// Act
		assertThrows( APICodedException.class,
				() -> spyImpl.authenticate( authenticateRequest() ) );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeBtobetRESTRequest( any(), any(), any() );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeHTTPRequest( any(), any( URL.class ), any() );

	}

	@Test
	void authenticateGenericException() throws IOException {
		// Arrange
		Mockito.doThrow( new IOException() )
				.when( spyImpl )
				.executeBtobetRESTRequest( any(), any(), any() );

		// Act
		assertThrows( IOException.class, () -> spyImpl.authenticate( authenticateRequest() ) );
	}

	@Test
	void globoGenerateToken() throws IOException {
		// Arrange
		Mockito.doReturn( generateGloboTokenResponse() )
				.when( spyImpl )
				.executeHTTPRequest( any(), any( URL.class ), any() );

		// Act
		GenerateBtoBetAuthTokenResponse response = spyImpl.generateAuthToken(
				globoGenerateTokenRequest() );

		// Assert
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeBtobetRESTRequest( any(), any(), any() );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeHTTPRequest( any(), any( URL.class ), any() );
		assertEquals( HAPPY_PATH_STATUS_CODE, response.getStatusCode() );
		assertEquals( new BigDecimal( "1000.00" ), response.getBalance() );
		assertNull( response.getStatusMessage() );
		assertEquals( AUTH_TOKEN, response.getAuthToken() );
		assertEquals( EXTERNAL_ID_1, response.getExternalId() );
		assertEquals( CURRENCY_CODE_1, response.getCurrency() );
	}

	@Test
	void globoGenerateTokenException() throws IOException {
		// Arrange
		Mockito.doReturn( generateGloboTokenResponseError() )
				.when( spyImpl )
				.executeHTTPRequest( any(), any( URL.class ), any() );

		// Act
		assertThrows( APICodedException.class,
				() -> spyImpl.generateAuthToken( globoGenerateTokenRequest() ) );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeBtobetRESTRequest( any(), any(), any() );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeHTTPRequest( any(), any( URL.class ), any() );
	}

	@Test
	void globoGenerateTokenGenericException() throws IOException {
		// Arrange
		Mockito.doThrow( new IOException() )
				.when( spyImpl )
				.executeBtobetRESTRequest( any(), any(), any() );

		// Act
		assertThrows( IOException.class,
				() -> spyImpl.generateAuthToken( globoGenerateTokenRequest() ) );
	}

	@Test
	void getBalance() throws IOException {
		// Arrange
		Mockito.doReturn( getBalanceResponse() )
				.when( spyImpl )
				.executeHTTPRequest( any(), any( URL.class ), any() );

		// Act
		GetBalanceResponse response = spyImpl.getBalance( getBalanceRequest() );

		// Assert
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeBtobetRESTRequest( any(), any(), any() );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeHTTPRequest( any(), any( URL.class ), any() );
		assertEquals( HAPPY_PATH_STATUS_CODE, response.getStatusCode() );
		assertEquals( new BigDecimal( "1000.00" ), response.getBalance() );
		assertNull( response.getStatusMessage() );
	}

	@Test
	void getBalanceException() throws IOException {
		// Arrange
		Mockito.doReturn( getBalanceResponseError() )
				.when( spyImpl )
				.executeHTTPRequest( any(), any( URL.class ), any() );

		// Act
		assertThrows( APICodedException.class, () -> spyImpl.getBalance( getBalanceRequest() ) );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeBtobetRESTRequest( any(), any(), any() );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeHTTPRequest( any(), any( URL.class ), any() );
	}

	@Test
	void getBalanceGenericException() throws IOException {
		// Arrange
		Mockito.doThrow( new IOException() )
				.when( spyImpl )
				.executeBtobetRESTRequest( any(), any(), any() );

		// Act
		assertThrows( IOException.class, () -> spyImpl.getBalance( getBalanceRequest() ) );
	}

	@Test
	void testWin() throws IOException {
		// Arrange
		Mockito.doReturn( winResponse() )
				.when( spyImpl )
				.executeHTTPRequest( any(), any( URL.class ), any() );

		// Act
		WinResponse response = spyImpl.win( winRequest() );

		// Assert
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeBtobetRESTRequest( any(), any(), any() );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeHTTPRequest( any(), any( URL.class ), any() );
		assertEquals( HAPPY_PATH_STATUS_CODE, response.getStatusCode() );
		assertNull( response.getStatusMessage() );
		assertEquals( WIN_AMOUNT_1, response.getBalance() );
		assertEquals( EXTERNAL_TRANSACTION_ID_1, response.getExternalTransactionId() );
		assertNull( response.getStatusMessage() );
	}

	@Test
	void testWinAPICodedException() throws IOException {
		// Arrange
		Mockito.doReturn( winResponseError() )
				.when( spyImpl )
				.executeHTTPRequest( any(), any( URL.class ), any() );

		// Act
		assertThrows( APICodedException.class, () -> spyImpl.win( winRequest() ) );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeBtobetRESTRequest( any(), any(), any() );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeHTTPRequest( any(), any( URL.class ), any() );
	}

	@Test
	void testWinGenericException() throws IOException {
		// Arrange
		Mockito.doThrow( new IOException() )
				.when( spyImpl )
				.executeBtobetRESTRequest( any(), any(), any() );

		// Act
		assertThrows( IOException.class, () -> spyImpl.win( winRequest() ) );
	}

	@Test
	void testCancelBet() throws IOException {
		// Arrange
		Mockito.doReturn( cancelBetResponse() )
				.when( spyImpl )
				.executeHTTPRequest( any(), any( URL.class ), any() );

		// Act
		final CancelBetResponse response = spyImpl.cancelBet( cancelBetRequest() );

		// Assert
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeBtobetRESTRequest( any(), any(), any() );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeHTTPRequest( any(), any( URL.class ), any() );
		assertEquals( HAPPY_PATH_STATUS_CODE, response.getStatusCode() );
		assertNull( response.getStatusMessage() );
		assertEquals( CANCEL_AMOUNT_1, response.getBalance() );
		assertEquals( EXTERNAL_TRANSACTION_ID_1, response.getExternalTransactionId() );
		assertNull( response.getStatusMessage() );
	}

	@Test
	void testCancelBetException() throws IOException {
		// Arrange
		Mockito.doReturn( cancelBetResponseError() )
				.when( spyImpl )
				.executeHTTPRequest( any(), any( URL.class ), any() );

		// Act
		assertThrows( APICodedException.class, () -> spyImpl.cancelBet( cancelBetRequest() ) );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeBtobetRESTRequest( any(), any(), any() );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeHTTPRequest( any(), any( URL.class ), any() );
	}

	@Test
	void testCancelBetGenericException() throws IOException {
		// Arrange
		Mockito.doThrow( new IOException() )
				.when( spyImpl )
				.executeBtobetRESTRequest( any(), any(), any() );

		// Act
		assertThrows( IOException.class, () -> spyImpl.cancelBet( cancelBetRequest() ) );
	}

	@Test
	void testBet() throws IOException {
		// Arrange
		Mockito.doReturn( betResponse() )
				.when( spyImpl )
				.executeHTTPRequest( any(), any( URL.class ), any() );

		// Act
		final BetRequest betRequest = betRequest();
		final BetResponse response = spyImpl.bet( betRequest );

		// Assert
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeBtobetRESTRequest( any(), any(), any() );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeHTTPRequest( any(), any( URL.class ), any() );
		assertEquals( HAPPY_PATH_STATUS_CODE, response.getStatusCode() );
		assertNull( response.getStatusMessage() );
		assertEquals( BET_EXTERNAL_TRANSACTION_ID, response.getExternalTransactionId() );
		assertEquals( BET_BALANCE_2, response.getBalance() );
	}

	@Test
	void testBetException() throws IOException {
		// Arrange
		Mockito.doReturn( betResponseError() )
				.when( spyImpl )
				.executeHTTPRequest( any(), any( URL.class ), any() );

		// Act
		assertThrows( APICodedException.class, () -> spyImpl.bet( betRequest() ) );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeBtobetRESTRequest( any(), any(), any() );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeHTTPRequest( any(), any( URL.class ), any() );
	}

	@Test
	void testBetGenericException() throws IOException {
		// Arrange
		Mockito.doThrow( new IOException() )
				.when( spyImpl )
				.executeBtobetRESTRequest( any(), any(), any() );

		// Act
		assertThrows( IOException.class, () -> spyImpl.bet( betRequest() ) );
	}

	@Test
	void testRefund() throws IOException {
		// Arrange
		Mockito.doReturn( refundResponse() )
				.when( spyImpl )
				.executeHTTPRequest( any(), any( URL.class ), any() );

		// Act
		final RefundResponse response = spyImpl.refund( refundRequest() );

		// Assert
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeBtobetRESTRequest( any(), any(), any() );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeHTTPRequest( any(), any( URL.class ), any() );
		assertEquals( HAPPY_PATH_STATUS_CODE, response.getStatusCode() );
		assertNull( response.getStatusMessage() );
		assertEquals( REFUND_AMOUNT_1, response.getBalance() );
		assertEquals( EXTERNAL_TRANSACTION_ID_1, response.getExternalTransactionId() );
		assertNull( response.getStatusMessage() );
	}

	@Test
	void testRefundException() throws IOException {
		// Arrange
		Mockito.doReturn( refundResponseError() )
				.when( spyImpl )
				.executeHTTPRequest( any(), any( URL.class ), any() );
		RefundRequest request = refundRequest();

		// Act
		assertThrows( APICodedException.class, () -> spyImpl.refund( request ) );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeBtobetRESTRequest( any(), any(), any() );
		Mockito.verify( spyImpl, Mockito.times( 1 ) )
				.executeHTTPRequest( any(), any( URL.class ), any() );
	}

	@Test
	void testRefundGenericException() throws IOException {
		// Arrange
		Mockito.doThrow( new IOException() )
				.when( spyImpl )
				.executeBtobetRESTRequest( any(), any(), any() );

		// Act
		assertThrows( IOException.class, () -> spyImpl.refund( refundRequest() ) );
	}

	private String winResponse() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString( WinResponse.builder()
				.balance( WIN_AMOUNT_1 )
				.externalTransactionId( EXTERNAL_TRANSACTION_ID_1 )
				.build() );
	}

	private String winResponseError() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString( WinResponse.builder()
				.statusCode( SYSTEM_ERROR_STATUS_CODE )
				.statusMessage( FAIL_STATUS_MESSAGE )
				.build() );
	}

	private WinRequest winRequest() {
		return (WinRequest) WinRequest.builder()
				.gameSubRoundId( GAME_SUB_ROUND_ID )
				.bonusPromotionCode( BONUS_PROMOTION_CODE )
				.externalTransactionId( EXTERNAL_TRANSACTION_ID_1 )
				.originalProvider( ORGINAL_PROVIDER )
				.amount( WIN_AMOUNT_1 )
				.currency( CURRENCY_CODE_1 )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.authToken( AUTH_TOKEN )
				.externalId( EXTERNAL_ID_1 )
				.gameCode( GAME_CODE )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();
	}

	private String cancelBetResponse() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString( CancelBetResponse.builder()
				.balance( CANCEL_BALANCE_1 )
				.externalTransactionId( EXTERNAL_TRANSACTION_ID_1 )
				.build() );
	}

	private String cancelBetResponseError() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString( CancelBetResponse.builder()
				.statusCode( SYSTEM_ERROR_STATUS_CODE )
				.statusMessage( FAIL_STATUS_MESSAGE )
				.build() );
	}

	private CancelBetRequest cancelBetRequest() {
		return (CancelBetRequest) CancelBetRequest.builder()
				.originalProvider( ORGINAL_PROVIDER )
				.amount( CANCEL_AMOUNT_1 )
				.currency( CURRENCY_CODE_1 )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.authToken( INTEGRATION_TEST_AUTH_TOKEN_2 )
				.gameCode( GAME_CODE )
				.externalId( EXTERNAL_ID_1 )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();
	}

	private AuthenticateRequest authenticateRequest() {
		return (AuthenticateRequest) AuthenticateRequest.builder()
				.authToken( AUTH_TOKEN )
				.gameCode( GAME_CODE )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();
	}

	private GenerateBtoBetAuthTokenRequest globoGenerateTokenRequest() {
		return (GenerateBtoBetAuthTokenRequest) GenerateBtoBetAuthTokenRequest.builder()
				.globoId( GLOBO_ID )
				.clientId( CLIENT_ID )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.build();
	}

	private GetBalanceRequest getBalanceRequest() {
		return (GetBalanceRequest) GetBalanceRequest.builder()
				.authToken( AUTH_TOKEN )
				.gameCode( GAME_CODE )
				.externalId( EXTERNAL_ID_1 )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();
	}

	private BetRequest betRequest() {
		return (BetRequest) BetRequest.builder()
				.gameSubRoundId( NULL_VALUE )
				.originalProvider( NULL_VALUE )
				.amount( BET_AMOUNT )
				.currency( CURRENCY_CODE_1 )
				.gameRoundId( BET_GAME_ROUND_ID )
				.transactionId( BET_TRANSACTION_ID )
				.authToken( AUTH_TOKEN )
				.externalId( EXTERNAL_ID_1 )
				.gameCode( GAME_CODE )
				.apiUsername( API_USERNAME )
				.apiPassword( API_PASSWORD )
				.clientId( CLIENT_ID )
				.build();
	}

	private RefundRequest refundRequest() {
		return (RefundRequest) RefundRequest.builder()
				.originalProvider( ORGINAL_PROVIDER )
				.amount( REFUND_AMOUNT_1 )
				.currency( CURRENCY_CODE_1 )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.authToken( INTEGRATION_TEST_AUTH_TOKEN_2 )
				.gameCode( GAME_CODE )
				.externalId( EXTERNAL_ID_1 )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();
	}

	private String authenticateResponse() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(
				AuthenticateResponse.Builder.anAuthenticateResponse()
						.statusCode( HAPPY_PATH_STATUS_CODE )
						.balance( new BigDecimal( "1000.00" ) )
						.birthdate( "1989-01-01T00:00:00" )
						.country( "BR" )
						.currency( CURRENCY_CODE_1 )
						.externalId( EXTERNAL_ID_1 )
						.gender( "M" )
						.language( "ES" )
						.nickname( "Fanduel" )
						.phone( "1234567890" )
						.build() );
	}

	private String generateGloboTokenResponse() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(
				GenerateBtoBetAuthTokenResponse.Builder.aGenerateBtobetAuthTokenResponse()
						.statusCode( HAPPY_PATH_STATUS_CODE )
						.balance( BET_BALANCE_2 )
						.authToken( AUTH_TOKEN )
						.currency( CURRENCY_CODE_1 )
						.externalId( EXTERNAL_ID_1 )
						.newCreatedUser( false )
						.build() );
	}

	private String getBalanceResponse() throws JsonProcessingException {
		BtoBetResponse.Builder builder = new GetBalanceResponse.Builder().balance( BET_BALANCE_2 )
				.statusCode( HAPPY_PATH_STATUS_CODE );
		return new ObjectMapper().writeValueAsString(
				( (GetBalanceResponse.Builder) builder ).build() );
	}

	private String betResponse() throws JsonProcessingException {
		BtoBetResponse.Builder builder = new BetResponse.Builder().externalTransactionId(
				BET_EXTERNAL_TRANSACTION_ID )
				.balance( BET_BALANCE_2 )
				.statusCode( HAPPY_PATH_STATUS_CODE );

		return new ObjectMapper().writeValueAsString( ( (BetResponse.Builder) builder ).build() );
	}

	private String refundResponse() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString( RefundResponse.builder()
				.balance( REFUND_BALANCE_1 )
				.externalTransactionId( EXTERNAL_TRANSACTION_ID_1 )
				.build() );
	}

	private String authenticateResponseError() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(
				AuthenticateResponse.Builder.anAuthenticateResponse()
						.statusCode( SYSTEM_ERROR_STATUS_CODE )
						.statusMessage( FAIL_STATUS_MESSAGE )
						.externalId( ERROR_EXTERNAL_ID )
						.build() );
	}

	private String generateGloboTokenResponseError() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(
				GenerateBtoBetAuthTokenResponse.Builder.aGenerateBtobetAuthTokenResponse()
						.statusCode( SYSTEM_ERROR_STATUS_CODE )
						.statusMessage( FAIL_STATUS_MESSAGE )
						.externalId( 0 )
						.newCreatedUser( false )
						.build() );
	}

	private String getBalanceResponseError() throws JsonProcessingException {
		final GetBalanceResponse.Builder builder = (GetBalanceResponse.Builder) GetBalanceResponse.builder()
				.statusCode( SYSTEM_ERROR_STATUS_CODE )
				.statusMessage( FAIL_STATUS_MESSAGE );
		return new ObjectMapper().writeValueAsString( builder.build() );
	}

	private String betResponseError() throws JsonProcessingException {
		final BetResponse.Builder builder = (BetResponse.Builder) BetResponse.builder()
				.statusCode( SYSTEM_ERROR_STATUS_CODE )
				.statusMessage( FAIL_STATUS_MESSAGE );

		return new ObjectMapper().writeValueAsString( builder.build() );
	}

	private String refundResponseError() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString( RefundResponse.builder()
				.statusCode( SYSTEM_ERROR_STATUS_CODE )
				.statusMessage( FAIL_STATUS_MESSAGE )
				.build() );
	}
}
