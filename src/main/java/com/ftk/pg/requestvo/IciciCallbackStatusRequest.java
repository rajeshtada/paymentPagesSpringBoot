package com.ftk.pg.requestvo;

public class IciciCallbackStatusRequest {
	private String merchantId ;
	private String subMerchantId ;
	private String terminalId;
	private String transactionType;
	private String merchantTranId ;
	private String transactionDate;
	private String BankRRN;
	private String refID;
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getSubMerchantId() {
		return subMerchantId;
	}
	public void setSubMerchantId(String subMerchantId) {
		this.subMerchantId = subMerchantId;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getMerchantTranId() {
		return merchantTranId;
	}
	public void setMerchantTranId(String merchantTranId) {
		this.merchantTranId = merchantTranId;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getBankRRN() {
		return BankRRN;
	}
	public void setBankRRN(String bankRRN) {
		BankRRN = bankRRN;
	}
	public String getRefID() {
		return refID;
	}
	public void setRefID(String refID) {
		this.refID = refID;
	}
	@Override
	public String toString() {
		return "IciciCallbackStatusRequest [merchantId=" + merchantId + ", subMerchantId=" + subMerchantId
				+ ", terminalId=" + terminalId + ", transactionType=" + transactionType + ", merchantTranId="
				+ merchantTranId + ", transactionDate=" + transactionDate + ", BankRRN=" + BankRRN + ", refID=" + refID
				+ "]";
	}
	
	

}
