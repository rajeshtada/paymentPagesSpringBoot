package com.ftk.pg.util;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.MerchantSetting;
import com.ftk.pg.requestvo.AuthenticationRequest;
import com.ftk.pg.requestvo.AuthorizationRequest;
import com.ftk.pg.requestvo.KotakRequestWrapper;
import com.ftk.pg.requestvo.RefundApiRequest;
import com.ftk.pg.requestvo.ResendOtpApiRequest;
import com.ftk.pg.requestvo.SaleStatusQueryApiRequest;
import com.ftk.pg.requestvo.VerifyOtpApiRequest;
import com.ftk.pg.responsevo.AuthenticationResponse;
import com.ftk.pg.responsevo.AutorizationResponse;
import com.ftk.pg.responsevo.KotakResponseWrapper;
import com.ftk.pg.responsevo.RefundApiResponse;
import com.ftk.pg.responsevo.ResendOtpResponse;
import com.ftk.pg.responsevo.SaleStatusQueryApiResponse;
import com.ftk.pg.responsevo.VerifyOtpApiResponse;
import com.google.gson.Gson;

public class KotakUtil {

	private static Logger logger = LogManager.getLogger(KotakUtil.class);

	private static String salt = "asdasdaa2qe21sd12wx2";
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
	public final static String KOTAK_URL_KEY = "KOTAK_URL_KEY";

	public final static String KOTAK_CHECKSUM_KEY = "KOTAK_CHECKSUM_KEY";
	public final static String KOTAK_ENCRYPTION_KEY = "KOTAK_ENCRYPTION_KEY";

	public static String KOTAK_COMMAND_AUTHENTICATION_KEY = "KOTAK_COMMAND_AUTHENTICATION_KEY";

//	public static String KOTAK_API_AUTHENTICATION_URL_KEY = "KOTAK_API_AUTHENTICATION_URL_KEY";
	public static String KOTAK_API_AUTHENTICATION_RESPONSE_URL_KEY = "KOTAK_API_AUTHENTICATION_RESPONSE_URL_KEY";
	public static String KOTAK_AUTHENTICATION_API_URL = "KOTAK_AUTHENTICATION_API_URL";
	public static String KOTAK_AUTHORIZATION_API_URL = "KOTAK_AUTHORIZATION_API_URL";
	public static String KOTAK_RUPAY_VERIFY_OTP_API_URL = "KOTAK_RUPAY_VERIFY_OTP_API_URL";
	public static String KOTAK_RUPAY_RESEND_OTP_API_URL = "KOTAK_RUPAY_RESEND_OTP_API_URL";
	public static String KOTAK_SALE_STATUS_API_URL = "KOTAK_SALE_STATUS_API_URL";
	public static String KOTAK_REFUND_API_URL = "KOTAK_REFUND_API_URL";

	public static String encrypt(String strToEncrypt, String key) {
		String encryptedStr = null;
		try {
			byte[] ivParams = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(ivParams);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] messageArr = strToEncrypt.getBytes();
			byte[] keyparam = key.getBytes();
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			byte[] encoded = new byte[messageArr.length + 16];
			System.arraycopy(ivParams, 0, encoded, 0, 16);
			System.arraycopy(messageArr, 0, encoded, 16, messageArr.length);
			cipher.init(1, secretKey, ivspec);
			byte[] encryptedBytes = cipher.doFinal(encoded);
			encryptedStr = new String(Base64.getEncoder().encode(encryptedBytes));
			return encryptedStr;
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return encryptedStr;
	}

	public static String decrypt(String strToDecrypt, String key) {
		String decryptedStr = null;
		try {
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(2, secretKey, ivspec);
			// decryptedStr = URLDecoder.decode(strToDecrypt, "UTF-8");
			decryptedStr = strToDecrypt;
			decryptedStr = new String(cipher.doFinal(Base64.getDecoder().decode(decryptedStr))).trim();
			return decryptedStr;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
		// return decryptedStr;
	}

	public static String getHMAC256Checksum(String msg, String checkSumKey) {
		Mac sha512_HMAC = null;
		String result = null;
		try {
			byte[] byteKey = checkSumKey.getBytes("UTF-8");
			final String HMAC_SHA256 = "HmacSHA256";
			sha512_HMAC = Mac.getInstance(HMAC_SHA256);
			SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA256);
			sha512_HMAC.init(keySpec);
			byte[] mac_data = sha512_HMAC.doFinal((msg).getBytes("UTF-8"));
			// result = Base64.toBase64String(mac_data);
			result = bytesToHex(mac_data);

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		} finally {
			System.out.println("Done");
		}
		return result;

	}

