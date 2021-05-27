package com.fanduel.external.client.paddypower.transfer;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import com.google.common.base.MoreObjects;

public class UserSessionDetails {

	private long accountId;
	private String initials;
	private boolean overrideIpBan;
	private Currency currency;
	private String country;
	private String firstName;
	private String nickname;
	private BigDecimal balance;
	private String token;

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId( final long accountId ) {
		this.accountId = accountId;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials( final String initials ) {
		this.initials = initials;
	}

	public boolean isOverrideIpBan() {
		return overrideIpBan;
	}

	public void setOverrideIpBan( final boolean overrideIpBan ) {
		this.overrideIpBan = overrideIpBan;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency( final Currency currency ) {
		this.currency = currency;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry( final String country ) {
		this.country = country;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName( final String firstName ) {
		this.firstName = firstName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname( final String nickname ) {
		this.nickname = nickname;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance( final BigDecimal balance ) {
		this.balance = balance;
	}

	public String getToken() {
		return token;
	}

	public void setToken( final String token ) {
		this.token = token;
	}

	@Override
	public boolean equals( final Object o ) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		final UserSessionDetails that = (UserSessionDetails) o;
		return accountId == that.accountId && overrideIpBan == that.overrideIpBan && Objects.equals(
				initials, that.initials ) && Objects.equals( currency,
				that.currency ) && Objects.equals( country, that.country ) && Objects.equals(
				firstName, that.firstName ) && Objects.equals( nickname,
				that.nickname ) && Objects.equals( balance, that.balance ) && Objects.equals( token,
				that.token );
	}

	@Override
	public int hashCode() {
		return Objects.hash( accountId, initials, overrideIpBan, currency, country, firstName,
				nickname, balance, token );
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "accountId", accountId )
				.toString();
	}

	public static final class Builder {
		private long accountId;
		private String initials;
		private boolean overrideIpBan;
		private Currency currency;
		private String country;
		private String firstName;
		private String nickname;
		private BigDecimal balance;
		private String token;

		private Builder() {}

		public static Builder anUserSessionDetails() { return new Builder(); }

		public Builder accountId( long accountId ) {
			this.accountId = accountId;
			return this;
		}

		public Builder initials( String initials ) {
			this.initials = initials;
			return this;
		}

		public Builder overrideIpBan( boolean overrideIpBan ) {
			this.overrideIpBan = overrideIpBan;
			return this;
		}

		public Builder currency( Currency currency ) {
			this.currency = currency;
			return this;
		}

		public Builder country( String country ) {
			this.country = country;
			return this;
		}

		public Builder firstName( String firstName ) {
			this.firstName = firstName;
			return this;
		}

		public Builder nickname( String nickname ) {
			this.nickname = nickname;
			return this;
		}

		public Builder balance( BigDecimal balance ) {
			this.balance = balance;
			return this;
		}

		public Builder token( String token ) {
			this.token = token;
			return this;
		}

		public UserSessionDetails build() {
			UserSessionDetails userSessionDetails = new UserSessionDetails();
			userSessionDetails.setAccountId( accountId );
			userSessionDetails.setInitials( initials );
			userSessionDetails.setOverrideIpBan( overrideIpBan );
			userSessionDetails.setCurrency( currency );
			userSessionDetails.setCountry( country );
			userSessionDetails.setFirstName( firstName );
			userSessionDetails.setNickname( nickname );
			userSessionDetails.setBalance( balance );
			userSessionDetails.setToken( token );
			return userSessionDetails;
		}
	}
}
