package com.ftk.pg.dto;

import java.math.BigDecimal;

import com.ftk.pg.modal.CardBean;
import com.ftk.pg.util.Util;

public class PaymentRequest {

	private String login;
	private String pass;
	private BigDecimal amt;
	private String paymentMode;
	private String txncurr;
	private String merchantTxnId;
	private String date;
	private String od;
	private String mobile;
	private String carddata;
	private String ru;
	private String signature;
	private String bankid;
	private Long transactionId;
	private String requestType;
	private String paymentType;
	private String mEmail;
	private String mNumber;
	private String productType;
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String txnType;
	private String productDetails;
	private String van;
	private String name;
	private String ipAddress;

	private Long merchantId;
	
	private CardBean cardBean;

	
	public CardBean getCardBean() {
		return cardBean;
	}

	public void setCardBean(CardBean cardBean) {
		this.cardBean = cardBean;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
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

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOd() {
		return od;
	}

	public void setOd(String od) {
		this.od = od;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCarddata() {
		return carddata;
	}

	public void setCarddata(String carddata) {
		this.carddata = carddata;
	}

	public String getRu() {
		return ru;
	}

	public void setRu(String ru) {
		this.ru = ru;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getmEmail() {
		return mEmail;
	}

	public void setmEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	public String getmNumber() {
		return mNumber;
	}

	public void setmNumber(String mNumber) {
		this.mNumber = mNumber;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	@Override
	public String toString() {
		return "PaymentRequest [login=" + login + ", pass=" + pass + ", amt=" + amt + ", paymentMode=" + paymentMode
				+ ", txncurr=" + txncurr + ", merchantTxnId=" + merchantTxnId + ", date=" + date + ", od=" + od
				+ ", mobile=" + Util.maskStringValue(mobile) + ", carddata=" + Util.maskStringValue(carddata) + ", ru="
				+ ru + ", signature=" + signature + ", bankid=" + bankid + ", transactionId=" + transactionId
				+ ", requestType=" + requestType + ", paymentType=" + paymentType + ", mEmail="
				+ Util.maskEmailStringValue(mEmail) + ", mNumber=" + Util.maskStringValue(mNumber) + ", productType="
				+ productType + ", merchantId=" + merchantId + ", txnType=" + txnType + ", productDetails="
				+ productDetails + ", van=" + van + ", name=" + name + ", udf1=" + udf1 + ", udf2="
				+ Util.maskStringValue(udf2) + ", udf3=" + Util.maskEmailStringValue(udf3) + ", udf4=" + udf4
				+ ", udf5=" + udf5 + ", ipAddress=" + ipAddress + "]";
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public String getVan() {
		return van;
	}

	public void setVan(String van) {
		this.van = van;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
