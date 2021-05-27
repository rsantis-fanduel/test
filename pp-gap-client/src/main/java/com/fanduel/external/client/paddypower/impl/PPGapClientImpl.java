package com.fanduel.external.client.paddypower.impl;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import static com.fanduel.api.ApiPreconditions.notNegativeOrZero;
import static com.fanduel.api.ApiPreconditions.notNull;
import static com.fanduel.api.ApiPreconditions.notNullOrEmpty;
import static com.fanduel.external.client.paddypower.PPGapClientModule.CANCEL_TRANSFER;
import static com.fanduel.external.client.paddypower.PPGapClientModule.KEEP_ALIVE;
import static com.fanduel.external.client.paddypower.PPGapClientModule.RETRIEVE_BALANCE;
import static com.fanduel.external.client.paddypower.PPGapClientModule.RETRIEVE_USER_SESSION_DETAILS;
import static com.fanduel.external.client.paddypower.PPGapClientModule.TRANSFER;
import static com.fanduel.external.client.paddypower.impl.exception.PPGapClientExceptionHelper.mapException;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.fanduel.external.client.paddypower.PPGapClient;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.KeepAliveRequestType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.KeepAliveResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.PartnerAPIExceptionFault;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.PartnerAPIService;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.PartnerAPIServiceImplService;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.RetrieveBalanceRequestType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.RetrieveBalanceResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.RetrieveUserSessionDetailsResponseType;
import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.TransferResponseType;
import com.fanduel.external.client.paddypower.transfer.TransferRequest;
import com.fanduel.external.client.paddypower.transfer.TransferResponse;
import com.fanduel.external.client.paddypower.transfer.UserSessionDetails;
import com.fanduel.external.client.paddypower.transfer.UserSessionDetailsRequest;
import com.fanduel.external.client.paddypower.transformer.GapToTransferResponseTransformer;
import com.fanduel.external.client.paddypower.transformer.GapToUserSessionDetailsTransformer;
import com.fanduel.external.client.paddypower.transformer.TransferRequestToGapCancelTransformer;
import com.fanduel.external.client.paddypower.transformer.TransferRequestToGapTransformer;
import com.fanduel.external.client.paddypower.transformer.UserSessionDetailsRequestToGapTransformer;
import com.fanduel.metrics.MetricRegistries;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;

/**
 * SOAP implementation of PaddyPower GAP API client.
 *
 * IMPORTANT: This implementation relies on projects to properly setup JDK http.* system properties
 * according with the load expected.
 */
public class PPGapClientImpl implements PPGapClient {

	private static final MetricRegistry METRICS = MetricRegistries.getRegistry();
	private final Map<String, Timer> timers;

	private static final TransferRequestToGapTransformer TRANSFER_REQUEST_TO_GAP_TRANSFORMER = new TransferRequestToGapTransformer();
	private static final GapToTransferResponseTransformer GAP_TO_TRANSFER_RESPONSE_TRANSFORMER = new GapToTransferResponseTransformer();
	private static final UserSessionDetailsRequestToGapTransformer USER_SESSION_DETAILS_TO_GAP_TRANSFORMER = new UserSessionDetailsRequestToGapTransformer();
	private static final GapToUserSessionDetailsTransformer GAP_TO_USER_SESSION_DETAILS_TRANSFORMER = new GapToUserSessionDetailsTransformer();
	private static final TransferRequestToGapCancelTransformer TRANSFER_REQUEST_TO_GAP_CANCEL_TRANSFORMER = new TransferRequestToGapCancelTransformer();

	private static final String X_CLIENT_HEADER_KEY = "X-Client";

	/*
	 * According to JAX-WS spec, ports are not thread safe. Creation of ports is
	 * also expensive. This workaround expects a pool of reusable threads
	 * calling the client.
	 */
	private ThreadLocal<PartnerAPIService> portRef = new ThreadLocal<PartnerAPIService>() {
		@Override
		protected PartnerAPIService initialValue() {
			return addCustomHeaders( service.getPartnerAPIServiceImplPort() );
		}
	};

	private final Map<String, List<String>> customRequesHeaders;
	private final PartnerAPIServiceImplService service;

	public PPGapClientImpl( URL wsdlLocation, QName serviceName, String clientId ) {
		this( clientId, new PartnerAPIServiceImplService( wsdlLocation, serviceName ) );

	}

	@VisibleForTesting
	protected PPGapClientImpl( String clientId, PartnerAPIServiceImplService service ) {
		this.service = service;
		this.customRequesHeaders = ImmutableMap.of( X_CLIENT_HEADER_KEY,
				Collections.singletonList( clientId ) );
		this.timers = Stream.of( RETRIEVE_USER_SESSION_DETAILS, KEEP_ALIVE, TRANSFER,
				CANCEL_TRANSFER, RETRIEVE_BALANCE )
				.collect( toMap( identity(), name -> METRICS.timer(
						MetricRegistry.name( PPGapClientImpl.class, name, "timer" ) ) ) );
	}

	@Override
	public UserSessionDetails retrieveUserSessionDetails(
			final UserSessionDetailsRequest request ) {
		notNullOrEmpty( "token", request.getToken() );

		try ( Timer.Context ignored = timers.get( RETRIEVE_USER_SESSION_DETAILS ).time() ) {
			RetrieveUserSessionDetailsResponseType retrieveUserSessionDetailsResponseType = getPortInstance()
					.retrieveUserSessionDetails(
							USER_SESSION_DETAILS_TO_GAP_TRANSFORMER.transform( request ) );
			return GAP_TO_USER_SESSION_DETAILS_TRANSFORMER.transform(
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
			return keepAliveResponseType.getResponse().getString();
		} catch ( PartnerAPIExceptionFault partnerAPIExceptionFault ) {
			throw mapException( partnerAPIExceptionFault );
		}

	}

	@Override
	public TransferResponse transfer( final TransferRequest request ) {
		validateTransfer( request );

		try ( Timer.Context ignored = timers.get( TRANSFER ).time() ) {
			TransferResponseType transferResponseType = getPortInstance().transfer(
					TRANSFER_REQUEST_TO_GAP_TRANSFORMER.transform( request ) );
			return GAP_TO_TRANSFER_RESPONSE_TRANSFORMER.transform( transferResponseType );
		} catch ( PartnerAPIExceptionFault partnerAPIExceptionFault ) {
			throw mapException( partnerAPIExceptionFault );
		}
	}

	@Override
	public void cancelTransfer( final TransferRequest request ) {
		notNullOrEmpty( "reference", request.getReference() );

		try ( Timer.Context ignored = timers.get( CANCEL_TRANSFER ).time() ) {
			getPortInstance().cancelTransfer(
					TRANSFER_REQUEST_TO_GAP_CANCEL_TRANSFORMER.transform( request ) );
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

			return new BigDecimal( retrieveBalanceResponseType.getResponse().getString() );
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

	private PartnerAPIService addCustomHeaders( PartnerAPIService port ) {
		( (BindingProvider) port ).getRequestContext()
				.put( MessageContext.HTTP_REQUEST_HEADERS, customRequesHeaders );
		return port;
	}

	@VisibleForTesting
	protected PartnerAPIService getPortInstance() {
		return this.portRef.get();
	}
}
