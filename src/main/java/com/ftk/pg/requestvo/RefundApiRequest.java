package com.ftk.pg.requestvo;

public class RefundApiRequest {

	private String BankId;

	private String MerchantId;

	private String TerminalId;

	private String OrderId;

	private String AccessCode;

	private String RefundAmount;

	private String RetRefNo;

	private String RefCancellationId;

	private String RefReasonCode;

	private String TxnType;
	
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

	public String getRefReasonCode() {
		return RefReasonCode;
	}

	public void setRefReasonCode(String refReasonCode) {
		RefReasonCode = refReasonCode;
	}

	public String getTxnType() {
		return TxnType;
	}

	public void setTxnType(String txnType) {
		TxnType = txnType;
	}

	public String getSecureHash() {
		return SecureHash;
	}

	public void setSecureHash(String secureHash) {
		SecureHash = secureHash;
	}

	@Override
	public String toString() {
		return "RefundApiRequest [BankId=" + BankId + ", MerchantId=" + MerchantId + ", TerminalId=" + TerminalId
				+ ", OrderId=" + OrderId + ", AccessCode=" + AccessCode + ", RefundAmount=" + RefundAmount
				+ ", RetRefNo=" + RetRefNo + ", RefCancellationId=" + RefCancellationId + ", RefReasonCode="
				+ RefReasonCode + ", TxnType=" + TxnType + ", SecureHash=" + SecureHash + "]";
	}

	

}