	public static String bytesToHex(byte[] bytes) {
		final char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static String encryptkbsegxml(String message, String key)
			throws GeneralSecurityException, UnsupportedEncodingException {
		if (message == null || key == null) {
			throw new IllegalArgumentException("text to be encrypted and key should not be null");
		}

		logger.info("message to encrypt:::" + message);
		logger.info("key to encrypt:::" + key);
		Security.setProperty("crypto.policy", "unlimited");

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		byte[] messageArr = message.getBytes();
		byte[] keyparam = key.getBytes();
		SecretKeySpec keySpec = new SecretKeySpec(keyparam, "AES");
		byte[] ivParams = new byte[16];
		byte[] encoded = new byte[messageArr.length + 16];
		System.arraycopy(ivParams, 0, encoded, 0, 16);
		System.arraycopy(messageArr, 0, encoded, 16, messageArr.length);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(ivParams));
		byte[] encryptedBytes = cipher.doFinal(encoded);
		encryptedBytes = Base64.getEncoder().encode(encryptedBytes);
		return new String(encryptedBytes);

	}

	public static String decryptkbsegxml(String encryptedStr, String key)
			throws GeneralSecurityException, UnsupportedEncodingException {
		if (encryptedStr == null || key == null) {
			throw new IllegalArgumentException("text to be decrypted and key should not be null");
		}
		logger.info("string to decrypt:::" + encryptedStr);
		logger.info("key to decrypt:::" + key);
		if (encryptedStr == null && encryptedStr.equalsIgnoreCase("")) {
			return "";
		}
		Security.setProperty("crypto.policy", "unlimited");

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		byte[] keyparam = key.getBytes();
		SecretKeySpec keySpec = new SecretKeySpec(keyparam, "AES");
		byte[] encoded = encryptedStr.getBytes();
		encoded = Base64.getDecoder().decode(encoded);
		byte[] decodedEncrypted = new byte[encoded.length - 16];
		System.arraycopy(encoded, 16, decodedEncrypted, 0, encoded.length - 16);
		byte[] ivParams = new byte[16];
		System.arraycopy(encoded, 0, ivParams, 0, ivParams.length);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(ivParams));
		byte[] decryptedBytes = cipher.doFinal(decodedEncrypted);
		return new String(decryptedBytes);

	}

	public static String encryptKBSegQueryback2(String message, String key)
			throws GeneralSecurityException, UnsupportedEncodingException {
		if (message == null || key == null) {
			throw new IllegalArgumentException("text to be encrypted and key should not be null");
		}
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		byte[] messageArr = message.getBytes();
		byte[] keyparam = key.getBytes();
		SecretKeySpec keySpec = new SecretKeySpec(keyparam, "AES");
		byte[] ivParams = new byte[16];
		byte[] encoded = new byte[messageArr.length + 16];
		System.arraycopy(ivParams, 0, encoded, 0, 16);
		System.arraycopy(messageArr, 0, encoded, 16, messageArr.length);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(ivParams));
		byte[] encryptedBytes = cipher.doFinal(encoded);
		encryptedBytes = Base64.getEncoder().encode(encryptedBytes);
		return new String(encryptedBytes);
	}

	public static String decryptKBSegQueryback2(String encryptedStr, String key)
			throws GeneralSecurityException,UnsupportedEncodingException
 {
		if (encryptedStr == null || key == null) {
			throw new IllegalArgumentException("text to be decrypted and key should not be null");
		}
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		byte[] keyparam = key.getBytes();
		SecretKeySpec keySpec = new SecretKeySpec(keyparam, "AES");
		byte[] encoded = encryptedStr.getBytes();
		encoded = Base64.getDecoder().decode(encoded);
		byte[] decodedEncrypted = new byte[encoded.length - 16];
		System.arraycopy(encoded, 16, decodedEncrypted, 0, encoded.length - 16);
		byte[] ivParams = new byte[16];
		System.arraycopy(encoded, 0, ivParams, 0, ivParams.length);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(ivParams));
		byte[] decryptedBytes = cipher.doFinal(decodedEncrypted);
		return new String(decryptedBytes);
	}

	public static void main(String[] args) throws Exception {
		String jsonResponse = "{\"BankId\": \"000065\",\n" + "\"MerchantId\": \"TEST00000000001\",\n"
				+ "\"TerminalId\": \"TEST0001\",\n" + "\"OrderId\": \"TEST210610193304\",\n"
				+ "\"AccessCode\": \"OXEY8157\",\n" + "\"MCC\": \"6300\",\n" + "\"Currency\": \"356\",\n"
				+ "\"Amount\": \"5000000\",\n" + "\"TxnType\": \"25\",\n" + "\"PgId\": \"211610041815\",\n"
				+ "\"Response Code\": \"00\",\n"
				+ "\"ResponseMessage\": \"Loan Request Approved and OTP has been sent to Customer\u0027s Registered Mobile Number\",\n"
				+ "\"MaskedCardNumber\": \"545301XXXXXX1234\"}";
		String hash = KotakSecureHashGenenerator.generateHash(jsonResponse, "F3C63F6FFFC176E4AAD530C59833719D");
		System.out.println(hash);

	}

	public static String encryptCardRequest(String msg, String keyString) {
		try {
			byte[] dataInBytes = msg.getBytes();
			Cipher encryptionCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

			byte[] key = keyString.getBytes();
			SecretKey originalKey = new SecretKeySpec(key, 0, key.length, "AES");

			encryptionCipher.init(Cipher.ENCRYPT_MODE, originalKey);
			byte[] encryptedBytes = encryptionCipher.doFinal(dataInBytes);
			return Base64.getEncoder().encodeToString(encryptedBytes);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static String decryptCardRequest(String msg, String keyString) {
		try {
			byte[] dataInBytes = Base64.getDecoder().decode(msg.getBytes());
			Cipher encryptionCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

			// byte[] key = Base64.getDecoder().decode(keyString.getBytes());
			byte[] key = keyString.getBytes();
			SecretKey originalKey = new SecretKeySpec(key, 0, key.length, "AES");

			encryptionCipher.init(Cipher.DECRYPT_MODE, originalKey);
			byte[] decrypted = encryptionCipher.doFinal(dataInBytes);
			return new String(decrypted);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static AuthenticationResponse callAuthenticationApi(AuthenticationRequest request,
			MerchantSetting merchantSetting, Map<String, String> propertyMap) {
		try {
			request.setCommand(propertyMap.get(KOTAK_COMMAND_AUTHENTICATION_KEY));

			logger.info("Kotak authentication request => " + request.toString());

			Gson gson = new Gson();
			String jsonRequest = gson.toJson(request);

			String secureHash = KotakSecureHashGenenerator.generateHash(jsonRequest, merchantSetting.getSetting3());
			request.setSecureHash(secureHash);

			jsonRequest = gson.toJson(request);
			logger.info("Kotak authentication final request encData => " + jsonRequest.toString());

			String encKey = merchantSetting.getmPassword();
			String encrytString = KotakUtil.encryptCardRequest(jsonRequest, encKey);

			logger.info("encrypted kotak authentication api => " + encrytString);

			KotakRequestWrapper requestWrapper = new KotakRequestWrapper();
			requestWrapper.setBankId(merchantSetting.getSetting4());
			requestWrapper.setMerchantId(merchantSetting.getMloginId());
			requestWrapper.setTerminalId(merchantSetting.getSetting1());
			requestWrapper.setOrderId(String.valueOf(request.getOrderId()));
			requestWrapper.setEncData(encrytString);

			String stringRequest = gson.toJson(requestWrapper);

			logger.info("encrypted final authentication api => " + stringRequest);

			String authUrl = propertyMap.get(KOTAK_AUTHENTICATION_API_URL);

			KotakCall call = new KotakCall();
			call.setBaseUrl(authUrl);

			AuthenticationResponse authResponse = call.authentication(stringRequest, encKey);

			return authResponse;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static AuthenticationResponse callAuthenticationApi2(AuthenticationRequest request,
			MerchantSetting merchantSetting, Map<String, String> propertyMap) {
		try {
			request.setCommand(propertyMap.get(KOTAK_COMMAND_AUTHENTICATION_KEY));

			Gson gson = new Gson();
			String jsonRequest = gson.toJson(request);

			String secureHash = KotakSecureHashGenenerator.generateHash(jsonRequest, merchantSetting.getSetting3());
			request.setSecureHash(secureHash);

			jsonRequest = gson.toJson(request);

			String encKey = merchantSetting.getmPassword();
			String encrytString = KotakUtil.encryptCardRequest(jsonRequest, encKey);

			logger.info("encrypted kotak authentication api => " + encrytString);

			KotakRequestWrapper requestWrapper = new KotakRequestWrapper();
			requestWrapper.setBankId(merchantSetting.getSetting4());
			requestWrapper.setMerchantId(merchantSetting.getMloginId());
			requestWrapper.setTerminalId(merchantSetting.getSetting1());
			requestWrapper.setOrderId(String.valueOf(request.getOrderId()));
			requestWrapper.setEncData(encrytString);

			String stringRequest = gson.toJson(requestWrapper);

			logger.info("kotak authentication final requestString => " + stringRequest);

			String authUrl = propertyMap.get(KOTAK_AUTHENTICATION_API_URL);

			KotakCall call = new KotakCall();
			call.setBaseUrl(authUrl);

//			AuthenticationResponse authResponse = call.authentication(stringRequest, encKey);
			String responseString = call.authentication(stringRequest);

			if (responseString != null) {
				KotakResponseWrapper responseWrapper = gson.fromJson(responseString, KotakResponseWrapper.class);

				if (responseWrapper != null) {
					String encRes = responseWrapper.getEncData();
					String decString = KotakUtil.decryptCardRequest(encRes, encKey);
					logger.info("kotak authentication response encData => " + decString);
					AuthenticationResponse authResponse = gson.fromJson(decString, AuthenticationResponse.class);
					authResponse.setPgId(responseWrapper.getPgId());
					return authResponse;
				}
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static AutorizationResponse authorizationApi(AuthorizationRequest request, MerchantSetting merchantSetting,
			Map<String, String> propertyMap) {
		String authUrl = propertyMap.get(KOTAK_AUTHORIZATION_API_URL);
		String encKey = merchantSetting.getmPassword();

		Gson gson = new Gson();

		String encrytString = "";
		try {
			String req = gson.toJson(request);
			String hash = KotakSecureHashGenenerator.generateHash(req, merchantSetting.getSetting3());
			request.setSecureHash(hash);

			String finalRequest = gson.toJson(request);

			encrytString = KotakUtil.encryptCardRequest(finalRequest, encKey);
			logger.info("encrytString =>" + encrytString);

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		KotakRequestWrapper requestWrapper = new KotakRequestWrapper();
		requestWrapper.setBankId(merchantSetting.getSetting4());
		requestWrapper.setMerchantId(merchantSetting.getMloginId());
		requestWrapper.setTerminalId(merchantSetting.getSetting1());
		requestWrapper.setOrderId(request.getOrderId());
		requestWrapper.setEncData(encrytString);
		String stringRequest = gson.toJson(requestWrapper);
		logger.info("kotak Authorization final KotakRequestWrapper : " + stringRequest);

		KotakCall call = new KotakCall();
		call.setBaseUrl(authUrl);
		AutorizationResponse autorizationResponse = call.authorization(stringRequest, encKey);

		return autorizationResponse;
	}

	public static SaleStatusQueryApiResponse saleStatusApi(SaleStatusQueryApiRequest request,
			MerchantSetting merchantSetting, Map<String, String> propertyMap) {
		String authUrl = propertyMap.get(KOTAK_SALE_STATUS_API_URL);
		String encKey = merchantSetting.getmPassword();

		Gson gson = new Gson();

		String encrytString = "";
		try {
			String req = gson.toJson(request);
			String hash = KotakSecureHashGenenerator.generateHash(req, merchantSetting.getSetting3());
			request.setSecureHash(hash);

			String finalRequest = gson.toJson(request);
			encrytString = KotakUtil.encryptCardRequest(finalRequest, encKey);
			logger.info("encrytString =>" + encrytString);

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		KotakRequestWrapper requestWrapper = new KotakRequestWrapper();
		requestWrapper.setBankId(merchantSetting.getSetting4());
		requestWrapper.setMerchantId(merchantSetting.getMloginId());
		requestWrapper.setTerminalId(merchantSetting.getSetting1());
		requestWrapper.setOrderId(request.getOrderId());
		requestWrapper.setEncData(encrytString);
		String stringRequest = gson.toJson(requestWrapper);

		logger.info("SaleStatusQueryApi KotakRequestWrapper : " + stringRequest);

		KotakCall call = new KotakCall();
		call.setBaseUrl(authUrl);
		SaleStatusQueryApiResponse saleStatusQueryApiResponse = call.saleStatusQueryApi(stringRequest, encKey);

		return saleStatusQueryApiResponse;
	}

	public static VerifyOtpApiResponse verifyOtpApi(VerifyOtpApiRequest request, MerchantSetting merchantSetting,
			Map<String, String> propertyMap) {
		String authUrl = propertyMap.get(KOTAK_RUPAY_VERIFY_OTP_API_URL);
		String encKey = merchantSetting.getmPassword();

		Gson gson = new Gson();

		String encrytString = "";
		try {
			String req = gson.toJson(request);
			String hash = KotakSecureHashGenenerator.generateHash(req, merchantSetting.getSetting3());
			request.setSecureHash(hash);

			String finalRequest = gson.toJson(request);

			logger.info("encData =>" + encrytString);

			encrytString = KotakUtil.encryptCardRequest(finalRequest, encKey);

			logger.info("VerifyOtpApiResponse encrytString =>" + encrytString);

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		KotakRequestWrapper requestWrapper = new KotakRequestWrapper();
		requestWrapper.setBankId(merchantSetting.getSetting4());
		requestWrapper.setMerchantId(merchantSetting.getMloginId());
		requestWrapper.setTerminalId(merchantSetting.getSetting1());
		requestWrapper.setOrderId(request.getOrderId());
		requestWrapper.setEncData(encrytString);
		String stringRequest = gson.toJson(requestWrapper);
		logger.info("verifyOtpApi KotakRequestWrapper : " + stringRequest);

		KotakCall call = new KotakCall();
		call.setBaseUrl(authUrl);
		VerifyOtpApiResponse verifyOtpApiResponse = call.verifyOtp(stringRequest, encKey);

		return verifyOtpApiResponse;
	}

	public static ResendOtpResponse resendOtpResponse(ResendOtpApiRequest request, MerchantSetting merchantSetting,
			Map<String, String> propertyMap) {
		String authUrl = propertyMap.get(KOTAK_RUPAY_RESEND_OTP_API_URL);
		String encKey = merchantSetting.getmPassword();
		Gson gson = new Gson();

		String encrytString = "";
		try {
			String req = gson.toJson(request);
			String hash = KotakSecureHashGenenerator.generateHash(req, merchantSetting.getSetting3());
			request.setSecureHash(hash);

			String finalRequest = gson.toJson(request);

			logger.info("encData =>" + encrytString);

			encrytString = KotakUtil.encryptCardRequest(finalRequest, encKey);

			logger.info("VerifyOtpApiResponse encrytString =>" + encrytString);

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		KotakRequestWrapper requestWrapper = new KotakRequestWrapper();
		requestWrapper.setBankId(merchantSetting.getSetting4());
		requestWrapper.setMerchantId(merchantSetting.getMloginId());
		requestWrapper.setTerminalId(merchantSetting.getSetting1());
		requestWrapper.setOrderId(request.getOrderId());
		requestWrapper.setEncData(encrytString);
		String stringRequest = gson.toJson(requestWrapper);
		logger.info("verifyOtpApi KotakRequestWrapper : " + stringRequest);

		KotakCall call = new KotakCall();
		call.setBaseUrl(authUrl);
		ResendOtpResponse resendOtpResponse = call.resendOtp(stringRequest, encKey);
		return resendOtpResponse;
	}

	public static RefundApiResponse refund(RefundApiRequest request, MerchantSetting merchantSetting,
			Map<String, String> propertyMap) {
		String authUrl = propertyMap.get(KOTAK_REFUND_API_URL);
		String encKey = merchantSetting.getmPassword();
		Gson gson = new Gson();

		String encrytString = "";
		try {
			String req = gson.toJson(request);
			String hash = KotakSecureHashGenenerator.generateHash(req, merchantSetting.getSetting3());
			request.setSecureHash(hash);

			String finalRequest = gson.toJson(request);

			logger.info("encData =>" + encrytString);

			encrytString = KotakUtil.encryptCardRequest(finalRequest, encKey);

			logger.info("VerifyOtpApiResponse encrytString =>" + encrytString);

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		KotakRequestWrapper requestWrapper = new KotakRequestWrapper();
		requestWrapper.setBankId(merchantSetting.getSetting4());
		requestWrapper.setMerchantId(merchantSetting.getMloginId());
		requestWrapper.setTerminalId(merchantSetting.getSetting1());
		requestWrapper.setOrderId(request.getOrderId());
		requestWrapper.setEncData(encrytString);
		String stringRequest = gson.toJson(requestWrapper);
		logger.info("verifyOtpApi KotakRequestWrapper : " + stringRequest);

		KotakCall call = new KotakCall();
		call.setBaseUrl(authUrl);
		RefundApiResponse refundApiResponse = call.refundApi(stringRequest, encKey);
		return refundApiResponse;
	}

}
