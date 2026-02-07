package com.ftk.pg.responsevo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IciciNeftRequeryResponse {

	@JsonProperty(value = "CreditDate")
	private String creditDate;
	
	@JsonProperty(value = "STATUS")
	private String status;
	
	@JsonProperty(value = "Response")
	private String response;
	
	@JsonProperty(value = "UTRNUMBER")
	private String utrNo;



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

	public String getCreditDate() {
		return creditDate;
	}

	public void setCreditDate(String creditDate) {
		this.creditDate = creditDate;
	}
	
	
	
}
