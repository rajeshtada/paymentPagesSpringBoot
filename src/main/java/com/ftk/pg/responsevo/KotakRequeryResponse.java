package com.ftk.pg.responsevo;

public class KotakRequeryResponse {

	private String messageCode;

	private String dateAndTime;

	private String merchantId;
	
	private String merchantReference;
	
	private String bankReferenceNumber;

	private String amount;

	private String authStatus;

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





	public String getBankReferenceNumber() {
		return bankReferenceNumber;
	}





	public void setBankReferenceNumber(String bankReferenceNumber) {
		this.bankReferenceNumber = bankReferenceNumber;
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





	public String getCheckSum() {
		return checkSum;
	}





	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}


	



	@Override
	public String toString() {
		return "KotakRequeryResponse [messageCode=" + messageCode + ", dateAndTime=" + dateAndTime + ", merchantId="
				+ merchantId + ", merchantReference=" + merchantReference + ", bankReferenceNumber="
				+ bankReferenceNumber + ", amount=" + amount + ", authStatus=" + authStatus + ", checkSum=" + checkSum
				+ "]";
	}





	public KotakRequeryResponse(String response) {
		String[] arr = response.split("\\|");
		this.messageCode = arr[0];
		this.dateAndTime = arr[1];
		this.merchantId = arr[2];
		this.merchantReference = arr[3];
		this.amount = arr[4];
		this.authStatus = arr[5];
		this.bankReferenceNumber = arr[6];
		this.checkSum = arr[7];
	}





	public KotakRequeryResponse(String messageCode, String dateAndTime, String merchantId, String merchantReference,
			String bankReferenceNumber, String amount, String authStatus, String checkSum) {
		super();
		this.messageCode = messageCode;
		this.dateAndTime = dateAndTime;
		this.merchantId = merchantId;
		this.merchantReference = merchantReference;
		this.bankReferenceNumber = bankReferenceNumber;
		this.amount = amount;
		this.authStatus = authStatus;
		this.checkSum = checkSum;
	}
	
	
}
