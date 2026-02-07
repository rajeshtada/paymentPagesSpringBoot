package com.ftk.pg.util;

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

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.requestvo.CardBinRequestWrapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CardBinUtils {

	private static final Charset UTF_8 = StandardCharsets.UTF_8;
	private static final String CIPHER_ALGORITHM = "AES/GCM/NoPadding";
	private static final String FACTORY_INSTANCE = "PBKDF2WithHmacSHA512";
	private static final int TAG_LENGTH = 16;
	private static final int IV_LENGTH = 12;
	private static final int SALT_LENGTH = 16;
	private static final int KEY_LENGTH = 32;
	private static final int ITERATIONS = 65535;
	public static final String NEW_CARD_BEAN_API="NEW_CARD_BEAN_API";
	public static final String NEW_CARD_BEAN_ENABLE="NEW_CARD_BEAN_ENABLE";
	public static final String CARD_BEAN_V2_API_ENABLE="CARD_BEAN_V2_API_ENABLE";
	public static final String CARD_BIN_MASTER_KEY="CARD_BIN_MASTER_KEY";
	public static final String CARD_BIN_METHOD="CARD_BIN_METHOD";
	public static final String CARD_BIN_MID="CARD_BIN_MID";

	public static String encrypt(String text, String key) throws Exception {
		byte[] salt = getRandomNonce(SALT_LENGTH);
		byte[] iv = getRandomNonce(IV_LENGTH);

		SecretKey secretKey = getSecretKey(key, salt);

		Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, secretKey, iv);
		byte[] encryptedMessageByte = cipher.doFinal(text.getBytes(UTF_8));
		byte[] cipherByte = ByteBuffer.allocate(salt.length + iv.length + encryptedMessageByte.length).put(salt).put(iv)
				.put(encryptedMessageByte).array();
		return Base64.getEncoder().encodeToString(cipherByte);
	}

	public static String decrypt(String text, String key) throws Exception {
		try {
			

		byte[] decode = Base64.getDecoder().decode(text.getBytes(UTF_8));
		ByteBuffer byteBuffer = ByteBuffer.wrap(decode);
		byte[] salt = new byte[SALT_LENGTH];
		byteBuffer.get(salt);
		byte[] iv = new byte[IV_LENGTH];
		byteBuffer.get(iv);
		byte[] content = new byte[byteBuffer.remaining()];
		byteBuffer.get(content);

		SecretKey aesKeyFromPassword = getAESKeyFromPassword(key.toCharArray(), salt);
		GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH * 8, iv);
		Cipher cipher = initCipher(Cipher.DECRYPT_MODE, aesKeyFromPassword, iv);

		byte[] plainText = cipher.doFinal(content);
		return new String(plainText, UTF_8);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static SecretKey getSecretKey(String password, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH * 8);
		SecretKeyFactory factory = SecretKeyFactory.getInstance(FACTORY_INSTANCE);
		return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
	}

	private static SecretKey getAESKeyFromPassword(char[] password, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		KeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH * 8);
		SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
		return secret;
	}

	private static Cipher initCipher(int mode, SecretKey secretKey, byte[] iv) throws InvalidKeyException,
			InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(mode, secretKey, new GCMParameterSpec(TAG_LENGTH * 8, iv));
		return cipher;
	}

	private static byte[] getRandomNonce(int length) {
		byte[] nonce = new byte[length];
		new SecureRandom().nextBytes(nonce);
		return nonce;
	}

	public static boolean verifySignature(CardBinRequestWrapper requestWrapperDto) {
		String generatedSignature = generateSignature(
				requestWrapperDto.getMid() + requestWrapperDto.getMethod() + requestWrapperDto.getRequest());
		System.out.println("sign : " + generatedSignature);
		return generatedSignature.equals(requestWrapperDto.getSignature());
	}

	public static String signatureGenarator(CardBinRequestWrapper cardbinRequestWrapper) {
		return generateSignature(cardbinRequestWrapper.getMid() + cardbinRequestWrapper.getMethod()
				+ cardbinRequestWrapper.getRequest());
	}

	public static String generateSignature(String text) {
		return sha512(text);
	}

	public static String sha512(String text) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			return Base64.getEncoder().encodeToString(digest.digest(text.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
		}
		return null;
	}

	public static String apiCall(String data, String url) {

		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, data);
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();

			return response.body().string();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}