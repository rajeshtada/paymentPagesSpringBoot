package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.ProcessorWalletHolder;

@Repository
public interface ProcessorWalletHolderRepo extends JpaRepository<ProcessorWalletHolder, Long> {

	ProcessorWalletHolder findByProcessorAndWalletId(String processor, Long valueOf);

}
