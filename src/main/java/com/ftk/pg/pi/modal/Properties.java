package com.ftk.pg.pi.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "properties")
public class Properties {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long mid;
	private String processor;
	private String currency;
	private String paymode;
	private String product;
	private String setting1; // payment_url
	private String setting2; // Return_url
	private String setting3; // Login
	private String setting4; // Password
	private String setting5; // product_id
	private String setting6; // request_hash_key
	private String setting7; // response_hash_key
	private String setting8; // client_code
	private String setting9; // private_key
	private String setting10; // type
	private String setting11;
	private String setting12;
	private String setting13;
	private String setting14; //Used to enable the dynamic qr
	private String setting15;
	@Column(name = "payment_slip_no")
	private Long paymentSlipNo;

	@Column(name = "email_enable")
//	@ColumnDefault("0")
	private boolean emailEnable;
//	@ColumnDefault("0")
	private Boolean status;

	// merchant email setting
	@Column(name = "email_id")
	private String emailId;

	@Column(name = "email_password")
	private String emailPassword;

	@Column(name = "host_key")
	private String hostKey;

	@Column(name = "host_value")
	private String hostValue;

	@Column(name = "socket_port_key")
	private String socketPortKey;

	@Column(name = "socket_port_value")
	private String socketPortValue;

	@Column(name = "socket_class_key")
	private String socketClassKey;

	@Column(name = "socket_class_value")
	private String socketClassValue;

	@Column(name = "auth_key")
	private String authKey;

	@Column(name = "auth_value")
	private String authValue;

	@Column(name = "port_key")
	private String portKey;

	@Column(name = "port_value")
	private String portValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPaymode() {
		return paymode;
	}

	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getSetting1() {
		return setting1;
	}

	public void setSetting1(String setting1) {
		this.setting1 = setting1;
	}

	public String getSetting2() {
		return setting2;
	}

	public void setSetting2(String setting2) {
		this.setting2 = setting2;
	}

	public String getSetting3() {
		return setting3;
	}

	public void setSetting3(String setting3) {
		this.setting3 = setting3;
	}

	public String getSetting4() {
		return setting4;
	}

	public void setSetting4(String setting4) {
		this.setting4 = setting4;
	}

	public String getSetting5() {
		return setting5;
	}

	public void setSetting5(String setting5) {
		this.setting5 = setting5;
	}

	public String getSetting6() {
		return setting6;
	}

	public void setSetting6(String setting6) {
		this.setting6 = setting6;
	}

	public String getSetting7() {
		return setting7;
	}

	public void setSetting7(String setting7) {
		this.setting7 = setting7;
	}

	public String getSetting8() {
		return setting8;
	}

	public void setSetting8(String setting8) {
		this.setting8 = setting8;
	}

	public String getSetting9() {
		return setting9;
	}

	public void setSetting9(String setting9) {
		this.setting9 = setting9;
	}

	public String getSetting10() {
		return setting10;
	}

	public void setSetting10(String setting10) {
		this.setting10 = setting10;
	}

	public String getSetting11() {
		return setting11;
	}

	public void setSetting11(String setting11) {
		this.setting11 = setting11;
	}

	public String getSetting12() {
		return setting12;
	}

	public void setSetting12(String setting12) {
		this.setting12 = setting12;
	}

	public String getSetting13() {
		return setting13;
	}

	public void setSetting13(String setting13) {
		this.setting13 = setting13;
	}

	public String getSetting14() {
		return setting14;
	}

	public void setSetting14(String setting14) {
		this.setting14 = setting14;
	}

	public String getSetting15() {
		return setting15;
	}

	public void setSetting15(String setting15) {
		this.setting15 = setting15;
	}

	public boolean isEmailEnable() {
		return emailEnable;
	}

	public void setEmailEnable(boolean emailEnable) {
		this.emailEnable = emailEnable;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getHostKey() {
		return hostKey;
	}

	public void setHostKey(String hostKey) {
		this.hostKey = hostKey;
	}

	public String getHostValue() {
		return hostValue;
	}

	public void setHostValue(String hostValue) {
		this.hostValue = hostValue;
	}

	public String getSocketPortKey() {
		return socketPortKey;
	}

	public void setSocketPortKey(String socketPortKey) {
		this.socketPortKey = socketPortKey;
	}

	public String getSocketPortValue() {
		return socketPortValue;
	}

	public void setSocketPortValue(String socketPortValue) {
		this.socketPortValue = socketPortValue;
	}

	public String getSocketClassKey() {
		return socketClassKey;
	}

	public void setSocketClassKey(String socketClassKey) {
		this.socketClassKey = socketClassKey;
	}

	public String getSocketClassValue() {
		return socketClassValue;
	}

	public void setSocketClassValue(String socketClassValue) {
		this.socketClassValue = socketClassValue;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getAuthValue() {
		return authValue;
	}

	public void setAuthValue(String authValue) {
		this.authValue = authValue;
	}

	public String getPortKey() {
		return portKey;
	}

	public void setPortKey(String portKey) {
		this.portKey = portKey;
	}

	public String getPortValue() {
		return portValue;
	}

	public void setPortValue(String portValue) {
		this.portValue = portValue;
	}

	public Long getPaymentSlipNo() {
		return paymentSlipNo;
	}

	public void setPaymentSlipNo(Long paymentSlipNo) {
		this.paymentSlipNo = paymentSlipNo;
	}

	@Override
	public String toString() {
		return "Properties [id=" + id + ", mid=" + mid + ", processor=" + processor + ", currency=" + currency
				+ ", paymode=" + paymode + ", product=" + product + ", setting1=" + setting1 + ", setting2=" + setting2
				+ ", setting3=" + setting3 + ", setting4=" + setting4 + ", setting5=" + setting5 + ", setting6="
				+ setting6 + ", setting7=" + setting7 + ", setting8=" + setting8 + ", setting9=" + setting9
				+ ", setting10=" + setting10 + ", setting11=" + setting11 + ", setting12=" + setting12 + ", setting13="
				+ setting13 + ", setting14=" + setting14 + ", setting15=" + setting15 + ", paymentSlipNo="
				+ paymentSlipNo + ", emailEnable=" + emailEnable + ", status=" + status + ", emailId=" + emailId
				+ ", emailPassword=" + emailPassword + ", hostKey=" + hostKey + ", hostValue=" + hostValue
				+ ", socketPortKey=" + socketPortKey + ", socketPortValue=" + socketPortValue + ", socketClassKey="
				+ socketClassKey + ", socketClassValue=" + socketClassValue + ", authKey=" + authKey + ", authValue="
				+ authValue + ", portKey=" + portKey + ", portValue=" + portValue + "]";
	}

}
