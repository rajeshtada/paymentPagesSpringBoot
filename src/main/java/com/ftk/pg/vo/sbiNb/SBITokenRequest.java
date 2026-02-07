package com.ftk.pg.vo.sbiNb;
public class SBITokenRequest {
	
	private String encryptedData;
	private String iv;
	private String clientReferenceId;
	private String merchantId;
	private String acquirerMerchantId;
	private String acquirerInstanceId;
	private String cardType;
	private String authCode;
	private String provider;
	private String amount;
	private String currency;
	private String consumerId;
	private String tokenRequestorId;
	private String userConsent;
	private String userConsentTimestamp;
	private String merchantName;
	private String var1;
	private String var2;
	private String var3;
	
	public String getEncryptedData() {
		return encryptedData;
	}
	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}
	public String getIv() {
		return iv;
	}
	public void setIv(String iv) {
		this.iv = iv;
	}
	public String getClientReferenceId() {
		return clientReferenceId;
	}
	public void setClientReferenceId(String clientReferenceId) {
		this.clientReferenceId = clientReferenceId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getAcquirerMerchantId() {
		return acquirerMerchantId;
	}
	public void setAcquirerMerchantId(String acquirerMerchantId) {
		this.acquirerMerchantId = acquirerMerchantId;
	}
	public String getAcquirerInstanceId() {
		return acquirerInstanceId;
	}
	public void setAcquirerInstanceId(String acquirerInstanceId) {
		this.acquirerInstanceId = acquirerInstanceId;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public String getTokenRequestorId() {
		return tokenRequestorId;
	}
	public void setTokenRequestorId(String tokenRequestorId) {
		this.tokenRequestorId = tokenRequestorId;
	}
	public String getUserConsent() {
		return userConsent;
	}
	public void setUserConsent(String userConsent) {
		this.userConsent = userConsent;
	}
	public String getUserConsentTimestamp() {
		return userConsentTimestamp;
	}
	public void setUserConsentTimestamp(String userConsentTimestamp) {
		this.userConsentTimestamp = userConsentTimestamp;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getVar1() {
		return var1;
	}
	public void setVar1(String var1) {
		this.var1 = var1;
	}
	public String getVar2() {
		return var2;
	}
	public void setVar2(String var2) {
		this.var2 = var2;
	}
	public String getVar3() {
		return var3;
	}
	public void setVar3(String var3) {
		this.var3 = var3;
	}
	
	@Override
	public String toString() {
		return "SBITokenRequest [encryptedData=" + encryptedData + ", iv=" + iv + ", clientReferenceId="
				+ clientReferenceId + ", merchantId=" + merchantId + ", acquirerMerchantId=" + acquirerMerchantId
				+ ", acquirerInstanceId=" + acquirerInstanceId + ", cardType=" + cardType + ", authCode=" + authCode
				+ ", provider=" + provider + ", amount=" + amount + ", currency=" + currency + ", consumerId="
				+ consumerId + ", tokenRequestorId=" + tokenRequestorId + ", userConsent=" + userConsent
				+ ", userConsentTimestamp=" + userConsentTimestamp + ", merchantName=" + merchantName + ", var1=" + var1
				+ ", var2=" + var2 + ", var3=" + var3 + "]";
	}
	
	
	
	
	
	
}
