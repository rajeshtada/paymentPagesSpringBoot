package com.ftk.pg.requestvo;

public class BillDeskRequestHeader {
	private String contentType;
	private String accept;
	private String bdtraceId;
	private String bdTimeStramp;
	public String getBdtraceId() {
		return bdtraceId;
	}
	public void setBdtraceId(String bdtraceId) {
		this.bdtraceId = bdtraceId;
	}
	public String getBdTimeStramp() {
		return bdTimeStramp;
	}
	public void setBdTimeStramp(String bdTimeStramp) {
		this.bdTimeStramp = bdTimeStramp;
	}
	
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getAccept() {
		return accept;
	}
	public void setAccept(String accept) {
		this.accept = accept;
	}
	@Override
	public String toString() {
		return "BillDeskRequestHeader [contentType=" + contentType + ", accept=" + accept + ", bdtraceId=" + bdtraceId
				+ ", bdTimeStramp=" + bdTimeStramp + "]";
	}
	
	
	

}
