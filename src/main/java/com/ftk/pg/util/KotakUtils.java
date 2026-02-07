package com.ftk.pg.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.ftk.pg.exception.GlobalExceptionHandler;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class KotakUtils {

	private static Logger logger = LogManager.getLogger(KotakUtils.class);

	public static final String KOTAK_NB_REFUND_API_URL = "KOTAK_NB_REFUND_API_URL";
//	public static final String KOTAK_NB_REFUND_API_KEY = "KOTAK_NB_REFUND_API_KEY";
//	public static final String KOTAK_NB_REFUND_CHECKSUM_KEY = "KOTAK_NB_REFUND_CHECKSUM_KEY";
	public static final String KOTAK_NB_REFUND_MERTCHANT_ID = "KOTAK_NB_REFUND_MERTCHANT_ID";
	public static final String KOTAK_NB_REFUND_GENERATE_TOKEN_URL = "KOTAK_NB_REFUND_GENERATE_TOKEN_URL";
	public static final String KOTAK_NB_REFUND_CLIENT_ID = "KOTAK_NB_REFUND_CLIENT_ID";
	public static final String KOTAK_NB_REFUND_CLIENT_SECRET = "KOTAK_NB_REFUND_CLIENT_SECRET";
	public static final String KOTAK_ENCRYPTION_KEY = "KOTAK_ENCRYPTION_KEY";
	public static final String KOTAK_CHECKSUM_KEY = "KOTAK_CHECKSUM_KEY";
	
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
	
	

	public static String getHMAC256Checksum(String msg, String checkSumKey) {
		Mac sha512_HMAC = null;
		String result = null;
		try {
			byte[] byteKey = checkSumKey.getBytes("UTF-8");
			final String HMAC_SHA256 = "HmacSHA256";
			sha512_HMAC = Mac.getInstance(HMAC_SHA256);
			SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA256);
			sha512_HMAC.init(keySpec);
			byte[] mac_data = sha512_HMAC.doFinal((msg).getBytes("UTF-8"));
			// result = Base64.toBase64String(mac_data);
			result = bytesToHex(mac_data);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("Done");
		}
		return result;

	}

	public static String bytesToHex(byte[] bytes) {
		final char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static String encrypt(String message, String key)
			throws GeneralSecurityException, UnsupportedEncodingException {
		if (message == null || key == null) {
			throw new IllegalArgumentException("text to be encrypted and key should not be null");
		}
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		byte[] messageArr = message.getBytes();
		SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
		byte[] ivParams = new byte[16];
		byte[] encoded = new byte[messageArr.length + 16];
		System.arraycopy(ivParams, 0, encoded, 0, 16);
		System.arraycopy(messageArr, 0, encoded, 16, messageArr.length);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(ivParams));
		byte[] encryptedBytes = cipher.doFinal(encoded);
		encryptedBytes = Base64.getEncoder().encode(encryptedBytes);
		return new String(encryptedBytes);
	}

	public static String decrypt(String encryptedStr, String key)
			throws GeneralSecurityException, UnsupportedEncodingException {
		if (encryptedStr == null || key == null) {
			throw new IllegalArgumentException("text to be decrypted and key should not be null");
		}
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
		byte[] encoded = encryptedStr.getBytes();
		encoded = Base64.getDecoder().decode(encoded);
		byte[] decodedEncrypted = new byte[encoded.length - 16];
		System.arraycopy(encoded, 16, decodedEncrypted, 0, encoded.length - 16);
		byte[] ivParams = new byte[16];
		System.arraycopy(encoded, 0, ivParams, 0, ivParams.length);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(ivParams));
		byte[] decryptedBytes = cipher.doFinal(decodedEncrypted);
		return new String(decryptedBytes);
	}
	
	public static String generateToken( String generateTokenurl, String clientId, String clientSecret) {
		try {
			OAuthClient client = new OAuthClient(new URLConnectionClient());

			OAuthClientRequest request = OAuthClientRequest.tokenLocation(generateTokenurl)
					.setGrantType(GrantType.CLIENT_CREDENTIALS).setClientId(clientId).setClientSecret(clientSecret)
					.buildBodyMessage();

//			System.out.println(request.getBody());

			String token = client.accessToken(request, OAuth.HttpMethod.POST, OAuthJSONAccessTokenResponse.class)
					.getAccessToken();

			return token;
		} catch (Exception exn) {
			exn.printStackTrace();
		}
		return null;
	}
	
	public static String postApi(String authUrl, String requestString, String token) {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, requestString);
			Request request = new Request.Builder().url(authUrl).method("POST", body)
					.addHeader("Content-Type", "application/json").addHeader("Authorization", "Bearer " + token)
					.build();
			Response response = client.newCall(request).execute();
			logger.info("response : " + response);
			String responseString = response.body().string();
			logger.info("response ==> " + responseString);
			if (response.code() == 200) {
				return responseString;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	
	public static String dateAndTime(String dateStr) {
		try {
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = inputFormat.parse(dateStr);
        
        // Then format it to the desired output
        SimpleDateFormat outputFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        String formatted = outputFormat.format(date);
        return formatted;
		}catch(Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}
	
	public static void main(String[] args) throws GeneralSecurityException, UnsupportedEncodingException {
		String str = "12012018021010|BAJAJFIN|166982320605442|Bajaj FIN|07212000004221|1|Mnth|12022023|13032030|F|||||4CC8D40D686EF387928F1DEDEE7C940E7B2A97BA4A88D6815F7C294FAFF57157";
		// String key ="6e852f87d0b44684b3addd5b7fa99301";
		String key = "211995C8985ABA0F291E1941608FEFBD";
		// String enc = encrypt(str, key);
		// System.out.println(enc);
		String str1 = "fuxnhBRDsB9DojRm0YO06l3/Tp409ykCPJP1yxwN+Hy4HTsd8i3M0JBEfqnzTgH9N0ceE7cTMrR2u6f8gvzUgtqg3h90O8oq+PMLLG2vb/1l96Ift/R7MIYgNgOcrx2OJUo0fXm9sCt5rECuNlcFTwoat+X0umlaECm8+5Sbm5ibI0ZeokqfEohwuyJyHUyEm9yFWzsDQJXfVwKYAJlo6FhLs0hrMGFmBoQzqGYNnAxsuge/o4O3C1K7cKXf1zGOCUf8jldiKUYS9h7lpiUyJQ==";
		String dcr = decrypt(
				"Vq8p4mttR1oYYtwhdnW1wh+tkiY1VYZbFuDS4h3vXQu0a2hUJfRCDMMgVVdGdfdb",
				key);
		System.out.println(dcr);
	}
}
