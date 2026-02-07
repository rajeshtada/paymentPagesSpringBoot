package com.ftk.pg.paymentPageApis;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GenerateInvoice {

//	String urlValue = "https://pay1.getepay.in:8443/getepayPortal/pg/generateInvoice";
//	String urlValue = "https://portal.getepay.in:8443/getepayPortal/pg/generateInvoice";
	
	// uat 1 mid
//	String key = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
//	String iv = "hlnuyA9b4YxDq6oJSZFl8g==";
//	String mid = "108";
//	String terminalId = "getepay.merchant61062@icici";

	// uat 887729 mid
//	String key = "lGed7VR7NcE1oSeCeUN0ng==";
//	String iv = "9TXpxfuXD6/4RJ0cuOCX0g==";
//	String mid = "705016";
//	String terminalId = "getepay.merchant129118@icici";
	
	
	// live 76606 mid
//	String key = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
//	String iv = "hlnuyA9b4YxDq6oJSZFl8g==";
//	String mid = "66439";
//	String terminalId = "getepay.merchant59020@icici";
	
	// live 887729 mid
//	String key = "lGed7VR7NcE1oSeCeUN0ng==";
//	String iv = "9TXpxfuXD6/4RJ0cuOCX0g==";
//	String mid = "705016";
//	String terminalId = "getepay.merchant129118@icici";
	
	private String environment;
	private String urlValue;
	String key ;
	String iv ;
	String mid;
	String terminalId ;
	
	public GenerateInvoice() {
//		urlValue = "https://pay1.getepay.in:8443/getepayPortal/pg/generateInvoice";
//		key = "lGed7VR7NcE1oSeCeUN0ng==";
//		iv = "9TXpxfuXD6/4RJ0cuOCX0g==";
//		mid = "705016";
//		terminalId = "getepay.merchant129118@icici";
		this("uat", "887729");
//		this("uat", "1");
//		this("uat", "44");
//		this("live", "76606");
//		this("live", "887729");
		
//		urlValue = "https://pay1.getepay.in:8443/getepayPortal/pg/generateInvoice";
//		key = "vNGZcokl1NxopB5x4rQZUbQolVdk4sXepH76Zgw2lSE=";
//		iv = "zHt9Xb1zQ0ZCm0/EwOXSvw==";
		
		
//		mid = "705111";
//		terminalId = "merchant8437.augp@aubank";
	}

	public GenerateInvoice(String environment, String mId) {
		super();
		this.environment = environment;
		if ( environment.equalsIgnoreCase("live")) {
			urlValue = "https://portal.getepay.in:8443/getepayPortal/pg/generateInvoice";
			if (mId.equals("76606")) {
				key = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
				iv = "hlnuyA9b4YxDq6oJSZFl8g==";
				mid = "66439";
				terminalId = "getepay.merchant59020@icici";
			} else if (mId.equals("887729") ) { 
				key = "lGed7VR7NcE1oSeCeUN0ng==";
				iv = "9TXpxfuXD6/4RJ0cuOCX0g==";
				mid = "705016";
				terminalId = "getepay.merchant129118@icici";
			}
		} else if (environment.equalsIgnoreCase("uat")) {
			urlValue = "https://pay1.getepay.in:8443/getepayPortal/pg/generateInvoice";
			if (mId.equals("1") ) {
				key = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
				iv = "hlnuyA9b4YxDq6oJSZFl8g==";
				mid = "108";
				terminalId = "getepay.merchant61062@icici";
			} else if (mId.equals("887729")) {
//				key = "lGed7VR7NcE1oSeCeUN0ng==";
//				iv = "9TXpxfuXD6/4RJ0cuOCX0g==";
				key = "leGd/GFbwbCzvOqhHl5qN8MBXnYDWxp+HZ8qmBA3qnA=";
				iv = "r/4GxOZLzvx7i1z4VIPA5A==";
				mid = "705016";
				terminalId = "getepay.merchant129118@icici";
			} else if (mId.equals("44")) {
				key = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
				iv = "hlnuyA9b4YxDq6oJSZFl8g==";
				mid = "44";
				terminalId = "getepay.merchant202500@icici";
			}
		}
	}

	public static void main(String[] args) {
		GenerateInvoice generateInvoice = new GenerateInvoice();
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
		System.out.println("jsonObject : "+jsonObject);
		String paymentUrl = jsonObject.getString("paymentUrl");
		System.out.println("paymentUrl : "+paymentUrl);

		String token = jsonObject.getString("token");
		System.out.println("token : "+token);

		System.out.println("https://pay1.getepay.in:8443/getePaymentPages/doPayment?token="+token);
		String newUrl = "https://pay1.getepay.in:8443/getePaymentPages/doPayment?token=#token#/pg/payment?token=#token";
		newUrl = newUrl.replaceAll("#token", token);
		System.out.println(newUrl);
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
//		String ru = "https://portal.getepay.in:8443/getepayPortal/pg/pgPaymentResponse";
//		String ru = "https://pay1.getepay.in:8443/getepayPortal/pg/pgPaymentResponse";
		String ru = "https://pay1.getepay.in:8443/getepayPortal/pg/v2/pgPaymentResponse";
		String req = "";

		String request = encryption.decrypt(req);
		System.out.println(request);

		String udf6 = "";
		udf6 = "test";
		String txnType = "single";
		String paymentMode = "ALL";
		String am = "100";
		// paymentMode = "DYNAMICQR";
//		String callbackUrl = "https://pay1.getepay.in:8443/getepayPortal/callbackPG";
//		callbackUrl = "https://pay1.getepay.in:8443/getePaymentPages/callBack/allPayment";
		String callbackUrl = "https://pay1.getepay.in:8443/getepayPortal/callbackPG";
//		 txnType = "multi";
//		 udf6 =
//		 "PHByb2R1Y3RzPgogIDxwcm9kdWN0PgogICAgPGNvZGU+QUNDMTwvY29kZT4KICAgIDxuYW1lPkFDQzE8L25hbWU+CiAgICA8YW1vdW50PjI8L2Ftb3VudD4KICA8L3Byb2R1Y3Q+CiAgICA8cHJvZHVjdD4KICAgIDxjb2RlPkFDQzI8L2NvZGU+CiAgICA8bmFtZT5BQ0MyPC9uYW1lPgogICAgPGFtb3VudD4zPC9hbW91bnQ+CiAgPC9wcm9kdWN0Pgo8L3Byb2R1Y3RzPg==";
		// am = "28080";
		// am = "1";
		req = "{\"mid\":\"" + mid + "\",\"amount\":\"" + am
				+ "\",\"merchantTransactionId\":\"4ee0927a7c3e36acd203\",\"transactionDate\":\"Tue Oct 07 11:02:35 IST 2025\",\"terminalId\":\""
				+ terminalId
				+ "\",\"udf1\":\"6350043232\",\"udf2\":\"siddharth@gmail.com\",\"udf3\":\"siddharth\",\"udf4\":\"14008|254819|2000\",\"udf5\":\"GETEPAYESHF00000227|2023-03-09\",\"udf6\":\""
				+ udf6 + "\",\"udf7\":\"\",\"udf8\":\"\",\"udf9\":\"\",\"udf10\":\"\",\"ru\":\"" + ru
				+ "\\t\",\"callbackUrl\":\"" + callbackUrl + "\",\"currency\":\"INR\",\"paymentMode\":\"" + paymentMode
				+ "\",\"bankId\":\"\",\"txnType\":\"" + txnType
				+ "\",\"productType\":\"IPG\",\"txnNote\":\"Colony world Txn\",\"vpa\":\"" + terminalId + "\"}";

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
