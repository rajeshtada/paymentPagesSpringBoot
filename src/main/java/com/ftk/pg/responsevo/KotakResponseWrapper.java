package com.ftk.pg.responsevo;
import java.io.Serializable;

public class KotakResponseWrapper implements Serializable {

	private String merchantId;
	
	private String terminalId;
	
	private String orderId;
	
	private String bankId;
	
	private String encData;
	
	private String errorCode;
	
	private String errorMessage;
	
	private String pgId;

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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getEncData() {
		return encData;
	}

	public void setEncData(String encData) {
		this.encData = encData;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getPgId() {
		return pgId;
	}

	public void setPgId(String pgId) {
		this.pgId = pgId;
	}

	@Override
	public String toString() {
		return "AuthenticationResponseWrapper [merchantId=" + merchantId + ", terminalId=" + terminalId + ", orderId="
				+ orderId + ", bankId=" + bankId + ", encData=" + encData + ", errorCode=" + errorCode
				+ ", errorMessage=" + errorMessage + ", pgId=" + pgId + "]";
	}
	
	
}
