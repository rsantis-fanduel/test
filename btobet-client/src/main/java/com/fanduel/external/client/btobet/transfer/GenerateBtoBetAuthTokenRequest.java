package com.fanduel.external.client.btobet.transfer;

import java.util.Objects;

import com.fanduel.transfer.annotation.TransferClass;
import com.fasterxml.jackson.annotation.JsonProperty;

@TransferClass
public class GenerateBtoBetAuthTokenRequest extends BtoBetRequestGeneral {

	@JsonProperty(value = "GloboId", required = true)
	private String globoId;

	@JsonProperty(value = "CurrencyISO")
	private String currencyISO;

	@JsonProperty(value = "LanguageISO")
	private String languageISO;

	@JsonProperty(value = "CountryISO")
	private String countryISO;

	@JsonProperty(value = "Username")
	private String username;

	@JsonProperty(value = "FirstName")
	private String firstName;

	@JsonProperty(value = "LastName")
	private String lastName;

	@JsonProperty(value = "Address")
	private String address;

	@JsonProperty(value = "Gender")
	private String gender;

	@JsonProperty(value = "PhoneNumber")
	private String phoneNumber;

	@JsonProperty(value = "DateOfBirth")
	private String dateOfBirth;

	@JsonProperty(value = "City")
	private String city;

	@JsonProperty(value = "Postcode")
	private String postcode;

	@JsonProperty(value = "IPAddress")
	private String ipAddress;

	@JsonProperty(value = "Email")
	private String email;

	public GenerateBtoBetAuthTokenRequest() {
		super();
	}

	GenerateBtoBetAuthTokenRequest( final Builder builder ) {
		super( builder );
		globoId = builder.globoId;
		gender = builder.gender;
		address = builder.address;
		city = builder.city;
		countryISO = builder.countryISO;
		currencyISO = builder.currencyISO;
		dateOfBirth = builder.dateOfBirth;
		firstName = builder.firstName;
		ipAddress = builder.ipAddress;
		languageISO = builder.languageISO;
		lastName = builder.lastName;
		phoneNumber = builder.phoneNumber;
		postcode = builder.postcode;
		email = builder.email;
		username = builder.username;
	}

	public String getGloboId() {
		return globoId;
	}

	public String getGender() {
		return gender;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getCountryISO() {
		return countryISO;
	}

	public String getCurrencyISO() {
		return currencyISO;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getLanguageISO() {
		return languageISO;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPostcode() {
		return postcode;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public void setGender( final String gender ) {
		this.gender = gender;
	}

	public void setAddress( final String address ) {
		this.address = address;
	}

	public void setCity( final String city ) {
		this.city = city;
	}

	public void setCountryISO( final String countryISO ) {
		this.countryISO = countryISO;
	}

	public void setCurrencyISO( final String currencyISO ) {
		this.currencyISO = currencyISO;
	}

	public void setDateOfBirth( final String dateOfBirth ) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setFirstName( final String firstName ) {
		this.firstName = firstName;
	}

	public void setIpAddress( final String ipAddress ) {
		this.ipAddress = ipAddress;
	}

	public void setLanguageISO( final String languageISO ) {
		this.languageISO = languageISO;
	}

	public void setLastName( final String lastName ) {
		this.lastName = lastName;
	}

	public void setPhoneNumber( final String phoneNumber ) {
		this.phoneNumber = phoneNumber;
	}

	public void setPostcode( final String postcode ) {
		this.postcode = postcode;
	}

	public void setUsername( final String username ) {
		this.username = username;
	}

	public void setEmail( final String email ) {
		this.email = email;
	}

	public void setGloboId( final String globoId ) {
		this.globoId = globoId;
	}

	@Override
	public boolean equals( final Object o ) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		if ( !super.equals( o ) ) {
			return false;
		}
		final GenerateBtoBetAuthTokenRequest that = (GenerateBtoBetAuthTokenRequest) o;
		return Objects.equals( globoId, that.globoId ) && Objects.equals( currencyISO,
				that.currencyISO ) && Objects.equals( languageISO,
				that.languageISO ) && Objects.equals( countryISO,
				that.countryISO ) && Objects.equals( username, that.username ) && Objects.equals(
				firstName, that.firstName ) && Objects.equals( lastName,
				that.lastName ) && Objects.equals( address, that.address ) && Objects.equals(
				gender, that.gender ) && Objects.equals( phoneNumber,
				that.phoneNumber ) && Objects.equals( dateOfBirth,
				that.dateOfBirth ) && Objects.equals( city, that.city ) && Objects.equals( postcode,
				that.postcode ) && Objects.equals( ipAddress, that.ipAddress ) && Objects.equals(
				email, that.email );
	}

	@Override
	public int hashCode() {
		return Objects.hash( super.hashCode(), globoId, currencyISO, languageISO, countryISO,
				username, firstName, lastName, address, gender, phoneNumber, dateOfBirth, city,
				postcode, ipAddress, email );
	}

	@Override
	public String toString() {
		return super.toStringHelper()
				.add( "globoId", globoId )
				.add( "currencyIso", currencyISO )
				.add( "languageIso", languageISO )
				.add( "countryIso", countryISO )
				.add( "username", username )
				.add( "firstName", firstName )
				.add( "lastName", lastName )
				.add( "address", address )
				.add( "gender", gender )
				.add( "phoneNumber", phoneNumber )
				.add( "dateOfBirth", dateOfBirth )
				.add( "city", city )
				.add( "postcode", postcode )
				.add( "ipAddress", ipAddress )
				.add( "email", email )
				.toString();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder extends BtoBetRequestGeneral.Builder {
		private String globoId;
		private String currencyISO;
		private String languageISO;
		private String countryISO;
		private String username;
		private String firstName;
		private String lastName;
		private String address;
		private String gender;
		private String phoneNumber;
		private String dateOfBirth;
		private String city;
		private String postcode;
		private String ipAddress;
		private String email;

		public Builder globoId( final String globoId ) {
			this.globoId = globoId;
			return this;
		}

		public Builder currencyISO( final String currencyISO ) {
			this.currencyISO = currencyISO;
			return this;
		}

		public Builder languageISO( final String languageISO ) {
			this.languageISO = languageISO;
			return this;
		}

		public Builder countryISO( final String countryISO ) {
			this.countryISO = countryISO;
			return this;
		}

		public Builder username( final String username ) {
			this.username = username;
			return this;
		}

		public Builder firstName( final String firstName ) {
			this.firstName = firstName;
			return this;
		}

		public Builder lastName( final String lastName ) {
			this.lastName = lastName;
			return this;
		}

		public Builder address( final String address ) {
			this.address = address;
			return this;
		}

		public Builder gender( final String gender ) {
			this.gender = gender;
			return this;
		}

		public Builder phoneNumber( final String phoneNumber ) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public Builder dateOfBirth( final String dateOfBirth ) {
			this.dateOfBirth = dateOfBirth;
			return this;
		}

		public Builder city( final String city ) {
			this.city = city;
			return this;
		}

		public Builder postcode( final String postcode ) {
			this.postcode = postcode;
			return this;
		}

		public Builder ipAddress( final String ipAddress ) {
			this.ipAddress = ipAddress;
			return this;
		}

		public Builder email( final String email ) {
			this.email = email;
			return this;
		}

		@Override
		public GenerateBtoBetAuthTokenRequest build() {
			return new GenerateBtoBetAuthTokenRequest( this );
		}
	}
}
