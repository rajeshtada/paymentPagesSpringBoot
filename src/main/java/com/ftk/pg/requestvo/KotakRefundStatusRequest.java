package com.ftk.pg.requestvo;

public class KotakRefundStatusRequest {

	private String messageCode;
	private String dateAndTime;
	private String merchantId;
	private String merchantReferenceNumber;
	private String blankField1;
	private String blankField;
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
	public String getMerchantReferenceNumber() {
		return merchantReferenceNumber;
	}
	public void setMerchantReferenceNumber(String merchantReferenceNumber) {
		this.merchantReferenceNumber = merchantReferenceNumber;
	}
	public String getBlankField1() {
		return blankField1;
	}
	public void setBlankField1(String blankField1) {
		this.blankField1 = blankField1;
	}
	public String getBlankField() {
		return blankField;
	}
	public void setBlankField(String blankField) {
		this.blankField = blankField;
	}
	public String getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}
	@Override
	public String toString() {
		return "KotakRefundStatusRequest [messageCode=" + messageCode + ", dateAndTime=" + dateAndTime + ", merchantId="
				+ merchantId + ", merchantReferenceNumber=" + merchantReferenceNumber + ", blankField1=" + blankField1
				+ ", blankField=" + blankField + ", checkSum=" + checkSum + "]";
	}

	public String getRequestToCalculateChecksum() {
		return messageCode+"|"+dateAndTime+"|"+merchantId+"|"+merchantReferenceNumber+"|"+blankField1+"|"+blankField+"|";
	}
	
	public String getRequestWithChecksum() {
		return messageCode + "|" + dateAndTime + "|" + merchantId + "|" + merchantReferenceNumber + "|" + blankField1 + "|"
				+ blankField + "|" + checkSum;
	}
	
}
