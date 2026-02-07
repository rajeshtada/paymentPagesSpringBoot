package com.ftk.pg.responsevo;

public class Initiate2Response {

	private String pgTransactionId;
	private String rupayTransactionId;
	private String redirectURL;
	private String accuRequestId;
	private String session;
	private String status;
	private String errorcode;
	private String errormsg;
	public String getPgTransactionId() {
		return pgTransactionId;
	}
	public void setPgTransactionId(String pgTransactionId) {
		this.pgTransactionId = pgTransactionId;
	}
	public String getRupayTransactionId() {
		return rupayTransactionId;
	}
	public void setRupayTransactionId(String rupayTransactionId) {
		this.rupayTransactionId = rupayTransactionId;
	}
	public String getRedirectURL() {
		return redirectURL;
	}
	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}
	public String getAccuRequestId() {
		return accuRequestId;
	}
	public void setAccuRequestId(String accuRequestId) {
		this.accuRequestId = accuRequestId;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
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
	@Override
	public String toString() {
		return "Initiate2Response [pgTransactionId=" + pgTransactionId + ", rupayTransactionId=" + rupayTransactionId
				+ ", redirectURL=" + redirectURL + ", accuRequestId=" + accuRequestId + ", session=" + session
				+ ", status=" + status + ", errorcode=" + errorcode + ", errormsg=" + errormsg + "]";
	}
	
	
}
