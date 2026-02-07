package com.ftk.pg.responsevo;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IciciCompositPayIMPSResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3025713003751153649L;

	@JsonProperty(value = "Response", required = false)
	private String actCodeDesc;
	
	@JsonProperty(value = "ActCode", required = false)
	private String actCode;
	
	@JsonProperty(value = "TransRefNo", required = false)
	private String transRefNo;
	
	@JsonProperty(value = "BankRRN", required = false)
	private String bankRRN;
	
	@JsonProperty(value = "BeneName", required = false)
	private String beneName;

	@JsonProperty(value = "success", required = false)
	private String status;


	
	public String getActCodeDesc() {
		return actCodeDesc;
	}

	public void setActCodeDesc(String actCodeDesc) {
		this.actCodeDesc = actCodeDesc;
	}

	public String getActCode() {
		return actCode;
	}

	public void setActCode(String actCode) {
		this.actCode = actCode;
	}

	public String getTransRefNo() {
		return transRefNo;
	}

	public void setTransRefNo(String transRefNo) {
		this.transRefNo = transRefNo;
	}

	public String getBankRRN() {
		return bankRRN;
	}

	public void setBankRRN(String bankRRN) {
		this.bankRRN = bankRRN;
	}

	public String getBeneName() {
		return beneName;
	}

	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
	
	
}
