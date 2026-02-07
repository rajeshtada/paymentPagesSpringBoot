package com.ftk.pg.encryption;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;

public class AesEncryption {
	
	static Logger logger = LogManager.getLogger(AesEncryption.class);
	
	public static String iv = "aibKcM9Jq6i8NIt+ACg8LQ==";
	public static String ivKey = "dxW/a/raDOtWV9T/8UL8OLVig0am9k4kBMw4x9rddfg=";

	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[] { 'P', 'i', '@', 'a', 'T', 'o', 'm', '#', '$', 't', 'e', 'c', 'h',
			'1', '2', '3' };

	
	public static String encrypt(String data) throws Exception {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal = c.doFinal(data.getBytes());
			String encodeToString = Base64.getEncoder().encodeToString(encVal);
			if (Pattern.compile("\\/").matcher(encodeToString).find()) {
				encodeToString = encodeToString.replaceAll("/", "_");
			}
			return encodeToString;
	}

	
	public static String decrypt(String encryptedData) throws Exception {
		try {
			

			if (Pattern.compile("_").matcher(encryptedData).find()) {
				encryptedData = encryptedData.replaceAll("_", "/");
			}
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
			byte[] decValue = c.doFinal(decordedValue);
			return new String(decValue);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	
	private static Key generateKey() throws Exception {
		return new SecretKeySpec(keyValue, ALGO);
	}

	public static void main(String[] args) throws Exception {

			String key1 = "42707454342142707454342342707454";
//			String decrypt = decrypt("dc89e0fe26f11a13e6c73ee6ba1c45fbdf84f6abe8b198f3daf5030760f61d3cef9f0428215f781d7a4f3db866b0393dddcff955ef4069cf576c732161b40cdd4f71dacaf4237757b69a44c0844e4e28e4305c64595f0a161600babf5abd741036892cde4460393232984f1f95794fd4792ffbd619262b91ef1f14e50a7cd35b126d6d5cf893e1b099225827a85334522761b692338a1f50db8da6ed59cb29a821e73504a63be59c1366afcea3c60159994195eaf98acd83cdf4ab110aad6599ef1b7745f302700908fdd89d1bd1d0ab0e71835af75749c5f1f24f50548dc21b95da2684ad12e7aaf2f0a8f9b534a63a9bce0132a87dd89038afbe2496e4d4831d5423f94a5f86c83e39fbab1abf39e151cbc30ae5142e11243cc22de355d793a761c997bb1f0b93892b773255acc7fe05b369ad3d497f6681b6a1c561793d3404bf26d576b8e5ca00dea83acf264547bca0a27519b91b5652014c5e3ba6eb0c109be2169e919cdf0fa6c24ab57acfdfa473b8821d369ac1d88d37f3211306765af1acef58c1e71b60ce4fa92169d35170dea5e7faaed5d524144e3593");
			String decrypt = decrypt("U8l8DtehRf6RkbvtQh8H71xNMxZRYM+HQ1CjLGJ5n1E=");
			logger.info("Decrypted String ==>" + decrypt);
			System.out.println(decrypt);
		

	}
	
	public static void main1(String[] args) throws Exception {

//		String key1 = "logs/pg/paymentpages.log";
		String key1 = "logs/pgru/paymentpagesru.log";
		String encrypt = encrypt(key1);
		System.out.println(encrypt);

	}

	public static final char[] hexArray = "442A472D4B615064".toCharArray();
	public static final Integer INIT_VECTOR_LENGTH = 16;

	public static SecretKeySpec secretKey;
	public static byte[] key;

	public static void setKey(String myKey) throws Exception {
		MessageDigest sha = null;
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 32);
			secretKey = new SecretKeySpec(key, "AES");
		
	}

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static String bytesToHex1(byte[] hashInBytes) {

		StringBuilder sb = new StringBuilder();
		for (byte b : hashInBytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();

	}

	static String stringToHex(String string) {
		StringBuilder buf = new StringBuilder(200);
		for (char ch : string.toCharArray()) {
			if (buf.length() > 0)
				buf.append(' ');
			buf.append(String.format("%02x", (int) ch));
		}

		return buf.toString().replaceAll(" ", "");
	}

	public static String encryptHex(String request) throws Exception {
//		return bytesToHex(request.getBytes());
		String initVector = null;
			String secretKey = new String(hexArray);
			SecureRandom secureRandom = new SecureRandom();
			byte[] initVectorBytes = new byte[INIT_VECTOR_LENGTH / 2];
			secureRandom.nextBytes(initVectorBytes);
			initVector = bytesToHex(initVectorBytes);
			initVectorBytes = initVector.getBytes("UTF-8");

			IvParameterSpec ivParameterSpec = new IvParameterSpec(initVectorBytes);
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

			// Encrypt input text
			byte[] encrypted = cipher.doFinal(request.getBytes("UTF-8"));

			ByteBuffer byteBuffer = ByteBuffer.allocate(initVectorBytes.length + encrypted.length);
			byteBuffer.put(initVectorBytes);
			byteBuffer.put(encrypted);

			// Result is base64-encoded string: initVector + encrypted result
			// String result = Base64.encodeToString(byteBuffer.array(), Base64.DEFAULT);
			// String result = stringToHex(new String(byteBuffer.array(), "UTF-8"));
			String result = bytesToHex1(byteBuffer.array());
			return result;
		

		
	}

	public static String ivEncrypt(String string) throws Exception {
			byte[] ivs = Base64.getDecoder().decode(iv);
			IvParameterSpec ivspec = new IvParameterSpec(ivs);

			SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(ivKey), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivspec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(string.getBytes("UTF-8")));
		
	}

	public static String ivDecrypt(String message) throws Exception {
			byte[] msg = Base64.getDecoder().decode(message.getBytes());

			SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(ivKey), "AES");

			byte[] ivs = Base64.getDecoder().decode(iv);
			IvParameterSpec ivspec = new IvParameterSpec(ivs);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivspec);
			return new String(cipher.doFinal(msg));
		
	}
	
}
