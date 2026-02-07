package com.ftk.pg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.MerchantProductIdDetails;

@Repository
public interface MerchantProductIdDetailsRepo extends JpaRepository<MerchantProductIdDetails, Long> {

	List<MerchantProductIdDetails> findByMerchantId(Long mid);

	MerchantProductIdDetails findByMerchantIdAndProductCode(Long mid, String code);

//	List<MerchantProductIdDetails> getActiveProductListByMId(Long mid);
	List<MerchantProductIdDetails> findByMerchantIdAndStatus(Long mid,int status);

}
