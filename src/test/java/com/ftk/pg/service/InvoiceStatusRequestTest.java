package com.ftk.pg.service;

import com.ftk.pg.encryption.GcmPgEncryption;
import com.ftk.pg.vo.generateInvoice.InvoiceStatusRequest;
import com.ftk.pg.vo.generateInvoice.PgRequestWrapper;
import com.google.gson.Gson;

public class InvoiceStatusRequestTest {

	private static String url;
	private static String ru;
	static String key ;
	static String iv ;
	static String mid;
	static String terminalId ;
	
	public InvoiceStatusRequestTest(String environment, String mId) {
		if ( environment.equalsIgnoreCase("live")) {
			url = "https://portalv2.getepay.in/pg/generateInvoiceV3";
			ru = "https://portalv2.getepay.in/pgru/paymentResponse";
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
			url = "https://pay1.getepay.in/pg/pg/generateInvoiceV3";
			ru = "https://pay1.getepay.in/pgru/pgru/paymentResponse";
			if (mId.equals("1") ) {
				key = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
				iv = "hlnuyA9b4YxDq6oJSZFl8g==";
				mid = "108";
				terminalId = "getepay.merchant61062@icici";
			} else if (mId.equals("887729")) {
				key = "lGed7VR7NcE1oSeCeUN0ng==";
				iv = "9TXpxfuXD6/4RJ0cuOCX0g==";
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
	
	public static void main(String[] args) throws Exception {
		
		new InvoiceStatusRequestTest("uat", "44");
		Gson gson = new Gson();
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(iv, key);
		
		InvoiceStatusRequest req = new InvoiceStatusRequest();
		req.setMid(mid);
		req.setTerminalId(terminalId);
		req.setPaymentId("19125217");
		String encryptedReq = gcmPgEncryption.encryptWithMKeys(gson.toJson(req));
		
		
		PgRequestWrapper requestWrapper = new PgRequestWrapper();
		requestWrapper.setMid(mid);
		requestWrapper.setTerminalId(terminalId);
		requestWrapper.setReq(encryptedReq);
		
		String json = gson.toJson(requestWrapper);
		System.out.println("PgRequestWrapper : " + json);
		
		
		
	}
}
