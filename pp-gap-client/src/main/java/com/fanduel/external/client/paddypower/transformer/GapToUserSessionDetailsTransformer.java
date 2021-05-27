package com.fanduel.external.client.paddypower.transformer;

import java.math.BigDecimal;
import java.util.Currency;

import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.RetrieveUserSessionDetailsResponseType;
import com.fanduel.external.client.paddypower.transfer.UserSessionDetails;
import com.fanduel.transform.Transformer;

public class GapToUserSessionDetailsTransformer implements
		Transformer<RetrieveUserSessionDetailsResponseType, UserSessionDetails> {

	@Override
	public UserSessionDetails transform( final RetrieveUserSessionDetailsResponseType source ) {
		return UserSessionDetails.Builder.anUserSessionDetails()
				.accountId( source.getResponse().getProfile().getAccountId() )
				.firstName( source.getResponse().getProfile().getFirstName() )
				.nickname( source.getResponse().getProfile().getNickname() )
				.initials( source.getResponse().getProfile().getInitials() )
				.country( source.getResponse().getProfile().getCountryCode() )
				.currency( Currency.getInstance( source.getResponse().getProfile().getCurrency() ) )
				.balance( new BigDecimal( source.getResponse().getBalance() ) )
				.token( source.getResponse().getToken() )
				.overrideIpBan( source.getResponse().getProfile().isOverrideIpBan() )
				.build();
	}
}
