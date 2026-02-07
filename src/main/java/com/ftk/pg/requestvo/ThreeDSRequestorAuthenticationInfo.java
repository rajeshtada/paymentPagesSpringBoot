package com.ftk.pg.requestvo;

public class ThreeDSRequestorAuthenticationInfo {
	
	private String threeDSReqAuthMethod;
	
	private String threeDSReqAuthTimestamp;
	private String threeDSReqAuthData;
	public String getThreeDSReqAuthMethod() {
		return threeDSReqAuthMethod;
	}
	public void setThreeDSReqAuthMethod(String threeDSReqAuthMethod) {
		this.threeDSReqAuthMethod = threeDSReqAuthMethod;
	}
	public String getThreeDSReqAuthTimestamp() {
		return threeDSReqAuthTimestamp;
	}
	public void setThreeDSReqAuthTimestamp(String threeDSReqAuthTimestamp) {
		this.threeDSReqAuthTimestamp = threeDSReqAuthTimestamp;
	}
	public String getThreeDSReqAuthData() {
		return threeDSReqAuthData;
	}
	public void setThreeDSReqAuthData(String threeDSReqAuthData) {
		this.threeDSReqAuthData = threeDSReqAuthData;
	}
	
}
