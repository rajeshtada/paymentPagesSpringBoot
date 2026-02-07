package com.ftk.pg.requestvo;

public class QrRequest {

	private String amount;
	
	private String merchantId;
	
	private String terminalId;
	
	private String merchantTranId;
	
	private String billNumber;
	
	private String validatePayerAccFlag;
	
	private String payerAccount;
	
	private String payerIFSC;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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

	@Override
	public String toString() {
		return "QrRequest [amount=" + amount + ", merchantId=" + merchantId + ", terminalId=" + terminalId
				+ ", merchantTranId=" + merchantTranId + ", billNumber=" + billNumber + ", validatePayerAccFlag="
				+ validatePayerAccFlag + ", payerAccount=" + payerAccount + ", payerIFSC=" + payerIFSC + "]";
	}
}
