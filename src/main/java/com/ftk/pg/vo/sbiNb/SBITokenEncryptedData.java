package com.ftk.pg.vo.sbiNb;

import com.ftk.pg.util.Util;

public class SBITokenEncryptedData {

	private String pan;
	private String expiryMonth;
	private String expiryYear;
	private String email;
	private String securityCode;
	private String customerName;
	private String address1;
	private String address2;
	private String postalCode;
	private String city;
	private String state;
	private String countryCode;
	private String mobile;

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public String getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "SBITokenEncryptedData [pan=" + Util.maskStringValue(pan) + ", expiryMonth="
				+ Util.maskStringValue(expiryMonth) + ", expiryYear=" + Util.maskStringValue(expiryYear) + ", email="
				+ email + ", securityCode=" + Util.maskStringValue(securityCode) + ", customerName=" + customerName
				+ ", address1=" + address1 + ", address2=" + address2 + ", postalCode=" + postalCode + ", city=" + city
				+ ", state=" + state + ", countryCode=" + countryCode + ", mobile=" + mobile + "]";
	}

}
