package com.ftk.pg.util;

public class GetKeyParam {

	
	public static KeyParamVo fetchData(String environment, String midString) {
		
		String urlValue = null;
		String key = null;
		String iv = null;
		String mid= null;
		String terminalId = null;
		String ru = null;
		
		if ( environment.equalsIgnoreCase("live")) {
//			urlValue = "https://portal.getepay.in:8443/getepayPortal/pg/generateInvoice";
			urlValue = "https://portalv2.getepay.in/pg/generateInvoiceV3";
//			urlValue = "https://portalv2.getepay.in/pg/generateInvoiceV1";
			ru = "https://portalv2.getepay.in/pgru/paymentResponse";
			if (midString.equals("76606")) {
				key = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
				iv = "hlnuyA9b4YxDq6oJSZFl8g==";
				mid = "66439";
				terminalId = "getepay.merchant59020@icici";
			} else if (midString.equals("887729") ) {
				key = "lGed7VR7NcE1oSeCeUN0ng==";
				iv = "9TXpxfuXD6/4RJ0cuOCX0g==";
				mid = "705016";
				terminalId = "getepay.merchant129118@icici";
			}
		} else if (environment.equalsIgnoreCase("uat")) {
//			urlValue = "https://pay1.getepay.in/pg/pg/generateInvoiceV1";
			urlValue = "https://pay1.getepay.in/pg/pg/generateInvoiceV3";
			ru = "https://pay1.getepay.in/pgru/pgru/paymentResponse";
			
			if (midString.equals("1") ) {
				key = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
				iv = "hlnuyA9b4YxDq6oJSZFl8g==";
				mid = "108";
				terminalId = "getepay.merchant61062@icici";
			} else if (midString.equals("887729")) {
				key = "lGed7VR7NcE1oSeCeUN0ng==";
				iv = "9TXpxfuXD6/4RJ0cuOCX0g==";
				mid = "705016";
				terminalId = "getepay.merchant129118@icici";
			} else if (midString.equals("44")) {
				key = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
				iv = "hlnuyA9b4YxDq6oJSZFl8g==";
				mid = "44";
				terminalId = "getepay.merchant202500@icici";
			}
		} else if (environment.equalsIgnoreCase("local")) {
			urlValue = "http://localhost:8081/pg/pg/generateInvoiceV3";
			ru = "https://pay1.getepay.in/pgru/pgru/paymentResponse";
			if (midString.equals("44")) {
				key = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
				iv = "hlnuyA9b4YxDq6oJSZFl8g==";
				mid = "44";
				terminalId = "getepay.merchant202500@icici";
			}
		}
		
		
		KeyParamVo vo = new KeyParamVo();
		vo.setEnvironment(environment);
		vo.setIv(iv);
		vo.setKey(key);
		vo.setMid(mid);
		vo.setTerminalId(terminalId);
		vo.setUrlValue(urlValue);
		vo.setRu(ru);
		return vo;
	}
}
