package com.ftk.pg.requestvo;

public class VerifyOTPRequest {
	private String pgInstanceId;
	private String merchantId;
	private String merchantReferenceNo;
	private String pgTransactionId;
	private String otp;
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
	public String getMerchantReferenceNo() {
		return merchantReferenceNo;
	}
	public void setMerchantReferenceNo(String merchantReferenceNo) {
		this.merchantReferenceNo = merchantReferenceNo;
	}
	public String getPgTransactionId() {
		return pgTransactionId;
	}
	public void setPgTransactionId(String pgTransactionId) {
		this.pgTransactionId = pgTransactionId;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	@Override
	public String toString() {
		return "VerifyOTPRequest [pgInstanceId=" + pgInstanceId + ", merchantId=" + merchantId
				+ ", merchantReferenceNo=" + merchantReferenceNo + ", pgTransactionId=" + pgTransactionId + ", otp="
				+ otp + "]";
	}
	
	
	
	

}
