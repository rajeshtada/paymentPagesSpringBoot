package com.ftk.pg.util;

import java.util.Optional;

import javax.crypto.AEADBadTagException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftk.pg.encryption.AESGCM;
import com.ftk.pg.encryption.EncryptedResponse;
import com.ftk.pg.encryption.GcmPgEncryption;
import com.ftk.pg.exception.EncDecException;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.exception.InvalidAuthKeyException;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.pi.modal.MerchantKeys;
import com.ftk.pg.service.PaymentInitiationService;

public class EncryptionUtil {
	static Logger logger = LogManager.getLogger(EncryptionUtil.class);
//	public static EncryptedResponse encryptdata(String data,String ivKey) throws Exception {
//		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(ivKey);
//		EncryptedResponse encData = new EncryptedResponse();
//		String encrypteddata = Optional.ofNullable(gcmPgEncryption.encryptWithMKeys(data))
//				.orElseThrow(() -> new EncDecException("Unable to Encrypt the Response Data"));
//		encData.setData(encrypteddata);
//		return encData;
//	}

	public static String encrypt(String data, String ivKey) throws Exception {
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(ivKey);
		String encrypteddata = Optional.ofNullable(gcmPgEncryption.encryptWithMKeys(data))
				.orElseThrow(() -> new EncDecException("Unable to Encrypt the Response Data"));
		return encrypteddata;
	}

	public static <T> T decryptdata(String encryptedData, String ivKey, Class<T> clazz) throws Exception {
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(ivKey);
//		String decryptedData = Optional.ofNullable(gcmPgEncryption.decryptWithMKeys(encryptedData))
//				.orElseThrow(() -> new EncDecException("Unable to Decrypt the Response Data"));
//		ObjectMapper objectMapper = new ObjectMapper();
//		return objectMapper.readValue(decryptedData, clazz);
		try {
			String decryptedData = Optional.ofNullable(gcmPgEncryption.decryptWithMKeys(encryptedData))
					.orElseThrow(() -> new EncDecException("Unable to Decrypt the Response Data"));	
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(decryptedData, clazz);
		}catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			throw new InvalidAuthKeyException("Unable to Decrypt the Response Data");
		}
	}

//	public static EncryptedResponse encryptdata(String data, MerchantKeys merchantKeys) throws Exception {
//		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(merchantKeys.getIv(), merchantKeys.getKey());
//		String encrypteddata = Optional.ofNullable(gcmPgEncryption.encryptWithMKeys(data))
//				.orElseThrow(() -> new EncDecException("Unable to Encrypt the Response Data"));
//		EncryptedResponse encData = new EncryptedResponse();
//		encData.setData(encrypteddata);
//		return encData;
//	}

//	public static String encryptdata(String data, MerchantKeys merchantKeys) throws Exception {
//		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(merchantKeys.getIv(), merchantKeys.getKey());
//		String encrypteddata = Optional.ofNullable(gcmPgEncryption.encryptWithMKeys(data))
//				.orElseThrow(() -> new EncDecException("Unable to Encrypt the Response Data"));
//		return encrypteddata;
//	}
//
//	public static <T> T decryptdata(String encryptedData, MerchantKeys merchantKeys, Class<T> clazz) throws Exception {
//		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(merchantKeys.getIv(), merchantKeys.getKey());
//		String decryptedData = Optional.ofNullable(gcmPgEncryption.decryptWithMKeys(encryptedData))
//				.orElseThrow(() -> new EncDecException("Unable to Decrypt the Response Data"));
//		ObjectMapper objectMapper = new ObjectMapper();
//		return objectMapper.readValue(decryptedData, clazz);
//	}

}
