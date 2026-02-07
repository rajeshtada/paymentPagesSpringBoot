package com.ftk.pg.responsevo;

public class RefundApiResponse {
	private String BankId;

	private String MerchantId;

	private String TerminalId;

	private String PgId;

	private String ResponseCode;

	private String ResponseMessage;
	
	private String OrderId;

	private String AccessCode;

	private String RefundAmount;
	
	private String RetRefNo;

	private String RefCancellationId;

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

	public String getRefundAmount() {
		return RefundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		RefundAmount = refundAmount;
	}

	public String getRetRefNo() {
		return RetRefNo;
	}

	public void setRetRefNo(String retRefNo) {
		RetRefNo = retRefNo;
	}

	public String getRefCancellationId() {
		return RefCancellationId;
	}

	public void setRefCancellationId(String refCancellationId) {
		RefCancellationId = refCancellationId;
	}

	public String getSecureHash() {
		return SecureHash;
	}

	public void setSecureHash(String secureHash) {
		SecureHash = secureHash;
	}

	@Override
	public String toString() {
		return "RefundApiResponse [BankId=" + BankId + ", MerchantId=" + MerchantId + ", TerminalId=" + TerminalId
				+ ", PgId=" + PgId + ", ResponseCode=" + ResponseCode + ", ResponseMessage=" + ResponseMessage
				+ ", OrderId=" + OrderId + ", AccessCode=" + AccessCode + ", RefundAmount=" + RefundAmount
				+ ", RetRefNo=" + RetRefNo + ", RefCancellationId=" + RefCancellationId + ", SecureHash=" + SecureHash
				+ "]";
	}

	
	
	
}
