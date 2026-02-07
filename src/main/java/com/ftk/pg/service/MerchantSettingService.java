package com.ftk.pg.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ftk.pg.dao.MerchantSettingDao;
import com.ftk.pg.dto.PaymentRequest;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.MerchantSetting;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MerchantSettingService {

	static Logger logger = LogManager.getLogger(MerchantSettingService.class);

	private final MerchantSettingDao merchantSettingDao;

	public MerchantSetting getMerchantSetting(Long mid, PaymentRequest request) {

		MerchantSetting merchantSetting = null;
		MerchantSetting merchantSettingObj = null;
		try {
			merchantSettingObj = new MerchantSetting();
			merchantSettingObj.setMerchantId(mid);
			merchantSettingObj.setCurrency(request.getTxncurr());
			merchantSettingObj.setProductType(request.getProductType());
			merchantSettingObj.setPaymentMode(request.getPaymentMode());

			if (request.getPaymentMode() != null && request.getPaymentMode().equalsIgnoreCase("NB")
					&& request.getBankid() != null && !request.getBankid().equals("")) {
				merchantSettingObj.setBank(Long.valueOf(request.getBankid()));
			}

			merchantSetting = merchantSettingDao.findNonDefaultMerchantSetting(merchantSettingObj);
			if (merchantSetting == null && request.getPaymentMode() != null
					&& request.getPaymentMode().equalsIgnoreCase("NB")) {
				merchantSettingObj.setBank(null);

			}

			if (merchantSetting == null) {
				logger.info("Setting mid=>" + mid);
				logger.info("Setting currency=>" + request.getTxncurr());
				logger.info("Setting product type=>" + request.getProductType());
				logger.info("Setting payment mode=>" + request.getPaymentMode());

				merchantSetting = merchantSettingDao.findDefaultMerchantSetting(merchantSettingObj);
			}

			return merchantSetting;
		} catch (Exception e) {
			logger.info("Error While Fetching findNonDefaultMerchantSetting Inside validateAtomPaymentRequest: ");
			new GlobalExceptionHandler().customException(e);
		}
		return null;

	}

}
