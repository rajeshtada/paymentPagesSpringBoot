package com.ftk.pg.modal;

import java.io.Serializable;
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
@Table(name = "push_notification_request")
public class PushNotificationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "file_id")
	private String fileId;

	@Column(name = "settlement_history_id")
	private Long settlementHistoryId;

	@Column(name = "status")
	private int status; // default 0 success 1

	@Column(name = "type")
	private String type; // PushNofication / SMS

	@Column(name = "messege_type")
	private String messegeType; // Initiate / Reverse

	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private Date createdDate;

	@UpdateTimestamp
	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "settlement_date")
	private Date settlementDate;

	@Column(name = "file_type")
	private String fileType;

	@Override
	public String toString() {
		return "PushNotificationRequest [id=" + id + ", fileId=" + fileId + ", settlementHistoryId="
				+ settlementHistoryId + ", status=" + status + ", type=" + type + ", messegeType=" + messegeType
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", settlementDate="
				+ settlementDate + ", fileType=" + fileType + "]";
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Long getSettlementHistoryId() {
		return settlementHistoryId;
	}

	public void setSettlementHistoryId(Long settlementHistoryId) {
		this.settlementHistoryId = settlementHistoryId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessegeType() {
		return messegeType;
	}

	public void setMessegeType(String messegeType) {
		this.messegeType = messegeType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
