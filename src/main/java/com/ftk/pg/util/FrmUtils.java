package com.ftk.pg.util;


import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FrmUtils {

	static Logger logger = LogManager.getLogger(FrmUtils.class);

	public static final String FRM_RISK_API_URL = "FRM_RISK_API_URL";
	public static final String FRM_RISK_API_URL_V2 = "FRM_RISK_API_URL_V2";

	public static String frmRiskApiCall(String url, String data) throws IOException {

		try {

			logger.info("Json Encrypted Data===========>" + data);
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, data);
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();

			logger.info("Json decrypted Response===========>" + response);
			return response.toString();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		return null;

	}

	// Method to create an OkHttpClient that bypasses SSL
	private static OkHttpClient getUnsafeOkHttpClient() {
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCertificates = new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new java.security.cert.X509Certificate[] {};
				}
			} };

			// Install the all-trusting trust manager
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());

			// Create an SSL socket factory with the all-trusting manager
			SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			return new OkHttpClient.Builder()
					.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCertificates[0])
					.hostnameVerifier((hostname, session) -> true)
					.connectTimeout(60, TimeUnit.SECONDS) // Increase connection timeout
					.writeTimeout(60, TimeUnit.SECONDS)   // Increase write timeout
					.readTimeout(120, TimeUnit.SECONDS)   // Increase read timeout
					.build();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String frmRiskApiCall2(String url, String data) throws IOException {
		try {
			OkHttpClient client = getUnsafeOkHttpClient(); // Use the unsafe client

			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, data);

			Request request = new Request.Builder().url(url).post(body).addHeader("Content-Type", "application/json")
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)").addHeader("Accept", "*/*")
					.build();

			Response response = client.newCall(request).execute();

			// Print debug info
			logger.info("Status Code: " + response.code());
			String responseBody = response.body().string();
			logger.info("Response Body: " + responseBody);

			return responseBody;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		try {
			String url = "https://pj63eq5pok.execute-api.ap-south-1.amazonaws.com/UAT/api/v2/ruleValidation/validate/9ABCDEFG";
			String data = "{\"data\":\"64886719f6ad9b22a17f776e87c0c7e2945018244b45c6a9528b9f6d4c6f0ec753525d73e97f64a55e7364ab4e629dbdfbc88bd2147859b1cf746afa16417da98cea1fc7fdb2d723b2fd0d014cce97d3844de455bae0cc8092fac41f661e57f9924afeea821d8dfffd42120c2af35f68a755d7ae10b3ed46d8311e73e16ce5e9ee3e9c387a7f96f33da48b1e39b2d2ed55dfb5b3d2d4ff9885bb6f8b5315dd3b7a16f324e7d2dd0d25d9f79a92335bcd5b6756c18842d7824dee27daa363cf133410a441ca66e143aa5aad57b7ca527547493d1b78965ed9fbb8d9f135c717ee1dcf1d0eb5cfc546c2dbcf871cb11e\"}";

			// Call API
			frmRiskApiCall2(url, data);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
