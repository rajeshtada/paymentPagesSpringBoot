package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dmo_onboarding")
public class DmoOnboarding implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "message")
	private String message;

	@Column(name = "parent_merchant_id")
	private Long parentMerchantID;

	@Column(name = "eazy_pay_merchant_id")
	private Long eazypayMerchantID;

	@Column(name = "profile_id")
	private String profileID;

	@Column(name = "device_id")
	private String deviceID;

	@Column(name = "act_code")
	private String actCode;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "success")
	private Boolean success;

	@Column(name = "vpa_white_listed")
	private String vpaWhitelisted;

	@Column(name = "vpa")
	private String vpa;
	
	@Column(name = "allowCc")
	private String allowCc;

	@Column(name = "allowOd")
	private String allowOd;
	
	@Column(name = "allowPpi")
	private String allowPpi;
	
	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	@Column(name = "mcc_code")
	private String mccCode;
	
	@Column(name = "merchant_genre")
	private String merchantGenre;
	
	@Column(name = "merchant_class")
    private String merchantClass;
	
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getParentMerchantID() {
		return parentMerchantID;
	}

	public void setParentMerchantID(Long parentMerchantID) {
		this.parentMerchantID = parentMerchantID;
	}

	public Long getEazypayMerchantID() {
		return eazypayMerchantID;
	}

	public void setEazypayMerchantID(Long eazypayMerchantID) {
		this.eazypayMerchantID = eazypayMerchantID;
	}

	public String getProfileID() {
		return profileID;
	}

	public void setProfileID(String profileID) {
		this.profileID = profileID;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getActCode() {
		return actCode;
	}

	public void setActCode(String actCode) {
		this.actCode = actCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getVpaWhitelisted() {
		return vpaWhitelisted;
	}

	public void setVpaWhitelisted(String vpaWhitelisted) {
		this.vpaWhitelisted = vpaWhitelisted;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public String getAllowCc() {
		return allowCc;
	}

	public void setAllowCc(String allowCc) {
		this.allowCc = allowCc;
	}

	public String getAllowOd() {
		return allowOd;
	}

	public void setAllowOd(String allowOd) {
		this.allowOd = allowOd;
	}

	public String getAllowPpi() {
		return allowPpi;
	}

	public void setAllowPpi(String allowPpi) {
		this.allowPpi = allowPpi;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getMccCode() {
		return mccCode;
	}

	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}

	public String getMerchantGenre() {
		return merchantGenre;
	}

	public void setMerchantGenre(String merchantGenre) {
		this.merchantGenre = merchantGenre;
	}

	public String getMerchantClass() {
		return merchantClass;
	}

	public void setMerchantClass(String merchantClass) {
		this.merchantClass = merchantClass;
	}

	@Override
	public String toString() {
		return "DmoOnboarding [id=" + id + ", mid=" + mid + ", message=" + message + ", parentMerchantID="
				+ parentMerchantID + ", eazypayMerchantID=" + eazypayMerchantID + ", profileID=" + profileID
				+ ", deviceID=" + deviceID + ", actCode=" + actCode + ", mobileNumber=" + mobileNumber + ", success="
				+ success + ", vpaWhitelisted=" + vpaWhitelisted + ", vpa=" + vpa + ", allowCc=" + allowCc
				+ ", allowOd=" + allowOd + ", allowPpi=" + allowPpi + ", createdDate=" + createdDate + ", modifiedDate="
				+ modifiedDate + ", mccCode=" + mccCode + ", merchantGenre=" + merchantGenre + ", merchantClass="
				+ merchantClass + "]";
	}

	

}
