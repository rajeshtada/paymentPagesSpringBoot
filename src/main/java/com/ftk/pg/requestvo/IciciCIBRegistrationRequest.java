package com.ftk.pg.requestvo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IciciCIBRegistrationRequest {

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
	
	@JsonProperty(value = "ALIASID")
	private String aliasId;
	
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
	public String getAliasId() {
		return aliasId;
	}
	public void setAliasId(String aliasId) {
		this.aliasId = aliasId;
	}
	
	
}
