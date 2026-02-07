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
@Table(name = "settlement_indivisual_file")
public class SettlementIndivisualFile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "file_id")
	private Long fileid;
	@Column(name = "file_location")
	private String fileLocation;
	@Column(name = "file_type")
	private String fileType;
	@Column(name = "Settlement_date")
	private Date settlementDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFileid() {
		return fileid;
	}

	public void setFileid(long fileid) {
		this.fileid = fileid;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SettlementIndivisualFile [id=" + id + ", fileid=" + fileid + ", fileLocation=" + fileLocation
				+ ", fileType=" + fileType + ", settlementDate=" + settlementDate + "]";
	}

}
