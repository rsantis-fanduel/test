package com.fanduel.external.client.btobet.impl.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fanduel.external.client.btobet.transfer.BtoBetResponse;
import com.fanduel.external.client.btobet.transfer.GenerateBtoBetAuthTokenResponse;

class GenerateBtoBetAuthTokenResponseTest {

	private final static int HAPPY_PATH_STATUS_CODE = 0;
	private final static String AUTH_TOKEN = "authtoken";
	private final static BigDecimal BALANCE_1 = new BigDecimal( "1500.00" );
	private final static BigDecimal BALANCE_2 = new BigDecimal( "1000.00" );
	private final static BigDecimal BALANCE_3 = new BigDecimal( "500.00" );
	private final static long EXTERNAL_ID_1 = 12345l;
	private final static long EXTERNAL_ID_2 = 54321l;
	private final static long EXTERNAL_ID_3 = 13579l;
	private final static String CURRENCY_CODE = "BRL";
	private final static String ADDRESS = "ADDRESS";
	private final static String BIRTHDATE = "BIRTHDATE";
	private final static String CITY = "CITY";
	private final static String COUNTRY = "COUNTRY";
	private final static String EMAIL = "EMAIL";
	private final static String GENDER = "GENDER";
	private final static String IP_ADDRESS = "IPADDRESS";
	private final static String LANGUAGE = "LANGUAGE";
	private final static String FIRST_NAME = "FIRST";
	private final static String LAST_NAME = "LAST";
	private final static boolean NEW_CREATED_USER = false;
	private final static String NICKNAME = "NICKNAME";
	private final static String PHONE = "PHONE";
	private final static String POSTCODE = "POSTCODE";
	private final static String USERNAME = "USERNAME";

	private GenerateBtoBetAuthTokenResponse response1;
	private GenerateBtoBetAuthTokenResponse response2;
	private GenerateBtoBetAuthTokenResponse response3;

	@BeforeEach
	void before() {
		response1 = GenerateBtoBetAuthTokenResponse.Builder.aGenerateBtobetAuthTokenResponse()
				.statusCode( HAPPY_PATH_STATUS_CODE )
				.statusMessage( null )
				.authToken( AUTH_TOKEN )
				.balance( BALANCE_1 )
				.currency( CURRENCY_CODE )
				.externalId( EXTERNAL_ID_1 )
				.address( ADDRESS )
				.birthdate( BIRTHDATE )
				.city( CITY )
				.country( COUNTRY )
				.email( EMAIL )
				.gender( GENDER )
				.ipAddress( IP_ADDRESS )
				.language( LANGUAGE )
				.firstName( FIRST_NAME )
				.lastName( LAST_NAME )
				.newCreatedUser( NEW_CREATED_USER )
				.nickName( NICKNAME )
				.phone( PHONE )
				.postcode( POSTCODE )
				.username( USERNAME )
				.build();

		response2 = GenerateBtoBetAuthTokenResponse.Builder.aGenerateBtobetAuthTokenResponse()
				.statusCode( HAPPY_PATH_STATUS_CODE )
				.statusMessage( null )
				.authToken( AUTH_TOKEN )
				.balance( BALANCE_2 )
				.currency( CURRENCY_CODE )
				.externalId( EXTERNAL_ID_2 )
				.address( ADDRESS )
				.birthdate( BIRTHDATE )
				.city( CITY )
				.country( COUNTRY )
				.email( EMAIL )
				.gender( GENDER )
				.ipAddress( IP_ADDRESS )
				.language( LANGUAGE )
				.firstName( FIRST_NAME )
				.lastName( LAST_NAME )
				.newCreatedUser( NEW_CREATED_USER )
				.nickName( NICKNAME )
				.phone( PHONE )
				.postcode( POSTCODE )
				.username( USERNAME )
				.build();

		response3 = GenerateBtoBetAuthTokenResponse.Builder.aGenerateBtobetAuthTokenResponse()
				.statusCode( HAPPY_PATH_STATUS_CODE )
				.statusMessage( null )
				.authToken( AUTH_TOKEN )
				.balance( BALANCE_3 )
				.currency( CURRENCY_CODE )
				.externalId( EXTERNAL_ID_3 )
				.address( ADDRESS )
				.birthdate( BIRTHDATE )
				.city( CITY )
				.country( COUNTRY )
				.email( EMAIL )
				.gender( GENDER )
				.ipAddress( IP_ADDRESS )
				.language( LANGUAGE )
				.firstName( FIRST_NAME )
				.lastName( LAST_NAME )
				.newCreatedUser( NEW_CREATED_USER )
				.nickName( NICKNAME )
				.phone( PHONE )
				.postcode( POSTCODE )
				.username( USERNAME )
				.build();
	}

	@Test
	void testBuild() {
		assertEquals( AUTH_TOKEN, response1.getAuthToken() );
		assertEquals( BALANCE_1, response1.getBalance() );
		assertEquals( CURRENCY_CODE, response1.getCurrency() );
		assertEquals( EXTERNAL_ID_1, response1.getExternalId() );
		assertEquals( ADDRESS, response1.getAddress() );
		assertEquals( BIRTHDATE, response1.getBirthdate() );
		assertEquals( CITY, response1.getCity() );
		assertEquals( COUNTRY, response1.getCountry() );
		assertEquals( EMAIL, response1.getEmail() );
		assertEquals( GENDER, response1.getGender() );
		assertEquals( IP_ADDRESS, response1.getIpAddress() );
		assertEquals( LANGUAGE, response1.getLanguage() );
		assertEquals( FIRST_NAME, response1.getFirstName() );
		assertEquals( LAST_NAME, response1.getLastName() );
		assertEquals( NEW_CREATED_USER, response1.getNewCreatedUser() );
		assertEquals( NICKNAME, response1.getNickName() );
		assertEquals( PHONE, response1.getPhone() );
		assertEquals( POSTCODE, response1.getPostcode() );
		assertEquals( USERNAME, response1.getUsername() );

		assertEquals( BALANCE_2, response2.getBalance() );
		assertEquals( EXTERNAL_ID_2, response2.getExternalId() );

		assertEquals( BALANCE_3, response3.getBalance() );
		assertEquals( EXTERNAL_ID_3, response3.getExternalId() );
	}

	@Test
	void testHashCodeEqualWhenSame() {
		response2.setBalance( BALANCE_1 );
		response2.setExternalId( EXTERNAL_ID_1 );
		assertEquals( response1.hashCode(), response2.hashCode() );
		assertEquals( response1, response2 );
	}

	@Test
	void testToStringEqualWhenSame() {
		response2.setBalance( BALANCE_1 );
		response2.setExternalId( EXTERNAL_ID_1 );
		assertEquals( response1.toString(), response2.toString() );
		assertEquals( response1, response2 );
	}

	@Test
	void testHashCodeNotEqualWhenDifferent() {
		response3.setNewCreatedUser( true );
		assertNotEquals( response1.hashCode(), response3.hashCode() );
		assertNotEquals( response1, response3 );
	}

	@Test
	void testToStringNotEqualWhenDifferent() {
		assertNotEquals( response1.toString(), response3.toString() );
		assertNotEquals( response1, response3 );
	}

	@Test
	void testObjectEquality() {
		assertEquals( response1, response1 );
		assertNotEquals( null, response1 );
		assertNotEquals( response1, new GenerateBtoBetAuthTokenResponse() );
		assertNotEquals( response1, new BtoBetResponse() );
	}
}
