package com.ftk.pg.requestvo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IciciCollectRequest {


	private String payerVa;
	//@JsonProperty(value = "Amount")
	private String amount;
	private String note;
	private String collectByDate;
	private String merchantId;
	private String merchantName;
	private String subMerchantId;
	private String subMerchantName;
	private String terminalId;
	private String merchantTranId;
	private String billNumber;
	private String validatePayerAccFlag;
	private String payerAccount;
	private String payerIFSC;
	
	public String getPayerVa() {
		return payerVa;
	}
	public void setPayerVa(String payerVa) {
		this.payerVa = payerVa;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCollectByDate() {
		return collectByDate;
	}
	public void setCollectByDate(String collectByDate) {
		this.collectByDate = collectByDate;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getSubMerchantId() {
		return subMerchantId;
	}
	public void setSubMerchantId(String subMerchantId) {
		this.subMerchantId = subMerchantId;
	}
	public String getSubMerchantName() {
		return subMerchantName;
	}
	public void setSubMerchantName(String subMerchantName) {
		this.subMerchantName = subMerchantName;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getMerchantTranId() {
		return merchantTranId;
	}
	public void setMerchantTranId(String merchantTranId) {
		this.merchantTranId = merchantTranId;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public String getValidatePayerAccFlag() {
		return validatePayerAccFlag;
	}
	public void setValidatePayerAccFlag(String validatePayerAccFlag) {
		this.validatePayerAccFlag = validatePayerAccFlag;
	}
	public String getPayerAccount() {
		return payerAccount;
	}
	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}
	public String getPayerIFSC() {
		return payerIFSC;
	}
	public void setPayerIFSC(String payerIFSC) {
		this.payerIFSC = payerIFSC;
	}
	
	
	
}
