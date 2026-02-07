package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "charge_back_reverse")
public class ChargeBackReverse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "srh_id")
	private Long srhId;

	@Column(name = "charge_back_id")
	private Long chargeBackId;
	
	@Column(name = "recovery_id")
	private Long recoveryId;
	
	@Column(name = "txn_id")
	private Long txnId;
	
	@Column(name = "mid")
	private Long mid;
	
	@Column(name = "status")
	private int status;

	@Column(name = "refund_id")
	private Long refundId;
	
	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;
	
	@Column(name = "settlement_date")
	private Date settlementDate;

	@Column(name = "total_charge_back_amount")
	private BigDecimal totalChargeBackAmount;

	@Column(name = "recover_amount")
	private BigDecimal recoverAmount;
	

	@Column(name = "settlement_amount")
	private BigDecimal settlementAmount;

	@Column(name = "Settlement_part")
	private String SettlementPart;

	@Column(name = "remark")
	private String remark;

	@Column(name = "vpa")
	private String vpa;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSrhId() {
		return srhId;
	}

	public void setSrhId(Long srhId) {
		this.srhId = srhId;
	}

	public Long getChargeBackId() {
		return chargeBackId;
	}

	public void setChargeBackId(Long chargeBackId) {
		this.chargeBackId = chargeBackId;
	}

	public Long getRecoveryId() {
		return recoveryId;
	}

	public void setRecoveryId(Long recoveryId) {
		this.recoveryId = recoveryId;
	}

	public Long getTxnId() {
		return txnId;
	}

	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getRefundId() {
		return refundId;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public BigDecimal getTotalChargeBackAmount() {
		return totalChargeBackAmount;
	}

	public void setTotalChargeBackAmount(BigDecimal totalChargeBackAmount) {
		this.totalChargeBackAmount = totalChargeBackAmount;
	}

	public BigDecimal getRecoverAmount() {
		return recoverAmount;
	}

	public void setRecoverAmount(BigDecimal recoverAmount) {
		this.recoverAmount = recoverAmount;
	}

	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getSettlementPart() {
		return SettlementPart;
	}

	public void setSettlementPart(String settlementPart) {
		SettlementPart = settlementPart;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	@Override
	public String toString() {
		return "ChargeBackReverse [id=" + id + ", srhId=" + srhId + ", chargeBackId=" + chargeBackId + ", recoveryId="
				+ recoveryId + ", txnId=" + txnId + ", mid=" + mid + ", status=" + status + ", refundId=" + refundId
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", settlementDate="
				+ settlementDate + ", totalChargeBackAmount=" + totalChargeBackAmount + ", recoverAmount="
				+ recoverAmount + ", settlementAmount=" + settlementAmount + ", SettlementPart=" + SettlementPart
				+ ", remark=" + remark + ", vpa=" + vpa + "]";
	}
}
