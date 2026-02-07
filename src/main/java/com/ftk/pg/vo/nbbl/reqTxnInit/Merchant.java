package com.ftk.pg.vo.nbbl.reqTxnInit;

public class Merchant {
	public String mcc;
	public String mid;
	public String mName;
	public Beneficiary beneficiary;
	public ReturnUrl returnUrl;
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public Beneficiary getBeneficiary() {
		return beneficiary;
	}
	public void setBeneficiary(Beneficiary beneficiary) {
		this.beneficiary = beneficiary;
	}
	public ReturnUrl getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(ReturnUrl returnUrl) {
		this.returnUrl = returnUrl;
	}
	@Override
	public String toString() {
		return "Merchant [mcc=" + mcc + ", mid=" + mid + ", mName=" + mName + ", beneficiary=" + beneficiary
				+ ", returnUrl=" + returnUrl + "]";
	}
	
	
}