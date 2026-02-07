package com.ftk.pg.vo.nbbl.reqTxnInit;

public class Head {
	public String ver;
	public String ts;
	public String msgID;
	public String bankID;
	public String bankAppId;
	public String orgID;
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
	public String getMsgID() {
		return msgID;
	}
	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}
	public String getBankID() {
		return bankID;
	}
	public void setBankID(String bankID) {
		this.bankID = bankID;
	}
	public String getBankAppId() {
		return bankAppId;
	}
	public void setBankAppId(String bankAppId) {
		this.bankAppId = bankAppId;
	}
	public String getOrgID() {
		return orgID;
	}
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	public String getCorrelationKey() {
		return correlationKey;
	}
	public void setCorrelationKey(String correlationKey) {
		this.correlationKey = correlationKey;
	}
	@Override
	public String toString() {
		return "Head [ver=" + ver + ", ts=" + ts + ", msgID=" + msgID + ", bankID=" + bankID + ", bankAppId="
				+ bankAppId + ", orgID=" + orgID + ", correlationKey=" + correlationKey + "]";
	}
	
	
}