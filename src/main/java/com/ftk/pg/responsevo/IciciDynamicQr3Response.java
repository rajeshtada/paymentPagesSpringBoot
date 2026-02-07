package com.ftk.pg.responsevo;

public class IciciDynamicQr3Response {

	private String merchantId;

	private String terminalId;

	private String success;

	private String response;

	private String message;

	private String refId;

	private String merchantTranId;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getMerchantTranId() {
		return merchantTranId;
	}

	public void setMerchantTranId(String merchantTranId) {
		this.merchantTranId = merchantTranId;
	}

	@Override
	public String toString() {
		return "IciciDynamicQr3Response [merchantId=" + merchantId + ", terminalId=" + terminalId + ", success="
				+ success + ", response=" + response + ", message=" + message + ", refId=" + refId + ", merchantTranId="
				+ merchantTranId + "]";
	}
}
