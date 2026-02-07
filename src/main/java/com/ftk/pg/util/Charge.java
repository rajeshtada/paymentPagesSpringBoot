package com.ftk.pg.util;
import java.math.BigDecimal;

public class Charge {
	private BigDecimal convienceCharges;

	public BigDecimal getConvienceCharges() {
		return convienceCharges;
	}

	public void setConvienceCharges(BigDecimal convienceCharges) {
		this.convienceCharges = convienceCharges;
	}

	@Override
	public String toString() {
		return "Charge [convienceCharges=" + convienceCharges + "]";
	}
	

}