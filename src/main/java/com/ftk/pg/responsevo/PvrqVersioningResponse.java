package com.ftk.pg.responsevo;

import java.util.List;

public class PvrqVersioningResponse {
	
	private  String messageType;
	private String deviceChannel;
	private String p_messageVersion;
	private String acsStartVersion;
	private  String acsEndVersion;
	private String dsStartVersion;
	private String dsEndVersion;
	private String merchantTransID;
	private  String threeDSServerPaRqURL;
	private String threeDSServerTransID;
	private String threeDSACSMethodURL;
	private String threeDSJSBrowserDetailFetchURL;	
    private ThreeDSMethodDataFormPost threeDSMethodDataFormPost;
    
    private List<String> acsInfoInd;
    
    public String scheme;
    public String errorCode;
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
	public String getP_messageVersion() {
		return p_messageVersion;
	}
	public void setP_messageVersion(String p_messageVersion) {
		this.p_messageVersion = p_messageVersion;
	}
	public String getAcsStartVersion() {
		return acsStartVersion;
	}
	public void setAcsStartVersion(String acsStartVersion) {
		this.acsStartVersion = acsStartVersion;
	}
	public String getAcsEndVersion() {
		return acsEndVersion;
	}
	public void setAcsEndVersion(String acsEndVersion) {
		this.acsEndVersion = acsEndVersion;
	}
	public String getDsStartVersion() {
		return dsStartVersion;
	}
	public void setDsStartVersion(String dsStartVersion) {
		this.dsStartVersion = dsStartVersion;
	}
	public String getDsEndVersion() {
		return dsEndVersion;
	}
	public void setDsEndVersion(String dsEndVersion) {
		this.dsEndVersion = dsEndVersion;
	}
	public String getMerchantTransID() {
		return merchantTransID;
	}
	public void setMerchantTransID(String merchantTransID) {
		this.merchantTransID = merchantTransID;
	}
	public String getThreeDSServerPaRqURL() {
		return threeDSServerPaRqURL;
	}
	public void setThreeDSServerPaRqURL(String threeDSServerPaRqURL) {
		this.threeDSServerPaRqURL = threeDSServerPaRqURL;
	}
	public String getThreeDSServerTransID() {
		return threeDSServerTransID;
	}
	public void setThreeDSServerTransID(String threeDSServerTransID) {
		this.threeDSServerTransID = threeDSServerTransID;
	}
	public String getThreeDSACSMethodURL() {
		return threeDSACSMethodURL;
	}
	public void setThreeDSACSMethodURL(String threeDSACSMethodURL) {
		this.threeDSACSMethodURL = threeDSACSMethodURL;
	}
	public String getThreeDSJSBrowserDetailFetchURL() {
		return threeDSJSBrowserDetailFetchURL;
	}
	public void setThreeDSJSBrowserDetailFetchURL(String threeDSJSBrowserDetailFetchURL) {
		this.threeDSJSBrowserDetailFetchURL = threeDSJSBrowserDetailFetchURL;
	}
	public ThreeDSMethodDataFormPost getThreeDSMethodDataFormPost() {
		return threeDSMethodDataFormPost;
	}
	public void setThreeDSMethodDataFormPost(ThreeDSMethodDataFormPost threeDSMethodDataFormPost) {
		this.threeDSMethodDataFormPost = threeDSMethodDataFormPost;
	}
	public List<String> getAcsInfoInd() {
		return acsInfoInd;
	}
	public void setAcsInfoInd(List<String> acsInfoInd) {
		this.acsInfoInd = acsInfoInd;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	@Override
	public String toString() {
		return "PvrqVersioningResponse [messageType=" + messageType + ", deviceChannel=" + deviceChannel
				+ ", p_messageVersion=" + p_messageVersion + ", acsStartVersion=" + acsStartVersion + ", acsEndVersion="
				+ acsEndVersion + ", dsStartVersion=" + dsStartVersion + ", dsEndVersion=" + dsEndVersion
				+ ", merchantTransID=" + merchantTransID + ", threeDSServerPaRqURL=" + threeDSServerPaRqURL
				+ ", threeDSServerTransID=" + threeDSServerTransID + ", threeDSACSMethodURL=" + threeDSACSMethodURL
				+ ", threeDSJSBrowserDetailFetchURL=" + threeDSJSBrowserDetailFetchURL + ", threeDSMethodDataFormPost="
				+ threeDSMethodDataFormPost + ", acsInfoInd=" + acsInfoInd + ", scheme=" + scheme + ", errorCode="
				+ errorCode + "]";
	}
	
	
    
    
	
	

}
