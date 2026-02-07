package com.ftk.pg.requestvo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IciciCompositPayIMPSRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3422722906638422583L;
	private String localTxnDtTime;
	private String beneAccNo;
	private String beneIFSC;
	private String amount;
	private String tranRefNo;
	private String paymentRef;
	private String senderName;
	private String mobile;
	private String retailerCode;
	private String passCode;
	private String bcID;
	
	@JsonProperty(value = "crpId")
	private String crpId;
	
	private String crpUsr;

	private String aggrName;
	private String urn;
	
	
	public String getAggrName() {
		return aggrName;
	}

	public void setAggrName(String aggrName) {
		this.aggrName = aggrName;
	}

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public String getLocalTxnDtTime() {
		return localTxnDtTime;
	}

	public void setLocalTxnDtTime(String localTxnDtTime) {
		this.localTxnDtTime = localTxnDtTime;
	}

	public String getBeneAccNo() {
		return beneAccNo;
	}

	public void setBeneAccNo(String beneAccNo) {
		this.beneAccNo = beneAccNo;
	}

	public String getBeneIFSC() {
		return beneIFSC;
	}

	public void setBeneIFSC(String beneIFSC) {
		this.beneIFSC = beneIFSC;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTranRefNo() {
		return tranRefNo;
	}

	public void setTranRefNo(String tranRefNo) {
		this.tranRefNo = tranRefNo;
	}

	public String getPaymentRef() {
		return paymentRef;
	}

	public void setPaymentRef(String paymentRef) {
		this.paymentRef = paymentRef;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRetailerCode() {
		return retailerCode;
	}

	public void setRetailerCode(String retailerCode) {
		this.retailerCode = retailerCode;
	}

	public String getPassCode() {
		return passCode;
	}

	public void setPassCode(String passCode) {
		this.passCode = passCode;
	}

	public String getBcID() {
		return bcID;
	}

	public void setBcID(String bcID) {
		this.bcID = bcID;
	}

	public String getCrpId() {
		return crpId;
	}

	public void setCrpId(String crpId) {
		this.crpId = crpId;
	}

	public String getCrpUsr() {
		return crpUsr;
	}

	public void setCrpUsr(String crpUsr) {
		this.crpUsr = crpUsr;
	}
	
	
	
	
	
}
