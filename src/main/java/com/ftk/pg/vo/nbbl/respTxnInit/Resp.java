package com.ftk.pg.vo.nbbl.respTxnInit;

public class Resp {
	public String result; // "SUCCESS" or "FAILURE"
	public String errCode;
	public String errReason;
	public String bankId;
	public String bankAppId;
	public String initiationMode; // "QR", "INTENT", or "REDIRECTION"
	public String url;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrReason() {
		return errReason;
	}

	public void setErrReason(String errReason) {
		this.errReason = errReason;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankAppId() {
		return bankAppId;
	}

	public void setBankAppId(String bankAppId) {
		this.bankAppId = bankAppId;
	}

	public String getInitiationMode() {
		return initiationMode;
	}

	public void setInitiationMode(String initiationMode) {
		this.initiationMode = initiationMode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Resp [result=" + result + ", errCode=" + errCode + ", errReason=" + errReason + ", bankId=" + bankId
				+ ", bankAppId=" + bankAppId + ", initiationMode=" + initiationMode + ", url=" + url + "]";
	}

}