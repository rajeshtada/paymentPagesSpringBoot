package com.ftk.pg.responsevo;

public class AuthorizeApiResponse {
	private String transactionId;
	private String status;
	private String pgErrorCode;
	private String pgErrorDetail;
	private String approvalCode;
	private String rrn;
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
	public String getApprovalCode() {
		return approvalCode;
	}
	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	@Override
	public String toString() {
		return "ReverseApiResponse [transactionId=" + transactionId + ", status=" + status + ", pgErrorCode="
				+ pgErrorCode + ", pgErrorDetail=" + pgErrorDetail + ", approvalCode=" + approvalCode + ", rrn=" + rrn
				+ "]";
	}
	
	

}
