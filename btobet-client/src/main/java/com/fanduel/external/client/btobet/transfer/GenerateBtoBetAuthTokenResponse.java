package com.fanduel.external.client.btobet.transfer;

import java.math.BigDecimal;
import java.util.Objects;

import com.fanduel.transfer.annotation.TransferClass;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

@TransferClass
public class GenerateBtoBetAuthTokenResponse extends BtoBetResponse {
	@JsonProperty("AuthToken")
	private String authToken;

	@JsonProperty("Balance")
	private BigDecimal balance;

	@JsonProperty("Currency")
	private String currency;

	@JsonProperty("ExternalId")
	private long externalId;

	@JsonProperty("Address")
	private String address;

	@JsonProperty("BirthDate")
	private String birthdate;

	@JsonProperty("City")
	private String city;

	@JsonProperty("Country")
	private String country;

	@JsonProperty("Email")
	private String email;

	@JsonProperty("Gender")
	private String gender;

	@JsonProperty("IPAddress")
	private String ipAddress;

	@JsonProperty("Language")
	private String language;

	@JsonProperty("FirstName")
	private String firstName;

	@JsonProperty("LastName")
	private String lastName;

	@JsonProperty("NewCreatedUser")
	private boolean newCreatedUser;

	@JsonProperty("Nickname")
	private String nickName;

	@JsonProperty("Phone")
	private String phone;

	@JsonProperty("Postcode")
	private String postcode;

	@JsonProperty("Username")
	private String username;

	public String getAuthToken() {
		return authToken;
	}

	public long getExternalId() {
		return externalId;
	}

