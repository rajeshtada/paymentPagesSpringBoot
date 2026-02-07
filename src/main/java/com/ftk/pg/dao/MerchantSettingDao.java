package com.ftk.pg.dao;

import java.util.List;
import java.util.Map;

import com.ftk.pg.modal.MerchantSetting;

public interface MerchantSettingDao {

	List<MerchantSetting> findByMerchantName(MerchantSetting setting);

	void changeStatus(Long id, Boolean status);

	MerchantSetting findByMerchantSetting(MerchantSetting setting);

	MerchantSetting findDefaultMerchantSetting(MerchantSetting setting);
	MerchantSetting findDefaultMerchantSettingwithBank(MerchantSetting setting);

	MerchantSetting findNonDefaultMerchantSetting(MerchantSetting setting);

	List<MerchantSetting> findNotDefaultMerchantSetting(MerchantSetting setting);

	List<MerchantSetting> findNDefaultMerchantSetting(MerchantSetting setting);

//	MerchantSetting saveNotDefaultMerchantSetting(MerchantSetting merchantSetting);

//	Long saveMerchantSetting(MerchantSetting merchantSetting);

	List<MerchantSetting> findByMidAndDefault(MerchantSetting setting);

	Long defaultMerchantRowCount(MerchantSetting merchantSetting);

	MerchantSetting findMerchantSettingByPaymodeCurr(MerchantSetting setting);

	void deleteMerchantSettingsByName(MerchantSetting merchantSetting);

	/// After Change

	Long MerchantSettingsRowCount(MerchantSetting merchantSetting);

	List<MerchantSetting> findMerchantSetting(MerchantSetting merchantSetting);

	Map<Long, String> findAtomActiveMID();

	List<MerchantSetting> findByMids(List<Long> mids);

	List<MerchantSetting> deleteByIds(List<Long> ids);

	void persistInBatch(List<MerchantSetting> list);

	void remove(Long mid);
	  
	MerchantSetting findLyraMerchantSetting(MerchantSetting merchantObj);

	MerchantSetting findByMerchantSettingId(Long merchantSettingId);
	
	List<MerchantSetting> findNonDefaultMerchantSettingList(MerchantSetting setting);
	
	List<MerchantSetting> findDefaultMerchantSettingList(MerchantSetting merchantSettingObj);
	
	List<MerchantSetting> getEnableMerchantSettingList(MerchantSetting merchantSettingObj);

	MerchantSetting findByMidandPaymentModeandProcessor(Long mid, String paymentMode,
			String processor);
}
