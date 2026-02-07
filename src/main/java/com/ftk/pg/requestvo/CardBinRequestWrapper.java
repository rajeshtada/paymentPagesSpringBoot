package com.ftk.pg.requestvo;

public class CardBinRequestWrapper {

	private String mid;
    private String method;
	private String request;
	private String signature;
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	@Override
	public String toString() {
		return "CardBinRequest [mid=" + mid + ", method=" + method + ", request=" + request + ", signature=" + signature
				+ "]";
	}
	
	
	
	
}
