package com.ftk.pg.util;
public class ResendOTPResponse {
	
	private String status;
	private String validityPeriod;
	private String errorcode;
	private String errormsg;	
	private String pgTransactionId;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getValidityPeriod() {
		return validityPeriod;
	}
	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	public String getPgTransactionId() {
		return pgTransactionId;
	}
	public void setPgTransactionId(String pgTransactionId) {
		this.pgTransactionId = pgTransactionId;
	}
	@Override
	public String toString() {
		return "ResendOTPResponse [status=" + status + ", validityPeriod=" + validityPeriod + ", errorcode=" + errorcode
				+ ", errormsg=" + errormsg + ", pgTransactionId=" + pgTransactionId + "]";
	}
	
	

}