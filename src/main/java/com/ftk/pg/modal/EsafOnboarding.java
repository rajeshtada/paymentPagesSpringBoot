package com.ftk.pg.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "esaf_onboarding")
public class EsafOnboarding implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	@Column(name = "mid")
	private Long mid;
	@Column(name = "mcc_Code")
	private String mccCode;

	@Column(name = "merchant_name")
	private String name;
	@Column(name = "message")
	private String message;
	@Column(name = "responseCode")
	private String responseCode;
	@Column(name = "mobile_no")
	private String mobileno;
	@Column(name = "referenceId")
	private String referenceId;
	@Column(name = "status")
	private String status;
	@Column(name = "virtualAddress")
	private String virtualAddr;
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
	public String getMccCode() {
		return mccCode;
	}
	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVirtualAddr() {
		return virtualAddr;
	}
	public void setVirtualAddr(String virtualAddr) {
		this.virtualAddr = virtualAddr;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "EsafOnboarding [id=" + id + ", mid=" + mid + ", mccCode=" + mccCode + ", name=" + name + ", message="
				+ message + ", responseCode=" + responseCode + ", mobileno=" + mobileno + ", referenceId=" + referenceId
				+ ", status=" + status + ", virtualAddr=" + virtualAddr + "]";
	}
	
	

}
