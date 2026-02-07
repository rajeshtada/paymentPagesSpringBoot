package com.ftk.pg.modal;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "alert_management")
public class AlertManagement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "alert_id")
	private Long alertId;

	@Column(name = "date_stamp")
	private LocalDateTime dateStamp;

	@Column(name = "type")
	private String type;

	@Column(name = "status")
	private String status;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAlertId() {
		return alertId;
	}

	public void setAlertId(Long alertId) {
		this.alertId = alertId;
	}

	public LocalDateTime getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(LocalDateTime dateStamp) {
		this.dateStamp = dateStamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "AlertManagement [id=" + id + ", alertId=" + alertId + ", dateStamp=" + dateStamp + ", type=" + type
				+ ", status=" + status + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + "]";
	}

}