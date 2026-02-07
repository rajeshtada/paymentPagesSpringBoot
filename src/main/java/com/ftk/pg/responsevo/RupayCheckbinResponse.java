package com.ftk.pg.responsevo;

public class RupayCheckbinResponse {

	private String status;
	private String errorcode;
	private String qualifiedInternetpin;
	private String authenticationNotRequired;
	private String availableAuthMode;
	private String additionalProductsSupported;
	private String errormsg;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getQualifiedInternetpin() {
		return qualifiedInternetpin;
	}
	public void setQualifiedInternetpin(String qualifiedInternetpin) {
		this.qualifiedInternetpin = qualifiedInternetpin;
	}
	public String getAuthenticationNotRequired() {
		return authenticationNotRequired;
	}
	public void setAuthenticationNotRequired(String authenticationNotRequired) {
		this.authenticationNotRequired = authenticationNotRequired;
	}
	public String getAvailableAuthMode() {
		return availableAuthMode;
	}
	public void setAvailableAuthMode(String availableAuthMode) {
		this.availableAuthMode = availableAuthMode;
	}
	public String getAdditionalProductsSupported() {
		return additionalProductsSupported;
	}
	public void setAdditionalProductsSupported(String additionalProductsSupported) {
		this.additionalProductsSupported = additionalProductsSupported;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	@Override
	public String toString() {
		return "RupayCheckbinResponse [status=" + status + ", errorcode=" + errorcode + ", qualifiedInternetpin="
				+ qualifiedInternetpin + ", authenticationNotRequired=" + authenticationNotRequired
				+ ", availableAuthMode=" + availableAuthMode + ", additionalProductsSupported="
				+ additionalProductsSupported + ", errormsg=" + errormsg + "]";
	}

	
	
	
	
}
