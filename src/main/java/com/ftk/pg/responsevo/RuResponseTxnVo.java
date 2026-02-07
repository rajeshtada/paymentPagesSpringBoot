package com.ftk.pg.responsevo;

import com.ftk.pg.modal.TransactionLog;

import lombok.ToString;

@ToString
public class RuResponseTxnVo {

	private String responseCode;
	private String txnStatus;
	private String stage;
	private String processorTxnId;
	private String processorCode;
	private String orderNumber;
	private String bankErrorMsg;
	private TransactionLog transctionDetail;
	private boolean txnDoubleVerifiedFlag = false;

	public boolean isTxnDoubleVerifiedFlag() {
		return txnDoubleVerifiedFlag;
	}

	public void setTxnDoubleVerifiedFlag(boolean txnVerifiedFalg) {
		this.txnDoubleVerifiedFlag = txnVerifiedFalg;
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

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getProcessorTxnId() {
		return processorTxnId;
	}

	public void setProcessorTxnId(String processorTxnId) {
		this.processorTxnId = processorTxnId;
	}

	public String getProcessorCode() {
		return processorCode;
	}

	public void setProcessorCode(String processorCode) {
		this.processorCode = processorCode;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getBankErrorMsg() {
		return bankErrorMsg;
	}

	public void setBankErrorMsg(String bankErrorMsg) {
		this.bankErrorMsg = bankErrorMsg;
	}

	public TransactionLog getTransctionDetail() {
		return transctionDetail;
	}

	public void setTransctionDetail(TransactionLog transctionDetail) {
		this.transctionDetail = transctionDetail;
	}

}
