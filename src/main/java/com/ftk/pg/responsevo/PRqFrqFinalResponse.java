package com.ftk.pg.responsevo;

public class PRqFrqFinalResponse {
	
	
	   private String messageVersion ;
       private String messageType ;
       private String threeDSServerTransID ;
       private String acsTransID ;
       private String dsTransID ;
       private String merchantTransID ;
       private String eci ;
       private String authenticationValue ;
       private String transStatus ;
       private String errorCode ;
       private String errorDescription ;
       private String pmessageVersion ;
	public String getMessageVersion() {
		return messageVersion;
	}
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
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
	public String getEci() {
		return eci;
	}
	public void setEci(String eci) {
		this.eci = eci;
	}
	public String getAuthenticationValue() {
		return authenticationValue;
	}
	public void setAuthenticationValue(String authenticationValue) {
		this.authenticationValue = authenticationValue;
	}
	public String getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getPmessageVersion() {
		return pmessageVersion;
	}
	public void setPmessageVersion(String pmessageVersion) {
		this.pmessageVersion = pmessageVersion;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	@Override
	public String toString() {
		return "PRqFrqFinalResponse [messageVersion=" + messageVersion + ", messageType=" + messageType
				+ ", threeDSServerTransID=" + threeDSServerTransID + ", acsTransID=" + acsTransID + ", dsTransID="
				+ dsTransID + ", merchantTransID=" + merchantTransID + ", eci=" + eci + ", authenticationValue="
				+ authenticationValue + ", transStatus=" + transStatus + ", errorCode=" + errorCode
				+ ", errorDescription=" + errorDescription + ", pmessageVersion=" + pmessageVersion + "]";
	}
	
	
       
       
       
}
