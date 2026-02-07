package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "settlement_reverse_file")
public class SettlementReverseFile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "file_ids")
	private String fileIds;

	@CreationTimestamp
	private LocalDateTime createdDate;

	private String fileLocation;

	private String status;

	private String fileProcessType;

	@Column(name = "settlement_part")
	private String settlementPart;

	@Column(name = "settlement_date", updatable = false)
	private Date settlementDate;

	private String outputFile;

	@Override
	public String toString() {
		return "SettlementReverseFile [id=" + id + ", fileIds=" + fileIds + ", createdDate=" + createdDate
				+ ", fileLocation=" + fileLocation + ", status=" + status + ", fileProcessType=" + fileProcessType
				+ ", settlementPart=" + settlementPart + ", settlementDate=" + settlementDate + ", outputFile="
				+ outputFile + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFileProcessType() {
		return fileProcessType;
	}

	public void setFileProcessType(String fileProcessType) {
		this.fileProcessType = fileProcessType;
	}

	public String getSettlementPart() {
		return settlementPart;
	}

	public void setSettlementPart(String settlementPart) {
		this.settlementPart = settlementPart;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

}
