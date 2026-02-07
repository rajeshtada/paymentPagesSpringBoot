package com.ftk.pg.vo.sbiNb;

public class SbiRequestHeader {
	private String xapikey;
	private String pgInstanceId;
	private String merchantId;
	public String getXapikey() {
		return xapikey;
	}
	public void setXapikey(String xapikey) {
		this.xapikey = xapikey;
	}
	public String getPgInstanceId() {
		return pgInstanceId;
	}
	public void setPgInstanceId(String pgInstanceId) {
		this.pgInstanceId = pgInstanceId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	@Override
	public String toString() {
		return "SbiRequestHeader [xapikey=" + xapikey + ", pgInstanceId=" + pgInstanceId + ", merchantId=" + merchantId
				+ "]";
	}
	
	

}
