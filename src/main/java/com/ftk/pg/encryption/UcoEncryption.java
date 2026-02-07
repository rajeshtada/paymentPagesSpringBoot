package com.ftk.pg.encryption;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class UcoEncryption {

	public static void main(String[] args) throws Exception {
		String msg = "Mradul";
		String key = "getpay@febatest19#";

		String encrypted = encrypt(msg, key, "AES", 256);
		encrypted = "+PRhQQZdZb2XBDrfW0Myzg==";

		System.out.println(encrypted);
		System.out.println(decrypt(encrypted, key, "AES", 256));

	}

	private static byte[] getKey(String randomId, String cipherName, int keySize) throws Exception {
		int keyBytes = keySize / 8;
		int len = randomId.length();

		String key = randomId;
		if (len < keyBytes) {
			int repeatFactor = keyBytes / len;
			for (int i = 0; i < repeatFactor; i++) {
				key = key + randomId;
			}
		}

		return convertStringtoByteArray(key.substring(0, keyBytes));
	}

	public static String decrypt(String inputStr, String randomId, String symCipherAlgo, int keySize) throws Exception {
		byte[] Message = base64DecodeReturnBytes(inputStr);
		byte[] resultByt = null;

		byte[] randomByt = getKey(randomId, symCipherAlgo, keySize);
		SecretKeySpec skeySpec = new SecretKeySpec(randomByt, symCipherAlgo);

		Cipher cipher = Cipher.getInstance(symCipherAlgo);
		cipher.init(2, skeySpec);
		resultByt = cipher.doFinal(Message);

		return convertByteArrayToString(resultByt);
	}

	public static String encrypt(String inputStr, String randomId, String symCipherAlgo, int keySize) throws Exception {
		byte[] Message = convertStringtoByteArray(inputStr);

		byte[] resultByt = null;
		String B64EncryptedStr = null;

		byte[] randomByt = getKey(randomId, symCipherAlgo, keySize);
		SecretKeySpec skeySpec = new SecretKeySpec(randomByt, symCipherAlgo);

		Cipher cipher = Cipher.getInstance(symCipherAlgo);
		cipher.init(1, skeySpec);
		resultByt = cipher.doFinal(Message);
		B64EncryptedStr = base64Encode(resultByt);
		return B64EncryptedStr;
	}

	private static String convertByteArrayToString(byte[] resultByt) {
		return new String(resultByt);
	}

	private static byte[] convertStringtoByteArray(String inputStr) {

		return inputStr.getBytes();
	}

	private static String base64Encode(byte[] input) {
		return Base64.getEncoder().encodeToString(input);
	}

	private static byte[] base64DecodeReturnBytes(String encodedString) {
		return Base64.getDecoder().decode(encodedString);
	}

}