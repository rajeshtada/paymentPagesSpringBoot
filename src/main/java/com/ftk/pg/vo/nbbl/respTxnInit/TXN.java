package com.ftk.pg.vo.nbbl.respTxnInit;

public class TXN {
	public String refId;
	public String ts;

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	@Override
	public String toString() {
		return "TXN [refId=" + refId + ", ts=" + ts + "]";
	}

}