package com.ftk.pg.requestvo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IciciCompositPayRTGSRequest {

	@JsonProperty(value = "AGGRID")
	private String aggrId;
	
	@JsonProperty(value = "CORPID")
	private String corpId;
	
	@JsonProperty(value = "USERID")
	private String userId;
	
	@JsonProperty(value = "URN")
	private String urn;
	
	@JsonProperty(value = "AGGRNAME")
	private String aggrName;
	
	@JsonProperty(value = "UNIQUEID")
	private String uniqueId;
	
	@JsonProperty(value = "DEBITACC")
	private String debitAcc;
	
	@JsonProperty(value = "CREDITACC")
	private String creditAcc;
	
	@JsonProperty(value = "IFSC")
	private String ifsc;
	
	@JsonProperty(value = "AMOUNT")
	private String amount;
	
	@JsonProperty(value = "CURRENCY")
	private String currency;
	
	@JsonProperty(value = "TXNTYPE")
	private String txnType;
	
	@JsonProperty(value = "PAYEENAME")
	private String payeeName;
	
	@JsonProperty(value = "REMARKS")
	private String remarks;

	public String getAggrId() {
		return aggrId;
	}

	public void setAggrId(String aggrId) {
		this.aggrId = aggrId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public String getAggrName() {
		return aggrName;
	}

	public void setAggrName(String aggrName) {
		this.aggrName = aggrName;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getDebitAcc() {
		return debitAcc;
	}

	public void setDebitAcc(String debitAcc) {
		this.debitAcc = debitAcc;
	}

	public String getCreditAcc() {
		return creditAcc;
	}

	public void setCreditAcc(String creditAcc) {
		this.creditAcc = creditAcc;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
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

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}
