package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.ProcessorBankHolder;
@Repository
public interface ProcessorBankHolderRepo extends JpaRepository<ProcessorBankHolder, Long>{

	ProcessorBankHolder findByProcessorBankNameAndBankId(String processor, Long bankId);

}
