package com.ftk.pg.service;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.util.MGHostnameVerifier;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import com.nttdatapay.aipayclient.utils.Utility;

public class AipayService {
	static Logger logger = LogManager.getLogger(AipayService.class);

	private static final String AIPAY_URL = "https://caller.atomtech.in/ots/aipay/auth?";
	public static final String PAYNETZ_RETURN_URL = "http://localhost:8080/getePaymentPages/paynetznewResponse/#ru";

//	  public static String getAtomTokenId(String merchantId, String serverResp)
//	  {
//	    String result = "";
//	    try
//	    {
//	    	System.out.println("in get atomtokenID method"+merchantId+serverResp);
////	      result = Utility.httpPostCaller("merchId=" + merchantId + "&encData=" + serverResp, "https://caller.atomtech.in/ots/aipay/auth");
//	      WebClient.Builder webClientBuilder=WebClient.builder();
//	      webClientBuilder=getCertificateSkippedRestObject();
//	      result=webClientBuilder.build().post()
//	    		  .uri(AIPAY_URL+"merchId=" + merchantId + "&encData=" + serverResp)
//	    		  .contentType(MediaType.APPLICATION_JSON)
//	    		  .retrieve()
//	    		  .bodyToMono(String.class)
//	    		  .block();
//	    	System.out.println("Server result----------: " + result);
//	    } catch (Exception e) {
//	      e.getStackTrace();
//	    }
//
//	    return result;
//	  }

//	  public static String getAtomTokenId(String merchantId, String serverResp) {
//		
//		  try {
//			  String url=AIPAY_URL+"merchId=" + merchantId + "&encData=" + serverResp;
//				OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS)
//						.writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS)
//						.hostnameVerifier(new MGHostnameVerifier()).build();
//				MediaType mediaType = MediaType.parse("text/plain");
//				RequestBody body = RequestBody.create(mediaType, "");
//				Request request = new Request.Builder().url(url).method("POST", body)
//						.addHeader("Content-Type", "application/json").build();
//				Response response = client.newCall(request).execute();
//
//				String responseString = response.body().string();
//				return responseString;
//			} catch (Exception e) {
//				new GlobalExceptionHandler().customException(e);
//			}
//
//			return null;
//	  }
	public static String getAtomTokenId(String merchantId, String serverResp) {

		try {
			String url = AIPAY_URL + "merchId=" + merchantId + "&encData=" + serverResp;
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
			RequestBody body = RequestBody.create(mediaType, "");
			Request request = new Request.Builder().url(url).method("POST", body).build();
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return "";
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

//	  public static Builder getCertificateSkippedRestObject() throws SSLException {
//
//			SslContext sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)
//					.build();
//			HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
//
//			return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient));
//		}
}
