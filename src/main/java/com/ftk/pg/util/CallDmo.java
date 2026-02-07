package com.ftk.pg.util;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CallDmo {
//	static Logger logger = LogManager.getLogger(Call.class);

	String baseUrl;
	String baseUrlTxn;
	String authoriztion;

	public CallDmo(String baseUrl, String baseUrlTxn, String authoriztion) {
		this.baseUrl = baseUrl;
		this.baseUrlTxn = baseUrlTxn;
		this.authoriztion = authoriztion;
	}

	public CallDmo() {

	}

	public String requerypostapi(String requestString, String url, String apiKey) {
		try {
			System.out.println("url => " + url);

			String response = postApiDMO(url, requestString, apiKey);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	public String lyraEditMerchant(String requestString, String url) {
//		try {
//			System.out.println("url => " + url);
//
//			String response = postApi(url, requestString);
//			return response;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	private String postApiDMO(String apiUrl, String requestString, String apiKey) {
		try {
//			OkHttpClient client = new OkHttpClient().newBuilder().build();
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES).readTimeout(2,
					TimeUnit.MINUTES);
			OkHttpClient client = builder.build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, requestString);
			Request request = new Request.Builder().url(apiUrl).method("POST", body).addHeader("apikey", apiKey)
					.addHeader("Content-Type", "application/json").
					addHeader("accept","*/*").
					addHeader("accept-encoding","*").
					addHeader("accept-language","en-US,en;q=0.8,hi;q=0.6").
					addHeader("cache-control","no-cache").build();
			Response response = client.newCall(request).execute();

			String responseString = response.body().string();
//			System.out.println("response===> : " + responseString);

			return responseString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

//	private String postApi(String url, String requestString) {
//		try {
//			OkHttpClient client = new OkHttpClient().newBuilder().build();
//			MediaType mediaType = MediaType.parse("application/json");
//			RequestBody body = RequestBody.create(mediaType, requestString);
//			Request request = new Request.Builder().url(url).method("POST", body)
//					.addHeader("Authorization", "Basic " + authoriztion).addHeader("Content-Type", "application/json")
//					.build();
//			Response response = client.newCall(request).execute();
//			System.out.println("response : " + response);
//			String responseString = response.body().string();
//			System.out.println("response===> : " + responseString);
//
//			return responseString;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//
//	private String getApi(String url) {
//		try {
//			OkHttpClient client = new OkHttpClient().newBuilder().build();
////			MediaType mediaType = MediaType.parse("application/json");
//			RequestBody body = null;
//			Request request = new Request.Builder().url(url).method("GET", body)
//					.addHeader("Authorization", "Basic " + authoriztion).addHeader("Content-Type", "application/json")
//					.build();
//			Response response = client.newCall(request).execute();
//			System.out.println("response : " + response);
//			String responseString = response.body().string();
//			System.out.println("response===> : " + responseString);
//
//			return responseString;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getBaseUrlTxn() {
		return baseUrlTxn;
	}

	public void setBaseUrlTxn(String baseUrlTxn) {
		this.baseUrlTxn = baseUrlTxn;
	}

	public String getAuthoriztion() {
		return authoriztion;
	}

	public void setAuthoriztion(String authoriztion) {
		this.authoriztion = authoriztion;
	}

}
