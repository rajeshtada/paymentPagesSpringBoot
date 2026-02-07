package com.ftk.pg.paymentPageApis;

import com.ftk.pg.requestvo.OTPVerifyWrapperRequest;
import com.google.gson.Gson;
import com.ftk.pg.requestvo.OTPVerifyWrapperRequest;

public class ConvertToJson {

	public static void main(String[] args) {
		
		OTPVerifyWrapperRequest vo = new OTPVerifyWrapperRequest();
		Gson objGson = new Gson();
		String json = objGson.toJson(vo);
		System.out.println(json);
		
		
	}
}
