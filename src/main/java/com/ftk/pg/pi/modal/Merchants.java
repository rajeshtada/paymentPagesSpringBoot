package com.ftk.pg.pi.modal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "merchants")
public class Merchants {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long mid;

	@Column(name = "group_id")
	private Long groupId;

	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "mobile_number")
	private String mobileNumber;

	private String address;

	private String country;

	private String city;

	private String state;

	private String pincode;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "left_logo")
	private String leftLogo;

	@Column(name = "right_logo")
	private String rightLogo;

	@Column(name = "bank_partner")
	@ColumnDefault(value = "0")
	private Boolean bankPartner;

	@Column(name = "category_id")
	private Long categoryId;

	@Column(name = "channel_partner_id")
	private Long channelPartnerId;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "status")
	@ColumnDefault(value = "0")
	private Boolean status;

	@Column(name = "annual_start_date")
	private LocalDate annualStartDate;

	@Column(name = "annual_end_date")
	private LocalDate annualEndDate;

	@Column(name = "enable_b2c", columnDefinition = "boolean default false")
//	@DefaultValue(value = "0")
	private Boolean enableB2C;

	@Column(name = "enable_rest", columnDefinition = "boolean default false")
//	@DefaultValue(value = "0")
	private Boolean enableRest;

	@Column(name = "enable_vpa")
	private Boolean enableVpa;

	@Column(name = "vpa_id")
	private String vpaId;

	@Column(name = "vpa_name")
	private String vpaName;

	@Column(name = "vpa_qr_path")
	private String vpaQrPath;

	@Column(name = "vpa_mid")
	private String vpaMid;

	@Column(name = "enable_emp_qr")
	private Boolean enableEmpQr;

	@Column(name = "supp_language")
	private String suppLanguage; // english, hindi

	// merchant digital signature
	@Column(name = "digital_signature")
	private String digitalSignature;

	// Bank Details
	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "branch_name")
	private String branchName;

	@Column(name = "accountName")
	private String accountName;

	@Column(name = "accountNumber")
	private String accountNumber;

	@Column(name = "ifsc_code")
	private String ifscCode;

	@Column(name = "msg_flag")
	private Boolean msgFlag; // o for inactive and 1 for active

	@Column(name = "notification_flag", columnDefinition = "boolean default false")
//	@DefaultValue(value = "0")
	private Boolean notificationFlag; // o for inactive and 1 for active

	@Column(name = "email_flag", columnDefinition = "boolean default false")
//	@DefaultValue(value = "0")
	private Boolean emailFlag; // o for inactive and 1 for active

	@Column(name = "merchant_id")
	private Long merchantId;

	@Column(name = "designation")
	private String designation;

