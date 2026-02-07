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
@Table(name = "virtual_qr_transaction")
public class VirtualQrTransaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "vpa_id")
	private String vpaId;

	@Column(name = "txn_date")
	private Date txnDatetime;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private Date createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private Date modifiedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getVpaId() {
		return vpaId;
	}

	public void setVpaId(String vpaId) {
		this.vpaId = vpaId;
	}

	public Date getTxnDatetime() {
		return txnDatetime;
	}

	public void setTxnDatetime(Date txnDatetime) {
		this.txnDatetime = txnDatetime;
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

	@Override
	public String toString() {
		return "VirtualQrTransaction [id=" + id + ", transactionId=" + transactionId + ", vpaId=" + vpaId
				+ ", txnDatetime=" + txnDatetime + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
				+ "]";
	}
}
