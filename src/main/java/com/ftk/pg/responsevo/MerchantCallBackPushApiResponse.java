package com.ftk.pg.responsevo;

public class MerchantCallBackPushApiResponse {
	
	private String merchantId;
	
	private String terminalId;
	
	private String orderId;
	
	private String bankId;

	private String acknowledgement;

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

	public String getAcknowledgement() {
		return acknowledgement;
	}

	public void setAcknowledgement(String acknowledgement) {
		this.acknowledgement = acknowledgement;
	}

	@Override
	public String toString() {
		return "MerchantCallBackPushApiResponse [merchantId=" + merchantId + ", terminalId=" + terminalId + ", orderId="
				+ orderId + ", bankId=" + bankId + ", acknowledgement=" + acknowledgement + "]";
	}

	

	
}
