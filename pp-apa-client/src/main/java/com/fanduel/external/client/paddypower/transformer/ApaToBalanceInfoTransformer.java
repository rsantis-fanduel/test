package com.fanduel.external.client.paddypower.transformer;

import java.math.BigDecimal;

import com.fanduel.external.client.paddypower.impl.jaxws.generated.apa.RetrieveBalanceInfoResponseType;
import com.fanduel.external.client.paddypower.transfer.BalanceInfo;
import com.fanduel.transform.Transformer;

public class ApaToBalanceInfoTransformer implements
		Transformer<RetrieveBalanceInfoResponseType, BalanceInfo> {

	@Override
	public BalanceInfo transform( final RetrieveBalanceInfoResponseType source ) {
		return BalanceInfo.Builder.aBalanceInfo()
				.main( new BigDecimal( source.getResponse().getMain() ) )
				.bonus( new BigDecimal( source.getResponse().getBonus() ) )
				.total( new BigDecimal( source.getResponse().getTotal() ) )
				.build();
	}
}
