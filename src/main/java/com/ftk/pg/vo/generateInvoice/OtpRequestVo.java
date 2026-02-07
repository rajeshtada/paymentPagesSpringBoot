package com.ftk.pg.vo.generateInvoice;


public class OtpRequestVo {

//	@NotEmpty(message = "Please Provide Email-Id")
//	@Pattern(regexp = "^$|[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})", message = "Please Provide Valid Email ID")
	private String email;

//	@NotEmpty(message = "Please Provide Mobile Number")
//	@Pattern(regexp = "(^$|[0-9]{10})", message = "Please Provide 10 digit Mobile Number")
	private String mobileNo;

//	@NotEmpty(message = "Signature can't be null.")
	private String signature;

	private String url;
	private String username;
	private String password;
	private String sender;
	private String message;
	private String mid;

	public OtpRequestVo() {

	}

	public OtpRequestVo(String url, String username, String password, String sender, String mobileNo, String message) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.sender = sender;
		this.mobileNo = mobileNo;
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	@Override
	public String toString() {
		return "OtpRequestVo [email=" + email + ", mobileNo=" + mobileNo + ", signature=" + signature + ", url=" + url
				+ ", username=" + username + ", password=" + password + ", sender=" + sender + ", message=" + message
				+ ", mid=" + mid + "]";
	}

}
