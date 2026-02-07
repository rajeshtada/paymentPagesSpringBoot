package com.ftk.pg.responsevo;

import java.math.RoundingMode;

import com.ftk.pg.modal.TransactionLog;


public class RequeryResponse {

	private String amount;
	private String txnId;
	private String respCode;
	private String transactionStatus;
	private String paymentType;
	private String date;
	private String mobile;
	private String transactionCurrency;
	private boolean result;
	

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}


	@Override
	public String toString() {
		return "RequeryResponse [amount=" + amount + ", txnId=" + txnId + ", respCode=" + respCode
				+ ", transactionStatus=" + transactionStatus + ", paymentType=" + paymentType + ", date=" + date
				+ ", mobile=" + mobile + ", transactionCurrency=" + transactionCurrency + ", result=" + result
				 + "]";
	}
	
	public static RequeryResponse fromTransactionLog(TransactionLog txnLog) {

	
		RequeryResponse response = new RequeryResponse();
		response.setAmount(txnLog.getAmt().setScale(2, RoundingMode.HALF_UP).toPlainString());
		response.setTxnId(String.valueOf(txnLog.getTransactionId()));
		if(txnLog.getTxnStatus() != null && txnLog.getTxnStatus().equalsIgnoreCase("SUCCESS")) {
			response.setRespCode("00");
		} else if(txnLog.getTxnStatus() != null && txnLog.getTxnStatus().equalsIgnoreCase("FAILED")) {
			response.setRespCode("02");
		} else {
			response.setRespCode("01");
		}
		
		response.setTransactionStatus(txnLog.getTxnStatus());
		response.setPaymentType(txnLog.getPaymentMode());
		response.setDate(txnLog.getDate());
		response.setTransactionCurrency(txnLog.getTxncurr());
		if(txnLog.getMobile() != null) {
			response.setMobile(txnLog.getMobile());
		} else {
			response.setMobile("");
		}
		response.setResult(true);
		return response;		
	}

	
}
