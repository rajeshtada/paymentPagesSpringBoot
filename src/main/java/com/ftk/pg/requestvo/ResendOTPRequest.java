package com.ftk.pg.requestvo;

public class ResendOTPRequest {
	private String pgInstanceId;
	private String merchantId;
	private String pgTransactionId;
	private String merchantReferenceNo;
	private String cardHolderStatus;
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
	public String getPgTransactionId() {
		return pgTransactionId;
	}
	public void setPgTransactionId(String pgTransactionId) {
		this.pgTransactionId = pgTransactionId;
	}
	public String getMerchantReferenceNo() {
		return merchantReferenceNo;
	}
	public void setMerchantReferenceNo(String merchantReferenceNo) {
		this.merchantReferenceNo = merchantReferenceNo;
	}
	public String getCardHolderStatus() {
		return cardHolderStatus;
	}
	public void setCardHolderStatus(String cardHolderStatus) {
		this.cardHolderStatus = cardHolderStatus;
	}
	@Override
	public String toString() {
		return "ResendOTPRequest [pgInstanceId=" + pgInstanceId + ", merchantId=" + merchantId + ", pgTransactionId="
				+ pgTransactionId + ", merchantReferenceNo=" + merchantReferenceNo + ", cardHolderStatus="
				+ cardHolderStatus + "]";
	}
	
	

}
