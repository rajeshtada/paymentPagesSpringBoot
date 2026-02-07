package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.MerchantProducts;

@Repository
public interface MerchantProductsRepo extends JpaRepository<MerchantProducts, Long> {

	MerchantProducts findByMerchantIdAndProductType(Long mid, String productType);

	MerchantProducts findByMerchantId(Long mid);

//	MerchantProducts findByMerchantIdAndProductType(Long merchantId,String productType);

}
