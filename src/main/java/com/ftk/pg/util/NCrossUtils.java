package com.ftk.pg.util;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.MerchantSetting;


public class NCrossUtils {
	static Logger logger = LogManager.getLogger(NCrossUtils.class);

	public Map<String, String> propertyData(MerchantSetting merchantSetting, Map<String, String> propMap) {
		Map<String, String> mapUtils = new HashMap<String, String>();
		try {
			if (merchantSetting != null && !merchantSetting.getProcessor().equals("")) {
				if (merchantSetting.getProcessor().equals("SHIVALIKNB")) {
					// mapUtils.put("secureSecret",
					// propMap.get(NorthaCrossUtilsAPI.SHIVALIK_SALT_KEY));
					mapUtils.put("returnUrl", propMap.get(NorthaCrossUtilsAPI.SHIVALIK_RETURN_URL_V2));
					mapUtils.put("paymentApiUrl", propMap.get(NorthaCrossUtilsAPI.SHIVALIK_API_PAYMENT_REQUEST_URL));
					mapUtils.put("paymentStatusApiUrl",
							propMap.get(NorthaCrossUtilsAPI.SHIVALIK_API_PAYMENT_STATUS_URL));
					mapUtils.put("refundApiUrl", propMap.get(NorthaCrossUtilsAPI.SHIVALIK_API_REFUND_REQUEST_URL));
					mapUtils.put("refundStatusApiUrl", propMap.get(NorthaCrossUtilsAPI.SHIVALIK_API_REFUND_STATUS_URL));
					return mapUtils;
				} else if (merchantSetting.getProcessor().equals("IDFCNB")) {
					// mapUtils.put("secureSecret", propMap.get(NorthaCrossUtilsAPI.IDFC_SALT_KEY));
					mapUtils.put("returnUrl", propMap.get(NorthaCrossUtilsAPI.IDFC_RETURN_URL_V2));
					mapUtils.put("paymentApiUrl", propMap.get(NorthaCrossUtilsAPI.IDFC_API_PAYMENT_REQUEST_URL));
					mapUtils.put("paymentStatusApiUrl", propMap.get(NorthaCrossUtilsAPI.IDFC_API_PAYMENT_STATUS_URL));
					mapUtils.put("refundApiUrl", propMap.get(NorthaCrossUtilsAPI.IDFC_API_REFUND_REQUEST_URL));
					mapUtils.put("refundStatusApiUrl", propMap.get(NorthaCrossUtilsAPI.IDFC_API_REFUND_STATUS_URL));
					return mapUtils;
				}
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}
}
