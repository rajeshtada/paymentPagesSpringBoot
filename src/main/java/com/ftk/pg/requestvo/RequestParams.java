package com.ftk.pg.requestvo;

import com.ftk.pg.util.Utilities;

public class RequestParams {

	private String login;
	private String pass;
	private String amt;
	private String paymentMode;
	private String txncurr;
	private String merchantTxnId;
	private String mobile;
	private String nbmobile;
	private String number;
	private String cardType;
	private String expiry;
	private String cvc;
	private String name;
	private String nbbankid;
	private String walletbankid;
	private String upiId;
	private String wMobile;
	private Long transactionId;
	private String productType;
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String ru;

	private String month;
	private String year;

	private String neftName;
	private String neftNumber;

	private String browserLanguage;
	private String browserColorDepth;
	private String browserScreenHeight;
	private String browserScreenWidth;
	private String browserTZ;
	private String javaEnabled;
	private String jsEnabled;

	private String ipAddress;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getwMobile() {
		return wMobile;
	}

	public void setwMobile(String wMobile) {
		this.wMobile = wMobile;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getTxncurr() {
		return txncurr;
	}

	public void setTxncurr(String txncurr) {
		this.txncurr = txncurr;
	}

	public String getMerchantTxnId() {
		return merchantTxnId;
	}

	public void setMerchantTxnId(String merchantTxnId) {
		this.merchantTxnId = merchantTxnId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getCvc() {
		return cvc;
	}

	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNbbankid() {
		return nbbankid;
	}

	public void setNbbankid(String nbbankid) {
		this.nbbankid = nbbankid;
	}

	public String getWalletbankid() {
		return walletbankid;
	}

	public void setWalletbankid(String walletbankid) {
		this.walletbankid = walletbankid;
	}

	public String getUpiId() {
		return upiId;
	}

	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}

	public String getNbmobile() {
		return nbmobile;
	}

	public void setNbmobile(String nbmobile) {
		this.nbmobile = nbmobile;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getUdf1() {
		return udf1;
	}

	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	public String getUdf2() {
		return udf2;
	}

	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}

	public String getUdf3() {
		return udf3;
	}

	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}

	public String getUdf4() {
		return udf4;
	}

	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}

	public String getUdf5() {
		return udf5;
	}

	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}

	public String getRu() {
		return ru;
	}

	public void setRu(String ru) {
		this.ru = ru;
	}

	@Override
	public String toString() {
		return "RequestParams [login=" + login + ", pass=" + pass + ", amt=" + amt + ", paymentMode=" + paymentMode
				+ ", txncurr=" + txncurr + ", merchantTxnId=" + merchantTxnId + ", mobile=" + Utilities.maskStringValue(mobile) + ", nbmobile="
				+Utilities.maskStringValue(nbmobile) + ", number=" + Utilities.maskStringValue(number) + ", cardType=" + Utilities.maskStringValue(cardType) + ", expiry=" + Utilities.maskStringValue(expiry) + ", cvc=" + Utilities.maskStringValue(cvc)
				+ ", name=" + name + ", nbbankid=" + nbbankid + ", walletbankid=" + walletbankid + ", upiId=" + upiId
				+ ", wMobile=" + wMobile + ", transactionId=" + transactionId + ", productType=" + productType
				+ ", udf1=" + udf1 + ", udf2=" + Utilities.maskStringValue(udf2) + ", udf3=" +  Utilities.maskEmailStringValue(udf3) + ", udf4=" + udf4 + ", udf5=" + udf5 + ", ru="
				+ ru + ", month=" + Utilities.maskStringValue(month) + ", year=" + Utilities.maskStringValue(year) + ", neftName=" + neftName + ", neftNumber=" + neftNumber
				+ ", browserLanguage=" + browserLanguage + ", browserColorDepth=" + browserColorDepth
				+ ", browserScreenHeight=" + browserScreenHeight + ", browserScreenWidth=" + browserScreenWidth
				+ ", browserTZ=" + browserTZ + ", javaEnabled=" + javaEnabled + ", jsEnabled=" + jsEnabled
				+ ", ipAddress=" + ipAddress + "]";
	}

	public String getNeftName() {
		return neftName;
	}

	public void setNeftName(String neftName) {
		this.neftName = neftName;
	}

	public String getNeftNumber() {
		return neftNumber;
	}

	public void setNeftNumber(String neftNumber) {
		this.neftNumber = neftNumber;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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

	public String getJavaEnabled() {
		return javaEnabled;
	}

	public void setJavaEnabled(String javaEnabled) {
		this.javaEnabled = javaEnabled;
	}

	public String getJsEnabled() {
		return jsEnabled;
	}

	public void setJsEnabled(String jsEnabled) {
		this.jsEnabled = jsEnabled;
	}

	public String getBrowserData() {
		return browserLanguage + "|" + browserColorDepth + "|" + browserScreenHeight + "|" + browserScreenWidth + "|"
				+ browserTZ + "|" + javaEnabled + "|" + jsEnabled;

	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
}
