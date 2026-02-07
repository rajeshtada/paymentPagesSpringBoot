package com.ftk.pg.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.security.cert.X509Certificate;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YESBankUtils {

	static Logger logger = LogManager.getLogger(YESBankUtils.class);
	static SecretKeySpec keyEncDec=null;
	static IvParameterSpec IVParameterSpec=null;
	public static final String YES_BANK_RETURN_URL = "YES_BANK_RETURN_URL";
	public static final String YES_BANK_TRANSACTION_URL="YES_BANK_TRANSACTION_URL";
	public static final String YES_BANK_NB_KEY = "YES_BANK_NB_KEY";
	public static final String YES_BANK_PID = "YES_BANK_PID";
	public static final String YES_BANK_VERIFY_RETURN_URL = "YES_BANK_VERIFY_RETURN_URL";
	public static final String YES_BANK_VERIFY_URL = "YES_BANK_VERIFY_URL";
	public static final String YES_BANK_VERIFY_FLAG = "YES_BANK_VERIFY_FLAG";
	
	public static String CalculatedChecksum(String checksum) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(checksum.getBytes("UTF-8"));
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				sb.append(Integer.toString((hash[i] & 0xFF) + 256, 16).substring(1));
			}
			digest = sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return digest;
	}

	public static String encrypt(String finaldata, String p_key) throws Exception{
		generatekey(p_key);
		Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(1, keyEncDec, IVParameterSpec);
		byte[] encryptedBytes = cipher.doFinal(finaldata.getBytes("UTF-8"));
        String encryptedValue = Base64.getEncoder().encodeToString(encryptedBytes);
        return encryptedValue;
	}

	public static String decrypt(String finaldata, String p_key) throws Exception {
		generatekey(p_key);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(2, keyEncDec, IVParameterSpec);
//		byte[] l_decordedvalue = Base64.decodeBase64(p_encrypted_text.getBytes());
//		byte[] decValue = cipher.doFinal(l_decordedvalue);
//		String l_decryptedvalue = new String(decValue);
//		return l_decryptedvalue;
		byte[] decodedValue = Base64.getDecoder().decode(finaldata);
		byte[] decryptedValue = cipher.doFinal(decodedValue);
		String decryptedText = new String(decryptedValue, "UTF-8");
		return decryptedText;
	}
	
	public static String apicallTest(String url) {
		try {
			OkHttpClient client = new OkHttpClient.Builder()
					.sslSocketFactory(createSSLSocketFactory(), new TrustAllCertificates()) // Bypass SSL
					.hostnameVerifier((hostname, session) -> true) // Ignore hostname verification
					.connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
					.writeTimeout(60, TimeUnit.SECONDS).retryOnConnectionFailure(true) // Retry on failure
					.build();

			Request request = new Request.Builder().url(url).get().addHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36") // Mimic
																																			// a
																																			// browser
					.addHeader("Accept", "*/*") // Accept all responses
					.build();

			Response response = client.newCall(request).execute();

			if (!response.isSuccessful()) {
				logger.info("Yes NB Verfiy Api Call Request failed: " + response.code() + " - " + response.message());
			}

			String responseBody = response.body().string();
			logger.info("Yes NB Verfiy Api Call Response: " + responseBody);
			return responseBody;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Create SSL Socket Factory
	private static SSLSocketFactory createSSLSocketFactory() throws Exception {
		SSLContext sslContext = SSLContext.getInstance("TLSv1.2"); // Use TLS 1.2 (or 1.3)
		sslContext.init(null, new TrustManager[] { new TrustAllCertificates() }, new java.security.SecureRandom());
		return sslContext.getSocketFactory();
	}
	
	
	// Trust manager to ignore certificate validation
	public static class TrustAllCertificates implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] cert, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] cert, String authType) {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	}
	
	private static void generatekey(String p_key) {
		MessageDigest sha = null;
		try {
			byte[] key = p_key.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-256");
			key = sha.digest(key);

			byte[] temp_key = new byte[16];
			System.arraycopy(key, 0, temp_key, 0, 16);
			key = temp_key;

			keyEncDec = new SecretKeySpec(key, "AES");
			IVParameterSpec = new IvParameterSpec(key);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}
