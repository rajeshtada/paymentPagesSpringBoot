package com.ftk.pg.vo.sbiNb;

public class SbiRefundVoidResponse {
	
 private String	status;
 private String pgErrorCode;
 private String pgErrorDetail;
 private String transactionId;
 private String rrn;
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
public String getTransactionId() {
	return transactionId;
}
public void setTransactionId(String transactionId) {
	this.transactionId = transactionId;
}
public String getRrn() {
	return rrn;
}
public void setRrn(String rrn) {
	this.rrn = rrn;
}
@Override
public String toString() {
	return "RefundVoidResponse [status=" + status + ", pgErrorCode=" + pgErrorCode + ", pgErrorDetail=" + pgErrorDetail
			+ ", transactionId=" + transactionId + ", rrn=" + rrn + "]";
}
 
}
