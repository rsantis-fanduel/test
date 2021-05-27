package com.fanduel.external.client.btobet.transfer;

public class GetBalanceRequest extends BtoBetFinancialRequest {

	public GetBalanceRequest() {
		super();
	}

	GetBalanceRequest( final Builder builder ) {
		super( builder );
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder extends BtoBetFinancialRequest.Builder {

		@Override
		public GetBalanceRequest build() {
			return new GetBalanceRequest( this );
		}
	}
}
