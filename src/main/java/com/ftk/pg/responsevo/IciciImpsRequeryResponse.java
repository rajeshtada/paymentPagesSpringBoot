package com.ftk.pg.responsevo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IciciImpsRequeryResponse {

	@JsonProperty(value = "ActCode")
	private String actCode;
	
	@JsonProperty(value = "Response")
	private String response;
	
	@JsonProperty(value = "BankRRN")
	private String bankRrn;
	
	@JsonProperty(value = "BeneName")
	private String beneName;
	
	@JsonProperty(value = "TranRefNo")
	private String tranRefNo;
	
	@JsonProperty(value = "PaymentRef")
	private String paymentRef;
	
	@JsonProperty(value = "TranDateTime")
	private String tranDateTime;
	
	@JsonProperty(value = "Amount")
	private String amount;
	
	@JsonProperty(value = "BeneMMID")
	private String beneMid;
	
	@JsonProperty(value = "BeneMobile")
	private String beneMobile;
	
	@JsonProperty(value = "BeneAccNo")
	private String beneAccountNo;

	
	@JsonProperty(value = "BeneIFSC")
	private String beneIfsc;
	
	
	@JsonProperty(value = "RemMobile")
	private String remMobile;
	
	
	@JsonProperty(value = "RemName")
	private String remName;
	
	
	@JsonProperty(value = "RetailerCode")
	private String retailerCode;
	
	

	public String getActCode() {
		return actCode;
	}

	public void setActCode(String actCode) {
		this.actCode = actCode;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getBankRrn() {
		return bankRrn;
	}

	public void setBankRrn(String bankRrn) {
		this.bankRrn = bankRrn;
	}

	public String getBeneName() {
		return beneName;
	}

	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}

	public String getTranRefNo() {
		return tranRefNo;
	}

	public void setTranRefNo(String tranRefNo) {
		this.tranRefNo = tranRefNo;
	}

	public String getPaymentRef() {
		return paymentRef;
	}

	public void setPaymentRef(String paymentRef) {
		this.paymentRef = paymentRef;
	}

	public String getTranDateTime() {
		return tranDateTime;
	}

	public void setTranDateTime(String tranDateTime) {
		this.tranDateTime = tranDateTime;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBeneMid() {
		return beneMid;
	}

	public void setBeneMid(String beneMid) {
		this.beneMid = beneMid;
	}

	public String getBeneMobile() {
		return beneMobile;
	}

	public void setBeneMobile(String beneMobile) {
		this.beneMobile = beneMobile;
	}

	public String getBeneAccountNo() {
		return beneAccountNo;
	}

	public void setBeneAccountNo(String beneAccountNo) {
		this.beneAccountNo = beneAccountNo;
	}

	
	public String getBeneIfsc() {
		return beneIfsc;
	}

	public void setBeneIfsc(String beneIfsc) {
		this.beneIfsc = beneIfsc;
	}

	public String getRemMobile() {
		return remMobile;
	}

	public void setRemMobile(String remMobile) {
		this.remMobile = remMobile;
	}

	public String getRemName() {
		return remName;
	}

	public void setRemName(String remName) {
		this.remName = remName;
	}

	public String getRetailerCode() {
		return retailerCode;
	}

	public void setRetailerCode(String retailerCode) {
		this.retailerCode = retailerCode;
	}
	
	
}
