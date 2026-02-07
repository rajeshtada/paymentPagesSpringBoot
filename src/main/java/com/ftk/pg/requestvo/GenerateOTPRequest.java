package com.ftk.pg.requestvo;

import com.ftk.pg.util.Util;

public class GenerateOTPRequest {
	
	private String pgInstanceId;
	private String merchantId;
	private String merchantReferenceNo;
	private String pan;
	private String cardExpDate;
	private String cvd2;
	private String nameOnCard;
	private String cardHolderStatus;
	private String amount;	
	private String currencyCode;
	private String customerIpAddress;
	private String browserUserAgent;	
	private String httpAccept;
	private String ext1;
	private String ext2;
	private String amountInInr;	
	private String originalAmount;
	private String purposeOfAuthentication;
	private String tokenAuthenticationValue;
	private String email;
	private String orderDesc;
	
	
	public String getCustomerIpAddress() {
		return customerIpAddress;
	}
	public void setCustomerIpAddress(String customerIpAddress) {
		this.customerIpAddress = customerIpAddress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
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
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getCardExpDate() {
		return cardExpDate;
	}
	public void setCardExpDate(String cardExpDate) {
		this.cardExpDate = cardExpDate;
	}
	public String getCvd2() {
		return cvd2;
	}
	public void setCvd2(String cvd2) {
		this.cvd2 = cvd2;
	}
	public String getNameOnCard() {
		return nameOnCard;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	public String getCardHolderStatus() {
		return cardHolderStatus;
	}
	public void setCardHolderStatus(String cardHolderStatus) {
		this.cardHolderStatus = cardHolderStatus;
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
	
	public String getBrowserUserAgent() {
		return browserUserAgent;
	}
	public void setBrowserUserAgent(String browserUserAgent) {
		this.browserUserAgent = browserUserAgent;
	}
	public String getHttpAccept() {
		return httpAccept;
	}
	public void setHttpAccept(String httpAccept) {
		this.httpAccept = httpAccept;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getAmountInInr() {
		return amountInInr;
	}
	public void setAmountInInr(String amountInInr) {
		this.amountInInr = amountInInr;
	}
	public String getOriginalAmount() {
		return originalAmount;
	}
	public void setOriginalAmount(String originalAmount) {
		this.originalAmount = originalAmount;
	}
	public String getPurposeOfAuthentication() {
		return purposeOfAuthentication;
	}
	public void setPurposeOfAuthentication(String purposeOfAuthentication) {
		this.purposeOfAuthentication = purposeOfAuthentication;
	}
	public String getTokenAuthenticationValue() {
		return tokenAuthenticationValue;
	}
	public void setTokenAuthenticationValue(String tokenAuthenticationValue) {
		this.tokenAuthenticationValue = tokenAuthenticationValue;
	}
	@Override
	public String toString() {
		return "GenerateOTPRequest [pgInstanceId=" + pgInstanceId + ", merchantId=" + merchantId
				+ ", merchantReferenceNo=" + merchantReferenceNo + ", pan=" + Util.maskStringValue(pan) + ", cardExpDate=" +  Util.maskStringValue(cardExpDate)
				+ ", cvd2=" +  Util.maskStringValue(cvd2) + ", nameOnCard=" +  Util.maskStringValue(nameOnCard) + ", cardHolderStatus=" + cardHolderStatus
				+ ", amount=" + amount + ", currencyCode=" + currencyCode + ", customerIpAddress=" + customerIpAddress
				+ ", browserUserAgent=" + browserUserAgent + ", httpAccept=" + httpAccept + ", ext1=" + ext1 + ", ext2="
				+ ext2 + ", amountInInr=" + amountInInr + ", originalAmount=" + originalAmount
				+ ", purposeOfAuthentication=" + purposeOfAuthentication + ", tokenAuthenticationValue="
				+ tokenAuthenticationValue + ", email=" + email + ", orderDesc=" + orderDesc + "]";
	}
	
	

	
	  


}
