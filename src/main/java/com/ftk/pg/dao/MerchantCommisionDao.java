package com.ftk.pg.dao;

import java.util.List;

import com.ftk.pg.modal.MerchantCommision;

public interface MerchantCommisionDao {

	MerchantCommision findByMidAndPayMode(MerchantCommision commision);

	MerchantCommision findByMidAndBank(MerchantCommision commision);

	MerchantCommision findDefaultCommison(MerchantCommision commision);

	String findMerchantPaymentModeByName(MerchantCommision commision);

	List<MerchantCommision> findMerchatCommisions(MerchantCommision commision);

	int deleteMerchantCommission(MerchantCommision commision);

	List<MerchantCommision> bankWiseComissionList(MerchantCommision commision);

	List<MerchantCommision> findByMids(List<Long> mids);

	void deleteByIds(List<Long> ids);

	List<MerchantCommision> searchMerchatCommision(MerchantCommision commision);

	void persistInBatch(List<MerchantCommision> list);

	List<MerchantCommision> searchMerchatCommisionByContype(
			MerchantCommision merchantCommision, String conSubType);

	MerchantCommision searchMerchantCommisionGreaterThanSlabSingleResult(MerchantCommision commision);
}
