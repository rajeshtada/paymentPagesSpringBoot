package com.ftk.pg.requestvo;

public class RupayCheckbinRequest {
	private String pgInstanceId;
	private String cardBin;
	public String getPgInstanceId() {
		return pgInstanceId;
	}
	public void setPgInstanceId(String pgInstanceId) {
		this.pgInstanceId = pgInstanceId;
	}
	public String getCardBin() {
		return cardBin;
	}
	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}
	@Override
	public String toString() {
		return "RupayCheckbinRequest [pgInstanceId=" + pgInstanceId + ", cardBin=" + cardBin + "]";
	}
	

}
