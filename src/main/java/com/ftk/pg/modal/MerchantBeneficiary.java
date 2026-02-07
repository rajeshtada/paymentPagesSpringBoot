package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "merchant_beneficiary")
public class MerchantBeneficiary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	private Long mid;

	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "processor_name")
	private String processorName;

	@Column(name = "corp_id")
	private String corpId;

	@Column(name = "maker_id")
	private String makerId;

	@Column(name = "checker_id")
	private String checkerId;

	@Column(name = "bank_code")
	private String bankCode;

	@Column(name = "dateOfBirth")
	private Date dateOfBirth;

	@Column(name = "txn_particular")
	private String txnParticular;

	@Column(name = "txn_particular_remark")
	private String txnParticularRemark;

	@Column(name = "issue_branch_code")
	private String issueBranchCode;

	@Column(name = "branch_code")
	private String branchCode;

	@Column(name = "seller_code")
	private String sellerCode;

	@Column(name = "beneficiary_type")
	private String beneficiaryType;

	@Column(name = "settlement_term")
	private String settlementTerm;

	@Column(name = "commercial_term")
	private String commercialTerm;

	private String remark;

	private Integer action;

	@Column(name = "nodal_flag")
	private boolean nodalFlag;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "risk_parameter")
	private boolean riskParameter;

	@Column(name = "payment_mode")
	private String paymentMode;

	@Column(name = "daily_limit")
	private String dailyLimit;

	@Column(name = "weekly_limit")
	private String weeklyLimit;

	@Column(name = "monthly_limit")
	private String monthlyLimit;

	private Boolean status;

	private Boolean active;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "ifsc_code")
	private String ifscCode;

	private String state;

	private String city;

	private String address;

	private String pincode;

	private String email;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "pan_name")
	private String panName;

	@Column(name = "pan_number")
	private String panNumber;

	@Column(name = "pan_file")
	private String panFile;

	@Column(name = "aadhar_name")
	private String aadharName;

	@Column(name = "aadhar_number")
	private String aadharNumber;

	@Column(name = "aadhar_file")
	private String aadharFile;

	@Column(name = "res_status")
	private String resStatus;

	@Column(name = "error_code")
	private String errorCode;

	@Column(name = "error_desc")
	private String errorDesc;

	@Column(name = "ben_id")
	private String benId;

	@Column(name = "ben_name")
	private String benName;

	@Column(name = "corp_name")
	public String corpName;

	@Column(name = "res_remark")
	private String resRemark;

	@Column(name = "ref_no")
	private String refNo;

	@Column(name = "txn_time")
	private LocalDateTime txnTime;

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

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getProcessorName() {
		return processorName;
	}

	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	public String getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getTxnParticular() {
		return txnParticular;
	}

	public void setTxnParticular(String txnParticular) {
		this.txnParticular = txnParticular;
	}

	public String getTxnParticularRemark() {
		return txnParticularRemark;
	}

	public void setTxnParticularRemark(String txnParticularRemark) {
		this.txnParticularRemark = txnParticularRemark;
	}

	public String getIssueBranchCode() {
		return issueBranchCode;
	}

	public void setIssueBranchCode(String issueBranchCode) {
		this.issueBranchCode = issueBranchCode;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getBeneficiaryType() {
		return beneficiaryType;
	}

	public void setBeneficiaryType(String beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}

	public String getSettlementTerm() {
		return settlementTerm;
	}

	public void setSettlementTerm(String settlementTerm) {
		this.settlementTerm = settlementTerm;
	}

	public String getCommercialTerm() {
		return commercialTerm;
	}

	public void setCommercialTerm(String commercialTerm) {
		this.commercialTerm = commercialTerm;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public boolean isNodalFlag() {
		return nodalFlag;
	}

	public void setNodalFlag(boolean nodalFlag) {
		this.nodalFlag = nodalFlag;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public boolean isRiskParameter() {
		return riskParameter;
	}

	public void setRiskParameter(boolean riskParameter) {
		this.riskParameter = riskParameter;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(String dailyLimit) {
		this.dailyLimit = dailyLimit;
	}

	public String getWeeklyLimit() {
		return weeklyLimit;
	}

	public void setWeeklyLimit(String weeklyLimit) {
		this.weeklyLimit = weeklyLimit;
	}

	public String getMonthlyLimit() {
		return monthlyLimit;
	}

	public void setMonthlyLimit(String monthlyLimit) {
		this.monthlyLimit = monthlyLimit;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPanName() {
		return panName;
	}

	public void setPanName(String panName) {
		this.panName = panName;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getPanFile() {
		return panFile;
	}

	public void setPanFile(String panFile) {
		this.panFile = panFile;
	}

	public String getAadharName() {
		return aadharName;
	}

	public void setAadharName(String aadharName) {
		this.aadharName = aadharName;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getAadharFile() {
		return aadharFile;
	}

	public void setAadharFile(String aadharFile) {
		this.aadharFile = aadharFile;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getBenId() {
		return benId;
	}

	public void setBenId(String benId) {
		this.benId = benId;
	}

	public String getBenName() {
		return benName;
	}

	public void setBenName(String benName) {
		this.benName = benName;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getResRemark() {
		return resRemark;
	}

	public void setResRemark(String resRemark) {
		this.resRemark = resRemark;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public LocalDateTime getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(LocalDateTime txnTime) {
		this.txnTime = txnTime;
	}

	public String getResStatus() {
		return resStatus;
	}

	public void setResStatus(String resStatus) {
		this.resStatus = resStatus;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "MerchantBeneficiary [id=" + id + ", mid=" + mid + ", merchantName=" + merchantName + ", processorName="
				+ processorName + ", corpId=" + corpId + ", makerId=" + makerId + ", checkerId=" + checkerId
				+ ", bankCode=" + bankCode + ", dateOfBirth=" + dateOfBirth + ", txnParticular=" + txnParticular
				+ ", txnParticularRemark=" + txnParticularRemark + ", issueBranchCode=" + issueBranchCode
				+ ", branchCode=" + branchCode + ", sellerCode=" + sellerCode + ", beneficiaryType=" + beneficiaryType
				+ ", settlementTerm=" + settlementTerm + ", commercialTerm=" + commercialTerm + ", remark=" + remark
				+ ", action=" + action + ", nodalFlag=" + nodalFlag + ", createdDate=" + createdDate + ", modifiedDate="
				+ modifiedDate + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", riskParameter="
				+ riskParameter + ", paymentMode=" + paymentMode + ", dailyLimit=" + dailyLimit + ", weeklyLimit="
				+ weeklyLimit + ", monthlyLimit=" + monthlyLimit + ", status=" + status + ", active=" + active
				+ ", bankName=" + bankName + ", accountNumber=" + accountNumber + ", ifscCode=" + ifscCode + ", state="
				+ state + ", city=" + city + ", address=" + address + ", pincode=" + pincode + ", email=" + email
				+ ", mobileNumber=" + mobileNumber + ", panName=" + panName + ", panNumber=" + panNumber + ", panFile="
				+ panFile + ", aadharName=" + aadharName + ", aadharNumber=" + aadharNumber + ", aadharFile="
				+ aadharFile + ", resStatus=" + resStatus + ", errorCode=" + errorCode + ", errorDesc=" + errorDesc
				+ ", benId=" + benId + ", benName=" + benName + ", corpName=" + corpName + ", resRemark=" + resRemark
				+ ", refNo=" + refNo + ", txnTime=" + txnTime + "]";
	}

}
