package com.ftk.pg.util;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.vo.sbiNb.SBIRequestWrapper;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SBIUtils {
	static Logger logger = LogManager.getLogger(SBIUtils.class);

	public static final String SBI_NB_PAYMENT_API_URL="SBI_NB_PAYMENT_API_URL";
	public static final String SBI_NB_DOUBLE_VERIFICATION_API_URL="SBI_NB_DOUBLE_VERIFICATION_URL";
	public static final String SBI_NB_RETURN_URL="SBI_NB_RETURN_URL";
	public static final String SBI_NB_RETURN_URL_V2="SBI_NB_RETURN_URL_V2";
	public static final String SBINB_KEYPATH="SBINB_KEYPATH";
	

	public static String getSHA2Checksum(String data) {
		MessageDigest md;
		StringBuffer hexString = new StringBuffer();
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(data.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);

			}
			return hexString.toString();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		return null;
	}

	public static String Encrypt(String data,String Keypath) {

		String path = Keypath;
		byte[] key = null;
		try {
			key = returnbyte(path);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		String encData = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			int blockSize = cipher.getBlockSize();
			System.out.println(blockSize);
			byte[] iv = new byte[cipher.getBlockSize()];
			System.out.println(iv.length);
			byte[] dataBytes = data.getBytes();
			int plaintextLength = dataBytes.length;
			int remainder = plaintextLength % blockSize;
			if (remainder != 0) {
				plaintextLength += blockSize - remainder;
			}
			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
			SecureRandom randomSecureRandom = SecureRandom.getInstance("SHA1PRNG");
			randomSecureRandom.nextBytes(iv);
			GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
			cipher.init(1, keySpec, parameterSpec);
			byte[] results = cipher.doFinal(plaintext);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			outputStream.write(iv);
			outputStream.write(results);
			byte[] encrypteddata = outputStream.toByteArray();
//			encData = Base64.encodeBase64String(encrypteddata);
			encData = encodeBase64String(encrypteddata);

			encData = encData.replace("\n", "").replace("\r", "");
		} catch (Exception ex) {

		}

		return encData;
	}

	// Decryption
	public static String Decrypt(String encData,String Keypath) {

		String decdata = null;
		String path = Keypath;
		byte[] key = null;
		key = returnbyte(path);
		try {
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
//			byte[] results = Base64.decodeBase64(encData);
			byte[] results = decodeBase64(encData);

			byte[] iv = Arrays.copyOfRange(results, 0, cipher.getBlockSize());
			cipher.init(2, keySpec, new GCMParameterSpec(128, iv));
			byte[] results1 = Arrays.copyOfRange(results, cipher.getBlockSize(), results.length);
			byte[] ciphertext = cipher.doFinal(results1);
			decdata = new String(ciphertext).trim();
		} catch (Exception ex) {

		}

		return decdata;
	}

//	public static String EncryptTPV(String data) {
//
//		String path = "/media/shared/sbikey/GETEPAY_T.key";
//		byte[] key = null;
//		try {
//			key = returnbyte(path);
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//		}
//		String encData = null;
//		try {
//			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
//			int blockSize = cipher.getBlockSize();
//			System.out.println(blockSize);
//			byte[] iv = new byte[cipher.getBlockSize()];
//			System.out.println(iv.length);
//			byte[] dataBytes = data.getBytes();
//			int plaintextLength = dataBytes.length;
//			int remainder = plaintextLength % blockSize;
//			if (remainder != 0) {
//				plaintextLength += blockSize - remainder;
//			}
//			byte[] plaintext = new byte[plaintextLength];
//			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
//			SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
//			SecureRandom randomSecureRandom = SecureRandom.getInstance("SHA1PRNG");
//			randomSecureRandom.nextBytes(iv);
//			GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
//			cipher.init(1, keySpec, parameterSpec);
//			byte[] results = cipher.doFinal(plaintext);
//			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//			outputStream.write(iv);
//			outputStream.write(results);
//			byte[] encrypteddata = outputStream.toByteArray();
////			encData = Base64.encodeBase64String(encrypteddata);
//			encData = encodeBase64String(encrypteddata);
//
//			encData = encData.replace("\n", "").replace("\r", "");
//		} catch (Exception ex) {
//
//		}
//
//		return encData;
//	}
//
//	// Decryption
//	public static String DecryptTPV(String encData) {
//
//		String decdata = null;
//		String path = "/media/shared/sbikey/GETEPAY_T.key";
//		byte[] key = null;
//		key = returnbyte(path);
//		try {
//			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
//			SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
////			byte[] results = Base64.decodeBase64(encData);
//			byte[] results = decodeBase64(encData);
//
//			byte[] iv = Arrays.copyOfRange(results, 0, cipher.getBlockSize());
//			cipher.init(2, keySpec, new GCMParameterSpec(128, iv));
//			byte[] results1 = Arrays.copyOfRange(results, cipher.getBlockSize(), results.length);
//			byte[] ciphertext = cipher.doFinal(results1);
//			decdata = new String(ciphertext).trim();
//		} catch (Exception ex) {
//
//		}
//
//		return decdata;
//	}
	public  static byte[] returnbyte(String path) {
		FileInputStream fileinputstream;
		byte[] abyte = null;
		try {
			fileinputstream = new FileInputStream(path);
			abyte = new byte[fileinputstream.available()];
			fileinputstream.read(abyte);
			fileinputstream.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			new GlobalExceptionHandler().customException(e);
		}
		return abyte;
	}

	public static String encodeBase64String(byte[] encrypteddata) {
		String encodedString = Base64.getEncoder().encodeToString(encrypteddata);
		return encodedString;
	}

	public static byte[] decodeBase64(String encData) {
		byte[] decodedBytes = Base64.getDecoder().decode(encData);
		return decodedBytes;
	}
	

	public static String postApi(String url) {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS)
					.writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS)
					.hostnameVerifier(new MGHostnameVerifier()).build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = RequestBody.create(mediaType, "");
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();

			String responseString = response.body().string();
			return responseString;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		return null;
	}
	
public static String postapi2(String url,SBIRequestWrapper requestwrapper) {
	Gson gson = new Gson();
	try {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
    	MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("encdata", requestwrapper.getEncdata())
				
				.addFormDataPart("merchant_code",requestwrapper.getMerchant_code()).build();
		Request request = new Request.Builder().url(url).method("POST", body).build();
		Response response = client.newCall(request).execute();
		String res = response.body().string();

		return res;
	} catch (Exception e) {
		new GlobalExceptionHandler().customException(e);

	}
	return null;
}
}


