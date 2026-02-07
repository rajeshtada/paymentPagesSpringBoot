package com.ftk.pg.util;

import java.io.File;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.exception.InvalidAuthKeyException;

public class Util {
	static Logger logger = LogManager.getLogger(Util.class);
//	public static String GST_CHARGES = "18";
//	public static final String ICICI_QR_PREFIX_KEY = "ICICI_QR_PREFIX_KEY";
//	public static final String GETEPAY_PRIVATE_KEY = "GETEPAY_PRIVATE_KEY";
//
//	public static final String GETEPAY_PUBLIC_KEY = "GETEPAY_PUBLIC_KEY";
//
//	public static final String GETEPAY_ICICI_PRIVATE_KEY = "GETEPAY_ICICI_PRIVATE_KEY";
//
//	public static final String GETEPAY_ICICI_PUBLIC_KEY = "GETEPAY_ICICI_PUBLIC_KEY";
//
//	public static final String GETEPAY_ICICI_COLLECT_PRIVATE_KEY = "GETEPAY_ICICI_COLLECT_PRIVATE_KEY";
//
//	public static final String GETEPAY_CASHFREE_RU_KEY = "CASHFREE_RU";
//	public static final String GETEPAY_CASHFREE_CU_KEY = "CASHFREE_CU";
//	public static final String GETEPAY_CASHFREE_ORDER_URL_KEY = "CASHFREE_ORDER_URL";
//	public static final String GETEPAY_CASHFREE_PAYMENT_URL_KEY = "CASHFREE_PAYMENT_URL";
//	public static final String GETEPAY_CASHFREE_APIVERSION_KEY = "GETEPAY_CASHFREE_APIVERSION";
//	public static final String ICICI_MANDATE_URL = "ICICI_MANDATE_URL";
//
//	public static final int SETTLEMENT_SUMMARY_STATUS_NRNS = 0;
//	public static final int SETTLEMENT_SUMMARY_STATUS_RF = 2;
//	public static final int SETTLEMENT_SUMMARY_STATUS_RNS = 1;
//	public static final int SETTLEMENT_SUMMARY_STATUS_RS = 3;
//
//	public static final int SETTLEMENT_SUMMARY_STATUS_RR = 4;
//	public static final String QR_PATH = "/media/shared/dynamicqrpath";
//
//	public static String AUQR_CONFIG_API_KEY = "AUQR_CONFIG_API_KEY";
//	public static String AUQR_CONFIG_API_ENC128 = "AUQR_CONFIG_API_ENC128";
//	public static String AUQR_CONFIG_API_ENC256 = "AUQR_CONFIG_API_ENC256";
//	public static String AUQR_CONFIG_API_MODSTRING = "AUQR_CONFIG_API_MODSTRING";
//	public static String AUQR_CONFIG_API_EXPSTRING = "AUQR_CONFIG_API_EXPSTRING";
//	public static String AUQR_CONFIG_API_URL = "AUQR_CONFIG_API_URL";
//	public static String AUQR_CONFIG_API_USER = "AUQR_CONFIG_API_USER";
//
//	public static String AUQR_CONFIG_API_MERCHANTCONFIG = "AUQR_CONFIG_API_MERCHANTCONFIG";
//
//	public static String TXN_SYNC_API_URL = "TXN_SYNC_API_URL";
//	public static String TXN_SYNC_API_KEY = "TXN_SYNC_API_KEY";
//	public static String REQUARY_API_URL="REQUARY_API_URL";
	
	public static final String PAYOUT_PUBLIC_KEY = "PAYOUT_PUBLIC_KEY";
	public static final String GETEPAY_DYNAMICQR_URL_KEY = "GETEPAY_DYNAMICQR_URL_KEY";
	public static final String PAYOUT_PRIVATE_KEY = "PAYOUT_PRIVATE_KEY";

	public static String SOUNDBOX_NOTIFICATION_API_URL = "SOUNDBOX_NOTIFICATION_API_URL";
	public static String SOUNDBOX_NOTIFICATION_API_KEY = "SOUNDBOX_NOTIFICATION_API_KEY";
	
	public static String INVOICE_MSG_WITH_MERCHANT_NAME = "Pay the amount of INR #amount to #merchantName . Click #paymentURL - Futuretek";

	public static final String PAYMENT_MODE_DYNAMIC_QR_REFID = "gptn.";
	public static final String ICICI_PAYMENT_MODE_DYNAMIC_QR_REFID = "GETgptn";
//	public static final String REQUEST_DYNAMIC_QR_REFID = "gpdr.";
//  public static final String ICICI_REQUEST_DYNAMIC_QR_REFID = "GETgpdr";
//  public static final String PROCESSOR_IDS_SYNC="PROCESSOR_IDS_SYNC";
//  public static final String PG_PUSH_NOTIFICATION_URL="PG_PUSH_NOTIFICATION_URL";
//  public static final String ENABLE_NEW_DYNAMIC_QR="ENABLE_NEW_DYNAMIC_QR";
//  public static final String ENABLE_NEW_DYNAMIC_QR_MID="ENABLE_NEW_DYNAMIC_QR_MID";

