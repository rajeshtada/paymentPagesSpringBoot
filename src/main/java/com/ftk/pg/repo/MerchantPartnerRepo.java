package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.MerchantPartner;
@Repository
public interface MerchantPartnerRepo extends JpaRepository<MerchantPartner, Long>{

	MerchantPartner findByMid(Long mid);

}
