package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.MerchantRisk;
@Repository
public interface MerchantRiskRepo extends JpaRepository<MerchantRisk, Long>{

	MerchantRisk findByMidAndStatus(Long merchantId, int status);

}
