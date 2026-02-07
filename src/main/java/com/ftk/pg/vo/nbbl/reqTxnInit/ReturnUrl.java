package com.ftk.pg.vo.nbbl.reqTxnInit;

public class ReturnUrl {
	public String success;
	public String failure;
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getFailure() {
		return failure;
	}
	public void setFailure(String failure) {
		this.failure = failure;
	}
	@Override
	public String toString() {
		return "ReturnUrl [success=" + success + ", failure=" + failure + "]";
	}
	
	
}