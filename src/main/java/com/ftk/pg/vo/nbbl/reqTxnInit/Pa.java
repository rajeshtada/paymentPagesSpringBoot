package com.ftk.pg.vo.nbbl.reqTxnInit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class Pa {
	public String paID;
	public String paName;
//	public Creds creds;
	public Beneficiary beneficiary;
	public Object creds;
	
	public Object getCreds() {
		return creds;
	}
	public void setCreds(Object creds2) {
		this.creds = creds2;
	}
	public String getPaID() {
		return paID;
	}
	public void setPaID(String paID) {
		this.paID = paID;
	}
	public String getPaName() {
		return paName;
	}
	public void setPaName(String paName) {
		this.paName = paName;
	}
//	public Creds getCreds() {
//		return creds;
//	}
//	public void setCreds(Creds creds) {
//		this.creds = creds;
//	}
	public Beneficiary getBeneficiary() {
		return beneficiary;
	}
	public void setBeneficiary(Beneficiary beneficiary) {
		this.beneficiary = beneficiary;
	}
	@Override
	public String toString() {
		return "PA [paID=" + paID + ", paName=" + paName + ", creds=" + creds + ", beneficiary=" + beneficiary + "]";
	}
	
}