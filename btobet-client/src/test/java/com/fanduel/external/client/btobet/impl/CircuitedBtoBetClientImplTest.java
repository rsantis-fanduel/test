package com.fanduel.external.client.btobet.impl;

import static com.fanduel.external.client.btobet.BtoBetClientModule.createCircuitBreakerRegistry;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.fanduel.external.client.btobet.BtoBetClient;
import com.fanduel.runtime.config.Config;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CircuitedBtoBetClientImplTest extends ResilientBtoBetClientImplTest {
	@Override
	ResilientBtoBetClientImpl createClient( final BtoBetClient delegate, final Config config ) {
		return new ResilientBtoBetClientImpl( delegate, createCircuitBreakerRegistry( config ),
				false, null, null );
	}
}
