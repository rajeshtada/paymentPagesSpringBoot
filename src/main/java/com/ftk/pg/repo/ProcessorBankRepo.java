package com.ftk.pg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.ProcessorBank;
@Repository
public interface ProcessorBankRepo extends JpaRepository<ProcessorBank, Long> {

	List<ProcessorBank> findByProcessorAndMIdAndStatus(String processor, Long merchantId, boolean i);

	ProcessorBank findByBankIdAndProcessorAndMIdAndStatus(Long bankId, String processor, Long getmId, boolean status);

}
