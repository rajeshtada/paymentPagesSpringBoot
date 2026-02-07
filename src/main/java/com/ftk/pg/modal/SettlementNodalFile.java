package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Audited
@Table(name = "settlement_nodal_file")
public class SettlementNodalFile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id")
	private Long id;

	private String fileProcessor;

	private String fileProcessorName;

	private String fileIciciType;

	private String fileLocation;

	private String fileType;

	private Date settlementDate;


	private String fileProcessType;

	private int status = 0;

	private String outputFileLocation;

	private String fileName;
	
	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	public String getOutputFileLocation() {
		return outputFileLocation;
	}

	public void setOutputFileLocation(String outputFileLocation) {
		this.outputFileLocation = outputFileLocation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileProcessor() {
		return fileProcessor;
	}

	public String getFileProcessorName() {
		return fileProcessorName;
	}

	public void setFileProcessorName(String fileProcessorName) {
		this.fileProcessorName = fileProcessorName;
	}

	public void setFileProcessor(String fileProcessor) {
		this.fileProcessor = fileProcessor;
	}

	public String getFileIciciType() {
		return fileIciciType;
	}

	public void setFileIciciType(String fileIciciType) {
		this.fileIciciType = fileIciciType;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getFileProcessType() {
		return fileProcessType;
	}

	public void setFileProcessType(String fileProcessType) {
		this.fileProcessType = fileProcessType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "SettlementNodalFile [id=" + id + ", fileProcessor=" + fileProcessor + ", fileProcessorName="
				+ fileProcessorName + ", fileIciciType=" + fileIciciType + ", fileLocation=" + fileLocation
				+ ", fileType=" + fileType + ", settlementDate=" + settlementDate + ", fileProcessType="
				+ fileProcessType + ", status=" + status + ", outputFileLocation=" + outputFileLocation + ", fileName="
				+ fileName + "]";
	}


}
