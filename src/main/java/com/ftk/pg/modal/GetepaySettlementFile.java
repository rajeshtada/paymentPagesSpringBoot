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
@Table(name = "getepay_settlement_file")
public class GetepaySettlementFile implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5919870545971816005L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	@Column(name = "settlement_date")
	private Date settlementDate;
	
	@Column(name = "settlement_part")
	private String settlementPart;
	
	@Column(name = "settlement_file")
	private String settlementFile;
	
	@Column(name = "settlement_file_format")
	private String settlementFileFormat;
	
	private Long processorId;
	private String processor;
	
	@Column(name = "status")
	private int status;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getSettlementPart() {
		return settlementPart;
	}

	public void setSettlementPart(String settlementPart) {
		this.settlementPart = settlementPart;
	}

	public String getSettlementFile() {
		return settlementFile;
	}

	public void setSettlementFile(String settlementFile) {
		this.settlementFile = settlementFile;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSettlementFileFormat() {
		return settlementFileFormat;
	}

	public void setSettlementFileFormat(String settlementFileFormat) {
		this.settlementFileFormat = settlementFileFormat;
	}

	public Long getProcessorId() {
		return processorId;
	}

	public void setProcessorId(Long processorId) {
		this.processorId = processorId;
	}
	
	
	
}
