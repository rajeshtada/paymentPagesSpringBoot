package com.ftk.pg.pi.modal;


import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction_mapping")
public class TransactionMapping {

	@EmbeddedId
	TransactionMappingPk id;
//	
	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private Date createdDate;

	@Column(name = "pi_mid")
	private Long piMid;

	@Column(name = "pg_mid")
	private Long pgMid;

	@Column(name = "terminal_id")
	private String terminalId;

	@Column(name = "merchant_order_number")
	private String merchantOrderNumber;

	public TransactionMappingPk getId() {
		return id;
	}

	public void setId(TransactionMappingPk id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

//	public String getTerminalId() {
//		return terminalId;
//	}
//
//	public void setTerminalId(String terminalId) {
//		this.terminalId = terminalId;
//	}

	public Long getPiMid() {
		return piMid;
	}

	public void setPiMid(Long piMid) {
		this.piMid = piMid;
	}

	public Long getPgMid() {
		return pgMid;
	}

	public void setPgMid(Long pgMid) {
		this.pgMid = pgMid;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getMerchantOrderNumber() {
		return merchantOrderNumber;
	}

	public void setMerchantOrderNumber(String merchantOrderNumber) {
		this.merchantOrderNumber = merchantOrderNumber;
	}

	@Override
	public String toString() {
		return "TransactionMapping [id=" + id + ", createdDate=" + createdDate + ", piMid=" + piMid + ", pgMid=" + pgMid
				+ ", terminalId=" + terminalId + ", merchantOrderNumber=" + merchantOrderNumber + "]";
	}

}
