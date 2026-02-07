package com.ftk.pg.requestvo;

import com.ftk.pg.util.Util;

public class AuthorizeApiRequest {
	private String pgInstanceId;
	private String merchantId;
	private String pgTransactionId;
	private String merchantReferenceNo;
	private String altId;
	private String altExpiry;
	private String tokenAuthenticationValue;
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
	public String getPgTransactionId() {
		return pgTransactionId;
	}
	public void setPgTransactionId(String pgTransactionId) {
		this.pgTransactionId = pgTransactionId;
	}
	public String getMerchantReferenceNo() {
		return merchantReferenceNo;
	}
	public void setMerchantReferenceNo(String merchantReferenceNo) {
		this.merchantReferenceNo = merchantReferenceNo;
	}
	
	
	public String getAltId() {
		return altId;
	}
	public void setAltId(String altId) {
		this.altId = altId;
	}
	public String getAltExpiry() {
		return altExpiry;
	}
	public void setAltExpiry(String altExpiry) {
		this.altExpiry = altExpiry;
	}
	public String getTokenAuthenticationValue() {
		return tokenAuthenticationValue;
	}
	public void setTokenAuthenticationValue(String tokenAuthenticationValue) {
		this.tokenAuthenticationValue = tokenAuthenticationValue;
	}
	@Override
	public String toString() {
		return "AuthorizeApiRequest [pgInstanceId=" + pgInstanceId + ", merchantId=" + merchantId + ", pgTransactionId="
				+ pgTransactionId + ", merchantReferenceNo=" + merchantReferenceNo + ", altId=" + Util.maskStringValue(altId) + ", altExpiry="
				+  Util.maskStringValue(altExpiry) + ", tokenAuthenticationValue=" + tokenAuthenticationValue + "]";
	}
	
}
