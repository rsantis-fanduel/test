package com.fanduel.external.client.paddypower.impl;

import static com.fanduel.external.client.paddypower.PPGapClientModule.createCircuitBreakerRegistry;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.fanduel.external.client.paddypower.PPGapClient;
import com.fanduel.runtime.config.Config;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CircuitedPPGapClientImplTest extends ResilientPPGapClientImplTest {

	@Override
	ResilientPPGapClientImpl createClient( final PPGapClient delegate, final Config config ) {
		return new ResilientPPGapClientImpl( delegate, createCircuitBreakerRegistry( config ),
				false, null, null );
	}
}
