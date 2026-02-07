package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.ProcessorWallet;
@Repository
public interface ProcessorWalletRepo extends JpaRepository<ProcessorWallet, Long>{

	ProcessorWallet findByWalletIdAndMId(Long walletId, Long getmId);

	ProcessorWallet findByWalletIdAndProcessorAndMIdAndStatus(Long walletId, String processor, Long getmId,
			boolean status);

}
