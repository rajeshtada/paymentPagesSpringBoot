package com.ftk.pg.responsevo;

import java.io.Serializable;

public class DynamicQrResponse implements Serializable {
	
	private Integer status;

	private String message;

	private String qrPath;
	
	
	private String intentUrl;


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getQrPath() {
		return qrPath;
	}


	public void setQrPath(String qrPath) {
		this.qrPath = qrPath;
	}


	public String getIntentUrl() {
		return intentUrl;
	}


	public void setIntentUrl(String intentUrl) {
		this.intentUrl = intentUrl;
	}
	
	
	
}
