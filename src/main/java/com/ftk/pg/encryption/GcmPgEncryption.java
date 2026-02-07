package com.ftk.pg.encryption;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.util.EncryptionUtil;

public class GcmPgEncryption {
	static Logger logger = LogManager.getLogger(GcmPgEncryption.class);
	private static final Charset UTF_8 = StandardCharsets.UTF_8;
	private static final String CIPHER_ALGORITHM = "AES/GCM/NoPadding";
	private static final String FACTORY_INSTANCE = "PBKDF2WithHmacSHA512";
	private static final int TAG_LENGTH = 16;
	private static final int IV_LENGTH = 12;
	private static final int SALT_LENGTH = 16;
	private static final int KEY_LENGTH = 32;
	private static final int ITERATIONS = 10;
//	private static final String masterKey = "bVpxNHQ3dyF6JUMqRi1KYU5kUmdVa1hwMnI1dTh4L0E=";
	private static final String masterKey = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";

	public String iv;
	public String ivKey;
	public String mKey;

	public GcmPgEncryption(String iv) throws Exception {
		this.iv = iv;
		this.ivKey = masterKey;

		String combinedKey = masterKey + iv;
		byte[] aesKey = generateAESKey(combinedKey);
		mKey = Base64.getEncoder().encodeToString(aesKey);
	}

	public GcmPgEncryption(String iv, String key) throws Exception {
		this.iv = iv;
		this.ivKey = key;

		String combinedKey = key + iv;
		byte[] aesKey = generateAESKey(combinedKey);
		mKey = Base64.getEncoder().encodeToString(aesKey);
	}

	private static SecretKey getAESKeyFromPassword(char[] password, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		KeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH * 8);
		SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
		return secret;
	}

	public static byte[] getRandomNonce(int length) {
		byte[] nonce = new byte[length];
		new SecureRandom().nextBytes(nonce);
		return nonce;
	}

	public static SecretKey getSecretKey(String password, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH * 8);

		SecretKeyFactory factory = SecretKeyFactory.getInstance(FACTORY_INSTANCE);
		return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
	}

	private static Cipher initCipher(int mode, SecretKey secretKey, byte[] iv) throws InvalidKeyException,
			InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(mode, secretKey, new GCMParameterSpec(TAG_LENGTH * 8, iv));
		return cipher;
	}

	public String decryptWithMKeys(String cipherContent) throws Exception {

		byte[] decode = Base64.getDecoder().decode(cipherContent.getBytes(UTF_8));
		ByteBuffer byteBuffer = ByteBuffer.wrap(decode);

		byte[] salt = new byte[SALT_LENGTH];
		byteBuffer.get(salt);

		byte[] iv = new byte[IV_LENGTH];
		byteBuffer.get(iv);

		byte[] content = new byte[byteBuffer.remaining()];
		byteBuffer.get(content);

		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		SecretKey aesKeyFromPassword = getAESKeyFromPassword(mKey.toCharArray(), salt);
		cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH * 8, iv));
		byte[] plainText = cipher.doFinal(content);
		return new String(plainText, UTF_8);
	}

	public String encryptWithMKeys(String plainMessage) throws Exception {

		byte[] salt = getRandomNonce(SALT_LENGTH);
		SecretKey secretKey = getSecretKey(mKey, salt);

		byte[] iv = getRandomNonce(IV_LENGTH);

		Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, secretKey, iv);

		byte[] encryptedMessageByte = cipher.doFinal(plainMessage.getBytes(UTF_8));

		byte[] cipherByte = ByteBuffer.allocate(salt.length + iv.length + encryptedMessageByte.length).put(salt).put(iv)
				.put(encryptedMessageByte).array();
		return Base64.getEncoder().encodeToString(cipherByte);
	}

	private static byte[] generateAESKey(String combinedKey) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		return digest.digest(combinedKey.getBytes(StandardCharsets.UTF_8));
	}
}
