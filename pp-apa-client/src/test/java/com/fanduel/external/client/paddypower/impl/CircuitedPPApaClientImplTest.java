package com.fanduel.external.client.paddypower.impl;

import static com.fanduel.external.client.paddypower.PPApaClientModule.createCircuitBreakerRegistry;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.fanduel.external.client.paddypower.PPApaClient;
import com.fanduel.runtime.config.Config;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CircuitedPPApaClientImplTest extends ResilientPPApaClientImplTest {

	@Override
	ResilientPPApaClientImpl createClient( final PPApaClient delegate, final Config config ) {
		return new ResilientPPApaClientImpl( delegate, createCircuitBreakerRegistry( config ),
				false, null, null );
	}
}
