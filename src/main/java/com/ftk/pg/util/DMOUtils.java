package com.ftk.pg.util;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DMOUtils {
	public static String postApiDMO(String apiUrl, String requestString, String apiKey) {
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

}
