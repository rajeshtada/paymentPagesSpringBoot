package com.ftk.pg.responsevo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IciciCompositPayNEFTResponse implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7592625198890771967L;
	@JsonProperty(value = "REQID")
	private String reqId;
	@JsonProperty(value = "RESPONSE")
	private String response;
	@JsonProperty(value = "STATUS")
	private String status;
	@JsonProperty(value = "UNIQUEID")
	private String uniqueId;
	@JsonProperty(value = "URN")
	private String urn;
	@JsonProperty(value = "UTRNUMBER")
	private String utr;
	
	@JsonProperty(value = "ERRORCODE")
	private String errorCode;
	
	@JsonProperty(value = "RESPONSECODE")
	private String responseCode;
	
	@JsonProperty(value = "MESSAGE")
	private String message;
	
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getUrn() {
		return urn;
	}
	public void setUrn(String urn) {
		this.urn = urn;
	}
	public String getUtr() {
		return utr;
	}
	public void setUtr(String utr) {
		this.utr = utr;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	
}
