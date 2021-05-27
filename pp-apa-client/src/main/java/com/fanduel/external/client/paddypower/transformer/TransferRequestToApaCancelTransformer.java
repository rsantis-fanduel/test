package com.fanduel.external.client.paddypower.transformer;

import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.CancelTransferRequestType;
import com.fanduel.external.client.paddypower.transfer.TransferRequest;
import com.fanduel.transform.Transformer;

public class TransferRequestToApaCancelTransformer implements Transformer<TransferRequest, CancelTransferRequestType> {

	@Override
	public CancelTransferRequestType transform( final TransferRequest source ) {
		CancelTransferRequestType cancelTransferRequestType = new CancelTransferRequestType();
		cancelTransferRequestType.setTransferRef( source.getReference() );

		return cancelTransferRequestType;
	}
}
