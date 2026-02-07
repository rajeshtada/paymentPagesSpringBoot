package com.ftk.pg.responsevo;

public class GenerateInvoiceV2ResponseData {

	private String paymentId;
	private String paymentUrl;
	private String qrIntent;
	private String qr;
	private String token;
	private String qrPath;

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentUrl() {
		return paymentUrl;
	}

	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}

	public String getQrIntent() {
		return qrIntent;
	}

	public void setQrIntent(String qrIntent) {
		this.qrIntent = qrIntent;
	}

	public String getQr() {
		return qr;
	}

	public void setQr(String qr) {
		this.qr = qr;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getQrPath() {
		return qrPath;
	}

	public void setQrPath(String qrPath) {
		this.qrPath = qrPath;
	}

	@Override
	public String toString() {
		return "GenerateInvoiceV2ResponseData [paymentId=" + paymentId + ", paymentUrl=" + paymentUrl + ", qrIntent="
				+ qrIntent + ", qr=" + qr + ", token=" + token + ", qrPath=" + qrPath + "]";
	}
	
}
