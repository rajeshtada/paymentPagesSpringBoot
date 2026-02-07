package com.ftk.pg.vo.sbiNb;

public class SbiRefundVoidRequest {
	private String pgInstanceId;
	private String merchantId;
	private String action;
	private String orignalTransactionId;
	private String merchantReferenceNo;
	private String messageHash;
	private String amount;
	private String refundType;
	private String ext1;
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getOrignalTransactionId() {
		return orignalTransactionId;
	}
	public void setOrignalTransactionId(String orignalTransactionId) {
		this.orignalTransactionId = orignalTransactionId;
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
	public String getRefundType() {
		return refundType;
	}
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	@Override
	public String toString() {
		return "RefundVoidRequest [pgInstanceId=" + pgInstanceId + ", merchantId=" + merchantId + ", action=" + action
				+ ", orignalTransactionId=" + orignalTransactionId + ", merchantReferenceNo=" + merchantReferenceNo
				+ ", messageHash=" + messageHash + ", amount=" + amount + ", refundType=" + refundType + ", ext1="
				+ ext1 + "]";
	}
	
	
}
