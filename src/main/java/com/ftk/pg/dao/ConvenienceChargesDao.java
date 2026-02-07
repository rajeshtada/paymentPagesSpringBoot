package com.ftk.pg.dao;

import java.util.List;

import com.ftk.pg.modal.ConvenienceCharges;

public interface ConvenienceChargesDao {

	List<ConvenienceCharges> getByMid(Long mid);

	List<ConvenienceCharges> getConvenienceChargesByMidAndStatus(Long mid, int status);

	List<ConvenienceCharges> getConvenienceChargesByMidsAndStatus(List<Long> mids, int status);

	ConvenienceCharges getChargesByMidPaymentModeAndCardType(Long mid, String paymentMode, String cardType);
	List<ConvenienceCharges> getChargesByMidPaymentModeAndCardType(ConvenienceCharges convenienceCharge);

	void saveInBulk(List<ConvenienceCharges> convenienceChargesList);

	List<ConvenienceCharges> findConvenienceCharge(ConvenienceCharges cCharges);
	List<ConvenienceCharges> findConvenienceChargeOtherPayment(ConvenienceCharges cCharges);
	
	List<ConvenienceCharges> getChargesByMidAndBankId(ConvenienceCharges cCharges);
	List<ConvenienceCharges>getChargesByMidPaymentModeAndCardTypeAll(ConvenienceCharges cCharges);
}
