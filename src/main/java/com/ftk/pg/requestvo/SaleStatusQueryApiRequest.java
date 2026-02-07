package com.ftk.pg.requestvo;

public class SaleStatusQueryApiRequest {

	private String BankId;

	private String MerchantId;

	private String TerminalId;

	private String OrderId;

	private String AccessCode;
	
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
		return "SaleStatusQueryApiRequest [BankId=" + BankId + ", MerchantId=" + MerchantId + ", TerminalId="
				+ TerminalId + ", OrderId=" + OrderId + ", AccessCode=" + AccessCode + ", TxnType=" + TxnType
				+ ", SecureHash=" + SecureHash + "]";
	}

	
	
}
