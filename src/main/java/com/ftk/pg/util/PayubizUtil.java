package com.ftk.pg.util;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;

public class PayubizUtil {

	static Logger logger = LogManager.getLogger(PayubizUtil.class);
	//https://test.payu.in/_payment
	//https://secure.payu.in/_payment
	public static final String PAYU_URL_KEY="PAYU_URL_KEY";
	
	public static final String PAYU_PRODUCT_INFO = "getepay transaction";
	
	private static String checksumString = "#key|#txnid|#amount|#productinfo|#firstname|#email|#udf1|#udf2|#udf3|#udf4|#udf5||||||#SALT";
	
	private static String checksumResponseString = "#SALT|#status||||||#udf5|#udf4|#udf3|#udf2|#udf1|#email|#firstname|#productinfo|#amount|#txnid|#key";
	

	
	private static String CHECKSUM_ALGO="SHA-512";
	
	public static String calculateHash(Map<String, String> params, String salt) {
		
		Set<String> keys = params.keySet();
		String checksumStr = checksumString;
		
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if(checksumStr.contains("#"+key)) {
				String value = params.get(key);
				if(value == null) {
					value ="";
				}
				checksumStr = checksumStr.replace("#"+key, value);
			}
				
		}
		checksumStr = checksumStr.replace("#SALT", salt);		
		logger.info("Payu checksum String=>" + checksumStr);
		
		return checksum(CHECKSUM_ALGO, checksumStr);
	}
	
	public static String calculateResponseHash(Map<String, String> params, String salt) {
		
		Set<String> keys = params.keySet();
		String checksumStr = checksumResponseString;
		
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if(checksumStr.contains("#"+key)) {
				String value = params.get(key);
				if(value == null) {
					value ="";
				}
				checksumStr = checksumStr.replace("#"+key, value);
			}
				
		}
		checksumStr = checksumStr.replace("#SALT", salt);		
		logger.info("Payu checksum String=>" + checksumStr);
		
		return checksum(CHECKSUM_ALGO, checksumStr);
	}
	
	
	private static String checksum(String type, String str) {
		byte[] hashseq = str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest algorithm = MessageDigest.getInstance(type);
			algorithm.reset();
			algorithm.update(hashseq);
			byte messageDigest[] = algorithm.digest();

			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1)
					hexString.append("0");
				hexString.append(hex);
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return hexString.toString();

	}
	
}
