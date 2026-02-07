package com.ftk.pg.encryption;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.pi.modal.MerchantKeys;
import com.ftk.pg.responsevo.PaymentResponse;
import com.ftk.pg.vo.generateInvoice.PgRequest;
import com.ftk.pg.vo.generateInvoice.ResponseData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PgEncryption {

	Logger log = LogManager.getLogger(PgEncryption.class);
	public String iv;
	public String ivKey;

	public PgEncryption(String iv, String key) {
		this.iv = iv;
		this.ivKey = key;
	}

	public PgEncryption(MerchantKeys merchantKeys) {
		this(merchantKeys.getIv(), merchantKeys.getKey());
	}

	private static final String ALGO = "AES";

	public String encrypt(String string) {
		try {
			byte[] ivs = Base64.getDecoder().decode(iv);
			IvParameterSpec ivspec = new IvParameterSpec(ivs);
			SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(ivKey), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivspec);
			log.info("string ===>", string);
			byte[] encrypted = cipher.doFinal(string.getBytes("UTF-8"));
			return toHexString(encrypted);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public String decrypt(String message) {
		try {
			byte[] msg = toByteArray(message);
			SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(ivKey), "AES");
			byte[] ivs = Base64.getDecoder().decode(iv);
			IvParameterSpec ivspec = new IvParameterSpec(ivs);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivspec);
			return new String(cipher.doFinal(msg));
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static String toHexString(byte[] array) {
		return DatatypeConverter.printHexBinary(array);
	}

	public static byte[] toByteArray(String s) {
		return DatatypeConverter.parseHexBinary(s);
	}

	public String encryptResponse(ResponseData response) {
		try {
			log.info("response qr path ==>" + response.getQrPath());
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			String jsonResponse = gson.toJson(response);
			log.info("Returning response::" + jsonResponse);
			return encrypt(jsonResponse);
		} catch (Exception e) {
//			CustomExceptionLoggerError.customExceptionPrintTrace(log, e, "Error while encrypting response::");
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public String encryptResponse(Object response) {
		try {
			Gson gson = new Gson();
			String jsonResponse = gson.toJson(response);
			log.info("Returning response::" + jsonResponse);
			return encrypt(jsonResponse);
		} catch (Exception e) {
//			CustomExceptionLoggerError.customExceptionPrintTrace(log, e, "Error while encrypting response::");
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public String getDecrytedString(String requestString) {
		try {
			requestString = decrypt(requestString);
			log.info("Request received::" + requestString);
			return requestString;
		} catch (Exception e) {
//			CustomExceptionLoggerError.customExceptionPrintTrace(log, e, "Error while decrypting response::");
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public PgRequest decryptRequest(String requestString) {
		try {
			Gson gson = new Gson();
			requestString = decrypt(requestString);
			log.info("Request received::" + requestString);
			PgRequest decryptRequest = gson.fromJson(requestString, PgRequest.class);
			return decryptRequest;
		} catch (Exception e) {
//			CustomExceptionLoggerError.customExceptionPrintTrace(log, e, "Error while decrypting response::");
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}
	
//	public RequeryRequest decryptRequeryRequest(String requestString) {
//		try {
//			Gson gson = new Gson();
//			requestString = decrypt(requestString);
//			log.info("Request received::" + requestString);
//			RequeryRequest decryptRequest = gson.fromJson(requestString, RequeryRequest.class);
//			return decryptRequest;
//		} catch (Exception e) {
////			CustomExceptionLoggerError.customExceptionPrintTrace(log, e, "Error while decrypting response::");
//			new GlobalExceptionHandler().customException(e);
//		}
//		return null;
//	}

//	public static MerchantKeys generateKeys(){
//		MerchantKeys merchantKeys = new MerchantKeys();
//		try {
//			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//			SecretKey key = keyGenerator.generateKey();
//			String keyString =  Base64.getEncoder().encodeToString(key.getEncoded());
//			merchantKeys.setKey(keyString);
//
//			byte[] iv = CryptoUtils.getRandomNonce(16);
//			merchantKeys.setIv(Base64.getEncoder().encodeToString(iv));
//
//			return merchantKeys;
//
//		} catch (Exception e){
//			e.printStackTrace();
//		}
//
//		return null;
//	}

	public static MerchantKeys generateKeys() {
		MerchantKeys merchantKeys = new MerchantKeys();
		try {
			byte[] iv = new byte[16];
			new SecureRandom().nextBytes(iv);
			merchantKeys.setIv(Base64.getEncoder().encodeToString(iv));

			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(256);
			SecretKey key = keyGenerator.generateKey();
			merchantKeys.setKey(Base64.getEncoder().encodeToString(key.getEncoded()));

			return merchantKeys;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	public PgStatusRequest decryptStatusRequest(String reqString) {
//		try {
//			Gson gson = new Gson();
//			String requestString = decrypt(reqString);
//			log.info("Request received::" + requestString);
//			PgStatusRequest decryptRequest = gson.fromJson(requestString, PgStatusRequest.class);
//			return decryptRequest;
//		} catch (Exception e) {
//			CustomExceptionLoggerError.customExceptionPrintTrace(log, e, "Error while decrypting response::");
////			new GlobalExceptionHandler().customException(e);
//		}
//		return null;
//	}

	public String encryptStatusResponse(PaymentResponse paymentResponse) {
		try {
			Gson gson = new Gson();
			String responseString = gson.toJson(paymentResponse);
			log.info("Response send::" + responseString);
			responseString = encrypt(responseString);
			return responseString;
		} catch (Exception e) {
//			CustomExceptionLoggerError.customExceptionPrintTrace(log, e, "Error while decrypting response::");
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}
	
	public Object encryptRequestAsObject(String string) {
		try {
			byte[] ivs = Base64.getDecoder().decode(iv);
			IvParameterSpec ivspec = new IvParameterSpec(ivs);
			SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(ivKey), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivspec);
			byte[] encrypted = cipher.doFinal(string.getBytes("UTF-8"));
			return toHexString(encrypted);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public Object decryptRequestAsObject(String requestString, Class clazz) {
		try {
			Gson gson = new Gson();
			requestString = decrypt(requestString);
			log.info("Request received::" + requestString);
			Object decryptRequest = gson.fromJson(requestString, clazz);
			return decryptRequest;
		} catch (Exception e) {
//			CustomExceptionLoggerError.customExceptionPrintTrace(log, e, "Error while decrypting response::");
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public String encryptResponseAsObject(Object paymentResponse) {
		try {
			Gson gson = new Gson();
			String responseString = gson.toJson(paymentResponse);
			log.info("Response send::" + responseString);
			responseString = encrypt(responseString);
			return responseString;
		} catch (Exception e) {
//			CustomExceptionLoggerError.customExceptionPrintTrace(log, e, "Error while decrypting response::");
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

}
