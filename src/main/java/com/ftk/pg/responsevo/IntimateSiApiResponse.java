package com.ftk.pg.responsevo;

public class IntimateSiApiResponse {

	private String BankId;

	private String MerchantId;

	private String TerminalId;

	private String OrderId;

	private String MCC;

	private String AccessCode;
	
	private String SIID;

	private String Alertpreference;

	private String SiPreferredInitiationDate;
	
	private String ResponseCode;

	private String ResponseMessage;

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

	public String getMCC() {
		return MCC;
	}

	public void setMCC(String mCC) {
		MCC = mCC;
	}

	public String getAccessCode() {
		return AccessCode;
	}

	public void setAccessCode(String accessCode) {
		AccessCode = accessCode;
	}

	public String getSIID() {
		return SIID;
	}

	public void setSIID(String sIID) {
		SIID = sIID;
	}

	public String getAlertpreference() {
		return Alertpreference;
	}

	public void setAlertpreference(String alertpreference) {
		Alertpreference = alertpreference;
	}

	public String getSiPreferredInitiationDate() {
		return SiPreferredInitiationDate;
	}

	public void setSiPreferredInitiationDate(String siPreferredInitiationDate) {
		SiPreferredInitiationDate = siPreferredInitiationDate;
	}

	public String getResponseCode() {
		return ResponseCode;
	}

	public void setResponseCode(String responseCode) {
		ResponseCode = responseCode;
	}

	public String getResponseMessage() {
		return ResponseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		ResponseMessage = responseMessage;
	}

	public String getSecureHash() {
		return SecureHash;
	}

	public void setSecureHash(String secureHash) {
		SecureHash = secureHash;
	}

	@Override
	public String toString() {
		return "IntimateSiApiResponse [BankId=" + BankId + ", MerchantId=" + MerchantId + ", TerminalId=" + TerminalId
				+ ", OrderId=" + OrderId + ", MCC=" + MCC + ", AccessCode=" + AccessCode + ", SIID=" + SIID
				+ ", Alertpreference=" + Alertpreference + ", SiPreferredInitiationDate=" + SiPreferredInitiationDate
				+ ", ResponseCode=" + ResponseCode + ", ResponseMessage=" + ResponseMessage + ", SecureHash="
				+ SecureHash + "]";
	}

	
	
	
}
