package com.ftk.pg.modal;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "file_compare")

public class FileCompare implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "upload_nodal_file")
	private String uploadNodalFile;

	@Column(name = "upload_txn_file")
	private String uploadTxnFile;

	@Column(name = "created_Date")
	private Date createdDate;

	@Column(name = "part")
	private String part;

	@Column(name = "mis_matchFile_pth")
	private String misMatchFilePath;
	
	@Column(name = "status")
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUploadNodalFile() {
		return uploadNodalFile;
	}

	public void setUploadNodalFile(String uploadNodalFile) {
		this.uploadNodalFile = uploadNodalFile;
	}

	public String getUploadTxnFile() {
		return uploadTxnFile;
	}

	public void setUploadTxnFile(String uploadTxnFile) {
		this.uploadTxnFile = uploadTxnFile;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public String getMisMatchFilePath() {
		return misMatchFilePath;
	}

	public void setMisMatchFilePath(String misMatchFilePath) {
		this.misMatchFilePath = misMatchFilePath;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "FileCompare [id=" + id + ", uploadNodalFile=" + uploadNodalFile + ", uploadTxnFile=" + uploadTxnFile
				+ ", createdDate=" + createdDate + ", part=" + part + ", misMatchFilePath=" + misMatchFilePath
				+ ", status=" + status + "]";
	}

	
}
