package com.ftk.pg.requestvo;

public class RedirectionRequestParameters {
	private String AccuCardholderId;
	private String AccuGuid;
	private String AccuReturnURL;
	private String session;
	private String AccuRequestId;
	public String getAccuCardholderId() {
		return AccuCardholderId;
	}
	public void setAccuCardholderId(String accuCardholderId) {
		AccuCardholderId = accuCardholderId;
	}
	public String getAccuGuid() {
		return AccuGuid;
	}
	public void setAccuGuid(String accuGuid) {
		AccuGuid = accuGuid;
	}
	public String getAccuReturnURL() {
		return AccuReturnURL;
	}
	public void setAccuReturnURL(String accuReturnURL) {
		AccuReturnURL = accuReturnURL;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getAccuRequestId() {
		return AccuRequestId;
	}
	public void setAccuRequestId(String accuRequestId) {
		AccuRequestId = accuRequestId;
	}
	@Override
	public String toString() {
		return "RedirectionRequestParameters [AccuCardholderId=" + AccuCardholderId + ", AccuGuid=" + AccuGuid
				+ ", AccuReturnURL=" + AccuReturnURL + ", session=" + session + ", AccuRequestId=" + AccuRequestId
				+ "]";
	}
	
	
	
	

}
