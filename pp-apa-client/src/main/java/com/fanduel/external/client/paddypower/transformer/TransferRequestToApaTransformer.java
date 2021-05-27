package com.fanduel.external.client.paddypower.transformer;

import java.math.RoundingMode;

import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.TransferRequestType;
import com.fanduel.external.client.paddypower.transfer.TransferRequest;
import com.fanduel.transform.Transformer;

public class TransferRequestToApaTransformer implements
		Transformer<TransferRequest, TransferRequestType> {

	@Override
	public TransferRequestType transform( final TransferRequest source ) {
		TransferRequestType transferRequestType = new TransferRequestType();
		transferRequestType.setAccountId( source.getAccountId() );
		transferRequestType.setAmount( source.getAmount()
				.setScale( 2, RoundingMode.HALF_EVEN )
				.toString() );
		transferRequestType.setCurrency( source.getCurrency().getCurrencyCode() );
		transferRequestType.setToken( source.getToken() );
		transferRequestType.setTransferRef( source.getReference() );
		transferRequestType.setTransferDesc( source.getDescription() );

		return transferRequestType;
	}
}
