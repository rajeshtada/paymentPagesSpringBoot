package com.ftk.pg.util;

import java.io.IOException;

import com.ftk.pg.encryption.GcmPgEncryption;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestUtil {

	
	public static String postApi(String url, String req, String token) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, req);
		Request request = new Request.Builder().url(url).method("POST", body)
				.addHeader("Authorization", "Bearer " + token).addHeader("Content-Type", "application/json").build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public static String enc(String data, String ivKey) throws Exception {
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(ivKey);
		String enc = gcmPgEncryption.encryptWithMKeys(data);
		System.out.println("enc => " + enc);
		return enc;
	}

	public static String dec(String enc, String ivKey) throws Exception {
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(ivKey);
		String dec = gcmPgEncryption.decryptWithMKeys(enc);
		System.out.println("dec => " + dec);
		return dec;
	}
	
}