	public static final String AUQR_PATH = "/media/shared/home/bahetymradul/donotdelete/projectData/AUQR/StaticQR/";
	public static final String BATUWA_PATH = "/media/shared/home/bahetymradul/donotdelete/projectData/dbatuwaQR/StaticQRCode/";
	public static final String MERCHNAT_PATH = "/media/shared/home/bahetymradul/donotdelete/projectData/getepayonboardQR/StaticQRCode/";
	public static final String MERCHNAT_PATH2 = "/media/shared/home/bahetymradul/donotdelete/projectData/GetepayOnBoardQR/StaticQRCode/";

	public static String maskStringValue(String value) {

		String maskString = "";
		try {
			String last = "";
			if (value.length() > 4) {
				last = value.substring(value.length() - 4);
			} else {
				last = value.substring(value.length() - 0);
			}
			int len = value.length() - last.length();
			String subString = value.substring(0, len);
			int size = subString.length();
			for (int i = 0; i < size; i++) {
				maskString += "*";
			}
			maskString += last;
		} catch (Exception e) {
		}
		return maskString;

	}

	public static String maskEmailStringValue(String email) {

		if (email != null && !email.equals("")) {

			int atIndex = email.indexOf('@');
			if (atIndex == -1) {
				return email;
			}
			int dotIndex = email.indexOf('.', atIndex);
			if (dotIndex == -1) {
				return email;
			}
			String prefix = email.substring(0, 3);
			String suffix = email.substring(dotIndex - 0);
			String mask = "";
			for (int i = 3; i < dotIndex - 3; i++) {
				mask += "*";
			}
			return prefix + mask + suffix;
		}
		return null;

	}

	public static String extractToken(String auth) throws InvalidAuthKeyException {
		// Remove "Bearer " prefix
		try {
			String token = auth.substring(7);
			return token;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			throw new InvalidAuthKeyException("InvalidAuthKeyException");
		}

	}
	
	
	  public static long generateNumber() {
			return (long) (Math.random() * 10000000 + 3333300000L);
		}


	    public static boolean isNumeric(final CharSequence cs) {
	        if (isEmpty(cs)) {
	            return false;
	        }
	        final int sz = cs.length();
	        for (int i = 0; i < sz; i++) {
	            if (!Character.isDigit(cs.charAt(i))) {
	                return false;
	            }
	        }
	        return true;
	    }

	    public static boolean isEmpty(final CharSequence cs) {
	        return cs == null || cs.length() == 0;
	    }
	    
		public static String callApi(String url, String jsonRequest) throws Exception, RestClientException {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.postForEntity(url, jsonRequest, String.class);
			return response.getBody();
		}
		

	public static String getQrImagePath(String qrString) {

		String qr = qrString;
		qr = qr.toLowerCase().replace("staticauqr/", "");
		qr = qr.toLowerCase().replace("staticqrcode/", "");
		logger.info(qr);

		qrString = qrString.split("/")[1];

		if (qr.contains("@aubank")) {
			return AUQR_PATH + qrString;
		} else if (qr.contains("mdbatuwa")) {
			return BATUWA_PATH + qrString;
		} else {
			return qrImageExists(qrString, MERCHNAT_PATH, MERCHNAT_PATH2);
		}
	}

	private static String qrImageExists(String qrString, String merchantPath1, String merchantPath2) {
		String fCapsqrString = qrString.substring(0, 1).toUpperCase() + qrString.substring(1);
		String path = "";
		File f = new File(merchantPath1 + qrString);
		if (f.exists()) {
			path = f.getAbsolutePath();
			logger.info("returning path==>" + path);
			return path;
		}
		f = new File(merchantPath1 + fCapsqrString);
		if (f.exists()) {
			path = f.getAbsolutePath();
			logger.info("returning path==>" + path);
			return path;
		}

		f = new File(merchantPath2 + fCapsqrString);
		if (f.exists()) {
			path = f.getAbsolutePath();
			logger.info("returning path==>" + path);
			return path;
		}

		f = new File(merchantPath2 + qrString);
		if (f.exists()) {
			path = f.getAbsolutePath();
			logger.info("returning path==>" + path);
			return path;
		}
		logger.info("returning path==>" + path);
		return path;
	}

	
	public static String encryptAES(byte[] decrypted, byte[] iv, byte[] key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		SecretKey secKey = new SecretKeySpec(key, 0, key.length, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secKey, ivSpec);
		byte[] encrypted = cipher.doFinal(decrypted);
		return Base64.getEncoder().encodeToString(encrypted);
	}

	public static String decryptAES(byte[] encrypted, byte[] iv, byte[] key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		SecretKey secKey = new SecretKeySpec(key, 0, key.length, "AES");
		cipher.init(Cipher.DECRYPT_MODE, secKey, ivSpec);
		byte[] decryptedText = cipher.doFinal(encrypted);
		return new String(decryptedText);
	}
}
