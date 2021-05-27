package com.fanduel.external.client.btobet.transfer;

import com.fanduel.transfer.annotation.TransferClass;

@TransferClass
public class CancelBetRequest extends BtoBetBettingRelatedRequest {

	public CancelBetRequest() {
		super();
	}

	CancelBetRequest( final Builder builder ) {
		super( builder );
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder extends BtoBetBettingRelatedRequest.Builder {

		@Override
		public CancelBetRequest build() {
			return new CancelBetRequest( this );
		}
	}

}
