package com.ftk.pg.requestvo;
import java.io.Serializable;

public class VoidApiRequest implements Serializable{

	private String BankId;

	private String MerchantId;

	private String TerminalId;
	
	private String OrderId;
	
	private String MCC;
	
	private String AccessCode;
	
	private String PgId;
	
	private String RetRefNo;
	
	private String ApprovalCode;
	
	private String CancellationId;
	
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

	public String getPgId() {
		return PgId;
	}

	public void setPgId(String pgId) {
		PgId = pgId;
	}

	public String getRetRefNo() {
		return RetRefNo;
	}

	public void setRetRefNo(String retRefNo) {
		RetRefNo = retRefNo;
	}

	public String getApprovalCode() {
		return ApprovalCode;
	}

	public void setApprovalCode(String approvalCode) {
		ApprovalCode = approvalCode;
	}

	public String getCancellationId() {
		return CancellationId;
	}

	public void setCancellationId(String cancellationId) {
		CancellationId = cancellationId;
	}

	public String getSecureHash() {
		return SecureHash;
	}

	public void setSecureHash(String secureHash) {
		SecureHash = secureHash;
	}

	@Override
	public String toString() {
		return "VoidApiRequest [BankId=" + BankId + ", MerchantId=" + MerchantId + ", TerminalId=" + TerminalId
				+ ", OrderId=" + OrderId + ", MCC=" + MCC + ", AccessCode=" + AccessCode + ", PgId=" + PgId
				+ ", RetRefNo=" + RetRefNo + ", ApprovalCode=" + ApprovalCode + ", CancellationId=" + CancellationId
				+ ", SecureHash=" + SecureHash + "]";
	}

	
	
	
	
}
