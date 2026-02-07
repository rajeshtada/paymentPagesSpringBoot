package com.ftk.pg.responsevo;

public class IciciCallbackStatusResponse {
	private String Response;
	private String merchantId;
	private String subMerchantId;
	private String terminalId;
	private String success;
	private String message;
	private String merchantTranId;
	private String OriginalBankRRN;
	private String PayerVA;
	private String Amount;
	private String status;
	private String TxnInitDate;
	private String TxnCompletionDate;
	private String refundRRN;
	public String getResponse() {
		return Response;
	}
	public void setResponse(String response) {
		Response = response;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getSubMerchantId() {
		return subMerchantId;
	}
	public void setSubMerchantId(String subMerchantId) {
		this.subMerchantId = subMerchantId;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMerchantTranId() {
		return merchantTranId;
	}
	public void setMerchantTranId(String merchantTranId) {
		this.merchantTranId = merchantTranId;
	}
	public String getOriginalBankRRN() {
		return OriginalBankRRN;
	}
	public void setOriginalBankRRN(String originalBankRRN) {
		OriginalBankRRN = originalBankRRN;
	}
	public String getPayerVA() {
		return PayerVA;
	}
	public void setPayerVA(String payerVA) {
		PayerVA = payerVA;
	}
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTxnInitDate() {
		return TxnInitDate;
	}
	public void setTxnInitDate(String txnInitDate) {
		TxnInitDate = txnInitDate;
	}
	public String getTxnCompletionDate() {
		return TxnCompletionDate;
	}
	public void setTxnCompletionDate(String txnCompletionDate) {
		TxnCompletionDate = txnCompletionDate;
	}
	public String getRefundRRN() {
		return refundRRN;
	}
	public void setRefundRRN(String refundRRN) {
		this.refundRRN = refundRRN;
	}
	@Override
	public String toString() {
		return "IciciCallbackStatusResponse [Response=" + Response + ", merchantId=" + merchantId + ", subMerchantId="
				+ subMerchantId + ", terminalId=" + terminalId + ", success=" + success + ", message=" + message
				+ ", merchantTranId=" + merchantTranId + ", OriginalBankRRN=" + OriginalBankRRN + ", PayerVA=" + PayerVA
				+ ", Amount=" + Amount + ", status=" + status + ", TxnInitDate=" + TxnInitDate + ", TxnCompletionDate="
				+ TxnCompletionDate + ", refundRRN=" + refundRRN + "]";
	}
	
	

}
