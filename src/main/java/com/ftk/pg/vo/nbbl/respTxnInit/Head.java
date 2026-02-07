package com.ftk.pg.vo.nbbl.respTxnInit;

public class Head {
	public String ver;
	public String ts;
	public String msgId;
	public String orgId;
	public String correlationKey;

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getCorrelationKey() {
		return correlationKey;
	}

	public void setCorrelationKey(String correlationKey) {
		this.correlationKey = correlationKey;
	}

	@Override
	public String toString() {
		return "Head [ver=" + ver + ", ts=" + ts + ", msgId=" + msgId + ", orgId=" + orgId + ", correlationKey="
				+ correlationKey + "]";
	}

}