	public String getCurrency() {
		return currency;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getUsername() {
		return username;
	}

	public String getPostcode() {
		return postcode;
	}

	public String getLastName() {
		return lastName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getCity() {
		return city;
	}

	public String getAddress() {
		return address;
	}

	public String getGender() {
		return gender;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public String getCountry() {
		return country;
	}

	public String getLanguage() {
		return language;
	}

	public String getEmail() {
		return email;
	}

	public String getNickName() {
		return nickName;
	}

	public String getPhone() {
		return phone;
	}

	public boolean getNewCreatedUser() {
		return newCreatedUser;
	}

	public void setFirstName( final String firstName ) {
		this.firstName = firstName;
	}

	public void setUsername( final String username ) {
		this.username = username;
	}

	public void setPostcode( final String postcode ) {
		this.postcode = postcode;
	}

	public void setLastName( final String lastName ) {
		this.lastName = lastName;
	}

	public void setIpAddress( final String ipAddress ) {
		this.ipAddress = ipAddress;
	}

	public void setCity( final String city ) {
		this.city = city;
	}

	public void setAddress( final String address ) {
		this.address = address;
	}

	public void setGender( final String gender ) {
		this.gender = gender;
	}

	public void setLanguage( final String language ) {
		this.language = language;
	}

	public void setCountry( final String country ) {
		this.country = country;
	}

	public void setBirthdate( final String birthdate ) {
		this.birthdate = birthdate;
	}

	public void setEmail( final String email ) {
		this.email = email;
	}

	public void setNewCreatedUser( final boolean newCreatedUser ) {
		this.newCreatedUser = newCreatedUser;
	}

	public void setNickName( final String nickName ) {
		this.nickName = nickName;
	}

	public void setPhone( final String phone ) {
		this.phone = phone;
	}

	public void setAuthToken( final String authToken ) {
		this.authToken = authToken;
	}

	public void setCurrency( final String currency ) {
		this.currency = currency;
	}

	public void setExternalId( final long externalId ) {
		this.externalId = externalId;
	}

	public void setBalance( final BigDecimal balance ) {
		this.balance = balance;
	}

	@Override
	public boolean equals( final Object o ) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		final GenerateBtoBetAuthTokenResponse response = (GenerateBtoBetAuthTokenResponse) o;
		return externalId == response.externalId && newCreatedUser == response.newCreatedUser && Objects
				.equals( authToken, response.authToken ) && Objects.equals( balance, response.balance ) && Objects.equals( currency,
				response.currency ) && Objects.equals( address, response.address ) && Objects.equals( birthdate,
				response.birthdate ) && Objects.equals( city, response.city ) && Objects.equals(
				country, response.country ) && Objects.equals( email, response.email ) && Objects.equals( gender, response.gender ) && Objects.equals(
				ipAddress, response.ipAddress ) && Objects.equals( language, response.language ) && Objects.equals( firstName,
				response.firstName ) && Objects.equals( lastName, response.lastName ) && Objects.equals( nickName,
				response.nickName ) && Objects.equals( phone, response.phone ) && Objects.equals(
				postcode, response.postcode ) && Objects.equals( username, response.username );
	}

	@Override
	public int hashCode() {
		return Objects.hash( authToken, balance, currency, externalId, address, birthdate, city,
				country, email, gender, ipAddress, language, firstName, lastName, newCreatedUser,
				nickName, phone, postcode, username );
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "authToken", authToken )
				.add( "balance", balance )
				.add( "currency", currency )
				.add( "externalId", externalId )
				.add( "address", address )
				.add( "birthdate", birthdate )
				.add( "city", city )
				.add( "country", country )
				.add( "email", email )
				.add( "gender", gender )
				.add( "ipAddress", ipAddress )
				.add( "language", language )
				.add( "lastName", lastName )
				.add( "firstName", firstName )
				.add( "newCreatedUser", newCreatedUser )
				.add( "nickName", nickName )
				.add( "phone", phone )
				.add( "postcode", postcode )
				.add( "username", username )
				.toString();
	}

	public static final class Builder {
		private int statusCode;
		private String statusMessage;
		private String authToken;
		private BigDecimal balance;
		private String currency;
		private long externalId;
		private String address;
		private String birthdate;
		private String city;
		private String country;
		private String email;
		private String gender;
		private String ipAddress;
		private String language;
		private String lastName;
		private String firstName;
		private boolean newCreatedUser;
		private String nickName;
		private String phone;
		private String postcode;
		private String username;

		private Builder() {}

		public static Builder aGenerateBtobetAuthTokenResponse() {
			return new Builder();
		}

		public Builder statusCode( int statusCode ) {
			this.statusCode = statusCode;
			return this;
		}

		public Builder statusMessage( String statusMessage ) {
			this.statusMessage = statusMessage;
			return this;
		}

		public Builder authToken( String authToken ) {
			this.authToken = authToken;
			return this;
		}

		public Builder balance( BigDecimal balance ) {
			this.balance = balance;
			return this;
		}

		public Builder currency( String currency ) {
			this.currency = currency;
			return this;
		}

		public Builder externalId( long externalId ) {
			this.externalId = externalId;
			return this;
		}

		public Builder address( String address ) {
			this.address = address;
			return this;
		}

		public Builder birthdate( String birthdate ) {
			this.birthdate = birthdate;
			return this;
		}

		public Builder city( String city ) {
			this.city = city;
			return this;
		}

		public Builder country( String country ) {
			this.country = country;
			return this;
		}

		public Builder email( String email ) {
			this.email = email;
			return this;
		}

		public Builder gender( String gender ) {
			this.gender = gender;
			return this;
		}

		public Builder ipAddress( String ipAddress ) {
			this.ipAddress = ipAddress;
			return this;
		}

		public Builder language( String language ) {
			this.language = language;
			return this;
		}

		public Builder lastName( String lastName ) {
			this.lastName = lastName;
			return this;
		}

		public Builder firstName( String firstName ) {
			this.firstName = firstName;
			return this;
		}

		public Builder nickName( String nickName ) {
			this.nickName = nickName;
			return this;
		}

		public Builder phone( String phone ) {
			this.phone = phone;
			return this;
		}

		public Builder postcode( String postcode ) {
			this.postcode = postcode;
			return this;
		}

		public Builder username( String username ) {
			this.username = username;
			return this;
		}

		public Builder newCreatedUser( boolean newCreatedUser ) {
			this.newCreatedUser = newCreatedUser;
			return this;
		}

		public GenerateBtoBetAuthTokenResponse build() {
			GenerateBtoBetAuthTokenResponse generateBtobetAuthTokenResponse = new GenerateBtoBetAuthTokenResponse();
			generateBtobetAuthTokenResponse.setStatusCode( statusCode );
			generateBtobetAuthTokenResponse.setStatusMessage( statusMessage );
			generateBtobetAuthTokenResponse.setAuthToken( authToken );
			generateBtobetAuthTokenResponse.setBalance( balance );
			generateBtobetAuthTokenResponse.setCurrency( currency );
			generateBtobetAuthTokenResponse.setExternalId( externalId );
			generateBtobetAuthTokenResponse.setCity( city );
			generateBtobetAuthTokenResponse.setCountry( country );
			generateBtobetAuthTokenResponse.setBirthdate( birthdate );
			generateBtobetAuthTokenResponse.setAddress( address );
			generateBtobetAuthTokenResponse.setEmail( email );
			generateBtobetAuthTokenResponse.setGender( gender );
			generateBtobetAuthTokenResponse.setIpAddress( ipAddress );
			generateBtobetAuthTokenResponse.setLanguage( language );
			generateBtobetAuthTokenResponse.setLastName( lastName );
			generateBtobetAuthTokenResponse.setNickName( nickName );
			generateBtobetAuthTokenResponse.setPhone( phone );
			generateBtobetAuthTokenResponse.setPostcode( postcode );
			generateBtobetAuthTokenResponse.setUsername( username );
			generateBtobetAuthTokenResponse.setFirstName( firstName );
			return generateBtobetAuthTokenResponse;
		}

	}
}
