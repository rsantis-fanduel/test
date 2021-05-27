package com.fanduel.external.client.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.fanduel.api.exception.APICodedException;
import com.fanduel.api.exception.APIIllegalArgumentException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TokenVerifierTest {

	private static final String JWT_ID = "jwtid";
	private static final String SUBJECT = "subject";
	private static final String GLOBO_JWK_URL = "https://id.globo.com/auth/realms/globo.com/protocol/openid-connect/certs";
	private static final JWSAlgorithm GLOBO_EXPECTED_JWS_ALG = JWSAlgorithm.RS256;
	private static final String GLOBO_REQUIRED_ISSUER = "https://id.globo.com/auth/realms/globo.com";
	private static final HashSet<String> GLOBO_REQUIRED_CLAIMS = new HashSet<>(
			Arrays.asList( "iss", "sub", "exp", "iat", "jti" ) );

	private String expiredJwt = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJXLUppTjhfZXdyWE9uVnJFN2lfOGpIY28yU1R4dEtHZF94aW01R2N4WS1ZIn0.eyJleHAiOjE2MTg4NzQzNDMsImlhdCI6MTYxODg3MDc0MywiYXV0aF90aW1lIjoxNjE4ODcwNzI1LCJqdGkiOiI3ZjM4MTcxYi1hYTljLTQyMjEtOTM1YS1kOTA0ZjRmOTc5ZTUiLCJpc3MiOiJodHRwczovL2lkLmdsb2JvLmNvbS9hdXRoL3JlYWxtcy9nbG9iby5jb20iLCJzdWIiOiJmOjNjZGVhMWZiLTAwMmYtNDg5ZS1iOWMyLWQ1N2FiYTBhZTQ5NDpmOTNkMTJiMi0wNzhmLTQyMmUtYTUxNC0zNDU0OTQ5OGQ1OGUiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJmYW5kdWVsQGFwcHMuZ2xvYm9pZCIsIm5vbmNlIjoiTzVHTUNIRElDNkhKU1ZaRDg4UlUiLCJzZXNzaW9uX3N0YXRlIjoiODc4Yjc1NGYtZmExOS00MTY4LTg3NTAtYTI3YmQ1ZGUyMzhmIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwczovL2RldnN0YWNrLWNhcnRvbGFhdXRoZGVtby13ZWIudXNlMS5kZXYudXMuZmRib3gubmV0IiwiaHR0cHM6Ly9hcGkuZmFuZHVlbC5jb20iLCJodHRwczovL2xvY2FsaG9zdDo0MDAwIiwiaHR0cDovL2RldnN0YWNrLWdsb2JvLWFwaS51c2UxLmRldi51cy5mZGJveC5uZXQiLCJodHRwczovL2RldnN0YWNrLWNhcnRvbGFhdXRoZGVtby1hcGkudXNlMS5kZXYudXMuZmRib3gubmV0IiwiaHR0cDovL2RldnN0YWNrLWNhcnRvbGFhdXRoZGVtby1hcGkudXNlMS5kZXYudXMuZmRib3gubmV0IiwiaHR0cHM6Ly9kZXZzdGFjay1nbG9iby13ZWIudXNlMS5kZXYudXMuZmRib3gubmV0IiwiaHR0cHM6Ly9sb2NhbC5nbG9ib2kuY29tIiwiaHR0cHM6Ly9kZXZzdGFjay1nbG9iby1hcGkudXNlMS5kZXYudXMuZmRib3gubmV0IiwiaHR0cDovL2xvY2FsaG9zdDo0MDAwIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyJdfSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiem9uZWluZm8iOiJBbWVyaWNhL1Nhb19QYXVsbyIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJiaXJ0aGRhdGUiOiIxOTkzLTEwLTA3IiwiZ2VuZGVyIjoiIiwibmFtZSI6IkplZmYgQ2hvaSIsImxvY2FsZSI6InB0X0JSIiwiZW1haWwiOiJjaG9pamVmZjkzQGdtYWlsLmNvbSIsImdsb2JvX2lkIjoiZjkzZDEyYjItMDc4Zi00MjJlLWE1MTQtMzQ1NDk0OThkNThlIn0.BlchrKSBo95lcMsaJv_QDo-JO_AD2i46ChxK18_LmUu-8hYVNnC3uYG_Ig_wPs0lETgNcvmkny9kFqAMCRswT5sBaT7c5ol_lGYVx_cSSDnsX5slrN-OZFmTdlJX7wk7W39GLFgawUZOEGF40vumkWGmw-lQVRlheO4R7gwtS9EFhjTzioixdL_SqFlXn_ZVCLYz3_aLjyFWlq5GK9zxk_wUjBjhaOe1F9uyIMbu3v_GR3jl7kgUu6CxTembq3LBvP6xIpbLuDB9e0Y3XzstFYMYS9x1z2eF2miLVrigQ94posrSFpfDZCgnZj6Ri5LjVf2HnIgu9Y6AEFvfWKNO6g";
	private String fakeSignatureJwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2MTg4NzA3NDMsImF1dGhfdGltZSI6MTYxODg3MDcyNSwianRpIjoiN2YzODE3MWItYWE5Yy00MjIxLTkzNWEtZDkwNGY0Zjk3OWU1IiwiaXNzIjoiaHR0cHM6Ly9pZC5nbG9iby5jb20vYXV0aC9yZWFsbXMvZ2xvYm8uY29tIiwic3ViIjoiZjozY2RlYTFmYi0wMDJmLTQ4OWUtYjljMi1kNTdhYmEwYWU0OTQ6ZjkzZDEyYjItMDc4Zi00MjJlLWE1MTQtMzQ1NDk0OThkNThlIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZmFuZHVlbEBhcHBzLmdsb2JvaWQiLCJub25jZSI6Ik81R01DSERJQzZISlNWWkQ4OFJVIiwic2Vzc2lvbl9zdGF0ZSI6Ijg3OGI3NTRmLWZhMTktNDE2OC04NzUwLWEyN2JkNWRlMjM4ZiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly9kZXZzdGFjay1jYXJ0b2xhYXV0aGRlbW8td2ViLnVzZTEuZGV2LnVzLmZkYm94Lm5ldCIsImh0dHBzOi8vYXBpLmZhbmR1ZWwuY29tIiwiaHR0cHM6Ly9sb2NhbGhvc3Q6NDAwMCIsImh0dHA6Ly9kZXZzdGFjay1nbG9iby1hcGkudXNlMS5kZXYudXMuZmRib3gubmV0IiwiaHR0cHM6Ly9kZXZzdGFjay1jYXJ0b2xhYXV0aGRlbW8tYXBpLnVzZTEuZGV2LnVzLmZkYm94Lm5ldCIsImh0dHA6Ly9kZXZzdGFjay1jYXJ0b2xhYXV0aGRlbW8tYXBpLnVzZTEuZGV2LnVzLmZkYm94Lm5ldCIsImh0dHBzOi8vZGV2c3RhY2stZ2xvYm8td2ViLnVzZTEuZGV2LnVzLmZkYm94Lm5ldCIsImh0dHBzOi8vbG9jYWwuZ2xvYm9pLmNvbSIsImh0dHBzOi8vZGV2c3RhY2stZ2xvYm8tYXBpLnVzZTEuZGV2LnVzLmZkYm94Lm5ldCIsImh0dHA6Ly9sb2NhbGhvc3Q6NDAwMCJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiXX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInpvbmVpbmZvIjoiQW1lcmljYS9TYW9fUGF1bG8iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYmlydGhkYXRlIjoiMTk5My0xMC0wNyIsImdlbmRlciI6IiIsIm5hbWUiOiJKZWZmIENob2kiLCJsb2NhbGUiOiJwdF9CUiIsImVtYWlsIjoiY2hvaWplZmY5M0BnbWFpbC5jb20iLCJnbG9ib19pZCI6ImY5M2QxMmIyLTA3OGYtNDIyZS1hNTE0LTM0NTQ5NDk4ZDU4ZSJ9.WVFGy6qJhV-hTAqqRephnU_KYjZKqRn7nDWstROoQZOnDJv_vxvQugqcFRai1ilIB9p20I5sTAt1ndUIYfaqNehsG8J605aptgpd6TYaw_EwLPDNh5aUfj1l8PeioYCRLkB-GJFMqY1KTpy8SxoYnNPRRQIt_HidapKWZSesTnk";
	private String invalidJwt = "invalidjwt";

	private TokenVerifier globoTokenVerifier;

	@BeforeEach
	void before() throws IOException, ParseException {
		final String resourceName = "/globoCertificationKeys.json";
		final URL url = TokenVerifierTest.class.getResource( resourceName );
		final JWKSet jwkSet = JWKSet.load( new File( url.getFile() ) );

		final JWKSource<SecurityContext> keySource = new ImmutableJWKSet<>( jwkSet );

		globoTokenVerifier = new TokenVerifier( keySource, GLOBO_EXPECTED_JWS_ALG,
				GLOBO_REQUIRED_ISSUER, GLOBO_REQUIRED_CLAIMS );
	}

	@Test
	void testVerifySignatureValidJwt() throws JOSEException, NoSuchAlgorithmException {
		final KeyPair keyPair = generateKeyPair();
		final JWK jwk = generateJWK( keyPair );
		final JWKSet jwkSet = new JWKSet( jwk );
		final JWKSource jwkSource = new ImmutableJWKSet( jwkSet );
		final String validJwt = generateToken( keyPair, jwk.getKeyID() );

		final HashSet<String> GENERIC_REQUIRED_CLAIMS = new HashSet<>(
				Arrays.asList( "iss", "sub", "iat", "jti" ) );
		TokenVerifier genericTokenVerifier = new TokenVerifier( jwkSource, GLOBO_EXPECTED_JWS_ALG,
				GLOBO_REQUIRED_ISSUER, GENERIC_REQUIRED_CLAIMS );

		assertTrue( genericTokenVerifier.verifyTokenSignature( validJwt ) );
	}

	@Test
	void testVerifyGloboSignatureFakeSignatureJwt() {
		assertFalse( globoTokenVerifier.verifyTokenSignature( fakeSignatureJwt ) );
	}

	@Test
	void testVerifyGloboSignatureInvalidJwt() {
		assertThrows( APICodedException.class, () -> {
			globoTokenVerifier.verifyTokenSignature( invalidJwt );
		} );
	}

	@Test
	void testVerifyGloboSignatureExpiredJwt() {
		assertFalse( globoTokenVerifier.verifyTokenSignature( expiredJwt ) );
	}

	@Test
	void testVerifyGloboSignatureAlternateConstructor() throws MalformedURLException {
		final TokenVerifier tokenVerifier = new TokenVerifier( GLOBO_JWK_URL,
				GLOBO_EXPECTED_JWS_ALG, GLOBO_REQUIRED_ISSUER, GLOBO_REQUIRED_CLAIMS );
		assertFalse( tokenVerifier.verifyTokenSignature( expiredJwt ) );
	}

	@Test
	void testVerifyGloboSignatureNullJwt() {
		assertThrows( APIIllegalArgumentException.class, () -> {
			globoTokenVerifier.verifyTokenSignature( null );
		} );
	}

	@Test
	void testVerifyGloboSignatureEmptyJwt() {
		assertThrows( APIIllegalArgumentException.class, () -> {
			globoTokenVerifier.verifyTokenSignature( "" );
		} );
	}

	private String generateToken( final KeyPair keyPair, final String keyId ) throws JOSEException {
		final RSAKey.Builder builder = new RSAKey.Builder(
				(RSAPublicKey) keyPair.getPublic() ).privateKey( keyPair.getPrivate() )
				.keyUse( KeyUse.SIGNATURE );
		builder.keyID( keyId );

		final RSAKey rsaJWK = builder.build();

		final JWSSigner signer = new RSASSASigner( rsaJWK );

		final JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().subject( SUBJECT )
				.jwtID( JWT_ID )
				.issueTime( new Date( new Date().getTime() ) )
				.issuer( GLOBO_REQUIRED_ISSUER )
				.build();

		final SignedJWT signedJWT = new SignedJWT(
				new JWSHeader.Builder( JWSAlgorithm.RS256 ).keyID( keyId ).build(), claimsSet );

		signedJWT.sign( signer );
		return signedJWT.serialize();
	}

	private JWK generateJWK( final KeyPair keyPair ) {
		return new RSAKey.Builder( (RSAPublicKey) keyPair.getPublic() ).privateKey(
				(RSAPrivateKey) keyPair.getPrivate() )
				.keyUse( KeyUse.SIGNATURE )
				.keyID( UUID.randomUUID().toString() )
				.build();
	}

	private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		final KeyPairGenerator gen = KeyPairGenerator.getInstance( "RSA" );
		gen.initialize( 2048 );
		return gen.generateKeyPair();
	}
}
