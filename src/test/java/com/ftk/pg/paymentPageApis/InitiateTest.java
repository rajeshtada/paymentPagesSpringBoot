package com.ftk.pg.paymentPageApis;

import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.util.TestUtil;
import com.google.gson.Gson;

public class InitiateTest {

//	static String baseUrl = "https://eovtg2gb67.execute-api.ap-south-1.amazonaws.com/UAT";
//	static String baseUrl = "https://sandboxapi.getepay.in";
	static String baseUrl = "https://service.getepay.in";
//	static String baseUrl = "http://localhost:8080/pg";

	public static void main(String[] args) throws Exception {

		String token = "190e1819-f8f1-4984-8e8c-0e9fc55e9edb";

		InitiateTest InitiateTest = new InitiateTest();
		InitiateTest.initiate(token);
	}

	public void initiate(String token) throws Exception {
		String ivKey = token;

		String deReq = "{\n" + "        \"token\": \"" + token + "\"\n" + "    }";

		System.out.println("deReq==>" + deReq);
		String enc = TestUtil.enc(deReq, ivKey);

		String url = baseUrl + "/pg/api/payment";
		System.out.println("baseUrl==>" + url);
		String req = "{\n      \"data\": \"" + enc + "\"\n}";
		System.out.println("final Request==>" + req);
		String res = TestUtil.postApi(url, req, token);

		System.out.println(res);

		Gson gson = new Gson();
		ResponseWrapper decRes = gson.fromJson(res, ResponseWrapper.class);
		System.out.println("resData : " + decRes.getData());

		if (decRes != null && decRes.getData() != null) {
			String dec = TestUtil.dec(String.valueOf(decRes.getData()), ivKey);
			System.out.println(dec);
		}
	}

}
