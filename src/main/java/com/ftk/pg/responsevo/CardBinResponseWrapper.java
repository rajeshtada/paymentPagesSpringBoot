package com.ftk.pg.responsevo;

public class CardBinResponseWrapper {
	private String mid;
	private String method;
	private String response;
	private String status;
	private String message;
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
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	@Override
	public String toString() {
		return "CardBinResponse [mid=" + mid + ", method=" + method + ", response=" + response + ", status=" + status
				+ ", message=" + message + ", signature=" + signature + "]";
	}
	
	
}
