package com.ftk.pg.paymentPageApis;

import java.io.IOException;

import org.json.JSONObject;

import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.util.TestUtil;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ValidateTransactionTest {

//	static String baseUrl = "https://eovtg2gb67.execute-api.ap-south-1.amazonaws.com/UAT";
	static String baseUrl = "https://sandboxapi.getepay.in/pg";
//	static String baseUrl = "http://localhost:8080/pg";

	
	public static void main(String[] args) throws Exception {
		
		GenerateInvoice generateInvoice = new GenerateInvoice();
		
		String token = generateInvoice.invoice();
//		token = "4564e2f6-63ad-4a43-8f8f-3c4058a9d32b";
		String initiateResponse = initiate(token);
		
		
		JSONObject jsonObj = new JSONObject(initiateResponse);
		String txnId = (String) jsonObj.get("transactionId");
		System.out.println("txnId : "+txnId);
		
//		String txnId = "515760146";
//		String token = "6d2453ef-37fc-49a3-b063-0b5693dc0ed4";
		
		JSONObject encJsonItem = new JSONObject();
		encJsonItem.put("transactionId", txnId);
		System.out.println("Plain ValidateCollectJson : "+encJsonItem.toString());
		
		String encApiData = TestUtil.enc(encJsonItem.toString(), token);
		
//		JSONObject encJson = new JSONObject();
//		encJson.put("token", token);
//		JSONObject encJsonItem = new JSONObject();
//		encJsonItem.put("transactionId", txnId);
//		encJson.put("data", encJsonItem);
//		String url = baseUrl + "/api/v1/enc";
//		String encApiResponse = postEncApi(url, encJson.toString());
//		System.out.println("encApiResponse : "+encApiResponse);
//		JSONObject encApiObject = new JSONObject(encApiResponse);
//		String encApiData = (String)encApiObject.get("data");
		
		JSONObject validateCollectJsonReq = new JSONObject();
		validateCollectJsonReq.put("data", encApiData);
		System.out.println("enc ValidateCollectJsonReq : "+validateCollectJsonReq.toString());
		
		String validateCollectUrl = baseUrl + "/api/validateCollect";
		System.out.println("validateCollect url : "+validateCollectUrl);
		String postValidateCollectResponse = postValidateCollectReq(validateCollectUrl, validateCollectJsonReq.toString(), token);
		System.out.println("postValidateCollectResponse : "+postValidateCollectResponse);
		
		
		JSONObject postValidateCollectResponseJsonObj = new JSONObject(postValidateCollectResponse);
		String collectResData = (String) postValidateCollectResponseJsonObj.get("data");
		
//		JSONObject decJson = new JSONObject();
//		decJson.put("token", token);
//		decJson.put("data", collectResData);
//		String decUrl = baseUrl+"/api/v1/dec";
//		String postDecApi = postEncApi(decUrl, decJson.toString());
		
		String postDecApi = TestUtil.dec(collectResData, token);
		
		System.out.println("postDecApi : "+ postDecApi);
		
	}
	
	public static String postValidateCollectReq(String apiUrl, String requestBody, String token) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, requestBody);
				Request request = new Request.Builder()
				  .url(apiUrl)
				  .method("POST", body)
				  .addHeader("Content-Type", "application/json")
				  .addHeader("Authorization", "Bearer "+token)
				  .build();
				Response response = client.newCall(request).execute();
				return response.body().string();
	}
	
	public static String postEncApi(String url, String encJson) throws IOException {
//		OkHttpClient client = new OkHttpClient().newBuilder().build();
//		MediaType mediaType = MediaType.parse("application/json");
//		RequestBody body = RequestBody.create(mediaType, encJson);
//		Request request = new Request.Builder().url(url).method("POST", body)
//				.addHeader("Content-Type", "application/json").build();
//		Response response = client.newCall(request).execute();
//		return response.body().string();
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, encJson);
				Request request = new Request.Builder()
				  .url(url)
				  .method("POST", body)
				  .addHeader("Content-Type", "application/json")
				  .build();
				Response response = client.newCall(request).execute();
//				System.out.println(response);
				String string = response.body().string();
//				System.out.println(string);
				return string;
				
	}
	
	public static String initiate(String token) throws Exception {
		String ivKey = token;

		String deReq = "{\n" + "        \"token\": \"" + token + "\"\n" + "    }";

		System.out.println("deReq==>" + deReq);
		String enc = TestUtil.enc(deReq, ivKey);

		String url = baseUrl + "/api/payment";
		System.out.println("payment url==>" + url);
		String req = "{\n      \"data\": \"" + enc + "\"\n}";
		System.out.println("final payment Request==>" + req);
		String res = TestUtil.postApi(url, req, token);

		System.out.println(res);

		Gson gson = new Gson();
		ResponseWrapper decRes = gson.fromJson(res, ResponseWrapper.class);
		System.out.println("resData : " + decRes.getData());

		if (decRes != null && decRes.getData() != null) {
			String dec = TestUtil.dec(String.valueOf(decRes.getData()), ivKey);
			return dec;
		}
		return null;
	}
}
