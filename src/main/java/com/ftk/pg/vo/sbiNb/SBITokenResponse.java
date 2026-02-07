package com.ftk.pg.vo.sbiNb;

public class SBITokenResponse {
	private String status;
	private String statusCode;
	private String errorDesc;
	private String transactionId;
	private String tokenReferenceId;
	private String provider;
	private String tokenLast4;
	private String tokenStatus;
	private String encTokenInfo;
	private String iv;
	private String paymentAccountReference;
	private String tokenAssetId;
	private String msg;
	private String tokenExpiryDate;
	private String panLast4;
	private String clientReferenceId;
	private String panReferenceId;
	private String mappedTokenBin;
	private String var1;
	private String var2;
	private String var3;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTokenReferenceId() {
		return tokenReferenceId;
	}

	public void setTokenReferenceId(String tokenReferenceId) {
		this.tokenReferenceId = tokenReferenceId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getTokenLast4() {
		return tokenLast4;
	}

	public void setTokenLast4(String tokenLast4) {
		this.tokenLast4 = tokenLast4;
	}

	public String getTokenStatus() {
		return tokenStatus;
	}

	public void setTokenStatus(String tokenStatus) {
		this.tokenStatus = tokenStatus;
	}

	public String getEncTokenInfo() {
		return encTokenInfo;
	}

	public void setEncTokenInfo(String encTokenInfo) {
		this.encTokenInfo = encTokenInfo;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getPaymentAccountReference() {
		return paymentAccountReference;
	}

	public void setPaymentAccountReference(String paymentAccountReference) {
		this.paymentAccountReference = paymentAccountReference;
	}

	public String getTokenAssetId() {
		return tokenAssetId;
	}

	public void setTokenAssetId(String tokenAssetId) {
		this.tokenAssetId = tokenAssetId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTokenExpiryDate() {
		return tokenExpiryDate;
	}

	public void setTokenExpiryDate(String tokenExpiryDate) {
		this.tokenExpiryDate = tokenExpiryDate;
	}

	public String getPanLast4() {
		return panLast4;
	}

	public void setPanLast4(String panLast4) {
		this.panLast4 = panLast4;
	}

	public String getClientReferenceId() {
		return clientReferenceId;
	}

	public void setClientReferenceId(String clientReferenceId) {
		this.clientReferenceId = clientReferenceId;
	}

	public String getPanReferenceId() {
		return panReferenceId;
	}

	public void setPanReferenceId(String panReferenceId) {
		this.panReferenceId = panReferenceId;
	}

	public String getMappedTokenBin() {
		return mappedTokenBin;
	}

	public void setMappedTokenBin(String mappedTokenBin) {
		this.mappedTokenBin = mappedTokenBin;
	}

	public String getVar1() {
		return var1;
	}

	public void setVar1(String var1) {
		this.var1 = var1;
	}

	public String getVar2() {
		return var2;
	}

	public void setVar2(String var2) {
		this.var2 = var2;
	}

	public String getVar3() {
		return var3;
	}

	public void setVar3(String var3) {
		this.var3 = var3;
	}

	@Override
	public String toString() {
		return "SBITokenResponse [status=" + status + ", statusCode=" + statusCode + ", errorDesc=" + errorDesc
				+ ", transactionId=" + transactionId + ", tokenReferenceId=" + tokenReferenceId + ", provider="
				+ provider + ", tokenLast4=" + tokenLast4 + ", tokenStatus=" + tokenStatus + ", encTokenInfo="
				+ encTokenInfo + ", iv=" + iv + ", paymentAccountReference=" + paymentAccountReference
				+ ", tokenAssetId=" + tokenAssetId + ", msg=" + msg + ", tokenExpiryDate=" + tokenExpiryDate
				+ ", panLast4=" + panLast4 + ", clientReferenceId=" + clientReferenceId + ", panReferenceId="
				+ panReferenceId + ", mappedTokenBin=" + mappedTokenBin + ", var1=" + var1 + ", var2=" + var2
				+ ", var3=" + var3 + "]";
	}
}