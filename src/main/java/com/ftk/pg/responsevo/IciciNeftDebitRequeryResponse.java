package com.ftk.pg.responsevo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IciciNeftDebitRequeryResponse {

	@JsonProperty(value = "URN")
	private String urn;
	
	@JsonProperty(value = "STATUS")
	private String status;
	
	@JsonProperty(value = "RESPONSE")
	private String response;
	
	@JsonProperty(value = "UTRNUMBER")
	private String utrNo;
	
	@JsonProperty(value = "UNIQUEID")
	private String uniqueId;
	
	

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getUtrNo() {
		return utrNo;
	}

	public void setUtrNo(String utrNo) {
		this.utrNo = utrNo;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	
	
	
	
}
