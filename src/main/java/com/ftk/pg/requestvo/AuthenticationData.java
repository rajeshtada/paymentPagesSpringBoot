package com.ftk.pg.requestvo;

public class AuthenticationData {

	public String OTPRederingContent;

	public String ACSEndPoint;

	public MVM MVM;

	public Rupay Rupay;

	public BEPG BEPG;

	public String getOTPRederingContent() {
		return OTPRederingContent;
	}

	public void setOTPRederingContent(String oTPRederingContent) {
		OTPRederingContent = oTPRederingContent;
	}

	public String getACSEndPoint() {
		return ACSEndPoint;
	}

	public void setACSEndPoint(String aCSEndPoint) {
		ACSEndPoint = aCSEndPoint;
	}

	public MVM getMVM() {
		return MVM;
	}

	public void setMVM(MVM mVM) {
		MVM = mVM;
	}

	public Rupay getRupay() {
		return Rupay;
	}

	public void setRupay(Rupay rupay) {
		Rupay = rupay;
	}

	public BEPG getBEPG() {
		return BEPG;
	}

	public void setBEPG(BEPG bEPG) {
		BEPG = bEPG;
	}

	@Override
	public String toString() {
		return "AuthenticationData [OTPRederingContent=" + OTPRederingContent + ", ACSEndPoint=" + ACSEndPoint
				+ ", MVM=" + MVM + ", Rupay=" + Rupay + ", BEPG=" + BEPG + "]";
	}

}
