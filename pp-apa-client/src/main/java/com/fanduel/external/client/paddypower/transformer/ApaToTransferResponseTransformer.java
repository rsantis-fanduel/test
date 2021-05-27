package com.fanduel.external.client.paddypower.transformer;

import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.TransferResponseType;
import com.fanduel.external.client.paddypower.transfer.TransferResponse;
import com.fanduel.transform.Transformer;

public class ApaToTransferResponseTransformer implements
		Transformer<TransferResponseType, TransferResponse> {

	@Override
	public TransferResponse transform( final TransferResponseType source ) {
		return TransferResponse.Builder.aTransferResponse()
				.transferId( source.getResponse().getTransferId() )
				.token( source.getResponse().getToken() )
				.build();
	}
}
