package com.ftk.pg.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Formatter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.requestvo.AxisData;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AxisUtils {
	static Logger logger = LogManager.getLogger(AxisUtils.class);

	public static final String AXIS_NB_SECRET_KEY = "AXIS_NB_SECRET_KEY";
	public static final String AXIS_NB_IV = "AXIS_NB_IV";
	public static final String AXIS_NB_RETURN_URL = "AXIS_NB_RETURN_URL";
	public static final String AXIS_NB_RETURN_URL_V2 = "AXIS_NB_RETURN_URL_V2";
	public static final String AXIS_NB_TRANSACTION_URL = "AXIS_NB_TRANSACTION_URL";
	public static final String AXIS_NB_VERIFICATION_URL = "AXIS_NB_VERIFICATION_URL";
	public static final String AXIS_NB_SALT = "AXIS_NB_SALT";
	public static final String AXIS_NB_CATEGORY_ID = "AXIS_NB_CATEGORY_ID";
	public static final String AXIS_NB_CG = "AXIS_NB_CG";
	public static final String AXIS_NB_CRN = "AXIS_NB_CRN";
	public static final String AXIS_NB_MD = "AXIS_NB_MD";
	public static final String AXIS_NB_PID = "AXIS_NB_PID";
	public static final String AXIS_NB_RESPONSE = "AXIS_NB_RESPONSE";

	public static String encrypt(String salt, String iv, String encKey, String painString) { // AES256-Encryption
		Cipher cipher = null;
		byte[] encrypted = null;
		String encVal = "";
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] iv1 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
//			byte[] encKey1=hex(encKey);
			SecretKey key = generateKey1(salt, encKey);
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(hex(iv)));
			encrypted = cipher.doFinal(painString.getBytes("UTF-8"));
			encVal = new String(Base64.getEncoder().encode(encrypted));
			String str = "INFO~customer~AES256EncDnc~3~encrypt~" + encVal;
//			log_details.addlog(str);
		} catch (Exception e) {

			String str = "SHP_ERR~customer~AES256EncDnc~3~encrypt~" + e;

			// String errorMessage = "Exception at line " +
			// e.getStackTrace()[0].getLineNumber() + ": " + e.getMessage();

		}
		return encVal;

	}

	// String strToDecrypt, String SALT, String SECRET_KEY,String iv
	public static String qsDecrypt(String salt, String iv, String encKey, String strToDecrypt) { // AES256-Decryption
		Cipher cipher = null;
		try {
			byte[] devalue = null;
			String descryptedString = "";
			try {
				cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				SecretKey key = generateKey1(salt, encKey);
				cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(hex(iv)));
				devalue = cipher.doFinal((DatatypeConverter.parseBase64Binary(strToDecrypt)));
				descryptedString = new String(devalue);
			} catch (Exception e) {

			}
			return descryptedString;
		} catch (Exception e) {
		}
		return null;
	}

	public static SecretKey generateKey1(String salt, String passphrase) {
		SecretKey key = null;
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), hex(salt), 10, 256);
			key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
		} catch (Exception e) {

			String str = "SHP_ERR~customer~AES256EncDnc~3~generateKey1~" + e;

//			System.out.println("Error in generateKey1"+e);
			// String errorMessage = "Exception at line " +
			// e.getStackTrace()[0].getLineNumber() + ": " + e.getMessage();

		}
		return key;

	}

	public static byte[] hex(String str) {
		return DatatypeConverter.parseHexBinary(str);
	}

	public static String getSHA256(String value) throws SecurityException {
		String hashValue = "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			hashValue = byteArray2Hex(messageDigest.digest(value.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// LogManager.logError(arg0, e);
			String str = "SHP_ERR~customer~Common~3~getSHA256~" + e;

			throw new SecurityException(e.getMessage(), e);
		}

		return hashValue;
	}

	public static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		byte[] arrayOfByte = hash;
		int j = hash.length;
		for (int i = 0; i < j; i++) {
			byte b = arrayOfByte[i];

			formatter.format("%02x", new Object[] { Byte.valueOf(b) });
		}
		return formatter.toString();
	}

	public static String postapi(String data, String url) {
		try {
//		OkHttpClient client = new OkHttpClient().newBuilder().build();
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, data);
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			// System.out.info("mindgate utkarsh callback response :: " + response);

			String res = response.body().string();
			// logger.info("response body :: " + res);
			return res;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			// logger.info("Error in hit Utkarsh Bank Api :: " + e.getMessage());
		}
		return null;
	}

	public static String sendFormPostRequest(String url, AxisData axisdata) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("encdata", axisdata.getEncdata()).addFormDataPart("payeeid", axisdata.getPayeeid())
				.addFormDataPart("enccat", axisdata.getEnccat()).addFormDataPart("mercat", axisdata.getMercat())
				.build();
		Request request = new Request.Builder().url(url).method("POST", body).build();
		Response response = client.newCall(request).execute();
		String res = response.body().string();
		return res;
	}
}