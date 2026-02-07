package com.ftk.pg.responsevo;

public class Parameters {
	private String merchant_code;
	private String encdata;
	public String getMerchant_code() {
		return merchant_code;
	}
	public void setMerchant_code(String merchant_code) {
		this.merchant_code = merchant_code;
	}
	public String getEncdata() {
		return encdata;
	}
	public void setEncdata(String encdata) {
		this.encdata = encdata;
	}
	@Override
	public String toString() {
		return "Parameters [merchant_code=" + merchant_code + ", encdata=" + encdata + "]";
	}
	

}
