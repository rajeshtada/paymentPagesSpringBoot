package com.ftk.pg.paymentPageApis;

import java.io.IOException;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.requestvo.RedirectRequestVo;
import com.ftk.pg.responsevo.RedirectResponseVo;
import com.ftk.pg.util.EncryptionUtil;
import com.ftk.pg.util.TestUtil;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RedirectRequestTest {

	
//	static String baseUrl = "https://service.getepay.in/pg";
	static String baseUrl = "https://sandboxapi.getepay.in/pg";
//	static String baseUrl = "http://localhost:8080/pg";
	
	public static void main(String[] args) throws Exception {
		
//		String token = "86420de4-c794-4339-b8f1-4b0b192c0337";
//		String txnId = "52123654";
		
		GenerateInvoice generateInvoice = new GenerateInvoice();
		String token = generateInvoice.invoice();
		String initiateResponse = initiate(token);
		JSONObject jsonObj = new JSONObject(initiateResponse);
		String txnId = (String) jsonObj.get("transactionId");
		
		RedirectRequestVo reqVo = new RedirectRequestVo();
		Gson gson = new Gson();
		
		reqVo.setTransactionId(txnId);
		reqVo.setStatus("success");
		reqVo.setOtp("1122");
		System.out.println("RedirectRequestVo jsom : "+ reqVo);
		
		
		String requestJsonString = gson.toJson(reqVo);
		System.out.println("encString  : "+ requestJsonString);
		String encRequestData = EncryptionUtil.encrypt(requestJsonString, token);
		
		
		
		
//		RequestWrapper reqWrap = new RequestWrapper();
//		reqWrap.setData(encRequestData);
//		String redirectProcessJsonReq = gson.toJson(reqWrap);
		
		JSONObject redirectProcessJsonReq = new JSONObject();
		redirectProcessJsonReq.put("data", encRequestData);
		System.out.println("redirectProcessJsonReq : "+redirectProcessJsonReq.toString());
		
		String validateCollectUrl = baseUrl + "/api/redirectProcess";
		System.out.println("validateCollect url : "+validateCollectUrl);
		String postValidateCollectResponse = TestUtil.postApi(validateCollectUrl, redirectProcessJsonReq.toString(), token);
		System.out.println("postValidateCollectReq : "+postValidateCollectResponse);
		
		
		
		JSONObject postValidateCollectResponseJsonObj = new JSONObject(postValidateCollectResponse);
		String redirectResDataString = (String) postValidateCollectResponseJsonObj.get("data");
		
		
		RedirectResponseVo redirectReqVo = EncryptionUtil.decryptdata(String.valueOf(redirectResDataString), token,
				RedirectResponseVo.class);
		System.out.println(redirectReqVo);
		
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
