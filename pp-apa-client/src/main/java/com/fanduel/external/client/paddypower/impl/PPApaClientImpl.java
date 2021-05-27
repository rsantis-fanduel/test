package com.fanduel.external.client.paddypower.impl;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import static com.fanduel.api.ApiPreconditions.notNegativeOrZero;
import static com.fanduel.api.ApiPreconditions.notNull;
import static com.fanduel.api.ApiPreconditions.notNullOrEmpty;
import static com.fanduel.external.client.paddypower.PPApaClientModule.CANCEL_TRANSFER;
import static com.fanduel.external.client.paddypower.PPApaClientModule.KEEP_ALIVE;
import static com.fanduel.external.client.paddypower.PPApaClientModule.RETRIEVE_BALANCE;
import static com.fanduel.external.client.paddypower.PPApaClientModule.RETRIEVE_USER_SESSION_DETAILS;
import static com.fanduel.external.client.paddypower.PPApaClientModule.TRANSFER;
import static com.fanduel.external.client.paddypower.impl.exception.PPApaClientExceptionHelper.mapException;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Map;
import java.util.stream.Stream;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.fanduel.external.client.paddypower.PPApaClient;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.KeepAliveRequestType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.KeepAliveResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.PartnerAPIExceptionFault;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.PartnerAPIService;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.PartnerAPIService_Service;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.RetrieveBalanceInfoRequestType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.RetrieveBalanceInfoResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.RetrieveBalanceRequestType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.RetrieveBalanceResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.RetrieveUserSessionDetailsResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.TransferResponseType;
import com.fanduel.external.client.paddypower.transfer.BalanceInfo;
import com.fanduel.external.client.paddypower.transfer.TransferRequest;
import com.fanduel.external.client.paddypower.transfer.TransferResponse;
import com.fanduel.external.client.paddypower.transfer.UserSessionDetails;
import com.fanduel.external.client.paddypower.transfer.UserSessionDetailsRequest;
import com.fanduel.external.client.paddypower.transformer.ApaToBalanceInfoTransformer;
import com.fanduel.external.client.paddypower.transformer.ApaToTransferResponseTransformer;
import com.fanduel.external.client.paddypower.transformer.ApaToUserSessionDetailsTransformer;
import com.fanduel.external.client.paddypower.transformer.TransferRequestToApaCancelTransformer;
import com.fanduel.external.client.paddypower.transformer.TransferRequestToApaTransformer;
import com.fanduel.external.client.paddypower.transformer.UserSessionDetailsRequestToApaTransformer;
import com.fanduel.metrics.MetricRegistries;
import com.google.common.annotations.VisibleForTesting;

/**
 * SOAP implementation of PaddyPower APA API client.
 * <p>
 * IMPORTANT: This implementation relies on projects to properly setup JDK http.* system properties
 * according with the load expected.
 */
public class PPApaClientImpl implements PPApaClient {
	private static final Logger LOGGER = LoggerFactory.getLogger( PPApaClientImpl.class );
	private static final MetricRegistry METRICS = MetricRegistries.getRegistry();
	private final Map<String, Timer> timers;

	private static final TransferRequestToApaTransformer TRANSFER_REQUEST_TO_APA_TRANSFORMER = new TransferRequestToApaTransformer();
	private static final ApaToTransferResponseTransformer APA_TO_TRANSFER_RESPONSE_TRANSFORMER = new ApaToTransferResponseTransformer();
	private static final UserSessionDetailsRequestToApaTransformer USER_SESSION_DETAILS_TO_APA_TRANSFORMER = new UserSessionDetailsRequestToApaTransformer();
	private static final ApaToUserSessionDetailsTransformer APA_TO_USER_SESSION_DETAILS_TRANSFORMER = new ApaToUserSessionDetailsTransformer();
	private static final TransferRequestToApaCancelTransformer TRANSFER_REQUEST_TO_APA_CANCEL_TRANSFORMER = new TransferRequestToApaCancelTransformer();
	private ApaToBalanceInfoTransformer APA_TO_BALANCE_INFO_TRANSFORMER = new ApaToBalanceInfoTransformer();

	private static final String X_CLIENT_HEADER_KEY = "X-Client";

	/*
	 * According to JAX-WS spec, ports are not thread safe. Creation of ports is
	 * also expensive. This workaround expects a pool of reusable threads
	 * calling the client.
	 */
	private ThreadLocal<PartnerAPIService> portRef = new ThreadLocal<PartnerAPIService>() {
		@Override
		protected PartnerAPIService initialValue() {
			return service.getPartnerAPIService();
		}
	};

	private final PartnerAPIService_Service service;

	public PPApaClientImpl( URL wsdlLocation, QName serviceName, String clientId ) {
		this( clientId, new PartnerAPIService_Service( wsdlLocation, serviceName ) );

	}

