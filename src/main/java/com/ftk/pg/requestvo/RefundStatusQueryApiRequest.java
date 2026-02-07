package com.ftk.pg.requestvo;

public class RefundStatusQueryApiRequest {

	private String BankId;

	private String MerchantId;

	private String TerminalId;

	private String OrderId;

	private String AccessCode;

	private String RefCancellationId;

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

	public String getRefCancellationId() {
		return RefCancellationId;
	}

	public void setRefCancellationId(String refCancellationId) {
		RefCancellationId = refCancellationId;
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
		return "RefundStatusQueryApiRequest [BankId=" + BankId + ", MerchantId=" + MerchantId + ", TerminalId="
				+ TerminalId + ", OrderId=" + OrderId + ", AccessCode=" + AccessCode + ", RefCancellationId="
				+ RefCancellationId + ", TxnType=" + TxnType + ", SecureHash=" + SecureHash + "]";
	}

	
	
	
}
