package com.ftk.pg.util;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class UcoBankUtils {

	public static final String UCO_NB_URL_KEY = "UCO_NB_URL_KEY";
	public static final String UCO_NB_RURL = "UCO_NB_RURL";
	public static final String UCO_NB_CATEGORY_ID = "UCO_NB_CATEGORY_ID";
	public static final String UCO_NB_PID = "UCO_NB_PID";

	public static final String UCO_NB_SECRET_KEY = "UCO_NB_SECRET_KEY";
	public static final String UCO_NB_SALT = "UCO_NB_SALT";
	public static final String UCO_NB_ENCRYPTION_KEY = "UCO_NB_ENCRYPTION_KEY";

	public static String getSHA256(String input) throws UnsupportedEncodingException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// byte messageDigest[] =
			// md.digest(input.getBytes(FEBAConstants.CHARACTER_SET));
			byte messageDigest[] = md.digest(input.getBytes(StandardCharsets.UTF_8));
			BigInteger number = new BigInteger(1, messageDigest);
			StringBuilder hexString = new StringBuilder(number.toString(16));

			while (hexString.length() < 32) {
				hexString.insert(0, '0');
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

	}

	// public static void encrypt() {
	// try {
	// String Key = "testkey";
	// String QS = "";
	// //
	// "ShoppingMallTranFG.TRAN_CRN~INR|ShoppingMallTranFG.TXN_AMT~100.0|ShoppingMall
	// //
	// TranFG.PID~000000000756|ShoppingMallTranFG.PRN~8000088195691943|ShoppingMallTranF
	// // G.ITC~201611141000002";
	// String encryptedVal = null;
	// String encodedVal = null;
	// encryptedVal = UCOSymmetricCipherHelper.encrypt(QS, Key,
	// "AES", 256);
	// System.out.println("encryptedVal--->" + encryptedVal);
	// encodedVal = URLEncoder.encode(encryptedVal, "UTF-8");
	// System.out.println("encodedVal--->" + encodedVal);
	// } catch (Exception e) {
	// new GlobalExceptionHandler().customException(e);
	// }
	// }

	public static String encrypt(String strToEncrypt, String secretKeyString, String salt) {
		try {
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(secretKeyString.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

}
