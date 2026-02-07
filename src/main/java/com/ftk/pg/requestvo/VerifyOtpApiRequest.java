package com.ftk.pg.requestvo;

import java.io.Serializable;

public class VerifyOtpApiRequest implements Serializable {

	private String BankId;
	private String MerchantId;
	private String TerminalId;
	private String OrderId;
	private String AccessCode;
	private String PgId;
	private String OTP;
	private String OTPCancelled;
	private String SecureHash;
	public String getBankId() {
		return BankId;
	}
	public void setBankId(String bankId) {
		BankId = bankId;
	}
	public String getMerchantId() {
		return MerchantId;
	}
	public void setMerchantId(String merchantId) {
		MerchantId = merchantId;
	}
	public String getTerminalId() {
		return TerminalId;
	}
	public void setTerminalId(String terminalId) {
		TerminalId = terminalId;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getAccessCode() {
		return AccessCode;
	}
	public void setAccessCode(String accessCode) {
		AccessCode = accessCode;
	}
	public String getPgId() {
		return PgId;
	}
	public void setPgId(String pgId) {
		PgId = pgId;
	}
	public String getOTP() {
		return OTP;
	}
	public void setOTP(String oTP) {
		OTP = oTP;
	}
	public String getOTPCancelled() {
		return OTPCancelled;
	}
	public void setOTPCancelled(String oTPCancelled) {
		OTPCancelled = oTPCancelled;
	}
	public String getSecureHash() {
		return SecureHash;
	}
	public void setSecureHash(String secureHash) {
		SecureHash = secureHash;
	}
	@Override
	public String toString() {
		return "VerifyOtpApiRequest [BankId=" + BankId + ", MerchantId=" + MerchantId + ", TerminalId=" + TerminalId
				+ ", OrderId=" + OrderId + ", AccessCode=" + AccessCode + ", PgId=" + PgId + ", OTP=" + OTP
				+ ", OTPCancelled=" + OTPCancelled + ", SecureHash=" + SecureHash + "]";
	}

	
}
