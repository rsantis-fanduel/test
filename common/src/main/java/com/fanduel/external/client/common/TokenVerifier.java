package com.fanduel.external.client.common;

import static com.fanduel.api.ApiPreconditions.notNullOrEmpty;
import static com.fanduel.external.client.common.exception.ClientErrorCode.JWT_SIGNATURE_VERIFICATION_ERROR;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashSet;

import com.fanduel.api.exception.APICodedException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.nimbusds.jwt.proc.JWTClaimsSetVerifier;

public class TokenVerifier {

	private final ConfigurableJWTProcessor<SecurityContext> jwtProcessor;

	public TokenVerifier( final String jwkURL, final JWSAlgorithm jwsAlgorithm,
			final String requiredIssuer, final HashSet<String> requiredClaims )
			throws MalformedURLException {
		this( new RemoteJWKSet<>( new URL( jwkURL ) ), jwsAlgorithm, requiredIssuer,
				requiredClaims );
	}

	public TokenVerifier( final JWKSource<SecurityContext> keySource,
			final JWSAlgorithm jwsAlgorithm, final String requiredIssuer,
			final HashSet<String> requiredClaims ) {
		final JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(
				jwsAlgorithm, keySource );

		this.jwtProcessor = new DefaultJWTProcessor<>();
		jwtProcessor.setJWSKeySelector( keySelector );
		jwtProcessor.setJWTClaimsSetVerifier( new DefaultJWTClaimsVerifier<>(
				new JWTClaimsSet.Builder().issuer( requiredIssuer ).build(), requiredClaims ) );

	}

	public boolean verifyTokenSignature( final String token ) throws APICodedException {
		try {
			notNullOrEmpty( "token", token );

			final JWTClaimsSet jwtClaimsSet = jwtProcessor.process( token, null );
			final JWTClaimsSetVerifier verifier = jwtProcessor.getJWTClaimsSetVerifier();
			verifier.verify( jwtClaimsSet, null );
			return true;
		} catch ( BadJOSEException e ) {
			return false;
		} catch ( ParseException | JOSEException e ) {
			throw new APICodedException( JWT_SIGNATURE_VERIFICATION_ERROR );
		}
	}
}
