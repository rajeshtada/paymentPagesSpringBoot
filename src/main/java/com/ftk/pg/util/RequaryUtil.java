package com.ftk.pg.util;

import java.math.BigDecimal;

import com.ftk.pg.modal.TransactionLog;

public class RequaryUtil {

	public static BigDecimal requaryAmount(TransactionLog transactionLog) {
		BigDecimal amount = transactionLog.getAmt();
		BigDecimal commissionCharge = BigDecimal.ZERO;
		BigDecimal convinenceCharge = BigDecimal.ZERO;
		if (transactionLog.getCommisionType() != null && transactionLog.getCommisionType().equalsIgnoreCase("Excl")) {
			commissionCharge = transactionLog.getCommision();
			amount = amount.add(commissionCharge);

		} else {
			amount = amount.add(commissionCharge);
		}
		if (transactionLog.getServiceChargeType() != null
				&& transactionLog.getServiceChargeType().equalsIgnoreCase("Excl")) {
			convinenceCharge = new BigDecimal(transactionLog.getTotalServiceCharge());
			amount = amount.add(convinenceCharge);

		} else {
			amount = amount.add(convinenceCharge);
		}

		return amount;
	}

}
