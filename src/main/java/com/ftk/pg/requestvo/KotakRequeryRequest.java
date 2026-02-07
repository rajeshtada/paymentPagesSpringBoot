package com.ftk.pg.requestvo;

import java.math.BigDecimal;

public class KotakRequeryRequest {

	private String messageCode;

	private String dateAndTime;

	private String merchantId;
	
	private String merchantReference;
	
	private BigDecimal amount;
	
	private String transactionDescription;
	
	private String future1;

	private String future2;

	private String checkSum;
	
	
	
	
	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantReference() {
		return merchantReference;
	}

	public void setMerchantReference(String merchantReference) {
		this.merchantReference = merchantReference;
	}

	public String getFuture1() {
		return future1;
	}

	public void setFuture1(String future1) {
		this.future1 = future1;
	}

	public String getFuture2() {
		return future2;
	}

	public void setFuture2(String future2) {
		this.future2 = future2;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

//	@Override
//	public String toString() {
//		return "KotakRequeryRequest [messageCode=" + messageCode + ", dateAndTime=" + dateAndTime + ", merchantId="
//				+ merchantId + ", merchantReference=" + merchantReference + ", amount=" + amount
//				+ ", transactionDescription=" + transactionDescription + ", future1=" + future1 + ", future2=" + future2
//				+ ", checkSum=" + checkSum + "]";
//	}
//	
//	
//	public String getRequestToCalculateChecksum() {
//		return messageCode + "|" + dateAndTime + "|" + merchantId + "|" + merchantReference + "|" + "|" + transactionDescription + "|" +amount + future1 + "|" + future2+"|";
//	}
//	
//	public String getRequestWithChecksum() {
//		return messageCode + "|" + dateAndTime + "|" + merchantId + "|" + merchantReference + "|" + amount + "|" + transactionDescription + "|" + future1 + "|" + future2 + "|" + checkSum;
//	}

	@Override
	public String toString() {
		return "KotakRequeryRequest [messageCode=" + messageCode + ", dateAndTime=" + dateAndTime + ", merchantId="
				+ merchantId + ", merchantReference=" + merchantReference + ", future1=" + future1 + ", future2="
				+ future2 + ", checkSum=" + checkSum + "]";
	}

	public String getRequestToCalculateChecksum() {
		return messageCode + "|" + dateAndTime + "|" + merchantId + "|" + merchantReference + "|" + future1 + "|" + future2+"|";
	}

	public String getRequestWithChecksum() {
		return messageCode + "|" + dateAndTime + "|" + merchantId + "|" + merchantReference + "|" + future1 + "|" + future2 + "|" + checkSum;
	}
	
	
}
