package com.ftk.pg.requestvo;

import com.ftk.pg.util.Util;

public class Initiate2Request {
	private String pgInstanceId;
	
	private String merchantId;
	private String merchantReferenceNo;
	private String pan;
	private String cardExpDate;
	private String browserUserAgent;
	private String ipAddress;	
	private String httpAccept;
	private String nameOnCard;
	private String email;
	private String mobileNumber;
	private String amountInINR;
	private String authAmount;
	private String currencyCode;
	private String cvd2;
	private String orderDesc;
	private String ext1;
	private String ext2;	
	private String amountInInr;
	private String originalAmount;
	private String merchantResponseUrl;
	private String purposeOfAuthentication;
	private String tokenAuthenticationValue;
	private String transaction_type_indicator;
	
	
	
	public String getTransaction_type_indicator() {
		return transaction_type_indicator;
	}

	public void setTransaction_type_indicator(String transaction_type_indicator) {
		this.transaction_type_indicator = transaction_type_indicator;
	}

	public String getAmountInINR() {
		return amountInINR;
	}

	public void setAmountInINR(String amountInINR) {
		this.amountInINR = amountInINR;
	}

	public String getPgInstanceId() {
		return pgInstanceId;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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
	public String getBrowserUserAgent() {
		return browserUserAgent;
	}
	public void setBrowserUserAgent(String browserUserAgent) {
		this.browserUserAgent = browserUserAgent;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getHttpAccept() {
		return httpAccept;
	}
	public void setHttpAccept(String httpAccept) {
		this.httpAccept = httpAccept;
	}
	public String getAuthAmount() {
		return authAmount;
	}
	public void setAuthAmount(String authAmount) {
		this.authAmount = authAmount;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
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
	public String getMerchantResponseUrl() {
		return merchantResponseUrl;
	}
	public void setMerchantResponseUrl(String merchantResponseUrl) {
		this.merchantResponseUrl = merchantResponseUrl;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Initiate2Request [pgInstanceId=" + pgInstanceId + ", merchantId=" + merchantId
				+ ", merchantReferenceNo=" + merchantReferenceNo + ", pan=" + Util.maskStringValue(pan) + ", cardExpDate=" + Util.maskStringValue(cardExpDate)
				+ ", browserUserAgent=" + browserUserAgent + ", ipAddress=" + ipAddress + ", httpAccept=" + httpAccept
				+ ", nameOnCard=" +  Util.maskStringValue(nameOnCard) + ", email=" + email + ", mobileNumber=" + mobileNumber
				+ ", amountInINR=" + amountInINR + ", authAmount=" + authAmount + ", currencyCode=" + currencyCode
				+ ", cvd2=" +  Util.maskStringValue(cvd2) + ", orderDesc=" + orderDesc + ", ext1=" + ext1 + ", ext2=" + ext2 + ", amountInInr="
				+ amountInInr + ", originalAmount=" + originalAmount + ", merchantResponseUrl=" + merchantResponseUrl
				+ ", purposeOfAuthentication=" + purposeOfAuthentication + ", tokenAuthenticationValue="
				+ tokenAuthenticationValue + ", transaction_type_indicator=" + transaction_type_indicator + "]";
	}


	
	
	
	
	
	

}
