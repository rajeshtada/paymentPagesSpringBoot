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
@Table(name = "virtual_qr_code")
public class VirtualQrCode implements Serializable {

	private static final long serialVersionUID = 2037494244888540739L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "vpa")
	private String vpa;

	@Column(name = "virtual_vpa")
	private String virtualVpa;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "name")
	private String name;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "unique_id")
	private String uniqueId;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "branch_id")
	private String branchId;

	@Column(name = "status")
	private int status;

	@Column(name = "url")
	private String url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVirtualVpa() {
		return virtualVpa;
	}

	public void setVirtualVpa(String virtualVpa) {
		this.virtualVpa = virtualVpa;
	}

	@Override
	public String toString() {
		return "VirtualQrCode [id=" + id + ", mid=" + mid + ", vpa=" + vpa + ", virtualVpa=" + virtualVpa + ", userId="
				+ userId + ", name=" + name + ", phoneNumber=" + phoneNumber + ", uniqueId=" + uniqueId + ", emailId="
				+ emailId + ", branchId=" + branchId + ", status=" + status + ", url=" + url + "]";
	}

}
