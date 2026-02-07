package com.ftk.pg.requestvo;

public class UcoRequestVo {
	private String trancrn;
	private String txnamount;
	private String pid;
	private String prn;
	private String itc;
	private String shpngmallaccnum;
	private String checksum;

	public String getTrancrn() {
		return trancrn;
	}

	public void setTrancrn(String trancrn) {
		this.trancrn = trancrn;
	}

	public String getTxnamount() {
		return txnamount;
	}

	public void setTxnamount(String txnamount) {
		this.txnamount = txnamount;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPrn() {
		return prn;
	}

	public void setPrn(String prn) {
		this.prn = prn;
	}

	public String getItc() {
		return itc;
	}

	public void setItc(String itc) {
		this.itc = itc;
	}

	public String getShpngmallaccnum() {
		return shpngmallaccnum;
	}

	public void setShpngmallaccnum(String shpngmallaccnum) {
		this.shpngmallaccnum = shpngmallaccnum;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	@Override
	public String toString() {
		return "FebaRequestVo [trancrn=" + trancrn + ", txnamount=" + txnamount + ", pid=" + pid + ", prn=" + prn
				+ ", itc=" + itc + ", shpngmallaccnum=" + shpngmallaccnum + ", checksum=" + checksum + "]";
	}

//	public String getStringtoCalculateCheckSum() {
//		return trancrn + "|" + txnamount + "|" + pid + "|" + prn + "|" + itc + "|" + shpngmallaccnum;
//	}
//
//	public String getFinalQString() {
//		return trancrn + "|" + txnamount + "|" + pid + "|" + prn + "|" + itc + "|" + shpngmallaccnum + "|" + checksum;
//	}

//	public String getStringtoCalculateCheckSum() {
//		return "ShoppingMallTranFG.TRAN_CRN~" + trancrn + "|ShoppingMallTranFG.TXN_AMT~" + txnamount
//				+ "|ShoppingMallTranFG.PID~" + pid + "|ShoppingMallTranFG.PRN~" + prn + "|ShoppingMallTranFG.ITC~" + itc
//				+ "|ShoppingMallTranFG.SHPNG_MALL_ACC_NUM~" + shpngmallaccnum;
//	}
//
//	public String getFinalQString() {
//		return "ShoppingMallTranFG.TRAN_CRN~" + trancrn + "|ShoppingMallTranFG.TXN_AMT~" + txnamount
//				+ "|ShoppingMallTranFG.PID~" + pid + "|ShoppingMallTranFG.PRN~" + prn + "|ShoppingMallTranFG.ITC~" + itc
//				+ "|ShoppingMallTranFG.SHPNG_MALL_ACC_NUM~" + shpngmallaccnum + "|ShoppingMallTranFG.CHECK_SUM~"
//				+ checksum;
//
//	}

	public String getStringtoCalculateCheckSum() {
		return "ShoppingMallTranFG.TRAN_CRN~" + trancrn + "|ShoppingMallTranFG.TXN_AMT~" + txnamount
				+ "|ShoppingMallTranFG.PID~" + pid + "|ShoppingMallTranFG.PRN~" + prn + "|ShoppingMallTranFG.ITC~"
				+ itc;
	}

	public String getFinalQString() {
		return "ShoppingMallTranFG.TRAN_CRN~" + trancrn + "|ShoppingMallTranFG.TXN_AMT~" + txnamount
				+ "|ShoppingMallTranFG.PID~" + pid + "|ShoppingMallTranFG.PRN~" + prn + "|ShoppingMallTranFG.ITC~" + itc
				+ "|ShoppingMallTranFG.CHECK_SUM~" + checksum;

	}

}
