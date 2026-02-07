package com.ftk.pg.responsevo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IciciCIBRegistrationResponse {

	@JsonProperty(value = "AGGRNAME")
	private String aggrName;
	
	@JsonProperty(value = "AGGRID")
	private String aggrId;
	
	@JsonProperty(value = "CORPID")
	private String corpId;
	
	@JsonProperty(value = "USERID")
	private String userId;
	
	@JsonProperty(value = "URN")
	private String urn;
	
	@JsonProperty(value = "Response")
	private String response;
	
	@JsonProperty(value = "Message")
	private String message;
	
	
	public String getAggrName() {
		return aggrName;
	}
	public void setAggrName(String aggrName) {
		this.aggrName = aggrName;
	}
	public String getAggrId() {
		return aggrId;
	}
	public void setAggrId(String aggrId) {
		this.aggrId = aggrId;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUrn() {
		return urn;
	}
	public void setUrn(String urn) {
		this.urn = urn;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "IciciCIBRegistrationResponse [aggrName=" + aggrName + ", aggrId=" + aggrId + ", corpId=" + corpId
				+ ", userId=" + userId + ", urn=" + urn + ", response=" + response + ", message=" + message + "]";
	}
	
	
	
}
