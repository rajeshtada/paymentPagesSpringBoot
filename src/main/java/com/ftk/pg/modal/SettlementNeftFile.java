package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "settlement_neft_file")
public class SettlementNeftFile implements Serializable {

	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "id")
//	private Long id;

	@Id
	@Column(name = "fileId")
	private Long fileId;

	@OneToOne
	@JoinColumn(name = "fileId")
	@MapsId
	private UploadSettlementFile uploadSettlementFile;
	
	public UploadSettlementFile getUploadSettlementFile() {
		return uploadSettlementFile;
	}

	public void setUploadSettlementFile(UploadSettlementFile uploadSettlementFile) {
		this.uploadSettlementFile = uploadSettlementFile;
	}

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "file_processor_name")
	private String fileProcessorName;

	@Column(name = "file_location")
	private String fileLocation;

	@Column(name = "settlement_date")
	private Date settlementDate;

	@Column(name = "file_type")
	private String fileType;

	@Column(name = "status")
	private int status = 0;

	@Column(name = "output_file_location")
	private String outputFileLocation;

	@Column(name = "total_count")
	private BigDecimal totalCount;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getFileProcessorName() {
		return fileProcessorName;
	}

	public void setFileProcessorName(String fileProcessorName) {
		this.fileProcessorName = fileProcessorName;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOutputFileLocation() {
		return outputFileLocation;
	}

	public void setOutputFileLocation(String outputFileLocation) {
		this.outputFileLocation = outputFileLocation;
	}

	public BigDecimal getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(BigDecimal totalCount) {
		this.totalCount = totalCount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "SettlementNeftFile [fileId=" + fileId + ", createdDate=" + createdDate + ", fileProcessorName=" + fileProcessorName + ", fileLocation="
				+ fileLocation + ", settlementDate=" + settlementDate + ", fileType=" + fileType + ", status=" + status
				+ ", outputFileLocation=" + outputFileLocation + ", totalCount=" + totalCount + ", totalAmount="
				+ totalAmount + "]";
	}

}
