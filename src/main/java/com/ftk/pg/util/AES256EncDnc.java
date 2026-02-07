package com.ftk.pg.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Formatter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.ftk.pg.requestvo.AxisData;

import jakarta.xml.bind.DatatypeConverter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AES256EncDnc {

	private static final String SECRET_TEXT = "123sesq";
	private static final String SALT = "1axis_jtutvrb01eVlp";
	public static final String AXIS_DV_ENC_KEY = "AXIS_DV_ENC_KEY";
	public static final String AXIS_NB_VERIFICATION_URL = "AXIS_NB_VERIFICATION_URL";
	public static final String AXIS_NB_PID = "AXIS_NB_PID";
	public static final String AXIS_NB_CATEGORY_ID = "AXIS_NB_CATEGORY_ID";
	public static final String AXIS_NB_ENCCAT = "AXIS_NB_ENCCAT";
	
	
	
	public static SecretKey statickey = null;
	static {
		try {
			SecretKeyFactory factory1 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

			KeySpec spec1 = new PBEKeySpec(SECRET_TEXT.toCharArray(), SALT.getBytes(), 65536, 256);
			statickey = new SecretKeySpec(factory1.generateSecret(spec1).getEncoded(), "AES");
		} catch (Exception es) {

		}

	}

	public static byte[] setKey(String myKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {

		byte[] key;
		MessageDigest sha = null;
		key = myKey.getBytes("UTF-8");
		sha = MessageDigest.getInstance("SHA-256");
		key = sha.digest(key);
		key = Arrays.copyOf(key, 16); // use only first 128 bit
		return key;

	}

	public static String encryptforpayment(String inputStr, String sKey) {

		byte[] key = null;
		Cipher cipher = null;
		String encryptedValue = "";
		try {
			key = setKey(sKey);

			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

			IvParameterSpec ivParameterSpec = new IvParameterSpec(key);

			cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

			encryptedValue = DatatypeConverter.printBase64Binary(cipher.doFinal(inputStr.getBytes("UTF-8")));

		} catch (Exception e) {

		}

		return encryptedValue;

	}

	public static String decryptdoubleverification(String textToDecrypt1, String sKey) {

		String decryptedString = "";

		byte[] key = null;

		try {

			key = setKey(sKey);
			// key=sKey.getBytes();

			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
//	IvParameterSpec ivParameterSpec = new IvParameterSpec(key);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(key);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
			decryptedString = new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(textToDecrypt1)));
			;

		} catch (Exception e) {

		}

		return decryptedString;

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
