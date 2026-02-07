package com.ftk.pg.requestvo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IciciBeneficiaryRegistrationRequest {

	@JsonProperty(value = "CrpId")
	private String crpId;
	
	@JsonProperty(value = "CrpUsr")
	private String crpUsr;
	
	@JsonProperty(value = "BnfName")
	private String bnfName;
	
	@JsonProperty(value = "BnfNickName")
	private String bnfNickName;
	
	@JsonProperty(value = "BnfAccNo")
	private String bnfAccNo;
	
	@JsonProperty(value = "PayeeType")
	private String payeeType;
	
	@JsonProperty(value = "IFSC")
	private String ifsc;
	
	@JsonProperty(value = "AGGR_ID")
	private String aggrId;
	
	@JsonProperty(value = "URN")
	private String urn;

	public String getCrpId() {
		return crpId;
	}

	public void setCrpId(String crpId) {
		this.crpId = crpId;
	}

	public String getCrpUsr() {
		return crpUsr;
	}

	public void setCrpUsr(String crpUsr) {
		this.crpUsr = crpUsr;
	}

	public String getBnfName() {
		return bnfName;
	}

	public void setBnfName(String bnfName) {
		this.bnfName = bnfName;
	}

	public String getBnfNickName() {
		return bnfNickName;
	}

	public void setBnfNickName(String bnfNickName) {
		this.bnfNickName = bnfNickName;
	}

	public String getBnfAccNo() {
		return bnfAccNo;
	}

	public void setBnfAccNo(String bnfAccNo) {
		this.bnfAccNo = bnfAccNo;
	}

	public String getPayeeType() {
		return payeeType;
	}

	public void setPayeeType(String payeeType) {
		this.payeeType = payeeType;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getAggrId() {
		return aggrId;
	}

	public void setAggrId(String aggrId) {
		this.aggrId = aggrId;
	}

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}
	
	
	
	
}
