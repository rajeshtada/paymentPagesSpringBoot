package com.ftk.pg.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AuEncryptionUtil {

	public static final String AES_KEY_ALGORITHM = "AES";
	public static final String CHARSET = "UTF-8";

	public static byte[] cipherEncryptMessage(final byte[] message, final byte[] keyBytes, final String algorithm)
			throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException,
			BadPaddingException {
		final String keySpecAlgo = algorithm.split("/")[0];
		final Cipher cipher = Cipher.getInstance(algorithm);
		final SecretKey secretKey = new SecretKeySpec(keyBytes, keySpecAlgo);
		cipher.init(1, secretKey);
		return cipher.doFinal(message);
	}

	public static byte[] cipherEncryptMessage(final String message, final String key, final String algorithm)
			throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {
		return cipherEncryptMessage(message.getBytes("UTF-8"), key.getBytes("UTF-8"), algorithm);
	}

	public static byte[] cipherDecryptMessage(final byte[] encryptedMessage, final byte[] keyBytes,
			final String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
			BadPaddingException, IllegalBlockSizeException {
		final Cipher cipher = Cipher.getInstance(algorithm);
		final String keySpecAlgo = algorithm.split("/")[0];
		final SecretKey secretKey = new SecretKeySpec(keyBytes, keySpecAlgo);
		cipher.init(2, secretKey);
		return cipher.doFinal(encryptedMessage);
	}

	public static String cipherDecryptMessage(final byte[] encryptedMessage, final String key, final String algorithm)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, UnsupportedEncodingException {
		final byte[] decryptMessageUsingAES = cipherDecryptMessage(encryptedMessage, key.getBytes("UTF-8"), algorithm);
		return (decryptMessageUsingAES != null) ? new String(decryptMessageUsingAES, "UTF-8") : null;
	}

	public static byte[] mac(final byte[] message, final byte[] keyBytes, final String algorithm)
			throws InvalidKeyException, NoSuchAlgorithmException {
		final SecretKeySpec signingKey = new SecretKeySpec(keyBytes, algorithm);
		final Mac mac = Mac.getInstance(algorithm);
		mac.init(signingKey);
		return mac.doFinal(message);
	}

	public static byte[] mac(final String message, final String key, final String algorithm)
			throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		return mac(message.getBytes("UTF-8"), key.getBytes("UTF-8"), algorithm);
	}

}
