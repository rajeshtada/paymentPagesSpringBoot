package com.ftk.pg.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.apache.commons.codec.binary.Hex;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FrmEncryptionUtil {

	 private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
	    private static final int TAG_LENGTH_BIT = 128;
	    private static final int IV_LENGTH_BYTE = 12;
	    private static final Charset UTF_8 = StandardCharsets.UTF_8;
	    private static final String KEY = "aJceohRnRhlBjNVMI0OVxdnkwhGjjOxEl97ekggOSOU=";

	    public static String base64encrypt(String plainText) {
	        return Base64.getEncoder().encodeToString(plainText.getBytes(StandardCharsets.UTF_8));
	    }

	    public static String base64decrypt(String encryptedText) {
	        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
	        return new String(decodedBytes, StandardCharsets.UTF_8);
	    }

	    public static String encrypt(String pText) throws Exception {
	        byte[] decodedKey = Base64.getDecoder().decode(KEY);
	        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	        byte[] iv = CryptoUtils.getRandomNonce(IV_LENGTH_BYTE);
	        byte[] encryptedText = encryptWithPrefixIV(pText.getBytes(UTF_8), secretKey, iv);
	        return Hex.encodeHexString(encryptedText);
	    }

	    public static String decrypt(String encryptedHex) throws Exception {
	        byte[] decodedKey = Base64.getDecoder().decode(KEY);
	        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	        byte[] encryptedBytes = Hex.decodeHex(encryptedHex);
	        return decryptWithPrefixIV(encryptedBytes, secretKey);
	    }

	    public static byte[] encryptWithPrefixIV(byte[] pText, SecretKey secret, byte[] iv) throws Exception {
	        byte[] cipherText = encrypt(pText, secret, iv);
	        return ByteBuffer.allocate(iv.length + cipherText.length)
	                         .put(iv)
	                         .put(cipherText)
	                         .array();
	    }

	    public static byte[] encrypt(byte[] pText, SecretKey secret, byte[] iv) throws Exception {
	        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
	        cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
	        return cipher.doFinal(pText);
	    }

	    public static String decryptWithPrefixIV(byte[] cText, SecretKey secret) throws Exception {
	        ByteBuffer bb = ByteBuffer.wrap(cText);
	        byte[] iv = new byte[IV_LENGTH_BYTE];
	        bb.get(iv); 
	        byte[] cipherText = new byte[bb.remaining()];
	        bb.get(cipherText);
	        return decrypt(cipherText, secret, iv);
	    }

	    public static String decrypt(byte[] cText, SecretKey secret, byte[] iv) throws Exception {
	        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
	        cipher.init(Cipher.DECRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
	        byte[] plainText = cipher.doFinal(cText);
	        return new String(plainText, UTF_8);
	    }
	    
}
