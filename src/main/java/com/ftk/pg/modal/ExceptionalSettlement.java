package com.ftk.pg.modal;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "exceptional_settlement_history")
@Entity
public class ExceptionalSettlement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "processor")
	private String processor;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "remitter_account_no")
	private String remitterAccountNo;

	@Column(name = "remitter_name")
	private String remitterName;

	@Column(name = "ifsc_code")
	private String ifscCode;

	@Column(name = "beneficiary_account_no")
	private String beneficiaryAccountNo;

	@Column(name = "beneficiary_name")
	private String beneficiaryName;

	@Column(name = "vpa_id")
	private String vpaId;

	@Column(name = "note")
	private String note;

	@Column(name = "file_part")
	private String settlementPart;

	@Column(name = "status")
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemitterAccountNo() {
		return remitterAccountNo;
	}

	public void setRemitterAccountNo(String remitterAccountNo) {
		this.remitterAccountNo = remitterAccountNo;
	}

	public String getRemitterName() {
		return remitterName;
	}

	public void setRemitterName(String remitterName) {
		this.remitterName = remitterName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBeneficiaryAccountNo() {
		return beneficiaryAccountNo;
	}

	public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
		this.beneficiaryAccountNo = beneficiaryAccountNo;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getVpaId() {
		return vpaId;
	}

	public void setVpaId(String vpaId) {
		this.vpaId = vpaId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSettlementPart() {
		return settlementPart;
	}

	public void setSettlementPart(String settlementPart) {
		this.settlementPart = settlementPart;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ExceptionalSettlement [id=" + id + ", processor=" + processor + ", amount=" + amount
				+ ", remitterAccountNo=" + remitterAccountNo + ", remitterName=" + remitterName + ", ifscCode="
				+ ifscCode + ", beneficiaryAccountNo=" + beneficiaryAccountNo + ", beneficiaryName=" + beneficiaryName
				+ ", vpaId=" + vpaId + ", note=" + note + ", settlementPart=" + settlementPart + ", status=" + status
				+ "]";
	}

	
	
	
}
