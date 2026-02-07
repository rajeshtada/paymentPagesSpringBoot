package com.ftk.pg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.TransactionLog;

@Repository
public interface TransactionLogRepo extends JpaRepository<TransactionLog, Long> {

	TransactionLog findByMerchantIdAndMerchanttxnid(Long mid, String merchantTxnId);

//	TransactionLog findByMerchanttxnid(String merchantTxnId);

	List<TransactionLog> findByMerchanttxnid(String merchantTxnId);

}
