package com.fanduel.external.client.paddypower.transformer;

import com.fanduel.external.client.paddypower.impl.jaxws.generated.gap.RetrieveUserSessionDetailsRequestType;
import com.fanduel.external.client.paddypower.transfer.UserSessionDetailsRequest;
import com.fanduel.transform.Transformer;

public class UserSessionDetailsRequestToGapTransformer implements
		Transformer<UserSessionDetailsRequest, RetrieveUserSessionDetailsRequestType> {

	@Override
	public RetrieveUserSessionDetailsRequestType transform( final UserSessionDetailsRequest source ) {
		RetrieveUserSessionDetailsRequestType retrieveUserSessionDetailsRequestType = new RetrieveUserSessionDetailsRequestType();
		retrieveUserSessionDetailsRequestType.setToken( source.getToken() );

		return retrieveUserSessionDetailsRequestType;
	}
}
