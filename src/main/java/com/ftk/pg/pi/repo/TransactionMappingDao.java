package com.ftk.pg.pi.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.pi.modal.TransactionMapping;
import com.ftk.pg.pi.modal.TransactionMappingPk;
@Repository
public interface TransactionMappingDao extends JpaRepository<TransactionMapping, TransactionMappingPk>{

//	TransactionMapping findByIdProcessorIdAndIdTransactionId(Long processorId, Long transactionId);
	

}