//	@Column(name = "merchant_type", nullable = false, columnDefinition = "int default 0")
//	private int merchantType; // 0 for restro and 1 for kirana

	@Column(name = "merchant_type")
	private String merchantType;

	@Column(name = "business_type")
	private String businessType;

	@Column(name = "gst_no")
	private String gstNo;

	@Column(name = "pan_no")
	private String panNo;

	@Column(name = "store_sms_flag", columnDefinition = "boolean default false")
	private Boolean storeSMSFlag; // 0 for enable and 1 for disable

	@Column(name = "self_onboard")
	@ColumnDefault(value = "0")
	private int selfOnboard; // 0 for merchants by default or 1 for wheel merchants

	@Column(name = "privacy_consent", columnDefinition = "boolean default false")
	private Boolean privacyConsent;

	@Column(name = "settlement_setting")
	private String settlementSetting;

	public Merchants() {
		this.merchantType = "0";
	}

	public String getLeftLogo() {
		return leftLogo;
	}

	public void setLeftLogo(String leftLogo) {
		this.leftLogo = leftLogo;
	}

	public String getRightLogo() {
		return rightLogo;
	}

	public void setRightLogo(String rightLogo) {
		this.rightLogo = rightLogo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Boolean getBankPartner() {
		return bankPartner;
	}

	public void setBankPartner(Boolean bankPartner) {
		this.bankPartner = bankPartner;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getChannelPartnerId() {
		return channelPartnerId;
	}

	public void setChannelPartnerId(Long channelPartnerId) {
		this.channelPartnerId = channelPartnerId;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public LocalDate getAnnualStartDate() {
		return annualStartDate;
	}

	public void setAnnualStartDate(LocalDate annualStartDate) {
		this.annualStartDate = annualStartDate;
	}

	public LocalDate getAnnualEndDate() {
		return annualEndDate;
	}

	public void setAnnualEndDate(LocalDate annualEndDate) {
		this.annualEndDate = annualEndDate;
	}

	public Boolean getEnableB2C() {
		return enableB2C;
	}

	public void setEnableB2C(Boolean enableB2C) {
		this.enableB2C = enableB2C;
	}

	public Boolean getEnableRest() {
		return enableRest;
	}

	public void setEnableRest(Boolean enableRest) {
		this.enableRest = enableRest;
	}

	public Boolean getEnableVpa() {
		return enableVpa;
	}

	public void setEnableVpa(Boolean enableVpa) {
		this.enableVpa = enableVpa;
	}

	public String getVpaId() {
		return vpaId;
	}

	public void setVpaId(String vpaId) {
		this.vpaId = vpaId;
	}

	public String getVpaName() {
		return vpaName;
	}

	public void setVpaName(String vpaName) {
		this.vpaName = vpaName;
	}

	public String getVpaQrPath() {
		return vpaQrPath;
	}

	public void setVpaQrPath(String vpaQrPath) {
		this.vpaQrPath = vpaQrPath;
	}

	public String getVpaMid() {
		return vpaMid;
	}

	public void setVpaMid(String vpaMid) {
		this.vpaMid = vpaMid;
	}

	public Boolean getEnableEmpQr() {
		return enableEmpQr;
	}

	public void setEnableEmpQr(Boolean enableEmpQr) {
		this.enableEmpQr = enableEmpQr;
	}

	public String getSuppLanguage() {
		return suppLanguage;
	}

	public void setSuppLanguage(String suppLanguage) {
		this.suppLanguage = suppLanguage;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getDigitalSignature() {
		return digitalSignature;
	}

	public void setDigitalSignature(String digitalSignature) {
		this.digitalSignature = digitalSignature;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Boolean getMsgFlag() {
		return msgFlag;
	}

	public void setMsgFlag(Boolean msgFlag) {
		this.msgFlag = msgFlag;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Boolean getNotificationFlag() {
		return notificationFlag;
	}

	public void setNotificationFlag(Boolean notificationFlag) {
		this.notificationFlag = notificationFlag;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Boolean getEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(Boolean emailFlag) {
		this.emailFlag = emailFlag;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public Boolean getStoreSMSFlag() {
		return storeSMSFlag;
	}

	public void setStoreSMSFlag(Boolean storeSMSFlag) {
		this.storeSMSFlag = storeSMSFlag;
	}

	public int getSelfOnboard() {
		return selfOnboard;
	}

	public void setSelfOnboard(int selfOnboard) {
		this.selfOnboard = selfOnboard;
	}

	public Boolean getPrivacyConsent() {
		return privacyConsent;
	}

	public void setPrivacyConsent(Boolean privacyConsent) {
		this.privacyConsent = privacyConsent;
	}

	public String getSettlementSetting() {
		return settlementSetting;
	}

	public void setSettlementSetting(String settlementSetting) {
		this.settlementSetting = settlementSetting;
	}

	@Override
	public String toString() {
		return "Merchants [mid=" + mid + ", groupId=" + groupId + ", merchantName=" + merchantName + ", mobileNumber="
				+ mobileNumber + ", address=" + address + ", country=" + country + ", city=" + city + ", state=" + state
				+ ", pincode=" + pincode + ", userId=" + userId + ", leftLogo=" + leftLogo + ", rightLogo=" + rightLogo
				+ ", bankPartner=" + bankPartner + ", categoryId=" + categoryId + ", channelPartnerId="
				+ channelPartnerId + ", createdDate=" + createdDate + ", status=" + status + ", annualStartDate="
				+ annualStartDate + ", annualEndDate=" + annualEndDate + ", enableB2C=" + enableB2C + ", enableRest="
				+ enableRest + ", enableVpa=" + enableVpa + ", vpaId=" + vpaId + ", vpaName=" + vpaName + ", vpaQrPath="
				+ vpaQrPath + ", vpaMid=" + vpaMid + ", enableEmpQr=" + enableEmpQr + ", suppLanguage=" + suppLanguage
				+ ", digitalSignature=" + digitalSignature + ", bankName=" + bankName + ", branchName=" + branchName
				+ ", accountName=" + accountName + ", accountNumber=" + accountNumber + ", ifscCode=" + ifscCode
				+ ", msgFlag=" + msgFlag + ", notificationFlag=" + notificationFlag + ", emailFlag=" + emailFlag
				+ ", merchantId=" + merchantId + ", designation=" + designation + ", merchantType=" + merchantType
				+ ", businessType=" + businessType + ", gstNo=" + gstNo + ", panNo=" + panNo + ", storeSMSFlag="
				+ storeSMSFlag + ", selfOnboard=" + selfOnboard + ", privacyConsent=" + privacyConsent
				+ ", settlementSetting=" + settlementSetting + "]";
	}

}
