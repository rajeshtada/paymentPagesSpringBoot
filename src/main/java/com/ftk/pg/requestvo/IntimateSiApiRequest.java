package com.ftk.pg.requestvo;

public class IntimateSiApiRequest {

	private String BankId;

	private String MerchantId;

	private String TerminalId;

	private String OrderId;

	private String MCC;

	private String AccessCode;
	
	private String SIID;

	private String siAlertpreference;

	private String SiPreferredInitiationDate;

	private String CardNumber;
	
	private String CardTokenPan;

	private String MerchantTRID;

	private String CardTokenReferenceNo;

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

	public String getSiAlertpreference() {
		return siAlertpreference;
	}

	public void setSiAlertpreference(String siAlertpreference) {
		this.siAlertpreference = siAlertpreference;
	}

	public String getSiPreferredInitiationDate() {
		return SiPreferredInitiationDate;
	}

	public void setSiPreferredInitiationDate(String siPreferredInitiationDate) {
		SiPreferredInitiationDate = siPreferredInitiationDate;
	}

	public String getCardNumber() {
		return CardNumber;
	}

	public void setCardNumber(String cardNumber) {
		CardNumber = cardNumber;
	}

	public String getCardTokenPan() {
		return CardTokenPan;
	}

	public void setCardTokenPan(String cardTokenPan) {
		CardTokenPan = cardTokenPan;
	}

	public String getMerchantTRID() {
		return MerchantTRID;
	}

	public void setMerchantTRID(String merchantTRID) {
		MerchantTRID = merchantTRID;
	}

	public String getCardTokenReferenceNo() {
		return CardTokenReferenceNo;
	}

	public void setCardTokenReferenceNo(String cardTokenReferenceNo) {
		CardTokenReferenceNo = cardTokenReferenceNo;
	}

	public String getSecureHash() {
		return SecureHash;
	}

	public void setSecureHash(String secureHash) {
		SecureHash = secureHash;
	}

	@Override
	public String toString() {
		return "IntimateSiApiRequest [BankId=" + BankId + ", MerchantId=" + MerchantId + ", TerminalId=" + TerminalId
				+ ", OrderId=" + OrderId + ", MCC=" + MCC + ", AccessCode=" + AccessCode + ", SIID=" + SIID
				+ ", siAlertpreference=" + siAlertpreference + ", SiPreferredInitiationDate="
				+ SiPreferredInitiationDate + ", CardNumber=" + CardNumber + ", CardTokenPan=" + CardTokenPan
				+ ", MerchantTRID=" + MerchantTRID + ", CardTokenReferenceNo=" + CardTokenReferenceNo + ", SecureHash="
				+ SecureHash + "]";
	}

	
	
	
	
	
}