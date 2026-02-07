package com.ftk.pg.vo.nbbl;


public class NbblRequestWrapper {

	private String payload;
	private Signature signature;

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "NbblRequestWrapper [payload=" + payload + ", signature=" + signature + "]";
	}

}
