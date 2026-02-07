package com.ftk.pg.requestvo;

public class RupayEncryptedData {
	private String encryptedData;
	private String iv;
	public String getEncryptedData() {
		return encryptedData;
	}
	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}
	public String getIv() {
		return iv;
	}
	public void setIv(String iv) {
		this.iv = iv;
	}
	@Override
	public String toString() {
		return "RupayEncryptedData [encryptedData=" + encryptedData + ", iv=" + iv + "]";
	}
	

}
