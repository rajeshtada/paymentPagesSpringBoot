package com.ftk.pg.util;

import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.requestvo.NorthAcrossPaymentRequest;
import com.ftk.pg.requestvo.NorthAcrossPaymentStatusRequest;
import com.ftk.pg.requestvo.NorthAcrossRefundApiRequest;
import com.ftk.pg.requestvo.NorthAcross_RefundStatus_Request;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NorthAcrossUtil {
	public static Logger logger = LogManager.getLogger(NorthAcrossUtil.class);

	public static String encrypt(String strToEncrypt, String Request_Encyption_Key) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Request_Encyption_Key.getBytes("UTF-8"), "AES"));
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			logger.info("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static String decrypt(String strToDecrypt, String Response_Encyption_Key) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Response_Encyption_Key.getBytes("UTF-8"), "AES"));
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			logger.info("Error wSHIVALIK_SALT_KEYhile decrypting: " + e.toString());
		}
		return null;
	}

	public static String generateSHA512Signature(String data) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(data.getBytes("UTF-8"));
			byte byteData[] = md.digest();
			// convert the byte to hex format method 1
			StringBuffer hashCodeBuffer = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				hashCodeBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			return hashCodeBuffer.toString().toUpperCase();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static String generateHash(String req, String SaltKey) throws Exception {
		Gson gson = new Gson();
		NorthAcrossPaymentRequest request = gson.fromJson(req, NorthAcrossPaymentRequest.class);

		Map<String, Object> requestMap = gson.fromJson(req, Map.class);

		Set<String> keys = requestMap.keySet();

		Stream<String> sortedKeys = keys.stream().sorted(new KotakComparator());
		StringBuilder result = new StringBuilder();
		result.append(SaltKey);

		for (Iterator iterator = sortedKeys.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();

			Object obj = requestMap.get(string);
			String value = new String(String.valueOf(obj).getBytes(), "UTF-8");

			if (value != null && value.trim().length() > 0 && !string.equalsIgnoreCase("SecureHash")) {
				result.append("|").append(value);

			}
		}
		logger.info("Hash data========>" + result);
		String checksumString = result.toString();
		String sha256generation = generateSHA512Signature(checksumString);
		return sha256generation;
	}

	public static Boolean ReturnHashCalculate(String req, String SaltKey, String responseHash) throws Exception {
		Gson gson = new Gson();

		Map<String, Object> requestMap = gson.fromJson(req, Map.class);

		Set<String> keys = requestMap.keySet();

		Stream<String> sortedKeys = keys.stream().sorted(new KotakComparator());
		StringBuilder result = new StringBuilder();
		result.append(SaltKey);

		for (Iterator iterator = sortedKeys.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();

			Object obj = requestMap.get(string);
			String value;
			if (string.equals("hash")) {
				value = "";
			} else {
				value = new String(String.valueOf(obj).getBytes(), "UTF-8");
			}

			if (value != null && value.trim().length() > 0 && !string.equalsIgnoreCase("SecureHash")) {
				result.append("|").append(value);

			}
		}
		logger.info("Hash data========>" + result);
		String checksumString = result.toString();
		String sha256generation = generateSHA512Signature(checksumString);
		logger.info("sha256generation====>" + sha256generation);
		if (responseHash.equals(sha256generation)) {
			return true;
		} else {
			return false;
		}

	}

	public static String postapi(String data, String url) {
		try {
//			OkHttpClient client = new OkHttpClient().newBuilder().build();
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = RequestBody.create(mediaType, data);
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			// System.out.info("mindgate utkarsh callback response :: " + response);

			String res = response.body().string();
			// logger.info("response body :: " + res);
			return res;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			// logger.info("Error in hit Utkarsh Bank Api :: " + e.getMessage());
		}
		return null;
	}

	public static String PostApiCall(NorthAcrossPaymentStatusRequest paymentStatus, String paymentStatusApi) {

		try {
			SSLHelper.disableCertificateValidation();

			// Create the custom SSLContext
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] { new TrustAllCertificates() }, new java.security.SecureRandom());

			// Set the custom SSLContext as the default for all HTTPS connections
			SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			OkHttpClient client = new OkHttpClient.Builder()
					.sslSocketFactory(sslSocketFactory, new TrustAllCertificates())
					.hostnameVerifier(new MGHostnameVerifier()).build();

			// Rest of your code...
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("api_key", paymentStatus.getApi_key())
					.addFormDataPart("order_id", paymentStatus.getOrder_id())
					.addFormDataPart("bank_code", paymentStatus.getBank_code())
					.addFormDataPart("hash", paymentStatus.getHash()).build();
			Request request = new Request.Builder().url(paymentStatusApi).method("POST", body).build();
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return "";
	}

	public static String PostApiCallShiv(NorthAcrossPaymentStatusRequest paymentStatus, String paymentStatusApi) {
		Gson gson = new Gson();
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
        	MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("api_key", paymentStatus.getApi_key())
					.addFormDataPart("order_id", paymentStatus.getOrder_id())
					.addFormDataPart("bank_code", paymentStatus.getBank_code())
					.addFormDataPart("hash", paymentStatus.getHash()).build();
			Request request = new Request.Builder().url(paymentStatusApi).method("POST", body).build();
			Response response = client.newCall(request).execute();
			String res = response.body().string();

			return res;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);

		}
		return null;
	}

	public static String RefundApiCall(NorthAcrossRefundApiRequest refund, String paymentStatusApi) {

		try {
			logger.info("Enter in Post Api call=====>");

			OkHttpClient client = new OkHttpClient().newBuilder().hostnameVerifier(new MGHostnameVerifier()).build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("api_key", refund.getApi_key())
					.addFormDataPart("transaction_id", refund.getTransaction_id())
					.addFormDataPart("amount", refund.getAmount())
					.addFormDataPart("description", refund.getDescription()).addFormDataPart("hash", refund.getHash())
					.build();
			Request request = new Request.Builder().url(paymentStatusApi).method("POST", body).build();
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (Exception e) {

			new GlobalExceptionHandler().customException(e);
		}
		return "";
	}

	public static String RefundStatusApi(NorthAcross_RefundStatus_Request statusrefund, String url) {

		try {
			logger.info("Enter in Post Api call=====>");

			OkHttpClient client = new OkHttpClient().newBuilder().hostnameVerifier(new MGHostnameVerifier()).build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("api_key", statusrefund.getApi_key())
					.addFormDataPart("transaction_id", statusrefund.getTransaction_id())
					.addFormDataPart("hash", statusrefund.getHash()).build();
			Request request = new Request.Builder().url(url).method("POST", body).build();
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (Exception e) {

			new GlobalExceptionHandler().customException(e);
		}
		return "";
	}

	public static String northAcrossPostApi(String url, String requestString) {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = RequestBody.create(mediaType, requestString);
			Request request = new Request.Builder().url(url).method("POST", body).build();
			Response response = client.newCall(request).execute();
			logger.info("response : " + response);
			String responseString = response.body().string();
			logger.info("response ==> " + responseString);
			if (response.code() == 200) {
				return responseString;
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static class TrustAllCertificates implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] cert, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] cert, String authType) {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	}

	public static class SSLHelper {
		public static void disableCertificateValidation() throws Exception {
			TrustManager[] trustAllCertificates = new TrustManager[] { new TrustAllCertificates() };
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

			// Optionally, you can disable the hostname verification as well (not
			// recommended for production use)
			HostnameVerifier allHostsValid = (hostname, session) -> true;
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		}
	}

}
