package com.ftk.pg.requestvo;

public class NorthAcrossRequestWrapper {
	private String api_key;
	private String encrypted_data;
	public String getApi_key() {
		return api_key;
	}
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	public String getEncrypted_data() {
		return encrypted_data;
	}
	public void setEncrypted_data(String encrypted_data) {
		this.encrypted_data = encrypted_data;
	}
	@Override
	public String toString() {
		return "IDFCRequestWrapper [api_key=" + api_key + ", encrypted_data=" + encrypted_data + "]";
	}
	

}
