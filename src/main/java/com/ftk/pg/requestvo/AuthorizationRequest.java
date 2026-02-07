package com.ftk.pg.requestvo;

public class AuthorizationRequest {

	private String BankId;

	private String MerchantId;

	private String TerminalId;

	private String OrderId;

	private String AccessCode;

	private String PgId;

	private String PaRes;

	private String MD;

	private String session;

	private String AccuResponseCode;

	private String AccuGuid;

	private String AccuRequestId;

	private String SiHubId;

	private String CRes;

	private String SecureHash;

	public String getBankId() {
		return BankId;
	}

	public void setBankId(String bankId) {
		BankId = bankId;
	}

	public String getMerchantId() {
		return MerchantId;
	}

	public void setMerchantId(String merchantId) {
		MerchantId = merchantId;
	}

	public String getTerminalId() {
		return TerminalId;
	}

	public void setTerminalId(String terminalId) {
		TerminalId = terminalId;
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getAccessCode() {
		return AccessCode;
	}

	public void setAccessCode(String accessCode) {
		AccessCode = accessCode;
	}

	public String getPgId() {
		return PgId;
	}

	public void setPgId(String pgId) {
		PgId = pgId;
	}

	public String getPaRes() {
		return PaRes;
	}

	public void setPaRes(String paRes) {
		PaRes = paRes;
	}

	public String getMD() {
		return MD;
	}

	public void setMD(String mD) {
		MD = mD;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getAccuResponseCode() {
		return AccuResponseCode;
	}

	public void setAccuResponseCode(String accuResponseCode) {
		AccuResponseCode = accuResponseCode;
	}

	public String getAccuGuid() {
		return AccuGuid;
	}

	public void setAccuGuid(String accuGuid) {
		AccuGuid = accuGuid;
	}

	public String getAccuRequestId() {
		return AccuRequestId;
	}

	public void setAccuRequestId(String accuRequestId) {
		AccuRequestId = accuRequestId;
	}

	public String getSiHubId() {
		return SiHubId;
	}

	public void setSiHubId(String siHubId) {
		SiHubId = siHubId;
	}

	public String getCRes() {
		return CRes;
	}

	public void setCRes(String cRes) {
		CRes = cRes;
	}

	public String getSecureHash() {
		return SecureHash;
	}

	public void setSecureHash(String secureHash) {
		SecureHash = secureHash;
	}

	@Override
	public String toString() {
		return "AuthorizationRequest [BankId=" + BankId + ", MerchantId=" + MerchantId + ", TerminalId=" + TerminalId
				+ ", OrderId=" + OrderId + ", AccessCode=" + AccessCode + ", PgId=" + PgId + ", PaRes=" + PaRes
				+ ", MD=" + MD + ", session=" + session + ", AccuResponseCode=" + AccuResponseCode + ", AccuGuid="
				+ AccuGuid + ", AccuRequestId=" + AccuRequestId + ", SiHubId=" + SiHubId + ", CRes=" + CRes
				+ ", SecureHash=" + SecureHash + "]";
	}

}
