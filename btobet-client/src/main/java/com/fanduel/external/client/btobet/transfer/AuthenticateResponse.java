package com.fanduel.external.client.btobet.transfer;

import java.util.Objects;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticateResponse extends BtoBetResponse {

	@JsonProperty("ExternalId")
	private long externalId;

	@JsonProperty("Balance")
	private BigDecimal balance;

	@JsonProperty("Currency")
	private String currency;

	@JsonProperty("Gender")
	private String gender;

	@JsonProperty("Language")
	private String language;

	@JsonProperty("Country")
	private String country;

	@JsonProperty("Nickname")
	private String nickname;

	@JsonProperty("BirthDate")
	private String birthdate;

	@JsonProperty("Phone")
	private String phone;

	public long getExternalId() {
		return externalId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public String getCurrency() {
		return currency;
	}

	public String getGender() {
		return gender;
	}

	public String getLanguage() {
		return language;
	}

	public String getCountry() {
		return country;
	}

	public String getNickname() {
		return nickname;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public String getPhone() {
		return phone;
	}

	public void setExternalId( final long externalId ) {
		this.externalId = externalId;
	}

	public void setBalance( final BigDecimal balance ) {
		this.balance = balance;
	}

	public void setCurrency( final String currency ) {
		this.currency = currency;
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

	public void setNickname( final String nickname ) {
		this.nickname = nickname;
	}

	public void setBirthdate( final String birthdate ) {
		this.birthdate = birthdate;
	}

	public void setPhone( final String phone ) {
		this.phone = phone;
	}

	@Override
	public boolean equals( final Object o ) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		final AuthenticateResponse response = (AuthenticateResponse) o;
		return externalId == response.externalId && Objects.equals( balance,
				response.balance ) && Objects.equals( currency,
				response.currency ) && Objects.equals( gender, response.gender ) && Objects.equals(
				language, response.language ) && Objects.equals( country,
				response.country ) && Objects.equals( nickname,
				response.nickname ) && Objects.equals( birthdate,
				response.birthdate ) && Objects.equals( phone, response.phone );
	}

	@Override
	public int hashCode() {
		return Objects.hash( externalId, balance, currency, gender, language, country, nickname,
				birthdate, phone );
	}

	public static final class Builder {
		private int statusCode;
		private String statusMessage;
		private long externalId;
		private BigDecimal balance;
		private String currency;
		private String gender;
		private String language;
		private String country;
		private String nickname;
		private String birthdate;
		private String phone;

		private Builder() {}

		public static Builder anAuthenticateResponse() {
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

		public Builder externalId( long externalId ) {
			this.externalId = externalId;
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

		public Builder gender( String gender ) {
			this.gender = gender;
			return this;
		}

		public Builder language( String language ) {
			this.language = language;
			return this;
		}

		public Builder country( String country ) {
			this.country = country;
			return this;
		}

		public Builder nickname( String nickname ) {
			this.nickname = nickname;
			return this;
		}

		public Builder birthdate( String birthdate ) {
			this.birthdate = birthdate;
			return this;
		}

		public Builder phone( String phone ) {
			this.phone = phone;
			return this;
		}

		public AuthenticateResponse build() {
			AuthenticateResponse authenticateResponse = new AuthenticateResponse();
			authenticateResponse.setStatusCode( statusCode );
			authenticateResponse.setStatusMessage( statusMessage );
			authenticateResponse.setExternalId( externalId );
			authenticateResponse.setBalance( balance );
			authenticateResponse.setCurrency( currency );
			authenticateResponse.setGender( gender );
			authenticateResponse.setLanguage( language );
			authenticateResponse.setCountry( country );
			authenticateResponse.setNickname( nickname );
			authenticateResponse.setBirthdate( birthdate );
			authenticateResponse.setPhone( phone );
			return authenticateResponse;
		}
	}
}