	@VisibleForTesting
	protected PPApaClientImpl( String clientId, PartnerAPIService_Service service ) {
		this.service = service;
		this.timers = Stream.of( RETRIEVE_USER_SESSION_DETAILS, KEEP_ALIVE, TRANSFER,
				CANCEL_TRANSFER, RETRIEVE_BALANCE )
				.collect( toMap( identity(), name -> METRICS.timer(
						MetricRegistry.name( PPApaClientImpl.class, name, "timer" ) ) ) );
	}

	@Override
	public UserSessionDetails retrieveUserSessionDetails(
			final UserSessionDetailsRequest request ) {
		notNullOrEmpty( "token", request.getToken() );

		try ( Timer.Context ignored = timers.get( RETRIEVE_USER_SESSION_DETAILS ).time() ) {
			RetrieveUserSessionDetailsResponseType retrieveUserSessionDetailsResponseType = getPortInstance()
					.retrieveUserSessionDetails(
							USER_SESSION_DETAILS_TO_APA_TRANSFORMER.transform( request ) );
			return APA_TO_USER_SESSION_DETAILS_TRANSFORMER.transform(
					retrieveUserSessionDetailsResponseType );
		} catch ( PartnerAPIExceptionFault partnerAPIExceptionFault ) {
			throw mapException( partnerAPIExceptionFault );
		}
	}

	@Override
	public String keepAlive( final String token ) {
		notNullOrEmpty( "token", token );

		try ( Timer.Context ignored = timers.get( KEEP_ALIVE ).time() ) {
			KeepAliveRequestType keepAliveRequestType = new KeepAliveRequestType();
			keepAliveRequestType.setToken( token );

			KeepAliveResponseType keepAliveResponseType = getPortInstance().keepAlive(
					keepAliveRequestType );
			return keepAliveResponseType.getResponse();
		} catch ( PartnerAPIExceptionFault partnerAPIExceptionFault ) {
			throw mapException( partnerAPIExceptionFault );
		}
	}

	@Override
	public TransferResponse transfer( final TransferRequest request ) {
		validateTransfer( request );

		try ( Timer.Context ignored = timers.get( TRANSFER ).time() ) {
			TransferResponseType transferResponseType = getPortInstance().transfer(
					TRANSFER_REQUEST_TO_APA_TRANSFORMER.transform( request ) );
			return APA_TO_TRANSFER_RESPONSE_TRANSFORMER.transform( transferResponseType );
		} catch ( PartnerAPIExceptionFault partnerAPIExceptionFault ) {
			throw mapException( partnerAPIExceptionFault );
		}
	}

	@Override
	public void cancelTransfer( final TransferRequest request ) {
		notNullOrEmpty( "reference", request.getReference() );

		try ( Timer.Context ignored = timers.get( CANCEL_TRANSFER ).time() ) {
			getPortInstance().cancelTransfer(
					TRANSFER_REQUEST_TO_APA_CANCEL_TRANSFORMER.transform( request ) );
		} catch ( PartnerAPIExceptionFault partnerAPIExceptionFault ) {
			throw mapException( partnerAPIExceptionFault );
		}
	}

	@Override
	public BigDecimal retrieveBalance( final long accountId ) {
		notNegativeOrZero( "accountId", accountId );

		try ( Timer.Context ignored = timers.get( RETRIEVE_BALANCE ).time() ) {
			RetrieveBalanceRequestType retrieveBalanceRequestType = new RetrieveBalanceRequestType();
			retrieveBalanceRequestType.setAccountId( accountId );

			RetrieveBalanceResponseType retrieveBalanceResponseType = getPortInstance().retrieveBalance(
					retrieveBalanceRequestType );

			return new BigDecimal( retrieveBalanceResponseType.getResponse() );
		} catch ( PartnerAPIExceptionFault partnerAPIExceptionFault ) {
			throw mapException( partnerAPIExceptionFault );
		}
	}

	@Override
	public BalanceInfo retrieveBalanceInfo( final long accountId ) {
		notNegativeOrZero( "accountId", accountId );

		try ( Timer.Context ignored = timers.get( RETRIEVE_BALANCE ).time() ) {
			RetrieveBalanceInfoRequestType retrieveBalanceInfoRequestType = new RetrieveBalanceInfoRequestType();
			retrieveBalanceInfoRequestType.setAccountId( accountId );

			RetrieveBalanceInfoResponseType retrieveBalanceInfoResponseType = getPortInstance().retrieveBalanceInfo(
					retrieveBalanceInfoRequestType );
			BalanceInfo balanceInfo = APA_TO_BALANCE_INFO_TRANSFORMER.transform(
					retrieveBalanceInfoResponseType );
			balanceInfo.setAccountId( accountId );
			return balanceInfo;
		} catch ( PartnerAPIExceptionFault partnerAPIExceptionFault ) {
			throw mapException( partnerAPIExceptionFault );
		}

	}

	private void validateTransfer( TransferRequest request ) {
		notNegativeOrZero( "accountId", request.getAccountId() );
		notNullOrEmpty( "reference", request.getReference() );
		notNull( "amount", request.getAmount() );
		notNull( "currency", request.getCurrency() );
	}

	@VisibleForTesting
	protected PartnerAPIService getPortInstance() {
		return this.portRef.get();
	}
}
