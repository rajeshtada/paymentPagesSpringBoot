package com.ftk.pg.dto;

public class BankTxnResponseVo {

//	transactionDetails.setStage(stage);
//	transactionDetails.setProcessorCode(bankReponse);
//	transactionDetails.setResponseCode(responseCode);
//	transactionDetails.setTxnStatus(txnStatus);
//	transactionDetails.setOrderNumber(processorTransactionId);
	
	public Long transactionId;
	public String stage;
	public String processorCode;
	public String responseCode;
	public String txnStatus;
	public String orderNumber;
	public String bankErrorMsg;
	
	public String getBankErrorMsg() {
		return bankErrorMsg;
	}
	public void setBankErrorMsg(String bankErrorMsg) {
		this.bankErrorMsg = bankErrorMsg;
	}
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getProcessorCode() {
		return processorCode;
	}
	public void setProcessorCode(String processorCode) {
		this.processorCode = processorCode;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getTxnStatus() {
		return txnStatus;
	}
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	@Override
	public String toString() {
		return "BankTransactionVo [transactionId=" + transactionId + ", stage=" + stage + ", processorCode="
				+ processorCode + ", responseCode=" + responseCode + ", txnStatus=" + txnStatus + ", orderNumber="
				+ orderNumber + "]";
	}
	
	
}
