package com.ftk.pg.requestvo;

public class PgPushNotificationRequest {
	private String portalTxnId;
	private String pgTxnId;
	public String getPortalTxnId() {
		return portalTxnId;
	}
	public void setPortalTxnId(String portalTxnId) {
		this.portalTxnId = portalTxnId;
	}
	public String getPgTxnId() {
		return pgTxnId;
	}
	public void setPgTxnId(String pgTxnId) {
		this.pgTxnId = pgTxnId;
	}
	@Override
	public String toString() {
		return "PgPushNotificationRequest [portalTxnId=" + portalTxnId + ", pgTxnId=" + pgTxnId + "]";
	}
	

}