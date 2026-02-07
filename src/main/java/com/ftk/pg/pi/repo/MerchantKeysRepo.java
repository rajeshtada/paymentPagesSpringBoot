package com.ftk.pg.pi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.ftk.pg.pi.modal.MerchantKeys;
@Repository
public interface MerchantKeysRepo extends JpaRepository<MerchantKeys, Long>{

	MerchantKeys findByMidAndTerminalId(Long mid, String terminalId);

}
