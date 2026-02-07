package com.ftk.pg.ru;

import com.ftk.pg.encryption.GcmPgEncryption;
import com.ftk.pg.responsevo.MerchantRuResponseVo;
import com.ftk.pg.responsevo.MerchantRuResponseWrapper;
import com.google.gson.Gson;

public class PaymentResponsePageRequestTest {
	
	static String key = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
	static String iv = "hlnuyA9b4YxDq6oJSZFl8g==";
	static String mid = "44";
	static String terminalId = "getepay.merchant202500@icici";

	public static void main(String[] args) throws Exception {
		
		Gson gson = new Gson();
		
		MerchantRuResponseVo merchantRuResponseVo = new MerchantRuResponseVo();
		merchantRuResponseVo.setGetepayTxnId("11");
		
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(iv, key);
		String responseEncrypted = gcmPgEncryption.encryptWithMKeys(gson.toJson(merchantRuResponseVo));
		
		MerchantRuResponseWrapper merchantRuResponseWrapper = new MerchantRuResponseWrapper();
		merchantRuResponseWrapper.setResponse(responseEncrypted);
		merchantRuResponseWrapper.setMid(String.valueOf(mid));
		merchantRuResponseWrapper.setTerminalId(terminalId);
		merchantRuResponseWrapper.setStatus("SUCCESS");
		
		String json = gson.toJson(merchantRuResponseWrapper);
		System.out.println(json);
		
	}
}
