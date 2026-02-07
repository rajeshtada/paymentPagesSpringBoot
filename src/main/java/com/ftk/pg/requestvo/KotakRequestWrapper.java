package com.ftk.pg.requestvo;
import java.io.Serializable;

public class KotakRequestWrapper implements Serializable{

	private String merchantId;
	
	private String terminalId;
	
	private String orderId;
	
	private String bankId;
	
	private String encData;

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

	@Override
	public String toString() {
		return "AuthenticationRequestWrapper [merchantId=" + merchantId + ", terminalId=" + terminalId + ", orderId="
				+ orderId + ", bankId=" + bankId + ", encData=" + encData + "]";
	}
	
	
}
