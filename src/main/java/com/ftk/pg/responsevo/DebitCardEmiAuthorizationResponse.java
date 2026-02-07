package com.ftk.pg.responsevo;

public class DebitCardEmiAuthorizationResponse {

	private String BankId;

	private String MerchantId;

	private String TerminalId;

	private String OrderId;
	
	private String AccessCode;

	private String Amount;

	private String PgId;

	private String ResponseCode;

	private String ResponseMessage;
	
	private String MaskedCardNumber;
	
	private String ApacId;
	
	private String BankTxnID;

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

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getPgId() {
		return PgId;
	}

	public void setPgId(String pgId) {
		PgId = pgId;
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

	public String getMaskedCardNumber() {
		return MaskedCardNumber;
	}

	public void setMaskedCardNumber(String maskedCardNumber) {
		MaskedCardNumber = maskedCardNumber;
	}

	public String getApacId() {
		return ApacId;
	}

	public void setApacId(String apacId) {
		ApacId = apacId;
	}

	public String getBankTxnID() {
		return BankTxnID;
	}

	public void setBankTxnID(String bankTxnID) {
		BankTxnID = bankTxnID;
	}

	public String getSecureHash() {
		return SecureHash;
	}

	public void setSecureHash(String secureHash) {
		SecureHash = secureHash;
	}

	@Override
	public String toString() {
		return "DebitCardEmiAuthorizationResponse [BankId=" + BankId + ", MerchantId=" + MerchantId + ", TerminalId="
				+ TerminalId + ", OrderId=" + OrderId + ", AccessCode=" + AccessCode + ", Amount=" + Amount + ", PgId="
				+ PgId + ", ResponseCode=" + ResponseCode + ", ResponseMessage=" + ResponseMessage
				+ ", MaskedCardNumber=" + MaskedCardNumber + ", ApacId=" + ApacId + ", BankTxnID=" + BankTxnID
				+ ", SecureHash=" + SecureHash + "]";
	}

	

	
}
