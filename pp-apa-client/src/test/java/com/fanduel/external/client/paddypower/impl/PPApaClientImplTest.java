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
import com.fanduel.external.client.paddypower.exception.PPApaException;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.CancelTransferResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.ComplexTransferResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.JurisdictionDataType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.JurisdictionType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.KeepAliveResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.PartnerAPIExceptionFault;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.PartnerAPIExceptionType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.PartnerAPIService;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.PartnerAPIService_Service;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.ProfileType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.RetrieveBalanceInfoResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.RetrieveBalanceInfoType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.RetrieveBalanceResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.RetrieveUserSessionDetailsResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.TransferResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.UserSessionDetailsResponseType;
import com.fanduel.external.client.paddypower.transfer.BalanceInfo;
import com.fanduel.external.client.paddypower.transfer.TransferRequest;
import com.fanduel.external.client.paddypower.transfer.TransferResponse;
import com.fanduel.external.client.paddypower.transfer.UserSessionDetails;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PPApaClientImplTest {

	private PPApaClientImpl client;

	@Mock(extraInterfaces = BindingProvider.class)
	private PartnerAPIService port;

	@Mock
	private PartnerAPIService_Service service;

	@BeforeEach
	void before() {
		client = new PPApaClientImpl( "Fantasy", service );

		when( service.getPartnerAPIService() ).thenReturn( port );
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

		assertThrows( PPApaException.class, () -> client.retrieveUserSessionDetails(
				anUserSessionDetailsRequest().token( "Token" ).build() ) );
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

		assertThrows( PPApaException.class, () -> client.keepAlive( "Token" ) );
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

		assertThrows( PPApaException.class, ( ) -> client.transfer( transferRequest() ) );
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

		assertThrows( PPApaException.class,
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

		assertThrows( PPApaException.class, () -> client.retrieveBalance( 1 ) );
	}

	@Test
	void retrieveBalance_illegalArgument() {
		assertThrows( APIIllegalArgumentException.class, ( ) -> client.retrieveBalance( 0 ) );
		assertThrows( APIIllegalArgumentException.class, ( ) -> client.retrieveBalance( -1 ) );
	}

	private RetrieveBalanceResponseType retrieveBalanceResponse() {
		String balanceAsString = "22.2439";
		RetrieveBalanceResponseType response = new RetrieveBalanceResponseType();
		response.setResponse( balanceAsString );

		return response;
	}

	@Test
	void retrieveBalanceInfo() throws PartnerAPIExceptionFault {
		when( port.retrieveBalanceInfo( any() ) ).thenReturn( retrieveBalanceInfoResponse() );
		BalanceInfo balanceInfo = client.retrieveBalanceInfo( 1 );
		BalanceInfo expected = expectedBalanceInfo();
		assertEquals( expected, balanceInfo );
	}

	@Test
	void retrieveBalanceInfo_exception() throws PartnerAPIExceptionFault {
		doThrow( partnerAPIExceptionFault() ).when( port ).retrieveBalanceInfo( any() );

		assertThrows( PPApaException.class, () -> client.retrieveBalanceInfo( 1 ) );
	}

	@Test
	void retrieveBalanceInfo_illegalArgument() {
		assertThrows( APIIllegalArgumentException.class, ( ) -> client.retrieveBalanceInfo( 0 ) );
		assertThrows( APIIllegalArgumentException.class, ( ) -> client.retrieveBalanceInfo( -1 ) );
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
		String token = "NewToken";
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

	private RetrieveBalanceInfoResponseType retrieveBalanceInfoResponse() {
		RetrieveBalanceInfoResponseType response = new RetrieveBalanceInfoResponseType();
		RetrieveBalanceInfoType balanceInfo = new RetrieveBalanceInfoType();
		balanceInfo.setMain( "100.07" );
		balanceInfo.setBonus( "5.00" );
		balanceInfo.setTotal( "105.07" );
		response.setResponse( balanceInfo );
		return response;
	}

	private BalanceInfo expectedBalanceInfo() {
		return BalanceInfo.Builder.aBalanceInfo()
				.accountId( 1 )
				.main( new BigDecimal( "100.07" ) )
				.bonus( new BigDecimal( "5.00" ) )
				.total( new BigDecimal( "105.07" ) )
				.build();
	}

	private PartnerAPIExceptionFault partnerAPIExceptionFault() {
		PartnerAPIExceptionType faultInfo = new PartnerAPIExceptionType();
		faultInfo.setErrorCode( "code" );
		return new PartnerAPIExceptionFault( "exception", faultInfo );
	}
}
