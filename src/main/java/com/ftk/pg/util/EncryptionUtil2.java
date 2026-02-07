//package com.ftk.pg.util;
//
//import java.util.Optional;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ftk.pg.encryption.AESGCM;
//import com.ftk.pg.encryption.EncryptedResponse;
//import com.ftk.pg.encryption.GcmPgEncryption;
//import com.ftk.pg.exception.EncDecException;
//import com.ftk.pg.modal.Merchant;
//import com.ftk.pg.pi.modal.MerchantKeys;
//
//public class EncryptionUtil2 {
//
////	public static EncryptedResponse encryptdata(String data) throws Exception {
////		EncryptedResponse encData = new EncryptedResponse();
////		String encrypteddata = Optional.ofNullable(GcmPgEncryption.encryptWithMKeys(data))
////				.orElseThrow(() -> new EncDecException("Unable to Encrypt the Response Data"));
////		encData.setData(encrypteddata);
////		return encData;
////	}
//
////	public static <T> T decryptdata(String encryptedData, Class<T> clazz) throws Exception {
////		String decryptedData = Optional.ofNullable(GcmPgEncryption.decryptWithMKeys(encryptedData))
////				.orElseThrow(() -> new EncDecException("Unable to Decrypt the Response Data"));
////		ObjectMapper objectMapper = new ObjectMapper();
////		return objectMapper.readValue(decryptedData, clazz);
////	}
//
////	public static EncryptedResponse encryptdata(String data, MerchantKeys merchantKeys) throws Exception {
////		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(merchantKeys.getIv(), merchantKeys.getKey());
////		String encrypteddata = Optional.ofNullable(gcmPgEncryption.encryptWithMKeys(data))
////				.orElseThrow(() -> new EncDecException("Unable to Encrypt the Response Data"));
////		EncryptedResponse encData = new EncryptedResponse();
////		encData.setData(encrypteddata);
////		return encData;
////	}
//
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
//
//}
