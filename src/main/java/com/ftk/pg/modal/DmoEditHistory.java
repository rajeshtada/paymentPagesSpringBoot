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
@Table(name = "dmo_edit_history")
public class DmoEditHistory implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "eazy_pay_merchant_id")
	private Long eazypayMerchantID;

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
	
	private String dmoRequest;
	
	private String dmoResponse;
	
	private String type;

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

	public Long getEazypayMerchantID() {
		return eazypayMerchantID;
	}

	public void setEazypayMerchantID(Long eazypayMerchantID) {
		this.eazypayMerchantID = eazypayMerchantID;
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

	public String getDmoRequest() {
		return dmoRequest;
	}

	public void setDmoRequest(String dmoRequest) {
		this.dmoRequest = dmoRequest;
	}

	public String getDmoResponse() {
		return dmoResponse;
	}

	public void setDmoResponse(String dmoResponse) {
		this.dmoResponse = dmoResponse;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "DmoEditHistory [id=" + id + ", mid=" + mid + ", eazypayMerchantID=" + eazypayMerchantID + ", vpa=" + vpa
				+ ", allowCc=" + allowCc + ", allowOd=" + allowOd + ", allowPpi=" + allowPpi + ", createdDate="
				+ createdDate + ", modifiedDate=" + modifiedDate + ", dmoRequest=" + dmoRequest + ", dmoResponse="
				+ dmoResponse + ", type=" + type + "]";
	}

}
