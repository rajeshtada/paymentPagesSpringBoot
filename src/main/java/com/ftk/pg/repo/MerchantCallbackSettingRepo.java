package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.MerchantCallbackSetting;
@Repository
public interface MerchantCallbackSettingRepo extends JpaRepository<MerchantCallbackSetting, Long>{

	MerchantCallbackSetting findByMidAndStatus(Long merchantId, int i);

}
