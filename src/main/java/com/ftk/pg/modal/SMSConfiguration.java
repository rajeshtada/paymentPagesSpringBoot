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
@Table(name="sms_configuration")
public class SMSConfiguration implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="merchant_name")
	private String mName;
	
	@Column(name="trigger_code")
	private String triggerCode;
	
	@Column(name="is_Merhant")
	private Boolean isMerchant =false;
	
	@Column(name="is_Customer")
	private Boolean isCustomer =false;
	
	//TODO
	//@Type(type="text")
	@Column(name="sms_body")
	private String smsBody;
	
	private Long mid;
	
	

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getTriggerCode() {
		return triggerCode;
	}

	public void setTriggerCode(String triggerCode) {
		this.triggerCode = triggerCode;
	}

	public Boolean getIsMerchant() {
		return isMerchant;
	}

	public void setIsMerchant(Boolean isMerchant) {
		this.isMerchant = isMerchant;
	}

	public Boolean getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(Boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	public String getSmsBody() {
		return smsBody;
	}

	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}

	@Override
	public String toString() {
		return "SMSConfiguration [id=" + id + ", mName=" + mName + ", triggerCode=" + triggerCode + ", isMerchant="
				+ isMerchant + ", isCustomer=" + isCustomer + ", smsBody=" + smsBody + ", mid=" + mid + "]";
	}

	
}
