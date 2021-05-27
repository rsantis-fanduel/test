package com.fanduel.external.client.btobet.transfer;

import com.fanduel.transfer.annotation.TransferClass;

@TransferClass
public class RefundRequest extends BtoBetBettingRelatedRequest {

	public RefundRequest() {
		super();
	}

	RefundRequest( final Builder builder ) {
		super( builder );
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder extends BtoBetBettingRelatedRequest.Builder {

		@Override
		public RefundRequest build() {
			return new RefundRequest( this );
		}
	}
}
