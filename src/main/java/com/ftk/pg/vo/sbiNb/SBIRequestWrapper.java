package com.ftk.pg.vo.sbiNb;

public class SBIRequestWrapper {
	private String encdata;
	private String merchant_code;
	public String getEncdata() {
		return encdata;
	}
	public void setEncdata(String encdata) {
		this.encdata = encdata;
	}
	public String getMerchant_code() {
		return merchant_code;
	}
	public void setMerchant_code(String merchant_code) {
		this.merchant_code = merchant_code;
	}
	@Override
	public String toString() {
		return "SBIRequestWrapper [encdata=" + encdata + ", merchant_code=" + merchant_code + "]";
	}
	
	

}
