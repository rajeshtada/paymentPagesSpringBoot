package com.ftk.pg.vo.nbbl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class Signature {

	@SerializedName("protected")
	@JsonProperty("protected")
	private String protectedValue;
	
	private String signature;

	public String getProtectedValue() {
		return protectedValue;
	}

	public void setProtectedValue(String protectedValue) {
		this.protectedValue = protectedValue;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "Signature [protectedValue=" + protectedValue + ", signature=" + signature + "]";
	}
	
	
}
