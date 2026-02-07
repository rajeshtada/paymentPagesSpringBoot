package com.ftk.pg.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
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

public class IDBIVANUtils {
	static Logger logger = LogManager.getLogger(IDBIVANUtils.class);

	public static final String IDBI_ACC_TITLE = "IDBI_ACC_TITLE";
	public static final String IDBI_PARENT_ACC_NUMBER = "IDBI_PARENT_ACC_NUMBER";
	public static final String IDBI_CUSTOMER_ID = "IDBI_CUSTOMER_ID";
	public static final String IDBI_REMITTER_NAME = "IDBI_REMITTER_NAME";
	public static final String IDBI_MODE = "IDBI_MODE";
	public static final String IDBI_CHALLAN_URL = "IDBI_CHALLAN_URL";
	public static final String IDBI_CHALLAN_RU="IDBI_CHALLAN_RU";
	public static final String IDBI_CHALLAN_ID="IDBI_CHALLAN_ID";
	public static final String IDBI_CHALLAN_PASSWORD="IDBI_CHALLAN_PASSWORD";
	public static final String IDBI_IFSC_CODE="IDBI_IFSC_CODE";
	public static final String IDBI_BANK_NAME="IDBI_BANK_NAME";
	public static final String IDBI_BRANCH_NAME="IDBI_BRANCH_NAME";


	public static String apiCall(String requestBody, String apiUrl) throws Exception {
		try {

			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setConnectTimeout(10000);

			connection.setReadTimeout(10000);
			connection.setRequestMethod("POST");
			byte[] bodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);
			Map<String, String> headers = new LinkedHashMap<>();

			headers.put("Content-Type", "text/xml");

			connection.setDoOutput(true);
			connection.getOutputStream().write(bodyBytes);
			connection.getOutputStream().flush();
			int responseCode = connection.getResponseCode();
			StringBuilder response = new StringBuilder();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				System.out.println(responseCode + " :: " + response);

				connection.disconnect();
				return response.toString();
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static String apiCall2(String requestBody, String apiUrl) {
		try {
			SSLHelper.disableCertificateValidation();
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] { new TrustAllCertificates() }, new java.security.SecureRandom());
			javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
					.sslSocketFactory(sslSocketFactory, new TrustAllCertificates())
					.hostnameVerifier(new MGHostnameVerifier());

			OkHttpClient client = clientBuilder.build();
			OkHttpClientHelper helper = new OkHttpClientHelper(client);

			// Make SOAP API post request
			String soapEndpoint = apiUrl;
			String soapRequest = requestBody; // Implement your SOAP request building logic

			MediaType mediaType = MediaType.parse("text/xml");
			RequestBody body = RequestBody.create(mediaType, soapRequest);

			Request request = new Request.Builder().url(soapEndpoint).post(body).addHeader("Content-Type", "text/xml")
					.build();
			System.out.println(requestBody);
			Response response = client.newCall(request).execute();
			System.out.println(response);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		return null;
	}
	 public static String base64Encode(String data) {
	        byte[] encodedBytes = Base64.getEncoder().encode(data.getBytes());
	        return new String(encodedBytes);
	    }

	    public static String base64Decode(String encodedData) {
	        byte[] decodedBytes = Base64.getDecoder().decode(encodedData.getBytes());
	        return new String(decodedBytes);
	    }
}

class TrustAllCertificates implements X509TrustManager {
	public void checkClientTrusted(X509Certificate[] cert, String authType) {
	}

	public void checkServerTrusted(X509Certificate[] cert, String authType) {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}
}

class SSLHelper {
	public static void disableCertificateValidation() throws Exception {
		TrustManager[] trustAllCertificates = new TrustManager[] { new TrustAllCertificates() };
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
		HostnameVerifier allHostsValid = (hostname, session) -> true;
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}
}

class OkHttpClientHelper {
	private final OkHttpClient client;

	public OkHttpClientHelper(OkHttpClient client) {
		this.client = client;
	}

	public Response execute(Request request) throws IOException {
		return client.newCall(request).execute();
	}
	
}
