package com.ftk.pg.responsevo;
import java.io.Serializable;

public class KotakRefundStatusResponse implements Serializable{

	private String messageCode;
	
	private String dateAndTime;
	
	private String merchantId;
	
	private String merchantReference;
	
	private String amount;
	
	private String authStatus;
	
	private String bankReferenceNo;
	
	private String errorCode; 
	
	private String CheckSum;

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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getBankReferenceNo() {
		return bankReferenceNo;
	}

	public void setBankReferenceNo(String bankReferenceNo) {
		this.bankReferenceNo = bankReferenceNo;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getCheckSum() {
		return CheckSum;
	}

	public void setCheckSum(String checkSum) {
		CheckSum = checkSum;
	}
	@Override
	public String toString() {
		return "KotakRefundStatusResponse [messageCode=" + messageCode + ", dateAndTime=" + dateAndTime
				+ ", merchantId=" + merchantId + ", merchantReference=" + merchantReference + ", amount=" + amount
				+ ", authStatus=" + authStatus + ", bankReferenceNo=" + bankReferenceNo + ", errorCode=" + errorCode
				+ ", CheckSum=" + CheckSum + "]";
	}

	public KotakRefundStatusResponse(String response) {
		String[] arr = response.split("\\|");
		this.messageCode = arr[0];
		this.dateAndTime = arr[1];
		this.merchantId = arr[2];
		this.merchantReference = arr[3];
		this.bankReferenceNo = arr[6];
		this.amount = arr[4];
		this.authStatus = arr[5];
		this.errorCode = arr[7];
		this.CheckSum = arr[8];
	}

	public KotakRefundStatusResponse(String messageCode, String dateAndTime, String merchantId, String merchantReference,
			String bankReferenceNo, String amount, String authStatus, String errorCode, String checkSum) {
		this.messageCode = messageCode;
		this.dateAndTime = dateAndTime;
		this.merchantId = merchantId;
		this.merchantReference =merchantReference ;
		this.bankReferenceNo = bankReferenceNo;
		this.amount = amount;
		this.authStatus = authStatus;
		this.errorCode = errorCode;
		this.CheckSum = checkSum;
	}
	

}
