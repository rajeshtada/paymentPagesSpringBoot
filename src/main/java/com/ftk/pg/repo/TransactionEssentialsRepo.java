package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.TransactionEssentials;
@Repository
public interface TransactionEssentialsRepo extends JpaRepository<TransactionEssentials, Long>{

	TransactionEssentials findByTransactionId(Long transactionId);

}
