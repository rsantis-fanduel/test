package com.fanduel.external.client.paddypower.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static com.fanduel.external.client.paddypower.transfer.TransferRequest.Builder.aTransferRequest;
import static com.fanduel.external.client.paddypower.transfer.TransferResponse.Builder.aTransferResponse;
import static com.fanduel.external.client.paddypower.transfer.UserSessionDetailsRequest.Builder.anUserSessionDetailsRequest;

import java.math.BigDecimal;
import java.util.Currency;

import javax.xml.ws.BindingProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.fanduel.api.exception.APIIllegalArgumentException;
import com.fanduel.external.client.paddypower.exception.PPGapException;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.CancelTransferResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.ComplexTransferResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.JurisdictionDataType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.JurisdictionType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.KeepAliveResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.PartnerAPIExceptionFault;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.PartnerAPIExceptionType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.PartnerAPIService;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.PartnerAPIServiceImplService;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.ProfileType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.RetrieveBalanceResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.RetrieveUserSessionDetailsResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.StringResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.TransferResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.UserSessionDetailsResponseType;
import com.fanduel.external.client.paddypower.transfer.TransferRequest;
import com.fanduel.external.client.paddypower.transfer.TransferResponse;
import com.fanduel.external.client.paddypower.transfer.UserSessionDetails;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PPGapClientImplTest {

	private PPGapClientImpl client;

	@Mock(extraInterfaces = BindingProvider.class)
	private PartnerAPIService port;

	@Mock
	private PartnerAPIServiceImplService service;

	@BeforeEach
	void before() {
		client = new PPGapClientImpl( "Fantasy", service );

		when( service.getPartnerAPIServiceImplPort() ).thenReturn( port );
	}

	@Test
	void retrieveUserSessionDetails() throws PartnerAPIExceptionFault {
		when( port.retrieveUserSessionDetails( any() ) ).thenReturn(
				retrieveUserSessionDetailsResponse() );

		UserSessionDetails response = client.retrieveUserSessionDetails( anUserSessionDetailsRequest().token(
				"Token" )
				.build() );

		assertEquals( expectedUserSessionDetails(), response );
	}

	@Test
	void retrieveUserSessionDetails_exception() throws PartnerAPIExceptionFault {
		doThrow( partnerAPIExceptionFault() ).when( port ).retrieveUserSessionDetails( any() );

		assertThrows(
				PPGapException.class,
				( ) -> client.retrieveUserSessionDetails( anUserSessionDetailsRequest().token(
						"Token" ).build() ) );
	}

	@Test
	void retrieveUserSessionDetails_illegalArgument() {
		assertThrows( APIIllegalArgumentException.class,
				( ) -> client.retrieveUserSessionDetails( anUserSessionDetailsRequest().token( "" )
						.build() ) );
		assertThrows(
				APIIllegalArgumentException.class,
				( ) -> client.retrieveUserSessionDetails( anUserSessionDetailsRequest().token( null )
						.build() ) );
	}

	@Test
	void keepAlive() throws PartnerAPIExceptionFault {
		when( port.keepAlive( any() ) ).thenReturn( keepAliveResponse() );

		String newToken = client.keepAlive( "Token" );

		assertEquals( "NewToken", newToken );
	}

	@Test
	void keepAlive_exception() throws PartnerAPIExceptionFault {
		doThrow( partnerAPIExceptionFault() ).when( port ).keepAlive( any() );

		assertThrows( PPGapException.class, ( ) -> client.keepAlive( "Token" ) );
	}

	@Test
	void keepAlive_illegalArgument() {
		assertThrows( APIIllegalArgumentException.class, ( ) -> client.keepAlive( "" ) );
		assertThrows( APIIllegalArgumentException.class, ( ) -> client.keepAlive( null ) );
	}

	@Test
	void transfer() throws PartnerAPIExceptionFault {
		when( port.transfer( any() ) ).thenReturn( transferResponse() );

		TransferResponse response = client.transfer( transferRequest() );

		assertEquals( expectedTransferResponse(), response );
	}

	@Test
	void transfer_exception() throws PartnerAPIExceptionFault {
		doThrow( partnerAPIExceptionFault() ).when( port ).transfer( any() );

		assertThrows( PPGapException.class, ( ) -> client.transfer( transferRequest() ) );
	}

	@Test
	void transfer_illegalArgument() {
		assertThrows(
				APIIllegalArgumentException.class,
				( ) -> client.transfer(
				aTransferRequest().accountId( 0 )
						.amount( BigDecimal.ONE )
						.currency( Currency.getInstance( "GBP" ) )
						.description( "Desc" )
						.reference( "Ref" )
						.token( "Token" )
						.build() ) );
		assertThrows(
				APIIllegalArgumentException.class,
				( ) -> client.transfer(
				aTransferRequest().accountId( -1 )
						.amount( BigDecimal.ONE )
						.currency( Currency.getInstance( "GBP" ) )
						.description( "Desc" )
						.reference( "Ref" )
						.token( "Token" )
						.build() ) );
		assertThrows(
				APIIllegalArgumentException.class,
				( ) -> client.transfer(
				aTransferRequest().accountId( 1 )
						.amount( BigDecimal.ONE )
						.currency( Currency.getInstance( "GBP" ) )
						.description( "Desc" )
						.reference( "" )
						.token( "Token" )
						.build() ) );
		assertThrows(
				APIIllegalArgumentException.class,
				( ) -> client.transfer(
				aTransferRequest().accountId( 1 )
						.amount( BigDecimal.ONE )
						.currency( Currency.getInstance( "GBP" ) )
						.description( "Desc" )
						.reference( null )
						.token( "Token" )
						.build() ) );
		assertThrows(
				APIIllegalArgumentException.class,
				( ) -> client.transfer(
				aTransferRequest().accountId( 1 )
						.amount( null )
						.currency( Currency.getInstance( "GBP" ) )
						.description( "Desc" )
						.reference( "Ref" )
						.token( "Token" )
						.build() ) );
		assertThrows(
				APIIllegalArgumentException.class,
				( ) -> client.transfer(
				aTransferRequest().accountId( 1 )
						.amount( BigDecimal.ONE )
						.currency( null )
						.description( "Desc" )
						.reference( "Ref" )
						.token( "Token" )
						.build() ) );
	}

	@Test
	void cancelTransfer() throws PartnerAPIExceptionFault {
		when( port.cancelTransfer( any() ) ).thenReturn( new CancelTransferResponseType() );

		client.cancelTransfer( aTransferRequest().reference( "Ref" ).build() );

		verify( port ).cancelTransfer( any() );
	}

	@Test
	void cancelTransfer_exception() throws PartnerAPIExceptionFault {
		doThrow( partnerAPIExceptionFault() ).when( port ).cancelTransfer( any() );

		assertThrows( PPGapException.class,
				( ) -> client.cancelTransfer( aTransferRequest().reference( "Ref" ).build() ) );
	}

	@Test
	void cancelTransfer_illegalArgument() {
		assertThrows( APIIllegalArgumentException.class,
				( ) -> client.cancelTransfer( aTransferRequest().reference( "" ).build() ) );
		assertThrows( APIIllegalArgumentException.class,
				( ) -> client.cancelTransfer( aTransferRequest().reference( null ).build() ) );
	}

	@Test
	void retrieveBalance() throws PartnerAPIExceptionFault {
		when( port.retrieveBalance( any() ) ).thenReturn( retrieveBalanceResponse() );
		BigDecimal balance = client.retrieveBalance( 1 );

		assertEquals( new BigDecimal( "22.2439" ), balance );
	}

	@Test
	void retrieveBalance_exception() throws PartnerAPIExceptionFault {
		doThrow( partnerAPIExceptionFault() ).when( port ).retrieveBalance( any() );

		assertThrows( PPGapException.class, ( ) -> client.retrieveBalance( 1 ) );
	}

	@Test
	void retrieveBalance_illegalArgument() {
		assertThrows( APIIllegalArgumentException.class, ( ) -> client.retrieveBalance( 0 ) );
		assertThrows( APIIllegalArgumentException.class, ( ) -> client.retrieveBalance( -1 ) );
	}

	private RetrieveBalanceResponseType retrieveBalanceResponse() {
		StringResponseType balanceAsString = new StringResponseType();
		balanceAsString.setString( "22.2439" );
		RetrieveBalanceResponseType response = new RetrieveBalanceResponseType();
		response.setResponse( balanceAsString );

		return response;
	}

	private RetrieveUserSessionDetailsResponseType retrieveUserSessionDetailsResponse() {
		ProfileType profileResponse = new ProfileType();
		profileResponse.setAccountId( 1 );
		profileResponse.setFirstName( "FirstName" );
		profileResponse.setNickname( "TheFirst" );
		profileResponse.setInitials( "fn" );
		profileResponse.setCountryCode( "UK" );
		profileResponse.setCurrency( "GBP" );
		profileResponse.setOverrideIpBan( true );
		JurisdictionDataType jurisdictionDataResponse = new JurisdictionDataType();
		jurisdictionDataResponse.setNationalIdentifier( "123456" );
		JurisdictionType jurisdictionResponse = new JurisdictionType();
		jurisdictionResponse.setName( "Juris" );
		jurisdictionResponse.setJurisdictionData( jurisdictionDataResponse );
		UserSessionDetailsResponseType userSessionResponse = new UserSessionDetailsResponseType();
		userSessionResponse.setProfile( profileResponse );
		userSessionResponse.setJurisdiction( jurisdictionResponse );
		userSessionResponse.setBalance( "22.2439" );
		userSessionResponse.setToken( "Token" );

		RetrieveUserSessionDetailsResponseType response = new RetrieveUserSessionDetailsResponseType();
		response.setResponse( userSessionResponse );

		return response;
	}

	private UserSessionDetails expectedUserSessionDetails() {
		return UserSessionDetails.Builder.anUserSessionDetails()
				.accountId( 1 )
				.balance( new BigDecimal( "22.2439" ) )
				.country( "UK" )
				.currency( Currency.getInstance( "GBP" ) )
				.firstName( "FirstName" )
				.initials( "fn" )
				.nickname( "TheFirst" )
				.overrideIpBan( true )
				.token( "Token" )
				.build();
	}

	private KeepAliveResponseType keepAliveResponse() {
		StringResponseType token = new StringResponseType();
		token.setString( "NewToken" );
		KeepAliveResponseType response = new KeepAliveResponseType();
		response.setResponse( token );
		return response;
	}

	private TransferResponseType transferResponse() {
		ComplexTransferResponseType transferResponse = new ComplexTransferResponseType();
		transferResponse.setTransferId( 1 );
		transferResponse.setToken( "Token" );

		TransferResponseType response = new TransferResponseType();
		response.setResponse( transferResponse );

		return response;
	}

	private TransferResponse expectedTransferResponse() {
		return aTransferResponse().token( "Token" ).transferId( 1 ).build();
	}

	private TransferRequest transferRequest() {
		return aTransferRequest().accountId( 1 )
				.amount( BigDecimal.ONE )
				.currency( Currency.getInstance( "GBP" ) )
				.description( "Desc" )
				.reference( "Ref" )
				.token( "Token" )
				.build();
	}

	private PartnerAPIExceptionFault partnerAPIExceptionFault() {
		PartnerAPIExceptionType faultInfo = new PartnerAPIExceptionType();
		faultInfo.setMessage( "message" );
		faultInfo.setErrorCode( "code" );
		return new PartnerAPIExceptionFault( "exception", faultInfo );
	}
}
