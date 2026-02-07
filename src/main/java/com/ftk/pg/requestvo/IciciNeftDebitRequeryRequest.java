package com.ftk.pg.requestvo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IciciNeftDebitRequeryRequest {

	@JsonProperty(value = "AGGRID")
	private String aggrId;
	
	@JsonProperty(value = "CORPID")
	private String corpId;
	
	@JsonProperty(value = "USERID")
	private String userId;
	
	@JsonProperty(value = "URN")
	private String urn;
	
	@JsonProperty(value = "UNIQUEID")
	private String uniqueId;

	
	
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

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	
	
	
	
}
