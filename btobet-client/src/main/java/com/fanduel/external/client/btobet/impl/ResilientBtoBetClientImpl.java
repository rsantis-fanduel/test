package com.fanduel.external.client.btobet.impl;

import static com.fanduel.external.client.btobet.BtoBetClientModule.API_AUTHENTICATE;
import static com.fanduel.external.client.btobet.BtoBetClientModule.API_BET;
import static com.fanduel.external.client.btobet.BtoBetClientModule.API_CANCEL_BET;
import static com.fanduel.external.client.btobet.BtoBetClientModule.API_GENERATE_TOKEN;
import static com.fanduel.external.client.btobet.BtoBetClientModule.API_GET_BALANCE;
import static com.fanduel.external.client.btobet.BtoBetClientModule.API_REFUND;
import static com.fanduel.external.client.btobet.BtoBetClientModule.API_WIN;

import java.io.IOException;
import java.util.concurrent.Executor;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.metrics.CircuitBreakerMetrics;

import com.codahale.metrics.MetricRegistry;
import com.fanduel.external.client.btobet.BtoBetClient;
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
import com.fanduel.external.client.common.res4j.ResilientClient;
import com.fanduel.external.client.common.res4j.TimeLimiterRegistry;
import com.fanduel.metrics.MetricRegistries;

public class ResilientBtoBetClientImpl extends ResilientClient implements BtoBetClient {
	private static final MetricRegistry METRICS = MetricRegistries.getRegistry();

	private final BtoBetClient delegate;

	public ResilientBtoBetClientImpl( final BtoBetClient delegate,
			final CircuitBreakerRegistry circuitBreakerRegistry, final boolean timeLimiterEnabled,
			final Executor timeLimiterExecutor, final TimeLimiterRegistry timeLimiterRegistry ) {
		super( timeLimiterRegistry, circuitBreakerRegistry, timeLimiterEnabled,
				timeLimiterExecutor );
		METRICS.registerAll( CircuitBreakerMetrics.ofCircuitBreakerRegistry( "btobet-client",
				circuitBreakerRegistry ) );
		this.delegate = delegate;
	}

	@Override
	public GenerateBtoBetAuthTokenResponse generateAuthToken(
			final GenerateBtoBetAuthTokenRequest request ) {
		return callResiliently( API_GENERATE_TOKEN, () -> delegate.generateAuthToken( request ) );
	}

	@Override
	public AuthenticateResponse authenticate( final AuthenticateRequest request ) {
		return callResiliently( API_AUTHENTICATE, () -> delegate.authenticate( request ) );
	}

	@Override
	public GetBalanceResponse getBalance( final GetBalanceRequest request ) {
		return callResiliently( API_GET_BALANCE, () -> delegate.getBalance( request ) );
	}

	@Override
	public BetResponse bet( final BetRequest request ) {
		return callResiliently( API_BET, () -> delegate.bet( request ) );
	}

	@Override
	public CancelBetResponse cancelBet( final CancelBetRequest request ) {
		return callResiliently( API_CANCEL_BET, () -> delegate.cancelBet( request ) );
	}

	@Override
	public RefundResponse refund( final RefundRequest request ) throws IOException {
		return callResiliently( API_REFUND, () -> delegate.refund( request ) );
	}

	@Override
	public WinResponse win( final WinRequest request ) {
		return callResiliently( API_WIN, () -> delegate.win( request ) );
	}

}
