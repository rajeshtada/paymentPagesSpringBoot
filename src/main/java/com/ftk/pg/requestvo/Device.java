package com.ftk.pg.requestvo;

public class Device {
	private String deviceid;
	private String accept_header;
	private String init_channel;
	private String ip;
	private String user_agent;
	private String browser_language;
	private String browser_javascript_enabled;
	private String browser_tz;
	private String browser_color_depth;
	private String browser_java_enabled;
	private String browser_screen_height;
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getAccept_header() {
		return accept_header;
	}
	public void setAccept_header(String accept_header) {
		this.accept_header = accept_header;
	}
	public String getInit_channel() {
		return init_channel;
	}
	public void setInit_channel(String init_channel) {
		this.init_channel = init_channel;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUser_agent() {
		return user_agent;
	}
	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}
	public String getBrowser_language() {
		return browser_language;
	}
	public void setBrowser_language(String browser_language) {
		this.browser_language = browser_language;
	}
	public String getBrowser_javascript_enabled() {
		return browser_javascript_enabled;
	}
	public void setBrowser_javascript_enabled(String browser_javascript_enabled) {
		this.browser_javascript_enabled = browser_javascript_enabled;
	}
	public String getBrowser_tz() {
		return browser_tz;
	}
	public void setBrowser_tz(String browser_tz) {
		this.browser_tz = browser_tz;
	}
	public String getBrowser_color_depth() {
		return browser_color_depth;
	}
	public void setBrowser_color_depth(String browser_color_depth) {
		this.browser_color_depth = browser_color_depth;
	}
	public String getBrowser_java_enabled() {
		return browser_java_enabled;
	}
	public void setBrowser_java_enabled(String browser_java_enabled) {
		this.browser_java_enabled = browser_java_enabled;
	}
	public String getBrowser_screen_height() {
		return browser_screen_height;
	}
	public void setBrowser_screen_height(String browser_screen_height) {
		this.browser_screen_height = browser_screen_height;
	}
	@Override
	public String toString() {
		return "Device [deviceid=" + deviceid + ", accept_header=" + accept_header + ", init_channel=" + init_channel
				+ ", ip=" + ip + ", user_agent=" + user_agent + ", browser_language=" + browser_language
				+ ", browser_javascript_enabled=" + browser_javascript_enabled + ", browser_tz=" + browser_tz
				+ ", browser_color_depth=" + browser_color_depth + ", browser_java_enabled=" + browser_java_enabled
				+ ", browser_screen_height=" + browser_screen_height + "]";
	}
	
	
	
	
	

}
