package com.ftk.pg.modal;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "merchant_recovery_transaction")
public class MerchantRecoveryTransaction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fileId")
	private Long fileId;

	@Column(name = "settlement_history_id")
	private long settlementHistoryId;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "vpa")
	private String vpa;

	@Column(name = "part")
	private String part;

	@Column(name = "due_amount")
	private Double dueAmount;

	@Column(name = "recover_amount")
	private Double recoverAmount;

	@Column(name = "total_amount")
	private Double totalAmount;

	@Column(name = "to_be_recover_amount")
	private Double toBeRecover;

	@Temporal(TemporalType.DATE)
	@Column(name = "settlement_date")
	private Date settlementDate;

	@Column(name = "settlement_file_id")
	private long settlementFileId;

	@Column(name = "status")
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public Double getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(Double dueAmount) {
		this.dueAmount = dueAmount;
	}

	public Double getRecoverAmount() {
		return recoverAmount;
	}

	public void setRecoverAmount(Double recoverAmount) {
		this.recoverAmount = recoverAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
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

	public long getSettlementHistoryId() {
		return settlementHistoryId;
	}

	public void setSettlementHistoryId(long settlementHistoryId) {
		this.settlementHistoryId = settlementHistoryId;
	}

	public long getSettlementFileId() {
		return settlementFileId;
	}

	public void setSettlementFileId(long settlementFileId) {
		this.settlementFileId = settlementFileId;
	}

	@Override
	public String toString() {
		return "MerchantRecoveryTransaction [id=" + id + ", fileId=" + fileId + ", settlementHistoryId="
				+ settlementHistoryId + ", mid=" + mid + ", vpa=" + vpa + ", part=" + part + ", dueAmount=" + dueAmount
				+ ", recoverAmount=" + recoverAmount + ", totalAmount=" + totalAmount + ", toBeRecover=" + toBeRecover
				+ ", settlementDate=" + settlementDate + ", settlementFileId=" + settlementFileId + ", status=" + status
				+ "]";
	}

	public Double getToBeRecover() {
		return toBeRecover;
	}

	public void setToBeRecover(Double toBeRecover) {
		this.toBeRecover = toBeRecover;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	

}
