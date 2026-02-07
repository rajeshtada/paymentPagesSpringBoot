package com.ftk.pg.responsevo;
import java.util.Map;

public class CashfreeResponsePaymentData {

	private String url;
	private String content_type;
	private String method;
	
	private Map<String,String> payload;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, String> getPayload() {
		return payload;
	}

	public void setPayload(Map<String, String> payload) {
		this.payload = payload;
	}
	
	
	
	
}
