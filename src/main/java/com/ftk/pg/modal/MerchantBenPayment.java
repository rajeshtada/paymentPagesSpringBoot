package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "merchant_ben_payment")
public class MerchantBenPayment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "debit_acc_no")
	private String debitAccNo;

	@Column(name = "debit_acc_name")
	private String debitAccName;

	@Column(name = "debit_ifsc")
	private String debitIfsc;

	@Column(name = "debit_mobileNo")
	private String debitMobileNo;

	@Column(name = "debit_trnparticulars")
	private String debitTrnParticulars;

	@Column(name = "debit_partrnrmks")
	private String debitParTrnRmks;

	@Column(name = "mode_of_pay")
	private String modeOfPay;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "ben_id")
	private String benId;

	@Column(name = "corp_id")
	private String corpId;

	@Column(name = "maker_id")
	private String makerId;

	private Boolean status;

	@Column(name = "resp_cd")
	private String respCd;

	@Column(name = "channelpartner_refno")
	private String channelPartnerRefNo;

	@Column(name = "rrn")
	private String rrn;

	@Column(name = "error_cd")
	private String errorCd;

	@Column(name = "error_desc")
	private String errorDesc;

	@Column(name = "ref_no")
	private String refNo;

	@Column(name = "utr_no")
	private String utrNo;

	@Column(name = "po_num")
	private String poNum;

	@Column(name = "ben_acc_no")
	private String benAccNo;

	@Column(name = "txn_time")
	private String txnTime;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDebitAccNo() {
		return debitAccNo;
	}

	public void setDebitAccNo(String debitAccNo) {
		this.debitAccNo = debitAccNo;
	}

	public String getDebitAccName() {
		return debitAccName;
	}

	public void setDebitAccName(String debitAccName) {
		this.debitAccName = debitAccName;
	}

	public String getDebitIfsc() {
		return debitIfsc;
	}

	public void setDebitIfsc(String debitIfsc) {
		this.debitIfsc = debitIfsc;
	}

	public String getDebitMobileNo() {
		return debitMobileNo;
	}

	public void setDebitMobileNo(String debitMobileNo) {
		this.debitMobileNo = debitMobileNo;
	}

	public String getDebitTrnParticulars() {
		return debitTrnParticulars;
	}

	public void setDebitTrnParticulars(String debitTrnParticulars) {
		this.debitTrnParticulars = debitTrnParticulars;
	}

	public String getDebitParTrnRmks() {
		return debitParTrnRmks;
	}

	public void setDebitParTrnRmks(String debitParTrnRmks) {
		this.debitParTrnRmks = debitParTrnRmks;
	}

	public String getModeOfPay() {
		return modeOfPay;
	}

	public void setModeOfPay(String modeOfPay) {
		this.modeOfPay = modeOfPay;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBenId() {
		return benId;
	}

	public void setBenId(String benId) {
		this.benId = benId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getRespCd() {
		return respCd;
	}

	public void setRespCd(String respCd) {
		this.respCd = respCd;
	}

	public String getChannelPartnerRefNo() {
		return channelPartnerRefNo;
	}

	public void setChannelPartnerRefNo(String channelPartnerRefNo) {
		this.channelPartnerRefNo = channelPartnerRefNo;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getErrorCd() {
		return errorCd;
	}

	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getUtrNo() {
		return utrNo;
	}

	public void setUtrNo(String utrNo) {
		this.utrNo = utrNo;
	}

	public String getPoNum() {
		return poNum;
	}

	public void setPoNum(String poNum) {
		this.poNum = poNum;
	}

	public String getBenAccNo() {
		return benAccNo;
	}

	public void setBenAccNo(String benAccNo) {
		this.benAccNo = benAccNo;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "MerchantBenPayment [id=" + id + ", amount=" + amount + ", debitAccNo=" + debitAccNo + ", debitAccName="
				+ debitAccName + ", debitIfsc=" + debitIfsc + ", debitMobileNo=" + debitMobileNo
				+ ", debitTrnParticulars=" + debitTrnParticulars + ", debitParTrnRmks=" + debitParTrnRmks
				+ ", modeOfPay=" + modeOfPay + ", remarks=" + remarks + ", benId=" + benId + ", corpId=" + corpId
				+ ", makerId=" + makerId + ", status=" + status + ", respCd=" + respCd + ", channelPartnerRefNo="
				+ channelPartnerRefNo + ", rrn=" + rrn + ", errorCd=" + errorCd + ", errorDesc=" + errorDesc
				+ ", refNo=" + refNo + ", utrNo=" + utrNo + ", poNum=" + poNum + ", benAccNo=" + benAccNo + ", txnTime="
				+ txnTime + ", createdDate=" + createdDate + "]";
	}

}
