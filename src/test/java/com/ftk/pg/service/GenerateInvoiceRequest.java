package com.ftk.pg.service;

import java.time.LocalDateTime;

import org.json.JSONObject;

import com.ftk.pg.encryption.GcmPgEncryption;
import com.ftk.pg.util.GetKeyParam;
import com.ftk.pg.util.KeyParamVo;
import com.ftk.pg.vo.generateInvoice.GenerateInvoiceResponseWrapper;
import com.ftk.pg.vo.generateInvoice.PgRequest;
import com.ftk.pg.vo.generateInvoice.PgRequestWrapper;
import com.ftk.pg.vo.generateInvoice.ResponseData;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GenerateInvoiceRequest {

//	static String url = "https://pay1.getepay.in/pg/pg/generateInvoiceV3";
//	static String ru = "https://pay1.getepay.in/pgru/pgru/paymentResponse";
//	static String key = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
//	static String iv = "hlnuyA9b4YxDq6oJSZFl8g==";
//	static String mid = "44";
//	static String terminalId = "getepay.merchant202500@icici";
	
//	private static String url = "https://pay1.getepay.in/pg/pg/generateInvoiceV3";
	private static String url = "https://pay1.getepay.in:443/getepayPortal/pg/v2/generateInvoice";
//	private static String url = "http://localhost:8081/pg/generateInvoiceV3";
	private static String ru  = "https://pay1.getepay.in/pgru/pgru/paymentResponse";
	private static String key = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
	private static String iv = "hlnuyA9b4YxDq6oJSZFl8g==";
	private static String mid = "44";
	private static String terminalId = "getepay.merchant202500@icici";

	public GenerateInvoiceRequest(String environment, String mId) {

		KeyParamVo fetchData = GetKeyParam.fetchData(environment, mId);
//		url = fetchData.getUrlValue();
		key = fetchData.getKey();
		iv = fetchData.getIv();
		mid = fetchData.getMid();
		terminalId = fetchData.getTerminalId();
		ru = fetchData.getRu();
	}

	public GenerateInvoiceRequest() {
	}
	

	public static void main(String[] args) throws Exception {

//		new GenerateInvoiceRequest();
		new GenerateInvoiceRequest("uat", "44");
//		new GenerateInvoiceRequest("live", "887729");

		
		Gson gson = new Gson();
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(iv, key);

		PgRequest pgRequest = new PgRequest();
		pgRequest.setMid(mid);
		pgRequest.setTerminalId(terminalId);
		pgRequest.setRu(ru);
		pgRequest.setAmount("2.00");
		pgRequest.setCurrency("INR");
		pgRequest.setMerchantTransactionId("MTX123456789");
		pgRequest.setPaymentMode("ALL");
//		pgRequest.setTxnType("single");
		pgRequest.setUdf6("Extra");
//		pgRequest.setTxnType("multi");
//		pgRequest.setUdf6("PHByb2R1Y3RzPgogIDxwcm9kdWN0PgogICAgPGNvZGU+QWNjMTwvY29kZT4KICAgIDxuYW1lPnRlc3Q8L25hbWU+CiAgICA8YW1vdW50PjI1MDA8L2Ftb3VudD4KICA8L3Byb2R1Y3Q+CjwvcHJvZHVjdHM+");
//		pgRequest.setUdf6("PHByb2R1Y3RzPgogIDxwcm9kdWN0PgogICAgPGNvZGU+QWNjMTwvY29kZT4KICAgIDxuYW1lPkFjYzE8L25hbWU+CiAgICA8YW1vdW50PjI8L2Ftb3VudD4KICA8L3Byb2R1Y3Q+CiAgICA8cHJvZHVjdD4KICAgIDxjb2RlPkFjYzI8L2NvZGU+CiAgICA8bmFtZT5BY2MyPC9uYW1lPgogICAgPGFtb3VudD4zPC9hbW91bnQ+CiAgPC9wcm9kdWN0Pgo8L3Byb2R1Y3RzPg==");
		pgRequest.setUdf1("9999999999");
		pgRequest.setUdf2("email@example.com");
		pgRequest.setUdf3("John Doe");
		pgRequest.setExpiryDateTime("2025-12-31 23:59:59");
		pgRequest.setNoQr("1");
		pgRequest.setCallbackUrl("https://webhook.site/7c134537-2730-462a-baee-3ecacd512faf");
		System.out.println(gson.toJson(pgRequest));
		// Step 2: Encrypt the PgRequest
		String encryptedReq = gcmPgEncryption.encryptWithMKeys(gson.toJson(pgRequest));

		PgRequestWrapper requestWrapper = new PgRequestWrapper();
		requestWrapper.setMid(mid);
		requestWrapper.setTerminalId(terminalId);
		requestWrapper.setReq(encryptedReq);

		String json = gson.toJson(requestWrapper);
		System.out.println("PgRequestWrapper : " + json);
		System.out.println("url : " + url);
		System.out.println("api start : " + LocalDateTime.now());
		try {
			
			String postApi = postApi(json, url);
			GenerateInvoiceResponseWrapper response = gson.fromJson(postApi, GenerateInvoiceResponseWrapper.class);
			System.out.println("==== Invoice Generation Response ====");
			System.out.println("Status  : " + response.getStatus());
			System.out.println("Message : " + response.getMessage());
			System.out.println("Encrypted Response : " + response.getResponse());

			if (response.getStatus().equalsIgnoreCase("success")) {
				GcmPgEncryption encryptor = new GcmPgEncryption(iv, key);

				String decrypted = encryptor.decryptWithMKeys(response.getResponse());
				System.out.println("==== Decrypted Invoice Response ====");
				System.out.println(decrypted);

				ResponseData data = gson.fromJson(decrypted, ResponseData.class);
				System.out.println(data);
				
				System.out.println("Generate Invoice Decrypt response => " + decrypted);
				JSONObject jsonObject = new JSONObject(decrypted);

				String paymentUrl = jsonObject.getString("paymentUrl");
				System.out.println("paymentUrl : "+paymentUrl);

				String token = jsonObject.getString("token");
				System.out.println("token : "+token);
				
			} else {
				System.out.println("Invoice generation failed");
			}
		} catch (Exception e) {
			System.err.println("Failed to decrypt or parse invoice response: " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("code end : " + LocalDateTime.now());
	}

	public static String postApi(String encJson, String url) throws Exception {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, encJson);
		Request request = new Request.Builder().url(url).method("POST", body)
				.addHeader("Content-Type", "application/json").build();
		Response response = client.newCall(request).execute();
		String string = response.body().string();
		return string;

	}
}
