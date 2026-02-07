package com.ftk.pg.requestvo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IciciCollectResponse {
	
	
	private String response;
	private String merchantId;
	private String subMerchantId;
	private String terminalId;
	private String success;
	private String message;
	private String merchantTranId;
	
	@JsonProperty(value = "BankRRN")
	private String bankRRN;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getSubMerchantId() {
		return subMerchantId;
	}

	public void setSubMerchantId(String subMerchantId) {
		this.subMerchantId = subMerchantId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMerchantTranId() {
		return merchantTranId;
	}

	public void setMerchantTranId(String merchantTranId) {
		this.merchantTranId = merchantTranId;
	}

	public String getBankRRN() {
		return bankRRN;
	}

	public void setBankRRN(String bankRRN) {
		this.bankRRN = bankRRN;
	}
	

	
	
}