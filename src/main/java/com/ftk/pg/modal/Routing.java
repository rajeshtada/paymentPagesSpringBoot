package com.ftk.pg.modal;

import java.io.Serializable;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Audited
@Table(name = "setting_routing")
public class Routing implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "routing_id")
	private Long routingId;

	@Column(name = "default_setting_id")
	private Long defaultSettingId;

	@Column(name = "currency")
	private String currency;

	@Column(name = "payment_mode")
	private String paymentMode;

	@Column(name = "payment_type")
	private String paymentType;

	private boolean status;

	@Column(name = "bins")
	private String bins;

	@Column(name = "bank")
	private Long bank;

	// mid given by Atom eg:197
	@Column(name = "merchant_login_id")
	private String mloginId;

	@Column(name = "processor")
	private String processor;

	@Column(name = "setting_1")
	private String setting1;

	@Column(name = "setting_2")
	private String setting2;

	@Column(name = "setting_3")
	private String setting3;

	@Column(name = "setting_4")
	private String setting4;

	@Column(name = "setting_5")
	private String setting5;

	@Column(name = "setting_6")
	private String setting6;

	@Column(name = "setting_7")
	private String setting7;

	@Column(name = "setting_8")
	private String setting8;

	@Column(name = "setting_9")
	private String setting9;

	@Column(name = "setting_10")
	private String setting10;

	@Column(name = "is_default")
	private boolean isDefault;

	@Column(name = "merchant_password")
	private String mPassword;

	@Column(name = "product_type")
	private String productType;

	public String getmPassword() {
		return mPassword;
	}

	public void setmPassword(String mPassword) {
		this.mPassword = mPassword;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getBins() {
		return bins;
	}

	public void setBins(String bins) {
		this.bins = bins;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getMloginId() {
		return mloginId;
	}

	public void setMloginId(String mloginId) {
		this.mloginId = mloginId;
	}

	public Long getBank() {
		return bank;
	}

	public void setBank(Long bank) {
		this.bank = bank;
	}

	public Long getRoutingId() {
		return routingId;
	}

	public void setRoutingId(Long routingId) {
		this.routingId = routingId;
	}

	public Long getDefaultSettingId() {
		return defaultSettingId;
	}

	public void setDefaultSettingId(Long defaultSettingId) {
		this.defaultSettingId = defaultSettingId;
	}

	@Override
	public String toString() {
		return "Routing [routingId=" + routingId + ", defaultSettingId=" + defaultSettingId + ", currency=" + currency
				+ ", paymentMode=" + paymentMode + ", paymentType=" + paymentType + ", status=" + status + ", bins="
				+ bins + ", bank=" + bank + ", mloginId=" + mloginId + ", processor=" + processor + ", setting1="
				+ setting1 + ", setting2=" + setting2 + ", setting3=" + setting3 + ", setting4=" + setting4
				+ ", setting5=" + setting5 + ", setting6=" + setting6 + ", setting7=" + setting7 + ", setting8="
				+ setting8 + ", setting9=" + setting9 + ", setting10=" + setting10 + ", isDefault=" + isDefault
				+ ", mPassword=" + mPassword + ", productType=" + productType + "]";
	}

}
