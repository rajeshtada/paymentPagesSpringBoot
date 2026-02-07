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
@Table(name = "nodal_data_holder")
public class NodalRecordsHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3537203480661936784L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	private LocalDateTime transactionDate;
	private Date uploadDate; //
	private String codCCBRNTxn;
	private String codAccNo;
	private String txtTxnDesc;
	private Date valueDate;
	private String refChq;
	private String codDrcr;
	private String conTxnMneMonic;
	private String codTxnLiteral;
	private Double amount;
	private Date postDate;
	private String codSc;
	private String codAuthId;
	private String txnRefNo;

	@CreationTimestamp
	private LocalDateTime createdDate;
	private String refNo;
	private String fileType;

	private long settlementFileId;
	private Boolean verified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getCodCCBRNTxn() {
		return codCCBRNTxn;
	}

	public void setCodCCBRNTxn(String codCCBRNTxn) {
		this.codCCBRNTxn = codCCBRNTxn;
	}

	public String getCodAccNo() {
		return codAccNo;
	}

	public void setCodAccNo(String codAccNo) {
		this.codAccNo = codAccNo;
	}

	public String getTxtTxnDesc() {
		return txtTxnDesc;
	}

	public void setTxtTxnDesc(String txtTxnDesc) {
		this.txtTxnDesc = txtTxnDesc;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public String getRefChq() {
		return refChq;
	}

	public void setRefChq(String refChq) {
		this.refChq = refChq;
	}

	public String getCodDrcr() {
		return codDrcr;
	}

	public void setCodDrcr(String codDrcr) {
		this.codDrcr = codDrcr;
	}

	public String getConTxnMneMonic() {
		return conTxnMneMonic;
	}

	public void setConTxnMneMonic(String conTxnMneMonic) {
		this.conTxnMneMonic = conTxnMneMonic;
	}

	public String getCodTxnLiteral() {
		return codTxnLiteral;
	}

	public void setCodTxnLiteral(String codTxnLiteral) {
		this.codTxnLiteral = codTxnLiteral;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public String getCodSc() {
		return codSc;
	}

	public void setCodSc(String codSc) {
		this.codSc = codSc;
	}

	public String getCodAuthId() {
		return codAuthId;
	}

	public void setCodAuthId(String codAuthId) {
		this.codAuthId = codAuthId;
	}

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		return "NodalRecords [id=" + id + ", transactionDate=" + transactionDate + ", uploadDate=" + uploadDate
				+ ", codCCBRNTxn=" + codCCBRNTxn + ", codAccNo=" + codAccNo + ", txtTxnDesc=" + txtTxnDesc
				+ ", valueDate=" + valueDate + ", refChq=" + refChq + ", codDrcr=" + codDrcr + ", conTxnMneMonic="
				+ conTxnMneMonic + ", codTxnLiteral=" + codTxnLiteral + ", amount=" + amount + ", postDate=" + postDate
				+ ", codSc=" + codSc + ", codAuthId=" + codAuthId + ", txnRefNo=" + txnRefNo + ", createdDate="
				+ createdDate + ", refNo=" + refNo + ", fileType=" + fileType + ", settlementFileId=" + settlementFileId
				+ ", verified=" + verified + "]";
	}

	public long getSettlementFileId() {
		return settlementFileId;
	}

	public void setSettlementFileId(long settlementFileId) {
		this.settlementFileId = settlementFileId;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

}
