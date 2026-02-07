package com.ftk.pg.responsevo;

public class ParqAuthenticationResponse {
    private String messageVersion ;
    private String p_messageVersion ;
    private String threeDSServerTransID ;
    private String threeDSServerRefNumber ;
    private String acsTransID ;
    private String acsReferenceNumber ;
    private String dsReferenceNumber ;
    private String dsTransID ;
    private String messageType ;
    private String authenticationValue;
    private String eci;
    private String transStatus ;
    private String acsChallengeMandated ;
    private String acsChallengeReqUrl ;
    private String authenticationType ;
    private String messageCategory ;
    private String merchantTransID ;
    private String cReq ;
    private String authenticationUrl ;
    private AcsChallengeReqFormData acsChallengeReqFormData;
	public String getMessageVersion() {
		return messageVersion;
	}
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}
	public String getP_messageVersion() {
		return p_messageVersion;
	}
	public void setP_messageVersion(String p_messageVersion) {
		this.p_messageVersion = p_messageVersion;
	}
	public String getThreeDSServerTransID() {
		return threeDSServerTransID;
	}
	public void setThreeDSServerTransID(String threeDSServerTransID) {
		this.threeDSServerTransID = threeDSServerTransID;
	}
	public String getThreeDSServerRefNumber() {
		return threeDSServerRefNumber;
	}
	public void setThreeDSServerRefNumber(String threeDSServerRefNumber) {
		this.threeDSServerRefNumber = threeDSServerRefNumber;
	}
	public String getAcsTransID() {
		return acsTransID;
	}
	public void setAcsTransID(String acsTransID) {
		this.acsTransID = acsTransID;
	}
	public String getAcsReferenceNumber() {
		return acsReferenceNumber;
	}
	public void setAcsReferenceNumber(String acsReferenceNumber) {
		this.acsReferenceNumber = acsReferenceNumber;
	}
	public String getDsReferenceNumber() {
		return dsReferenceNumber;
	}
	public void setDsReferenceNumber(String dsReferenceNumber) {
		this.dsReferenceNumber = dsReferenceNumber;
	}
	public String getDsTransID() {
		return dsTransID;
	}
	public void setDsTransID(String dsTransID) {
		this.dsTransID = dsTransID;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getAuthenticationValue() {
		return authenticationValue;
	}
	public void setAuthenticationValue(String authenticationValue) {
		this.authenticationValue = authenticationValue;
	}
	public String getEci() {
		return eci;
	}
	public void setEci(String eci) {
		this.eci = eci;
	}
	public String getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	public String getAcsChallengeMandated() {
		return acsChallengeMandated;
	}
	public void setAcsChallengeMandated(String acsChallengeMandated) {
		this.acsChallengeMandated = acsChallengeMandated;
	}
	public String getAcsChallengeReqUrl() {
		return acsChallengeReqUrl;
	}
	public void setAcsChallengeReqUrl(String acsChallengeReqUrl) {
		this.acsChallengeReqUrl = acsChallengeReqUrl;
	}
	public String getAuthenticationType() {
		return authenticationType;
	}
	public void setAuthenticationType(String authenticationType) {
		this.authenticationType = authenticationType;
	}
	public String getMessageCategory() {
		return messageCategory;
	}
	public void setMessageCategory(String messageCategory) {
		this.messageCategory = messageCategory;
	}
	public String getMerchantTransID() {
		return merchantTransID;
	}
	public void setMerchantTransID(String merchantTransID) {
		this.merchantTransID = merchantTransID;
	}
	public String getcReq() {
		return cReq;
	}
	public void setcReq(String cReq) {
		this.cReq = cReq;
	}
	public String getAuthenticationUrl() {
		return authenticationUrl;
	}
	public void setAuthenticationUrl(String authenticationUrl) {
		this.authenticationUrl = authenticationUrl;
	}
	public AcsChallengeReqFormData getAcsChallengeReqFormData() {
		return acsChallengeReqFormData;
	}
	public void setAcsChallengeReqFormData(AcsChallengeReqFormData acsChallengeReqFormData) {
		this.acsChallengeReqFormData = acsChallengeReqFormData;
	}
	@Override
	public String toString() {
		return "ParqAuthenticationResponse [messageVersion=" + messageVersion + ", p_messageVersion=" + p_messageVersion
				+ ", threeDSServerTransID=" + threeDSServerTransID + ", threeDSServerRefNumber="
				+ threeDSServerRefNumber + ", acsTransID=" + acsTransID + ", acsReferenceNumber=" + acsReferenceNumber
				+ ", dsReferenceNumber=" + dsReferenceNumber + ", dsTransID=" + dsTransID + ", messageType="
				+ messageType + ", authenticationValue=" + authenticationValue + ", eci=" + eci + ", transStatus="
				+ transStatus + ", acsChallengeMandated=" + acsChallengeMandated + ", acsChallengeReqUrl="
				+ acsChallengeReqUrl + ", authenticationType=" + authenticationType + ", messageCategory="
				+ messageCategory + ", merchantTransID=" + merchantTransID + ", cReq=" + cReq + ", authenticationUrl="
				+ authenticationUrl + ", acsChallengeReqFormData=" + acsChallengeReqFormData + "]";
	}
	
   

}
