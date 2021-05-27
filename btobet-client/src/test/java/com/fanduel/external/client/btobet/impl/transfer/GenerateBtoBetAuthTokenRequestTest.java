package com.fanduel.external.client.btobet.impl.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fanduel.external.client.btobet.transfer.GenerateBtoBetAuthTokenRequest;

public class GenerateBtoBetAuthTokenRequestTest {
	private final String API_USERNAME = "fanduel";
	private final String API_PASSWORD = "fanduel123!";
	private final int CLIENT_ID = 123;
	private final String GLOBO_ID_1 = "abc";
	private final String GLOBO_ID_2 = "def";
	private final String GLOBO_ID_3 = "ghi";
	private final String CURRENCY_ISO = "BRL";
	private final String LANGUAGE_ISO = "PT";
	private final String COUNTRY_ISO = "BR";
	private final String USERNAME = "username";
	private final String FIRST_NAME = "First";
	private final String LAST_NAME = "Last";
	private final String ADDRESS = "Address";
	private final String GENDER = "F";
	private final String PHONE_NUMBER = "Phone";
	private final String DATE_OF_BIRTH = "Birthday";
	private final String CITY = "City";
	private final String POSTCODE = "Postcode";
	private final String IP_ADDRESS = "IPAddress";
	private final String EMAIL = "Email";

	private GenerateBtoBetAuthTokenRequest request1;
	private GenerateBtoBetAuthTokenRequest request2;
	private GenerateBtoBetAuthTokenRequest request3;

	@BeforeEach
	void before() {
		request1 = (GenerateBtoBetAuthTokenRequest) GenerateBtoBetAuthTokenRequest.builder()
				.globoId( GLOBO_ID_1 )
				.currencyISO( CURRENCY_ISO )
				.languageISO( LANGUAGE_ISO )
				.countryISO( COUNTRY_ISO )
				.username( USERNAME )
				.firstName( FIRST_NAME )
				.lastName( LAST_NAME )
				.address( ADDRESS )
				.gender( GENDER )
				.phoneNumber( PHONE_NUMBER )
				.dateOfBirth( DATE_OF_BIRTH )
				.city( CITY )
				.postcode( POSTCODE )
				.ipAddress( IP_ADDRESS )
				.email( EMAIL )
				.apiUsername( API_USERNAME )
				.apiPassword( API_PASSWORD )
				.clientId( CLIENT_ID )
				.build();

		request2 = (GenerateBtoBetAuthTokenRequest) GenerateBtoBetAuthTokenRequest.builder()
				.globoId( GLOBO_ID_2 )
				.currencyISO( CURRENCY_ISO )
				.languageISO( LANGUAGE_ISO )
				.countryISO( COUNTRY_ISO )
				.username( USERNAME )
				.firstName( FIRST_NAME )
				.lastName( LAST_NAME )
				.address( ADDRESS )
				.gender( GENDER )
				.phoneNumber( PHONE_NUMBER )
				.dateOfBirth( DATE_OF_BIRTH )
				.city( CITY )
				.postcode( POSTCODE )
				.ipAddress( IP_ADDRESS )
				.email( EMAIL )
				.apiUsername( API_USERNAME )
				.apiPassword( API_PASSWORD )
				.clientId( CLIENT_ID )
				.build();

		request3 = (GenerateBtoBetAuthTokenRequest) GenerateBtoBetAuthTokenRequest.builder()
				.globoId( GLOBO_ID_3 )
				.globoId( GLOBO_ID_3 )
				.currencyISO( CURRENCY_ISO )
				.languageISO( LANGUAGE_ISO )
				.countryISO( COUNTRY_ISO )
				.username( USERNAME )
				.firstName( FIRST_NAME )
				.lastName( LAST_NAME )
				.address( ADDRESS )
				.gender( GENDER )
				.phoneNumber( PHONE_NUMBER )
				.dateOfBirth( DATE_OF_BIRTH )
				.city( CITY )
				.postcode( POSTCODE )
				.ipAddress( IP_ADDRESS )
				.email( EMAIL )
				.apiUsername( API_USERNAME )
				.apiPassword( API_PASSWORD )
				.clientId( CLIENT_ID )
				.build();
	}

	@Test
	public void testBuild() {
		assertEquals( request1.getApiUsername(), API_USERNAME );
		assertEquals( request1.getApiPassword(), API_PASSWORD );
		assertEquals( request1.getClientId(), CLIENT_ID );
		assertEquals( request1.getGloboId(), GLOBO_ID_1 );
		assertEquals( request1.getCurrencyISO(), CURRENCY_ISO );
		assertEquals( request1.getLanguageISO(), LANGUAGE_ISO );
		assertEquals( request1.getCountryISO(), COUNTRY_ISO );
		assertEquals( request1.getUsername(), USERNAME );
		assertEquals( request1.getFirstName(), FIRST_NAME );
		assertEquals( request1.getLastName(), LAST_NAME );
		assertEquals( request1.getAddress(), ADDRESS );
		assertEquals( request1.getGender(), GENDER );
		assertEquals( request1.getPhoneNumber(), PHONE_NUMBER );
		assertEquals( request1.getDateOfBirth(), DATE_OF_BIRTH );
		assertEquals( request1.getCity(), CITY );
		assertEquals( request1.getPostcode(), POSTCODE );
		assertEquals( request1.getIpAddress(), IP_ADDRESS );
		assertEquals( request1.getEmail(), EMAIL );

		assertEquals( request2.getGloboId(), GLOBO_ID_2 );

		assertEquals( request3.getGloboId(), GLOBO_ID_3 );
	}

	@Test
	public void testHashCodeEqualWhenSame() {
		request2.setGloboId( GLOBO_ID_1 );
		assertEquals( request1.hashCode(), request2.hashCode() );
		assertEquals( request1, request2 );
	}

	@Test
	public void testToStringEqualWhenSame() {
		request2.setGloboId( GLOBO_ID_1 );
		assertEquals( request1.toString(), request2.toString() );
		assertEquals( request1, request2 );
	}

	@Test
	public void testHashCodeNotEqualWhenDifferent() {
		assertNotEquals( request1.hashCode(), request3.hashCode() );
		assertNotEquals( request1, request3 );
	}

	@Test
	public void testToStringNotEqualWhenDifferent() {
		assertNotEquals( request1.toString(), request3.toString() );
		assertNotEquals( request1, request3 );
	}

	@Test
	public void testObjectEquality() {
		assertEquals( request1, request1 );
		assertNotEquals( null, request1 );
		assertNotEquals( request1, new GenerateBtoBetAuthTokenRequest() );
	}
}
