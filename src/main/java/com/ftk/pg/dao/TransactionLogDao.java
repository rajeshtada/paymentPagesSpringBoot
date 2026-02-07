package com.ftk.pg.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ftk.pg.modal.FilterVo;
import com.ftk.pg.modal.TransactionLog;

public interface TransactionLogDao {

	TransactionLog findByMerchantAndMerchantTxnId(FilterVo filterVo, Boolean duplicateMerchantTxnId);

	List<TransactionLog> getSuccessTxnListByDateRangeAndMid(String format, String dateString, Long merchantId);

	BigDecimal getTransactionSumOfMid(LocalDateTime fromDate, LocalDateTime toDate, Long merchantId);

	TransactionLog findById(Long valueOf);

	void update(TransactionLog transactionLog);
	
	

}