package com.ftk.pg.requestvo;

import com.ftk.pg.util.Util;

public class ParqAuthenticationRequest {
    private String messageType ;
    private String deviceChannel ;
    private String merchantTransID ;
    private String messageVersion ;
    private String messageCategory ;
    private String threeDSServerTransID ;
    private String threeDSRequestorID ;
    private String threeDSRequestorName ;
    private String threeDSRequestorURL ;
    private String threeDSCompInd ;
    private String threeDSRequestorAuthenticationInd ;
    private ThreeDSRequestorAuthenticationInfo threeDSRequestorAuthenticationInfo ;
    private String acctNumber ;
    private String acctType ;
    private String addrMatch ;
    private String cardExpiryDate ;
    private String cardholderName ;
    private String mcc ;
    private String merchantName ;
    private String merchantUrl ;
    private String merchantCountryCode ;
    private String acquirerBIN ;
    private String acquirerID ;
    private String acquirerMerchantID ;
    private String purchaseCurrency ;
    private String purchaseExponent ;
    private String purchaseAmount ;
    private String purchaseDate ;
    private MobilePhone mobilePhone ;
    private WorkPhone workPhone ;
    private String email ;
    private HomePhone homePhone ;
    private String transType ;
    private String threeRIInd ;
    private String threeDSRequestorFinalAuthRespURL ;
    private String browserAcceptHeader ;
    private String browserIP ;
    private boolean browserJavaEnabled ;
    private boolean browserJavascriptEnabled ;
    private String browserLanguage ;
    private String browserColorDepth ;
    private String browserScreenHeight ;
    private String browserScreenWidth ;
    private String browserTZ ;
    private String browserUserAgent ;
    private String p_messageVersion ;
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getDeviceChannel() {
		return deviceChannel;
	}
	public void setDeviceChannel(String deviceChannel) {
		this.deviceChannel = deviceChannel;
	}
	public String getMerchantTransID() {
		return merchantTransID;
	}
	public void setMerchantTransID(String merchantTransID) {
		this.merchantTransID = merchantTransID;
	}
	public String getMessageVersion() {
		return messageVersion;
	}
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}
	public String getMessageCategory() {
		return messageCategory;
	}
	public void setMessageCategory(String messageCategory) {
		this.messageCategory = messageCategory;
	}
	public String getThreeDSServerTransID() {
		return threeDSServerTransID;
	}
	public void setThreeDSServerTransID(String threeDSServerTransID) {
		this.threeDSServerTransID = threeDSServerTransID;
	}
	public String getThreeDSRequestorID() {
		return threeDSRequestorID;
	}
	public void setThreeDSRequestorID(String threeDSRequestorID) {
		this.threeDSRequestorID = threeDSRequestorID;
	}
	public String getThreeDSRequestorName() {
		return threeDSRequestorName;
	}
	public void setThreeDSRequestorName(String threeDSRequestorName) {
		this.threeDSRequestorName = threeDSRequestorName;
	}
	public String getThreeDSRequestorURL() {
		return threeDSRequestorURL;
	}
	public void setThreeDSRequestorURL(String threeDSRequestorURL) {
		this.threeDSRequestorURL = threeDSRequestorURL;
	}
	public String getThreeDSCompInd() {
		return threeDSCompInd;
	}
	public void setThreeDSCompInd(String threeDSCompInd) {
		this.threeDSCompInd = threeDSCompInd;
	}
	public String getThreeDSRequestorAuthenticationInd() {
		return threeDSRequestorAuthenticationInd;
	}
	public void setThreeDSRequestorAuthenticationInd(String threeDSRequestorAuthenticationInd) {
		this.threeDSRequestorAuthenticationInd = threeDSRequestorAuthenticationInd;
	}
	public ThreeDSRequestorAuthenticationInfo getThreeDSRequestorAuthenticationInfo() {
		return threeDSRequestorAuthenticationInfo;
	}
	public void setThreeDSRequestorAuthenticationInfo(
			ThreeDSRequestorAuthenticationInfo threeDSRequestorAuthenticationInfo) {
		this.threeDSRequestorAuthenticationInfo = threeDSRequestorAuthenticationInfo;
	}
	public String getAcctNumber() {
		return acctNumber;
	}
	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public String getAddrMatch() {
		return addrMatch;
	}
	public void setAddrMatch(String addrMatch) {
		this.addrMatch = addrMatch;
	}
	public String getCardExpiryDate() {
		return cardExpiryDate;
	}
	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}
	public String getCardholderName() {
		return cardholderName;
	}
	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantUrl() {
		return merchantUrl;
	}
	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}
	public String getMerchantCountryCode() {
		return merchantCountryCode;
	}
	public void setMerchantCountryCode(String merchantCountryCode) {
		this.merchantCountryCode = merchantCountryCode;
	}
	public String getAcquirerBIN() {
		return acquirerBIN;
	}
	public void setAcquirerBIN(String acquirerBIN) {
		this.acquirerBIN = acquirerBIN;
	}
	public String getAcquirerID() {
		return acquirerID;
	}
	public void setAcquirerID(String acquirerID) {
		this.acquirerID = acquirerID;
	}
	public String getAcquirerMerchantID() {
		return acquirerMerchantID;
	}
	public void setAcquirerMerchantID(String acquirerMerchantID) {
		this.acquirerMerchantID = acquirerMerchantID;
	}
	public String getPurchaseCurrency() {
		return purchaseCurrency;
	}
	public void setPurchaseCurrency(String purchaseCurrency) {
		this.purchaseCurrency = purchaseCurrency;
	}
	public String getPurchaseExponent() {
		return purchaseExponent;
	}
	public void setPurchaseExponent(String purchaseExponent) {
		this.purchaseExponent = purchaseExponent;
	}
	public String getPurchaseAmount() {
		return purchaseAmount;
	}
	public void setPurchaseAmount(String purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public MobilePhone getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(MobilePhone mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public WorkPhone getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(WorkPhone workPhone) {
		this.workPhone = workPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public HomePhone getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(HomePhone homePhone) {
		this.homePhone = homePhone;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getThreeRIInd() {
		return threeRIInd;
	}
	public void setThreeRIInd(String threeRIInd) {
		this.threeRIInd = threeRIInd;
	}
	public String getThreeDSRequestorFinalAuthRespURL() {
		return threeDSRequestorFinalAuthRespURL;
	}
	public void setThreeDSRequestorFinalAuthRespURL(String threeDSRequestorFinalAuthRespURL) {
		this.threeDSRequestorFinalAuthRespURL = threeDSRequestorFinalAuthRespURL;
	}
	public String getBrowserAcceptHeader() {
		return browserAcceptHeader;
	}
	public void setBrowserAcceptHeader(String browserAcceptHeader) {
		this.browserAcceptHeader = browserAcceptHeader;
	}
	public String getBrowserIP() {
		return browserIP;
	}
	public void setBrowserIP(String browserIP) {
		this.browserIP = browserIP;
	}
	public boolean isBrowserJavaEnabled() {
		return browserJavaEnabled;
	}
	public void setBrowserJavaEnabled(boolean browserJavaEnabled) {
		this.browserJavaEnabled = browserJavaEnabled;
	}
	public boolean isBrowserJavascriptEnabled() {
		return browserJavascriptEnabled;
	}
	public void setBrowserJavascriptEnabled(boolean browserJavascriptEnabled) {
		this.browserJavascriptEnabled = browserJavascriptEnabled;
	}
	public String getBrowserLanguage() {
		return browserLanguage;
	}
	public void setBrowserLanguage(String browserLanguage) {
		this.browserLanguage = browserLanguage;
	}
	public String getBrowserColorDepth() {
		return browserColorDepth;
	}
	public void setBrowserColorDepth(String browserColorDepth) {
		this.browserColorDepth = browserColorDepth;
	}
	public String getBrowserScreenHeight() {
		return browserScreenHeight;
	}
	public void setBrowserScreenHeight(String browserScreenHeight) {
		this.browserScreenHeight = browserScreenHeight;
	}
	public String getBrowserScreenWidth() {
		return browserScreenWidth;
	}
	public void setBrowserScreenWidth(String browserScreenWidth) {
		this.browserScreenWidth = browserScreenWidth;
	}
	public String getBrowserTZ() {
		return browserTZ;
	}
	public void setBrowserTZ(String browserTZ) {
		this.browserTZ = browserTZ;
	}
	public String getBrowserUserAgent() {
		return browserUserAgent;
	}
	public void setBrowserUserAgent(String browserUserAgent) {
		this.browserUserAgent = browserUserAgent;
	}
	public String getP_messageVersion() {
		return p_messageVersion;
	}
	public void setP_messageVersion(String p_messageVersion) {
		this.p_messageVersion = p_messageVersion;
	}
	@Override
	public String toString() {
		return "PvrqAuthenticationRequest [messageType=" + messageType + ", deviceChannel=" + deviceChannel
				+ ", merchantTransID=" + merchantTransID + ", messageVersion=" + messageVersion + ", messageCategory="
				+ messageCategory + ", threeDSServerTransID=" + threeDSServerTransID + ", threeDSRequestorID="
				+ threeDSRequestorID + ", threeDSRequestorName=" + threeDSRequestorName + ", threeDSRequestorURL="
				+ threeDSRequestorURL + ", threeDSCompInd=" + threeDSCompInd + ", threeDSRequestorAuthenticationInd="
				+ threeDSRequestorAuthenticationInd + ", threeDSRequestorAuthenticationInfo="
				+ threeDSRequestorAuthenticationInfo + ", acctNumber=" + Util.maskStringValue(acctNumber) + ", acctType=" + acctType
				+ ", addrMatch=" + addrMatch + ", cardExpiryDate=" +  Util.maskStringValue(cardExpiryDate) + ", cardholderName="
				+  Util.maskStringValue(cardholderName) + ", mcc=" + mcc + ", merchantName=" + merchantName + ", merchantUrl=" + merchantUrl
				+ ", merchantCountryCode=" + merchantCountryCode + ", acquirerBIN=" + acquirerBIN + ", acquirerID="
				+ acquirerID + ", acquirerMerchantID=" + acquirerMerchantID + ", purchaseCurrency=" + purchaseCurrency
				+ ", purchaseExponent=" + purchaseExponent + ", purchaseAmount=" + purchaseAmount + ", purchaseDate="
				+ purchaseDate + ", mobilePhone=" + mobilePhone + ", workPhone=" + workPhone + ", email=" + email
				+ ", homePhone=" + homePhone + ", transType=" + transType + ", threeRIInd=" + threeRIInd
				+ ", threeDSRequestorFinalAuthRespURL=" + threeDSRequestorFinalAuthRespURL + ", browserAcceptHeader="
				+ browserAcceptHeader + ", browserIP=" + browserIP + ", browserJavaEnabled=" + browserJavaEnabled
				+ ", browserJavascriptEnabled=" + browserJavascriptEnabled + ", browserLanguage=" + browserLanguage
				+ ", browserColorDepth=" + browserColorDepth + ", browserScreenHeight=" + browserScreenHeight
				+ ", browserScreenWidth=" + browserScreenWidth + ", browserTZ=" + browserTZ + ", browserUserAgent="
				+ browserUserAgent + ", p_messageVersion=" + p_messageVersion + "]";
	}
	
    
}
