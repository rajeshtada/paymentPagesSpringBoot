package com.ftk.pg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.MerchantProductTransaction;
@Repository
public interface MerchantProductTransactionRepo extends JpaRepository<MerchantProductTransaction, Long>{

	List<MerchantProductTransaction> findByTxnIdAndMid(Long transactionId, Long mid);

}
