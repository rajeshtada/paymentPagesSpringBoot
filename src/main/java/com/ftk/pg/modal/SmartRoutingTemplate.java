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
@Table(name = "smart_routing_template")
public class SmartRoutingTemplate implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "template_id")
	private Long templateId;

	@Column(name = "product_type")
	private String productType;
	
	@Column(name = "payment_mode")
	private String paymentMode;
	
	@Column(name = "bank_id")
	private Long bankId;
	
	@Column(name = "bins")
	private String bins;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "setting_type")
	private String settingType;
	
	@Column(name = "card_type")
	private String cardType;
	
	@Column(name = "card_category")
	private String cardCategory;
	
	@Column(name = "priority_1")
	private String priority1;
	
	@Column(name = "priority_2")
	private String priority2;
	
	@Column(name = "priority_3")
	private String priority3;
	
	@Column(name = "status")
	private int status=0;

	public String getCardCategory() {
		return cardCategory;
	}

	public void setCardCategory(String cardCategory) {
		this.cardCategory = cardCategory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBins() {
		return bins;
	}

	public void setBins(String bins) {
		this.bins = bins;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSettingType() {
		return settingType;
	}

	public void setSettingType(String settingType) {
		this.settingType = settingType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getPriority1() {
		return priority1;
	}

	public void setPriority1(String priority1) {
		this.priority1 = priority1;
	}

	public String getPriority2() {
		return priority2;
	}

	public void setPriority2(String priority2) {
		this.priority2 = priority2;
	}

	public String getPriority3() {
		return priority3;
	}

	public void setPriority3(String priority3) {
		this.priority3 = priority3;
	}

	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "SmartRoutingTemplate [id=" + id + ", templateId=" + templateId + ", productType=" + productType
				+ ", paymentMode=" + paymentMode + ", bankId=" + bankId + ", bins=" + bins + ", currency=" + currency
				+ ", settingType=" + settingType + ", cardType=" + cardType + ", cardCategory=" + cardCategory
				+ ", priority1=" + priority1 + ", priority2=" + priority2 + ", priority3=" + priority3 + ", status="
				+ status + "]";
	}

}
