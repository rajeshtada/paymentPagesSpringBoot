package com.ftk.pg.requestvo;

public class PRqFrqFinalRequest {
	
	 private String messageType ;
     private String messageVersion ;
     private String threeDSServerTransID ;
     private String acsTransID ;
     private String dsTransID ;
     private String merchantTransID ;
     private String acquirerID ;
     private String acsAuthResponse ;
     private String p_messageVersion ;
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getMessageVersion() {
		return messageVersion;
	}
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}
	public String getThreeDSServerTransID() {
		return threeDSServerTransID;
	}
	public void setThreeDSServerTransID(String threeDSServerTransID) {
		this.threeDSServerTransID = threeDSServerTransID;
	}
	public String getAcsTransID() {
		return acsTransID;
	}
	public void setAcsTransID(String acsTransID) {
		this.acsTransID = acsTransID;
	}
	public String getDsTransID() {
		return dsTransID;
	}
	public void setDsTransID(String dsTransID) {
		this.dsTransID = dsTransID;
	}
	public String getMerchantTransID() {
		return merchantTransID;
	}
	public void setMerchantTransID(String merchantTransID) {
		this.merchantTransID = merchantTransID;
	}
	public String getAcquirerID() {
		return acquirerID;
	}
	public void setAcquirerID(String acquirerID) {
		this.acquirerID = acquirerID;
	}
	public String getAcsAuthResponse() {
		return acsAuthResponse;
	}
	public void setAcsAuthResponse(String acsAuthResponse) {
		this.acsAuthResponse = acsAuthResponse;
	}
	public String getP_messageVersion() {
		return p_messageVersion;
	}
	public void setP_messageVersion(String p_messageVersion) {
		this.p_messageVersion = p_messageVersion;
	}
	@Override
	public String toString() {
		return "PRqFrqFinalRequest [messageType=" + messageType + ", messageVersion=" + messageVersion
				+ ", threeDSServerTransID=" + threeDSServerTransID + ", acsTransID=" + acsTransID + ", dsTransID="
				+ dsTransID + ", merchantTransID=" + merchantTransID + ", acquirerID=" + acquirerID
				+ ", acsAuthResponse=" + acsAuthResponse + ", p_messageVersion=" + p_messageVersion + "]";
	}
     
     

}
