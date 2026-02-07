package com.ftk.pg.util;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.requestvo.IciciHybridRequest;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IciciDynamicQrCall {
	
	private static Logger logger = LogManager.getLogger(IciciDynamicQrCall.class);
	public static final String GETEPAY_ICICI_PRIVATE_KEY = "GETEPAY_ICICI_PRIVATE_KEY";
	public static final String ICICI_CPAYPUBLICCER_KEY = "ICICI_CPAYPUBLICCER_KEY";
	public static final String ICICI_PUBLIC_KEY_DYNAMIC_QR = "ICICI_PUBLIC_KEY_DYNAMIC_QR";
	public static final String ICICI_PRIVATE_KEY_DYNAMIC_QR = "ICICI_PRIVATE_KEY_DYNAMIC_QR";
	public static final String ICICI_DYNAMIC_QR_URL = "ICICI_DYNAMIC_QR_URL";

	public static String postApiQr3(String apiUrl, String requestString) {
		try {
			OkHttpClient client = (new OkHttpClient()).newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, requestString);
			Request request = new Builder().url(apiUrl).method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			logger.info("PostApiQr3 response ==> " + responseString);
			//todo//
			return responseString;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;

	}
	
	public static String hybridDeryption(Map<String, String> properties, IciciHybridRequest hybridResponse)
			throws Exception {
		String getepayClientCertificatePath = properties.get(GETEPAY_ICICI_PRIVATE_KEY);
		String encryptedData = hybridResponse.getEncryptedData();

		byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedData);
		// Arrays.copyOfRange(original, 16, original.length);
		byte[] ivBytes = Arrays.copyOfRange(encryptedDataBytes, 0, 16);
		byte[] finalEncryptedDataBytes = Arrays.copyOfRange(encryptedDataBytes, 16, encryptedDataBytes.length);

		String encryptedKeyRes = decrypt(hybridResponse.getEncryptedKey(), getepayClientCertificatePath);
		
		String decodedData = decryptAES(finalEncryptedDataBytes, ivBytes, encryptedKeyRes.getBytes());
		return decodedData;
	}
	
	public static String decrypt(String data, String privateKeyPath) throws Exception {
		return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(privateKeyPath));
	}
	
	public static String decrypt(byte[] data, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(cipher.doFinal(data));
	}
	
	public static PrivateKey getPrivateKey(String privateKeyPath) throws Exception {
		String privateKey = new String(Files.readAllBytes(Paths.get(privateKeyPath)));

		byte[] pkbytes = Base64.getDecoder().decode(privateKey.getBytes());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkbytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PrivateKey pk = keyFactory.generatePrivate(keySpec);
		return pk;
	}
	
	public static String decryptAES(byte[] encrypted, byte[] iv, byte[] key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/NOPADDING");

		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		SecretKey secKey = new SecretKeySpec(key, 0, key.length, "AES");
		try {
			cipher.init(Cipher.DECRYPT_MODE, secKey, ivSpec);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		byte[] decryptedText = cipher.doFinal(encrypted);
		return new String(decryptedText);
	}

}
