package com.ftk.pg.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptDecryptUtil {
	private static Cipher c;
	private static Key encryptDecryptKey;

	public EncryptDecryptUtil(String sMasterKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		String sAlgorithm = "AES";
		String sKey = sMasterKey.trim();

		if (sKey.length() != 16) {
			throw new InvalidKeyException("Master Key should be of 16 Characters");
		} else {
			byte[] sKeyBytes = sKey.getBytes();
			encryptDecryptKey = new SecretKeySpec(sKeyBytes, sAlgorithm);
			c = Cipher.getInstance(sAlgorithm);
		}
	}

	public String encrypt(String stringToEncrypt)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		c.init(Cipher.ENCRYPT_MODE, encryptDecryptKey);
		byte[] encryptedBytes = c.doFinal(stringToEncrypt.getBytes());
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	public String decrypt(String stringToDecrypt)
			throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
		c.init(Cipher.DECRYPT_MODE, encryptDecryptKey);
		byte[] decodedBytes = Base64.getDecoder().decode(this.replaceSpecialChars(stringToDecrypt));
		byte[] decryptedBytes = c.doFinal(decodedBytes);
		return new String(decryptedBytes);
	}

	private String replaceSpecialChars(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); ++i) {
			if (str.charAt(i) == ' ') {
				sb.append('+');
			} else {
				sb.append(str.charAt(i));
			}
		}
		return sb.toString();
	}
}