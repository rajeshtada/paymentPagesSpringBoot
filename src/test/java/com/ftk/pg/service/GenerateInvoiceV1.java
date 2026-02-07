package com.ftk.pg.service;


import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.ftk.pg.encryption.PgEncryption;
import com.ftk.pg.util.GetKeyParam;
import com.ftk.pg.util.KeyParamVo;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GenerateInvoiceV1 {

	public GenerateInvoiceV1() {
		urlValue = "https://pay1.getepay.in:8443/getepayPortal/pg/generateInvoice";
		key = "lGed7VR7NcE1oSeCeUN0ng==";
		iv = "9TXpxfuXD6/4RJ0cuOCX0g==";
		mid = "705016";
		terminalId = "getepay.merchant129118@icici";
//		this("uat", "887729");
//		this("uat", "1");
//		this("uat", "44");
//		this("live", "76606");
//		this("live", "887729");
		
	}

	private String environment;
	private String urlValue;
	String key ;
	String iv ;
	String mid;
	String terminalId ;
	
	public GenerateInvoiceV1(String environment, String mId) {
		super();
//		KeyParamVo fetchData = GetKeyParam.fetchData(environment, mId);
//		this.environment = fetchData.getEnvironment();
//		urlValue = fetchData.getUrlValue();
//		key  = fetchData.getKey();
//		iv = fetchData.getIv();
//		mid = fetchData.getMid();
//		terminalId = fetchData.getTerminalId();
	}
	

	public static void main(String[] args) {
		GenerateInvoiceV1 generateInvoice = new GenerateInvoiceV1();
//		GenerateInvoiceV1 generateInvoice = new GenerateInvoiceV1("uat", "44");
//		GenerateInvoiceV1 generateInvoice = new GenerateInvoiceV1("live", "887729");
		generateInvoice.invoice();
	}

	String invoice() {
		try {
			System.out.println("url => " + urlValue);
			String request = getRequest();
			String res = postApi(request, urlValue);
			return getResponse(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getResponse(String reponseString) throws JSONException {
		Gson gson = new Gson();
		PgEncryption encryption = new PgEncryption(iv, key);

		JSONObject jsonObject = new JSONObject(reponseString);

		// Extract values by their keys
		String response = jsonObject.getString("response");

		String decRes = encryption.decrypt(response);
		System.out.println("Generate Invoice Decrypt response => " + decRes);
		jsonObject = new JSONObject(decRes);

		String paymentUrl = jsonObject.getString("paymentUrl");
		System.out.println("paymentUrl : "+paymentUrl);

		String token = jsonObject.getString("token");
		System.out.println("token : "+token);

		return token;

	}

	private String postApi(String finalString, String url) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, finalString);
		Request request1 = new Request.Builder().url(url).method("POST", body)
				.addHeader("Content-Type", "application/json").build();
		Response response = client.newCall(request1).execute();
		String reponseString = response.body().string();
		System.out.println("response => " + reponseString);
		return reponseString;
	}

	private String getRequest() {
		PgEncryption encryption = new PgEncryption(iv, key);

//		String ru = "https://pay1.getepay.in:8443/getepayPortal/login";
		// ru = "https://pay1.getepay.in:8443/getepayPortal/getepayResponse";
		// ru = "https://pay1.getepay.in:8443/getepayPortal/pg/updateStatus";
//		ru = "https://pay1.getepay.in:8443/getepayPortal/callbackPG";
		// ru = "";
//		String ru = "https://pay1.getepay.in:8443/getepayPortal/pg/pgPaymentResponse";
		String ru = "https://portal.getepay.in:8443/getepayPortal/pg/pgPaymentResponse";
		String req = "";

		String request = encryption.decrypt(req);
		System.out.println(request);

		String udf6 = "";
		udf6 = "test";
		String txnType = "single";
		String paymentMode = "ALL";
		String am = "1";
		// paymentMode = "DYNAMICQR";
//		String callbackUrl = "https://pay1.getepay.in:8443/getepayPortal/callbackPG";
//		callbackUrl = "https://pay1.getepay.in:8443/getePaymentPages/callBack/allPayment";
		String callbackUrl = "https://pay1.getepay.in:8443/getepayPortal/callbackPG";
		// txnType = "multi";
		// udf6 =
		// "PHByb2R1Y3RzPgogICAgPHByb2R1Y3Q+CiAgICAgICAgPGNvZGU+Q29kZTAwMTwvY29kZT4KICAgICAgICA8bmFtZT43MDwvbmFtZT4KICAgICAgICA8YW1vdW50PjM3OTA8L2Ftb3VudD4KICAgIDwvcHJvZHVjdD4KICAgIDxwcm9kdWN0PgogICAgICAgIDxjb2RlPkNvZGUwMDI8L2NvZGU+CiAgICAgICAgPG5hbWU+NzE8L25hbWU+CiAgICAgICAgPGFtb3VudD4yNDI5MDwvYW1vdW50PgogICAgPC9wcm9kdWN0Pgo8L3Byb2R1Y3RzPg==";
		// am = "28080";
		// am = "1";
		req = "{\"mid\":\"" + mid + "\",\"amount\":\"" + am
				+ "\",\"merchantTransactionId\":\"4ee0927a7c3e36acd203\",\"transactionDate\":\"Mon Aug 07 11:02:35 IST 2025\",\"terminalId\":\""
				+ terminalId
				+ "\",\"udf1\":\"6350043232\",\"udf2\":\"siddharth@gmail.com\",\"udf3\":\"siddharth\",\"udf4\":\"14008|254819|2000\",\"udf5\":\"GETEPAYESHF00000227|2023-03-09\",\"udf6\":\""
				+ udf6 + "\",\"udf7\":\"\",\"udf8\":\"\",\"udf9\":\"\",\"udf10\":\"\",\"ru\":\"" + ru
				+ "\\t\",\"callbackUrl\":\"" + callbackUrl + "\",\"currency\":\"INR\",\"paymentMode\":\"" + paymentMode
				+ "\",\"bankId\":\"\",\"txnType\":\"" + txnType
				+"\",\"expiryDateTime\":\"2025-08-06 18:26:30"
				+ "\",\"productType\":\"IPG\",\"txnNote\":\"Colony world Txn\",\"vpa\":\"" + terminalId + "\"}";

//		pgRequest.setExpiryDateTime("2025-12-31 23:59:59");
		
		System.out.println("Generate Invoice req => " + req);
		req = encryption.encrypt(req);
//		System.out.println(" Generate Invoice encrypted req => " + req);

		String finalString = "{\r\n    \"mid\": \"" + mid + "\",\r\n    \"req\": \"" + req
				+ "\",\r\n    \"terminalId\": \"" + terminalId + "\"\r\n}";
		System.out.println(mid + " :: " + terminalId);
		System.out.println("finalString => " + finalString);

		return finalString;

	}

}
