package com.ftk.pg.pi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.pi.modal.IntermediateTransaction;
@Repository
public interface IntermediateTransactionRepo extends JpaRepository<IntermediateTransaction, Long>{

	IntermediateTransaction findByTransactionId(Long transactionId);

}
