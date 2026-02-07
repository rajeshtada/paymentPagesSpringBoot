package com.ftk.pg.requestvo;

public class BEPG {

	public String BEPG_TxnCtxId;

	public String BEPG_OtpValidityPeriod;

	public String BEPG_BepgTimeLogged;

	public String BEPG_BEPGRequestId;

	public String getBEPG_TxnCtxId() {
		return BEPG_TxnCtxId;
	}

	public void setBEPG_TxnCtxId(String bEPG_TxnCtxId) {
		BEPG_TxnCtxId = bEPG_TxnCtxId;
	}

	public String getBEPG_OtpValidityPeriod() {
		return BEPG_OtpValidityPeriod;
	}

	public void setBEPG_OtpValidityPeriod(String bEPG_OtpValidityPeriod) {
		BEPG_OtpValidityPeriod = bEPG_OtpValidityPeriod;
	}

	public String getBEPG_BepgTimeLogged() {
		return BEPG_BepgTimeLogged;
	}

	public void setBEPG_BepgTimeLogged(String bEPG_BepgTimeLogged) {
		BEPG_BepgTimeLogged = bEPG_BepgTimeLogged;
	}

	public String getBEPG_BEPGRequestId() {
		return BEPG_BEPGRequestId;
	}

	public void setBEPG_BEPGRequestId(String bEPG_BEPGRequestId) {
		BEPG_BEPGRequestId = bEPG_BEPGRequestId;
	}

	@Override
	public String toString() {
		return "BEPG [BEPG_TxnCtxId=" + BEPG_TxnCtxId + ", BEPG_OtpValidityPeriod=" + BEPG_OtpValidityPeriod
				+ ", BEPG_BepgTimeLogged=" + BEPG_BepgTimeLogged + ", BEPG_BEPGRequestId=" + BEPG_BEPGRequestId + "]";
	}
}