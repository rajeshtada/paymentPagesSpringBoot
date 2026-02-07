package com.ftk.pg.responsevo;

public class SbiTransactionStatusResponse {

	 
	 private String transactionId;
	 private String	status;
	 private String pgErrorCode;
	 private String pgErrorDetail;
	 
	 
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPgErrorCode() {
		return pgErrorCode;
	}
	public void setPgErrorCode(String pgErrorCode) {
		this.pgErrorCode = pgErrorCode;
	}
	public String getPgErrorDetail() {
		return pgErrorDetail;
	}
	public void setPgErrorDetail(String pgErrorDetail) {
		this.pgErrorDetail = pgErrorDetail;
	}
	
	
	@Override
	public String toString() {
		return "SbiTransactionStatusResponse [transactionId=" + transactionId + ", status=" + status + ", pgErrorCode="
				+ pgErrorCode + ", pgErrorDetail=" + pgErrorDetail + "]";
	}

}
