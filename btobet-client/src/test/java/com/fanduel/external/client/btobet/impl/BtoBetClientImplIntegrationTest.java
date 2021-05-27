package com.fanduel.external.client.btobet.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.fanduel.api.exception.APICodedException;
import com.fanduel.external.client.btobet.transfer.AuthenticateRequest;
import com.fanduel.external.client.btobet.transfer.AuthenticateResponse;
import com.fanduel.external.client.btobet.transfer.BetRequest;
import com.fanduel.external.client.btobet.transfer.BetResponse;
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

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BtoBetClientImplIntegrationTest {

	private BtoBetClientImpl client;
	
	final static String API_PASSWORD = "fanduel123!";
	private final static String API_USERNAME = "fanduel";
	private final static String INTEGRATION_TEST_AUTH_TOKEN_1 = "BtobetUser1";
	private final static String INTEGRATION_TEST_AUTH_TOKEN_2 = "BtobetUser2";
	private final static String INTEGRATION_TEST_FAILING_AUTH_TOKEN_1 = "InvalidBtobetToken1";
	private final static String INTEGRATION_TEST_FAILING_AUTH_TOKEN_2 = "InvalidBtobetToken2";
	private final static String GAME_CODE = "FanDuel";
	private final static int CLIENT_ID = 301;
	private final static long EXTERNAL_ID_1 = 12345l;
	private final static long EXTERNAL_ID_2 = 54321l;
	private final static BigDecimal BET_AMOUNT = new BigDecimal( "10.0" );
	private static final BigDecimal BET_BALANCE_1 = new BigDecimal( "1500.00" );
	private static final BigDecimal BET_BALANCE_2 = new BigDecimal( "1000.00" );
	private final static String BET_GAME_ROUND_ID = "123";
	private final static String BET_TRANSACTION_ID = "123456";
	private final static Long BET_EXTERNAL_TRANSACTION_ID = 12345678l;
	private final static String CURRENCY_CODE_1 = "BRL";
	private final static String CURRENCY_CODE_2 = "USD";
	private final static int HAPPY_PATH_STATUS_CODE = 0;
	private static final String STATUS_OK_MESSAGE = "Status OK";
	private static final String ENDPOINT_URL_KEY = "EndpointUrl";
	private static final Long EXTERNAL_TRANSACTION_ID_1 = 5877470l;
	private static final Long EXTERNAL_TRANSACTION_ID_2 = 5877471l;
	private static final String ORGINAL_PROVIDER = null;
	private static final BigDecimal WIN_AMOUNT_1 = new BigDecimal( "138.1" );
	private static final BigDecimal WIN_AMOUNT_2 = new BigDecimal( "138.1" );
	private static final BigDecimal CANCEL_AMOUNT_1 = new BigDecimal( "200.0" );
	private static final BigDecimal CANCEL_AMOUNT_2 = new BigDecimal( "300.0" );
	private static final BigDecimal REFUND_AMOUNT_1 = new BigDecimal( "200.0" );
	private static final BigDecimal REFUND_AMOUNT_2 = new BigDecimal( "300.0" );
	private static final BigDecimal WIN_BALANCE_1 = new BigDecimal( "138.1" );
	private static final BigDecimal WIN_BALANCE_2 = new BigDecimal( "138.1" );
	private static final BigDecimal CANCEL_BALANCE_1 = new BigDecimal( "200.0" );
	private static final BigDecimal CANCEL_BALANCE_2 = new BigDecimal( "300.0" );
	private static final BigDecimal REFUND_BALANCE_1 = new BigDecimal( "200.0" );
	private static final BigDecimal REFUND_BALANCE_2 = new BigDecimal( "300.0" );
	private static final String GAME_ROUND_ID = "45822468";
	private static final String GAME_SUB_ROUND_ID = null;
	private static final String TRANSACTION_ID = "69856774";
	private static final String BONUS_PROMOTION_CODE = null;

	@BeforeEach
	void before() {
		final String devstackName = System.getenv( "DEV_STACK_NAME" );
		final String apiEndpoint = String.format(
				"http://devstack-%s-api.use1.dev.us.fdbox.net:8081", devstackName );
		client = new BtoBetClientImpl( apiEndpoint, CLIENT_ID, API_USERNAME, API_PASSWORD,
				GAME_CODE );
	}

	@Test
	@Tag("wiremock")
	void generateTokenIntegration() throws IOException {
		GenerateBtoBetAuthTokenRequest request = (GenerateBtoBetAuthTokenRequest) GenerateBtoBetAuthTokenRequest
				.builder()
				.globoId( "12345" )
				.clientId( CLIENT_ID )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.build();
		GenerateBtoBetAuthTokenRequest request2 = (GenerateBtoBetAuthTokenRequest) GenerateBtoBetAuthTokenRequest
				.builder()
				.globoId( "54321" )
				.clientId( CLIENT_ID )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.build();

		GenerateBtoBetAuthTokenResponse response = client.generateAuthToken( request );
		GenerateBtoBetAuthTokenResponse response2 = client.generateAuthToken( request2 );

		assertEquals( 0, response.getStatusCode() );
		assertNull( response.getStatusMessage() );
		assertEquals( "BRL", response.getCurrency() );
		assertEquals( "1989-01-01T00:00:00", response.getBirthdate() );
		assertEquals( "BR", response.getCountry() );
		assertEquals( 12345l, response.getExternalId() );
		assertEquals( "M", response.getGender() );
		assertEquals( "PT", response.getLanguage() );
		assertEquals( "ADDRESS 1", response.getAddress() );
		assertEquals( "1234567890", response.getPhone() );
		assertEquals( "EMAIL 1", response.getEmail() );
		assertEquals( INTEGRATION_TEST_AUTH_TOKEN_1, response.getAuthToken() );

		assertEquals( 0, response2.getStatusCode() );
		assertNull( response2.getStatusMessage() );
		assertEquals( "USD", response2.getCurrency() );
		assertEquals( "1950-06-01T00:00:00", response2.getBirthdate() );
		assertEquals( "US", response2.getCountry() );
		assertEquals( 54321l, response2.getExternalId() );
		assertEquals( "F", response2.getGender() );
		assertEquals( "EN", response2.getLanguage() );
		assertEquals( "ADDRESS 2", response2.getAddress() );
		assertEquals( "0987654321", response2.getPhone() );
		assertEquals( "EMAIL 2", response2.getEmail() );
		assertEquals( INTEGRATION_TEST_AUTH_TOKEN_2, response2.getAuthToken() );
	}

	@Test
	@Tag("wiremock")
	void authenticateIntegration() throws IOException {
		AuthenticateRequest request = (AuthenticateRequest) AuthenticateRequest.builder()
				.authToken( INTEGRATION_TEST_AUTH_TOKEN_1 )
				.gameCode( GAME_CODE )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();
		AuthenticateRequest request2 = (AuthenticateRequest) AuthenticateRequest.builder()
				.authToken( INTEGRATION_TEST_AUTH_TOKEN_2 )
				.gameCode( GAME_CODE )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();

		AuthenticateResponse response = client.authenticate( request );
		AuthenticateResponse response2 = client.authenticate( request2 );

		assertEquals( 0, response.getStatusCode() );
		assertNull( response.getStatusMessage() );
		assertThat( BET_BALANCE_1, Matchers.comparesEqualTo( response.getBalance() ) );
		assertEquals( "1989-01-01T00:00:00", response.getBirthdate() );
		assertEquals( "BR", response.getCountry() );
		assertEquals( CURRENCY_CODE_1, response.getCurrency() );
		assertEquals( 12345l, response.getExternalId() );
		assertEquals( "M", response.getGender() );
		assertEquals( "PT", response.getLanguage() );
		assertEquals( "FanduelBR", response.getNickname() );
		assertEquals( "1234567890", response.getPhone() );

		assertEquals( 0, response2.getStatusCode() );
		assertNull( response2.getStatusMessage() );
		assertThat( BET_BALANCE_2, Matchers.comparesEqualTo( response2.getBalance() ) );
		assertEquals( "1950-06-01T00:00:00", response2.getBirthdate() );
		assertEquals( "US", response2.getCountry() );
		assertEquals( CURRENCY_CODE_2, response2.getCurrency() );
		assertEquals( 54321l, response2.getExternalId() );
		assertEquals( "F", response2.getGender() );
		assertEquals( "EN", response2.getLanguage() );
		assertEquals( "FanduelUS", response2.getNickname() );
		assertEquals( "0987654321", response2.getPhone() );
	}

	@Test
	@Tag("wiremock")
	void getBalanceIntegration() throws IOException {
		GetBalanceRequest request = (GetBalanceRequest) GetBalanceRequest.builder()
				.authToken( INTEGRATION_TEST_AUTH_TOKEN_1 )
				.gameCode( GAME_CODE )
				.externalId( 12345l )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();
		GetBalanceRequest request2 = (GetBalanceRequest) GetBalanceRequest.builder()
				.authToken( INTEGRATION_TEST_AUTH_TOKEN_2 )
				.gameCode( GAME_CODE )
				.externalId( 54321l )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();

		GetBalanceResponse response = client.getBalance( request );
		GetBalanceResponse response2 = client.getBalance( request2 );

		assertEquals( 0, response.getStatusCode() );
		assertNull( response.getStatusMessage() );
		assertThat( BET_BALANCE_1, Matchers.comparesEqualTo( response.getBalance() ) );

		assertEquals( 0, response2.getStatusCode() );
		assertNull( response2.getStatusMessage() );
		assertThat( BET_BALANCE_2, Matchers.comparesEqualTo( response2.getBalance() ) );
	}

	@Test
	@Tag("wiremock")
	void betIntegration() throws IOException {
		BetRequest request1 = (BetRequest) BetRequest.builder()
				.amount( BET_AMOUNT )
				.currency( CURRENCY_CODE_1 )
				.gameRoundId( BET_GAME_ROUND_ID )
				.transactionId( BET_TRANSACTION_ID )
				.authToken( INTEGRATION_TEST_AUTH_TOKEN_1 )
				.externalId( 12345l )
				.gameCode( GAME_CODE )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();
		BetRequest request2 = (BetRequest) BetRequest.builder()
				.amount( BET_AMOUNT )
				.currency( CURRENCY_CODE_1 )
				.gameRoundId( BET_GAME_ROUND_ID )
				.transactionId( BET_TRANSACTION_ID )
				.authToken( INTEGRATION_TEST_AUTH_TOKEN_2 )
				.externalId( 54321l )
				.gameCode( GAME_CODE )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();

		BetResponse response1 = client.bet( request1 );
		BetResponse response2 = client.bet( request2 );

		assertEquals( 0, response1.getStatusCode() );
		assertNull( response1.getStatusMessage() );
		assertThat( BET_BALANCE_1, Matchers.comparesEqualTo( response1.getBalance() ) );
		assertThat( BET_EXTERNAL_TRANSACTION_ID,
				Matchers.comparesEqualTo( response1.getExternalTransactionId() ) );

		assertEquals( 0, response2.getStatusCode() );
		assertNull( response2.getStatusMessage() );
		assertThat( BET_BALANCE_2, Matchers.comparesEqualTo( response2.getBalance() ) );
		assertThat( BET_EXTERNAL_TRANSACTION_ID,
				Matchers.comparesEqualTo( response2.getExternalTransactionId() ) );
	}

	@Test
	@Tag("wiremock")
	void testCancelBetIntegration() throws IOException {
		final CancelBetRequest request = (CancelBetRequest) CancelBetRequest.builder()
				.originalProvider( ORGINAL_PROVIDER )
				.amount( CANCEL_AMOUNT_1 )
				.currency( CURRENCY_CODE_1 )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.externalId( EXTERNAL_ID_1 )
				.authToken( INTEGRATION_TEST_AUTH_TOKEN_1 )
				.gameCode( GAME_CODE )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();
		final CancelBetRequest request2 = (CancelBetRequest) CancelBetRequest.builder()
				.originalProvider( ORGINAL_PROVIDER )
				.amount( CANCEL_AMOUNT_2 )
				.currency( CURRENCY_CODE_2 )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.authToken( INTEGRATION_TEST_AUTH_TOKEN_2 )
				.gameCode( GAME_CODE )
				.externalId( EXTERNAL_ID_2 )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();

		final CancelBetResponse response = client.cancelBet( request );
		final CancelBetResponse response2 = client.cancelBet( request2 );

		assertEquals( cancelBetResponse, response );
		assertEquals( cancelBetResponse2, response2 );
	}

	@Test
	@Tag("wiremock")
	void testWinIntegration() throws IOException {
		final WinRequest request = (WinRequest) WinRequest.builder()
				.gameSubRoundId( GAME_SUB_ROUND_ID )
				.bonusPromotionCode( BONUS_PROMOTION_CODE )
				.externalTransactionId( EXTERNAL_TRANSACTION_ID_1 )
				.originalProvider( ORGINAL_PROVIDER )
				.amount( WIN_AMOUNT_1 )
				.currency( CURRENCY_CODE_1 )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.authToken( INTEGRATION_TEST_AUTH_TOKEN_1 )
				.externalId( EXTERNAL_ID_1 )
				.gameCode( GAME_CODE )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();

		final WinRequest request2 = (WinRequest) WinRequest.builder()
				.gameSubRoundId( GAME_SUB_ROUND_ID )
				.bonusPromotionCode( BONUS_PROMOTION_CODE )
				.externalTransactionId( EXTERNAL_TRANSACTION_ID_2 )
				.originalProvider( ORGINAL_PROVIDER )
				.amount( WIN_AMOUNT_2 )
				.currency( CURRENCY_CODE_2 )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.authToken( INTEGRATION_TEST_AUTH_TOKEN_2 )
				.externalId( EXTERNAL_ID_2 )
				.gameCode( GAME_CODE )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();

		final WinResponse response = client.win( request );
		final WinResponse response2 = client.win( request2 );

		assertEquals( winResponse, response );
		assertEquals( winResponse2, response2 );
	}

	@Test
	@Tag("wiremock")
	void testRefundIntegration() throws IOException {
		final RefundRequest request = (RefundRequest) RefundRequest.builder()
				.originalProvider( ORGINAL_PROVIDER )
				.amount( REFUND_AMOUNT_1 )
				.currency( CURRENCY_CODE_1 )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.externalId( EXTERNAL_ID_1 )
				.authToken( INTEGRATION_TEST_AUTH_TOKEN_1 )
				.gameCode( GAME_CODE )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();
		final RefundRequest request2 = (RefundRequest) RefundRequest.builder()
				.originalProvider( ORGINAL_PROVIDER )
				.amount( REFUND_AMOUNT_2 )
				.currency( CURRENCY_CODE_2 )
				.gameRoundId( GAME_ROUND_ID )
				.transactionId( TRANSACTION_ID )
				.authToken( INTEGRATION_TEST_AUTH_TOKEN_2 )
				.gameCode( GAME_CODE )
				.externalId( EXTERNAL_ID_2 )
				.apiPassword( API_PASSWORD )
				.apiUsername( API_USERNAME )
				.clientId( CLIENT_ID )
				.build();

		final RefundResponse response = client.refund( request );
		final RefundResponse response2 = client.refund( request2 );

		assertEquals( refundResponse, response );
		assertEquals( refundResponse2, response2 );
	}

	@Test
	@Tag("wiremock")
	void testIntegreationException() {
		assertThrows( APICodedException.class,
				() -> client.generateAuthToken( generateBtoBetAuthTokenRequestException ) );
		assertThrows( APICodedException.class,
				() -> client.generateAuthToken( generateBtoBetAuthTokenRequestException2 ) );
		assertThrows( APICodedException.class,
				() -> client.authenticate( authenticateRequestException ) );
		assertThrows( APICodedException.class,
				() -> client.authenticate( authenticateRequestException2 ) );
		assertThrows( APICodedException.class,
				() -> client.getBalance( getBalanceRequestException ) );
		assertThrows( APICodedException.class,
				() -> client.getBalance( getBalanceRequestException2 ) );
		assertThrows( APICodedException.class, () -> client.bet( betRequestException ) );
		assertThrows( APICodedException.class, () -> client.bet( betRequestException2 ) );
		assertThrows( APICodedException.class,
				() -> client.cancelBet( cancelBetRequestException ) );
		assertThrows( APICodedException.class,
				() -> client.cancelBet( cancelBetRequestException2 ) );
		assertThrows( APICodedException.class, () -> client.refund( refundRequestException ) );
		assertThrows( APICodedException.class, () -> client.refund( refundRequestException2 ) );
		assertThrows( APICodedException.class, () -> client.win( winRequestException ) );
		assertThrows( APICodedException.class, () -> client.win( winRequestException2 ) );
	}

	private final CancelBetResponse cancelBetResponse = (CancelBetResponse) CancelBetResponse.builder()
			.balance( CANCEL_BALANCE_1 )
			.externalTransactionId( 5877465l )
			.statusCode( HAPPY_PATH_STATUS_CODE )
			.statusMessage( STATUS_OK_MESSAGE )
			.build();

	private final CancelBetResponse cancelBetResponse2 = (CancelBetResponse) CancelBetResponse.builder()
			.balance( CANCEL_BALANCE_2 )
			.externalTransactionId( 5877466l )
			.statusCode( HAPPY_PATH_STATUS_CODE )
			.statusMessage( STATUS_OK_MESSAGE )
			.build();

	private final WinResponse winResponse = (WinResponse) WinResponse.builder()
			.balance( WIN_BALANCE_1 )
			.externalTransactionId( 5877470l )
			.statusCode( HAPPY_PATH_STATUS_CODE )
			.build();

	private final WinResponse winResponse2 = (WinResponse) WinResponse.builder()
			.balance( WIN_BALANCE_2 )
			.externalTransactionId( 5877471l )
			.statusCode( HAPPY_PATH_STATUS_CODE )
			.build();

	private final RefundResponse refundResponse = (RefundResponse) RefundResponse.builder()
			.balance( REFUND_BALANCE_1 )
			.externalTransactionId( 5877465l )
			.statusMessage( STATUS_OK_MESSAGE )
			.statusCode( HAPPY_PATH_STATUS_CODE )
			.build();

	private final RefundResponse refundResponse2 = (RefundResponse) RefundResponse.builder()
			.balance( REFUND_BALANCE_2 )
			.externalTransactionId( 5877466l )
			.statusMessage( STATUS_OK_MESSAGE )
			.statusCode( HAPPY_PATH_STATUS_CODE )
			.build();

	private final GenerateBtoBetAuthTokenRequest generateBtoBetAuthTokenRequestException = (GenerateBtoBetAuthTokenRequest) GenerateBtoBetAuthTokenRequest
			.builder()
			.globoId( "55555" )
			.clientId( CLIENT_ID )
			.apiPassword( API_PASSWORD )
			.apiUsername( API_USERNAME )
			.build();

	private final GenerateBtoBetAuthTokenRequest generateBtoBetAuthTokenRequestException2 = (GenerateBtoBetAuthTokenRequest) GenerateBtoBetAuthTokenRequest
			.builder()
			.globoId( "44444" )
			.clientId( CLIENT_ID )
			.apiPassword( API_PASSWORD )
			.apiUsername( API_USERNAME )
			.build();

	private final AuthenticateRequest authenticateRequestException = (AuthenticateRequest) AuthenticateRequest
			.builder()
			.authToken( INTEGRATION_TEST_FAILING_AUTH_TOKEN_1 )
			.gameCode( GAME_CODE )
			.apiPassword( API_PASSWORD )
			.apiUsername( API_USERNAME )
			.clientId( CLIENT_ID )
			.build();

	private final AuthenticateRequest authenticateRequestException2 = (AuthenticateRequest) AuthenticateRequest
			.builder()
			.authToken( INTEGRATION_TEST_FAILING_AUTH_TOKEN_2 )
			.gameCode( GAME_CODE )
			.apiPassword( API_PASSWORD )
			.apiUsername( API_USERNAME )
			.clientId( CLIENT_ID )
			.build();

	private final GetBalanceRequest getBalanceRequestException = (GetBalanceRequest) GetBalanceRequest
			.builder()
			.authToken( INTEGRATION_TEST_FAILING_AUTH_TOKEN_1 )
			.gameCode( GAME_CODE )
			.externalId( 12345l )
			.apiPassword( API_PASSWORD )
			.apiUsername( API_USERNAME )
			.clientId( CLIENT_ID )
			.build();

	private final GetBalanceRequest getBalanceRequestException2 = (GetBalanceRequest) GetBalanceRequest
			.builder()
			.authToken( INTEGRATION_TEST_FAILING_AUTH_TOKEN_2 )
			.gameCode( GAME_CODE )
			.externalId( 54321l )
			.apiPassword( API_PASSWORD )
			.apiUsername( API_USERNAME )
			.clientId( CLIENT_ID )
			.build();

	private final BetRequest betRequestException = (BetRequest) BetRequest.builder()
			.amount( BET_AMOUNT )
			.currency( CURRENCY_CODE_1 )
			.gameRoundId( BET_GAME_ROUND_ID )
			.transactionId( BET_TRANSACTION_ID )
			.authToken( INTEGRATION_TEST_FAILING_AUTH_TOKEN_1 )
			.externalId( EXTERNAL_ID_1 )
			.gameCode( GAME_CODE )
			.apiPassword( API_PASSWORD )
			.apiUsername( API_USERNAME )
			.clientId( CLIENT_ID )
			.build();

	private final BetRequest betRequestException2 = (BetRequest) BetRequest.builder()
			.amount( BET_AMOUNT )
			.currency( CURRENCY_CODE_1 )
			.gameRoundId( BET_GAME_ROUND_ID )
			.transactionId( BET_TRANSACTION_ID )
			.authToken( INTEGRATION_TEST_FAILING_AUTH_TOKEN_2 )
			.externalId( EXTERNAL_ID_2 )
			.gameCode( GAME_CODE )
			.apiPassword( API_PASSWORD )
			.apiUsername( API_USERNAME )
			.clientId( CLIENT_ID )
			.build();

	private final CancelBetRequest cancelBetRequestException = (CancelBetRequest) CancelBetRequest.builder()
			.originalProvider( ORGINAL_PROVIDER )
			.amount( CANCEL_AMOUNT_1 )
			.currency( CURRENCY_CODE_1 )
			.gameRoundId( GAME_ROUND_ID )
			.transactionId( TRANSACTION_ID )
			.externalId( EXTERNAL_ID_1 )
			.authToken( INTEGRATION_TEST_FAILING_AUTH_TOKEN_1 )
			.gameCode( GAME_CODE )
			.apiPassword( API_PASSWORD )
			.apiUsername( API_USERNAME )
			.clientId( CLIENT_ID )
			.build();

	private final CancelBetRequest cancelBetRequestException2 = (CancelBetRequest) CancelBetRequest.builder()
			.originalProvider( ORGINAL_PROVIDER )
			.amount( CANCEL_AMOUNT_2 )
			.currency( CURRENCY_CODE_2 )
			.gameRoundId( GAME_ROUND_ID )
			.transactionId( TRANSACTION_ID )
			.authToken( INTEGRATION_TEST_FAILING_AUTH_TOKEN_2 )
			.gameCode( GAME_CODE )
			.externalId( EXTERNAL_ID_2 )
			.apiPassword( API_PASSWORD )
			.apiUsername( API_USERNAME )
			.clientId( CLIENT_ID )
			.build();

	private final RefundRequest refundRequestException = (RefundRequest) RefundRequest.builder()
			.originalProvider( ORGINAL_PROVIDER )
			.amount( REFUND_AMOUNT_1 )
			.currency( CURRENCY_CODE_1 )
			.gameRoundId( GAME_ROUND_ID )
			.transactionId( TRANSACTION_ID )
			.externalId( EXTERNAL_ID_1 )
			.authToken( INTEGRATION_TEST_FAILING_AUTH_TOKEN_1 )
			.gameCode( GAME_CODE )
			.apiPassword( API_PASSWORD )
			.apiUsername( API_USERNAME )
			.clientId( CLIENT_ID )
			.build();

	private final RefundRequest refundRequestException2 = (RefundRequest) RefundRequest.builder()
			.originalProvider( ORGINAL_PROVIDER )
			.amount( REFUND_AMOUNT_2 )
			.currency( CURRENCY_CODE_2 )
			.gameRoundId( GAME_ROUND_ID )
			.transactionId( TRANSACTION_ID )
			.authToken( INTEGRATION_TEST_FAILING_AUTH_TOKEN_2 )
			.gameCode( GAME_CODE )
			.externalId( EXTERNAL_ID_2 )
			.apiPassword( API_PASSWORD )
			.apiUsername( API_USERNAME )
			.clientId( CLIENT_ID )
			.build();

	private final WinRequest winRequestException = (WinRequest) WinRequest.builder()
			.gameSubRoundId( GAME_SUB_ROUND_ID )
			.bonusPromotionCode( BONUS_PROMOTION_CODE )
			.externalTransactionId( EXTERNAL_TRANSACTION_ID_1 )
			.originalProvider( ORGINAL_PROVIDER )
			.amount( WIN_AMOUNT_1 )
			.currency( CURRENCY_CODE_1 )
			.gameRoundId( GAME_ROUND_ID )
			.transactionId( TRANSACTION_ID )
			.authToken( INTEGRATION_TEST_FAILING_AUTH_TOKEN_1 )
			.externalId( EXTERNAL_ID_1 )
			.gameCode( GAME_CODE )
			.apiPassword( API_PASSWORD )
			.apiUsername( API_USERNAME )
			.clientId( CLIENT_ID )
			.build();

	private final WinRequest winRequestException2 = (WinRequest) WinRequest.builder()
			.gameSubRoundId( GAME_SUB_ROUND_ID )
			.bonusPromotionCode( BONUS_PROMOTION_CODE )
			.externalTransactionId( EXTERNAL_TRANSACTION_ID_2 )
			.originalProvider( ORGINAL_PROVIDER )
			.amount( WIN_AMOUNT_2 )
			.currency( CURRENCY_CODE_2 )
			.gameRoundId( GAME_ROUND_ID )
			.transactionId( TRANSACTION_ID )
			.authToken( INTEGRATION_TEST_FAILING_AUTH_TOKEN_2 )
			.externalId( EXTERNAL_ID_2 )
			.gameCode( GAME_CODE )
			.apiPassword( API_PASSWORD )
			.apiUsername( API_USERNAME )
			.clientId( CLIENT_ID )
			.build();

}
