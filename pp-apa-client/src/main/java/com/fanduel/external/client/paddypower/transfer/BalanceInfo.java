package com.fanduel.external.client.paddypower.transfer;

import java.math.BigDecimal;
import java.util.Objects;

public class BalanceInfo {
	private long accountId;
	private BigDecimal main;
	private BigDecimal bonus;
	private BigDecimal total;

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId( final long accountId ) {
		this.accountId = accountId;
	}

	public BigDecimal getMain() {
		return main;
	}

	public void setMain( final BigDecimal main ) {
		this.main = main;
	}

	public BigDecimal getBonus() {
		return bonus;
	}

	public void setBonus( final BigDecimal bonus ) {
		this.bonus = bonus;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal( final BigDecimal total ) {
		this.total = total;
	}

	@Override
	public boolean equals( final Object o ) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		final BalanceInfo that = (BalanceInfo) o;
		return main.equals( that.main ) && bonus.equals( that.bonus ) && total.equals( that.total );
	}

	@Override
	public int hashCode() {return Objects.hash( accountId, main, bonus, total );}

	public static final class Builder {
		private long accountId;
		private BigDecimal main;
		private BigDecimal bonus;
		private BigDecimal total;

		private Builder() {}

		public static Builder aBalanceInfo() { return new Builder(); }

		public Builder accountId( long accountId ) {
			this.accountId = accountId;
			return this;
		}

		public Builder main( BigDecimal main ) {
			this.main = main;
			return this;
		}

		public Builder bonus( BigDecimal bonus ) {
			this.bonus = bonus;
			return this;
		}

		public Builder total( BigDecimal total ) {
			this.total = total;
			return this;
		}

		public BalanceInfo build() {
			BalanceInfo balanceInfo = new BalanceInfo();
			balanceInfo.setAccountId( accountId );
			balanceInfo.setMain( main );
			balanceInfo.setBonus( bonus );
			balanceInfo.setTotal( total );
			return balanceInfo;
		}
	}
}
