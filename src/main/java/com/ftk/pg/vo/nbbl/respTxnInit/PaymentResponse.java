package com.ftk.pg.vo.nbbl.respTxnInit;

import java.util.List;

public class PaymentResponse {

	public Head head;
	public TXN txn;
	public Resp resp;
	public List<AdditionalInfo> additionalInfo;

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public TXN getTxn() {
		return txn;
	}

	public void setTxn(TXN txn) {
		this.txn = txn;
	}

	public Resp getResp() {
		return resp;
	}

	public void setResp(Resp resp) {
		this.resp = resp;
	}

	public List<AdditionalInfo> getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(List<AdditionalInfo> additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	@Override
	public String toString() {
		return "PaymentResponse [head=" + head + ", txn=" + txn + ", resp=" + resp + ", additionalInfo="
				+ additionalInfo + "]";
	}

}
