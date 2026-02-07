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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "partner")
public class Partner implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "mobile_no")
	public String mobileNo;

	@Column(name = "email")
	private String email;

	@Column(name = "city")
	private Long city;

	@Column(name = "state")
	private Long state;

	@Column(name = "country")
	private Long country;

	@Column(name = "pincode")
	private String pincode;

	@Column(name = "created_date")
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	@JoinColumn(name = "created_by")
	private String created_by;

	@JoinColumn(name = "modified_by")
	private String modified_by;

	private Integer status;

	@Column(name = "handler")
	private String handler;

	@Column(name = "logo_path")
	private String logoPath;

	@Column(name = "pdf_path")
	private String pdfPath;

	@Column(name = "charge")
	public double charge;

	@Column(name = "cancelled_cheque", nullable = false, columnDefinition = "INT default 0")
	public int cancelledCheque;

	@Column(name = "ifsc_prefix")
	private String ifscPrefix;

	@Column(name = "is_coperative")
	private Integer isCoperative;

	@Column(name = "bank_type")
	private String bankType;

	private boolean etb;

	@Column(name = "bank_operation_flag", nullable = false, columnDefinition = "INT default 0")
	private int bankOperationFlag = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
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

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getModified_by() {
		return modified_by;
	}

	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public double getCharge() {
		return charge;
	}

	public void setCharge(double charge) {
		this.charge = charge;
	}

	public int getCancelledCheque() {
		return cancelledCheque;
	}

	public void setCancelledCheque(int cancelledCheque) {
		this.cancelledCheque = cancelledCheque;
	}

	public String getIfscPrefix() {
		return ifscPrefix;
	}

	public void setIfscPrefix(String ifscPrefix) {
		this.ifscPrefix = ifscPrefix;
	}

	public Integer getIsCoperative() {
		return isCoperative;
	}

	public void setIsCoperative(Integer isCoperative) {
		this.isCoperative = isCoperative;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public boolean isEtb() {
		return etb;
	}

	public void setEtb(boolean etb) {
		this.etb = etb;
	}

	public int getBankOperationFlag() {
		return bankOperationFlag;
	}

	public void setBankOperationFlag(int bankOperationFlag) {
		this.bankOperationFlag = bankOperationFlag;
	}

	@Override
	public String toString() {
		return "Bank [id=" + id + ", name=" + name + ", username=" + username + ", mobileNo=" + mobileNo + ", email="
				+ email + ", city=" + city + ", state=" + state + ", country=" + country + ", pincode=" + pincode
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", created_by=" + created_by
				+ ", modified_by=" + modified_by + ", status=" + status + ", handler=" + handler + ", logoPath="
				+ logoPath + ", pdfPath=" + pdfPath + ", charge=" + charge + ", cancelledCheque=" + cancelledCheque
				+ ", ifscPrefix=" + ifscPrefix + ", isCoperative=" + isCoperative + ", bankType=" + bankType + ", etb="
				+ etb + "]";
	}

}
