package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.MerchantSetting;

@Repository
public interface MerchantSettingRepo extends JpaRepository<MerchantSetting, Long> {

	MerchantSetting findByMerchantIdAndPaymentModeAndProcessor(Long mid, String string, String string2);

//	MerchantSetting findByMerchantIdAndPaymentModeAndProcessorAndDefault(Long mid, String string, String string2, boolean defValue);
	
	/*@Query("SELECT m FROM MerchantSetting m " + "WHERE m.merchantId = :merchantId " + "AND m.currency = :currency "
			+ "AND m.paymentMode = :paymentMode " + "AND m.productType = :productType " + "AND m.isDefault = true "
			+ "AND m.status = 1")
	MerchantSetting findActiveDefaultSetting(@Param("merchantId") Long merchantId, @Param("currency") String currency,
			@Param("paymentMode") String paymentMode, @Param("productType") String productType);
	
	
/*	@Query("SELECT m FROM MerchantSetting m " +
		       "WHERE m.merchantId = :merchantId " +
		       "AND m.currency = :currency " +
		       "AND m.paymentMode = :paymentMode " +
		       "AND m.productType = :productType " +
		       "AND m.isDefault = true " +
		       "AND m.status = 1")
		MerchantSetting findActiveDefaultSetting(@Param("merchantId") Long merchantId,
		                                         @Param("currency") String currency,
		                                         @Param("paymentMode") String paymentMode,
		                                         @Param("productType") String productType);

*/
	//@Query("SELECT m FROM MerchantSetting m " + "WHERE m.merchantId = :merchantId " + "AND m.currency = :currency "
			//+ "AND m.paymentMode = :paymentMode " + "AND m.productType = :productType " + "AND m.isDefault = true "
			//+ "AND m.status = 1")
//	MerchantSetting findActiveDefaultSetting(@Param("merchantId") Long merchantId, @Param("currency") String currency,
			// @Param("paymentMode") String paymentMode, @Param("productType") String productType);

}
