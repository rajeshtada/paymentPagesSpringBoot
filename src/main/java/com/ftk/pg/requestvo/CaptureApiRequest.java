package com.ftk.pg.requestvo;

public class CaptureApiRequest {

	private String BankId;

	private String MerchantId;

	private String TerminalId;

	private String OrderId;

	private String AccessCode;
	
	private String CaptureAmount;

	private String RetRefNo;

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

	public String getCaptureAmount() {
		return CaptureAmount;
	}

	public void setCaptureAmount(String captureAmount) {
		CaptureAmount = captureAmount;
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
		return "CaptureApiRequest [BankId=" + BankId + ", MerchantId=" + MerchantId + ", TerminalId=" + TerminalId
				+ ", OrderId=" + OrderId + ", AccessCode=" + AccessCode + ", CaptureAmount=" + CaptureAmount
				+ ", RetRefNo=" + RetRefNo + ", RefCancellationId=" + RefCancellationId + ", TxnType=" + TxnType
				+ ", SecureHash=" + SecureHash + "]";
	}

	
	
	
}
