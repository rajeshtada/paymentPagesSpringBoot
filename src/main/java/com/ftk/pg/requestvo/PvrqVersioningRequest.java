package com.ftk.pg.requestvo;

import com.ftk.pg.util.Util;

public class PvrqVersioningRequest {
	private  String messageType;
	private String deviceChannel;
	private String merchantTransID;
	private String acctNumber;
	private String acquirerBIN;
	private String acquirerID;
	private String threeDSRequestorMethodNotificationRespURL;
	private String p_messageVersion;
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getDeviceChannel() {
		return deviceChannel;
	}
	public void setDeviceChannel(String deviceChannel) {
		this.deviceChannel = deviceChannel;
	}
	public String getMerchantTransID() {
		return merchantTransID;
	}
	public void setMerchantTransID(String merchantTransID) {
		this.merchantTransID = merchantTransID;
	}
	public String getAcctNumber() {
		return acctNumber;
	}
	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}
	public String getAcquirerBIN() {
		return acquirerBIN;
	}
	public void setAcquirerBIN(String acquirerBIN) {
		this.acquirerBIN = acquirerBIN;
	}
	public String getAcquirerID() {
		return acquirerID;
	}
	public void setAcquirerID(String acquirerID) {
		this.acquirerID = acquirerID;
	}
	public String getThreeDSRequestorMethodNotificationRespURL() {
		return threeDSRequestorMethodNotificationRespURL;
	}
	public void setThreeDSRequestorMethodNotificationRespURL(String threeDSRequestorMethodNotificationRespURL) {
		this.threeDSRequestorMethodNotificationRespURL = threeDSRequestorMethodNotificationRespURL;
	}
	public String getP_messageVersion() {
		return p_messageVersion;
	}
	public void setP_messageVersion(String p_messageVersion) {
		this.p_messageVersion = p_messageVersion;
	}
	@Override
	public String toString() {
		return "SbiVersioningRequest [messageType=" + messageType + ", deviceChannel=" + deviceChannel
				+ ", merchantTransID=" + merchantTransID + ", acctNumber=" + Util.maskStringValue(acctNumber) + ", acquirerBIN=" + acquirerBIN
				+ ", acquirerID=" + acquirerID + ", threeDSRequestorMethodNotificationRespURL="
				+ threeDSRequestorMethodNotificationRespURL + ", p_messageVersion=" + p_messageVersion + "]";
	}
	
	

}
