package com.ftk.pg.util;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.ftk.pg.responsevo.VerifyOutput;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ICICIBankUtils {

	public static final String ICICI_NB_URL_KEY = "ICICI_NB_URL_KEY";
	public static final String ICICI_NB_RU = "ICICI_NB_RU";
	public static final String ICICI_NB_RU_V2 = "ICICI_NB_RU_V2";
	public static final String ICICI_NB_CG = "ICICI_NB_CG";
	public static final String ICICI_NB_ACC_NO = "ICICI_NB_ACC_NO";

	public static final String ICICI_NB_VERIFICATION_URL_KEY = "ICICI_NB_VERIFICATION_URL_KEY";
	public static final String ICICI_NB_REQUERY_URL = "ICICI_NB_REQUERY_URL";

	public static VerifyOutput iciciNbVerification(String apiUrl) {
		VerifyOutput verifyOutput = new VerifyOutput();
		OkHttpClient client = new OkHttpClient().newBuilder().build();
//			MediaType mediaType = MediaType.parse("text/plain");
//			RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder().url(apiUrl).get().build();
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				throw new RuntimeException("Unexpected code " + response);
			}
			JsonObject jsonObject;
			String responseBody = response.body().string();

			JAXBContext context = JAXBContext.newInstance(VerifyOutput.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			// Convert XML string to Java object
//            VerifyOutput verifyOutput = (VerifyOutput) unmarshaller.unmarshal(new StringReader(xmlData));
			verifyOutput = (VerifyOutput) unmarshaller.unmarshal(new StringReader(responseBody));
			// Print the object

			return verifyOutput;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
