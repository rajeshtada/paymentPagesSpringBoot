package com.ftk.pg.requestvo;

import java.io.Serializable;

public class IciciCompositPayNEFTRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6213235704388584354L;

	private String tranRefNo;
	private String amount;
	private String senderAcctNo;
	private String beneAccNo;
	private String beneName;
	private String beneIFSC;
	private String mobile;
	private String narration1;
	private String narration2;
	private String crpId;
	private String crpUsr;
	private String aggrId;
	private String aggrName;
	private String Urn;
	private String txnType;
	public String getTranRefNo() {
		return tranRefNo;
	}
	public void setTranRefNo(String tranRefNo) {
		this.tranRefNo = tranRefNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getSenderAcctNo() {
		return senderAcctNo;
	}
	public void setSenderAcctNo(String senderAcctNo) {
		this.senderAcctNo = senderAcctNo;
	}
	public String getBeneAccNo() {
		return beneAccNo;
	}
	public void setBeneAccNo(String beneAccNo) {
		this.beneAccNo = beneAccNo;
	}
	public String getBeneName() {
		return beneName;
	}
	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}
	public String getBeneIFSC() {
		return beneIFSC;
	}
	public void setBeneIFSC(String beneIFSC) {
		this.beneIFSC = beneIFSC;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNarration1() {
		return narration1;
	}
	public void setNarration1(String narration1) {
		this.narration1 = narration1;
	}
	public String getNarration2() {
		return narration2;
	}
	public void setNarration2(String narration2) {
		this.narration2 = narration2;
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
	public String getAggrId() {
		return aggrId;
	}
	public void setAggrId(String aggrId) {
		this.aggrId = aggrId;
	}
	public String getAggrName() {
		return aggrName;
	}
	public void setAggrName(String aggrName) {
		this.aggrName = aggrName;
	}
	public String getUrn() {
		return Urn;
	}
	public void setUrn(String urn) {
		Urn = urn;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	
	
	
	
}
