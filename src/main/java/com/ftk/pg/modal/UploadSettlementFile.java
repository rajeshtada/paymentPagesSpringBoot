package com.ftk.pg.modal;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Audited
@Table(name = "settlementfile")
public class UploadSettlementFile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "file_id")
	private Long id;

	private String fileProcessor;

	private String fileProcessorName;

	private String fileIciciType;

	private String fileLocation;

	private String fileType;

	private Date settlementDate;

	private String settlementType;

	private String fileProcessType;

	private int status = 0;

	private String outputFileLocation;

	private String fileName;
	private String errorFile;

	private int exceptionReport;
	private int referenceNo;
	private String consolidatedRefNo;
	
	private int holidaySettlement;
	
	@ColumnDefault("'1'") 
	private int recovery_setting;

	public int getRecovery_setting() {
		return recovery_setting;
	}

	public void setRecovery_setting(int recovery_setting) {
		this.recovery_setting = recovery_setting;
	}

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

	public String getSettlementType() {
		return settlementType;
	}

	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
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

	public String getErrorFile() {
		return errorFile;
	}

	public void setErrorFile(String errorFile) {
		this.errorFile = errorFile;
	}

	public int getExceptionReport() {
		return exceptionReport;
	}

	public void setExceptionReport(int exceptionReport) {
		this.exceptionReport = exceptionReport;
	}

	public int getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(int referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getConsolidatedRefNo() {
		return consolidatedRefNo;
	}

	public void setConsolidatedRefNo(String consolidatedRefNo) {
		this.consolidatedRefNo = consolidatedRefNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getHolidaySettlement() {
		return holidaySettlement;
	}

	public void setHolidaySettlement(int holidaySettlement) {
		this.holidaySettlement = holidaySettlement;
	}

	@Override
	public String toString() {
		return "UploadSettlementFile [id=" + id + ", fileProcessor=" + fileProcessor + ", fileProcessorName="
				+ fileProcessorName + ", fileIciciType=" + fileIciciType + ", fileLocation=" + fileLocation
				+ ", fileType=" + fileType + ", settlementDate=" + settlementDate + ", settlementType=" + settlementType
				+ ", fileProcessType=" + fileProcessType + ", status=" + status + ", outputFileLocation="
				+ outputFileLocation + ", fileName=" + fileName + ", errorFile=" + errorFile + ", exceptionReport="
				+ exceptionReport + ", referenceNo=" + referenceNo + ", consolidatedRefNo=" + consolidatedRefNo
				+ ", holidaySettlement=" + holidaySettlement + ", recovery_setting=" + recovery_setting + "]";
	}

	
	
}
