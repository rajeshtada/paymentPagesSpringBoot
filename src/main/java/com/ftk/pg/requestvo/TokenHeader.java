package com.ftk.pg.requestvo;

public class TokenHeader {
	
	private String clientApiUser;
	private String clientApiKey;
	private String AuthToken;
	private String clientId;
	
	public String getClientApiUser() {
		return clientApiUser;
	}
	public void setClientApiUser(String clientApiUser) {
		this.clientApiUser = clientApiUser;
	}
	public String getClientApiKey() {
		return clientApiKey;
	}
	public void setClientApiKey(String clientApiKey) {
		this.clientApiKey = clientApiKey;
	}
	public String getAuthToken() {
		return AuthToken;
	}
	public void setAuthToken(String authToken) {
		AuthToken = authToken;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	@Override
	public String toString() {
		return "TokenHader [clientApiUser=" + clientApiUser + ", clientApiKey=" + clientApiKey + ", AuthToken="
				+ AuthToken + ", clientId=" + clientId + "]";
		
	}
	
}
