package com.ftk.pg.responsevo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IciciBeneficiaryRegistrationResponse {

	@JsonProperty(value = "Message")
	private String massage;
	
	@JsonProperty(value = "Response")
	private String response;

	@JsonProperty(value = "ErrorCode")	
	private String errorCode;
	
	
	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
	
}
