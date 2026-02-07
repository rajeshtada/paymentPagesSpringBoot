package com.ftk.pg.paymentPageApis;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftk.pg.dto.ChargesDto;
import com.ftk.pg.util.CommissionModel;
import com.ftk.pg.util.EncryptionUtil;
import com.ftk.pg.util.TestUtil;
import com.google.gson.Gson;

public class GenerateChargesTest {

//	static String baseUrl = "https://eovtg2gb67.execute-api.ap-south-1.amazonaws.com/UAT";
//	static String baseUrl = "https://sandboxapi.getepay.in/pg";
	static String baseUrl = "http://localhost:8080/pg";
	
	public static void main(String[] args) throws Exception {
		
		
		String token = "9984f9f6-bb3a-43aa-b5b1-ae19437676fd";
		String mid = "1";
		String cardNo = "";
		String paymentMode = "UPI";
		String amount = "100";
		String bankId = "";
		
		JSONObject requestPlainJson = new JSONObject();
		requestPlainJson.put("mid", mid);
		requestPlainJson.put("cardNo", cardNo);
		requestPlainJson.put("paymentMode", paymentMode);
		requestPlainJson.put("amount", amount);
		requestPlainJson.put("bankId", bankId);
		
//		Gson gson = new Gson();
//		ChargesDto chargeDto = new ChargesDto();
//		chargeDto.setMid(Long.valueOf(mid));
//		chargeDto.setPaymentMode(paymentMode);
//		chargeDto.setAmount(amount);
//		String requestPlainJson = gson.toJson(chargeDto);
		
		System.out.println("requestPlainJson : "+ requestPlainJson);
		
		String encRequestData = TestUtil.enc(requestPlainJson.toString(), token);
		
		JSONObject generateChargeJsonReq = new JSONObject();
		generateChargeJsonReq.put("data", encRequestData);
		System.out.println("generateChargeJsonReq : "+generateChargeJsonReq.toString());
		
		String getChargesUrl = baseUrl + "/api/charges";
		System.out.println("getChargesUrl url : "+getChargesUrl);
		String postApi = TestUtil.postApi(getChargesUrl, generateChargeJsonReq.toString(), token);
		
		System.out.println("getCharges Api Res : "+postApi);
		
		JSONObject postValidateCollectResponseJsonObj = new JSONObject(postApi);
		String redirectResDataString = (String) postValidateCollectResponseJsonObj.get("data");
		
		String dec = TestUtil.dec(redirectResDataString, token);
		
//		CommissionModel resVo = EncryptionUtil.decryptdata(String.valueOf(redirectResDataString), token,
//				CommissionModel.class);
		ObjectMapper objectMapper = new ObjectMapper();
		CommissionModel resVo = objectMapper.readValue(dec, CommissionModel.class);
		System.out.println(resVo);
		
	}
	
	
	
	
	// 
}




