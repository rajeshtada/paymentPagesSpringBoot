package com.ftk.pg.requestvo;

public class SbiTransactionStatusRequest {
	
	private String pgInstanceId;
	private String merchantId;
	private String merchantReferenceNo;
	private String messageHash;
	private String amount;
	private String currencyCode;
	public String getPgInstanceId() {
		return pgInstanceId;
	}
	public void setPgInstanceId(String pgInstanceId) {
		this.pgInstanceId = pgInstanceId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantReferenceNo() {
		return merchantReferenceNo;
	}
	public void setMerchantReferenceNo(String merchantReferenceNo) {
		this.merchantReferenceNo = merchantReferenceNo;
	}
	public String getMessageHash() {
		return messageHash;
	}
	public void setMessageHash(String messageHash) {
		this.messageHash = messageHash;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	
	@Override
	public String toString() {
		return "SbiTransactionStatusRequest [pgInstanceId=" + pgInstanceId + ", merchantId=" + merchantId
				+ ", merchantReferenceNo=" + merchantReferenceNo + ", messageHash=" + messageHash + ", amount=" + amount
				+ ", currencyCode=" + currencyCode + "]";
	}

	
}
