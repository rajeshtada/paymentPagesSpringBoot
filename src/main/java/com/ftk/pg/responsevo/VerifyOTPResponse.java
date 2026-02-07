package com.ftk.pg.responsevo;

public class VerifyOTPResponse {
	private String pgTransactionId;
	private String status;
	private String errorcode;
	
	private String errormsg;
	private String tranCtxId;
	private String authResponseCode;
	public String getPgTransactionId() {
		return pgTransactionId;
	}
	public void setPgTransactionId(String pgTransactionId) {
		this.pgTransactionId = pgTransactionId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getTranCtxId() {
		return tranCtxId;
	}
	public void setTranCtxId(String tranCtxId) {
		this.tranCtxId = tranCtxId;
	}
	public String getAuthResponseCode() {
		return authResponseCode;
	}
	public void setAuthResponseCode(String authResponseCode) {
		this.authResponseCode = authResponseCode;
	}
	@Override
	public String toString() {
		return "VerifyOTPResponse [pgTransactionId=" + pgTransactionId + ", status=" + status + ", errorcode="
				+ errorcode + ", errormsg=" + errormsg + ", tranCtxId=" + tranCtxId + ", authResponseCode="
				+ authResponseCode + "]";
	}
	
	
	
	
}
