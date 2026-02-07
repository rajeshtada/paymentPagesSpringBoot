package com.ftk.pg.pi.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Table(name = "sound_box_inventory")
@Entity
public class SoundBoxInventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 646457096690737106L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "searial_no")
	private String searialNo;

	@Column(name = "vpa")
	private String vpa;

	@Column(name = "created_date", updatable = false)
//	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modify_date")
//	@UpdateTimestamp
	private LocalDateTime modifyDate;

	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@Column(name = "modify_by")
	private String modifyBy;

	@Column(name = "status", nullable = false, columnDefinition = "int default 0")
	private int status;

	@Column(name = "virtual_vpa")
	private String virtualVpa;

	@Column(name = "sim_card_number")
	private String simCardNumber;

	@Column(name = "service_provider")
	private String serviceProvider;

	@Column(name = "address")
	private String address;

	@Column(name = "soundbox_type")
	private String soundboxType;

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

	public String getSearialNo() {
		return searialNo;
	}

	public void setSearialNo(String searialNo) {
		this.searialNo = searialNo;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(LocalDateTime modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getVirtualVpa() {
		return virtualVpa;
	}

	public void setVirtualVpa(String virtualVpa) {
		this.virtualVpa = virtualVpa;
	}

	public String getSimCardNumber() {
		return simCardNumber;
	}

	public void setSimCardNumber(String simCardNumber) {
		this.simCardNumber = simCardNumber;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSoundboxType() {
		return soundboxType;
	}

	public void setSoundboxType(String soundboxType) {
		this.soundboxType = soundboxType;
	}

	@Override
	public String toString() {
		return "SoundBoxInventory [id=" + id + ", mid=" + mid + ", searialNo=" + searialNo + ", vpa=" + vpa
				+ ", createdDate=" + createdDate + ", modifyDate=" + modifyDate + ", createdBy=" + createdBy
				+ ", modifyBy=" + modifyBy + ", status=" + status + ", virtualVpa=" + virtualVpa + ", simCardNumber="
				+ simCardNumber + ", serviceProvider=" + serviceProvider + ", address=" + address + ", soundboxType="
				+ soundboxType + "]";
	}

}
