package com.ftk.pg.modal;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "merchant_reverse_charges")
public class MerchantReverseCharges  extends BaseEntity  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "mid")
	private Long mid;
	
	@Column(name = "vpa")
	private String vpa;
	
	@Column(name="status")
	@NotNull
	@ColumnDefault("'0'")
	private int status;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "total_return_amount")
	private BigDecimal totalReturnAmount;
	
	@Column(name = "payment_mode")
	private String paymentMode;
	
	@Column(name = "charge_type")
	private String chargeType;
	
	@Column(name = "charge_ref_no")
	private Long chargeRefNo;
	
	@Column(name = "processor_name")
	private String processorName;
	
	@Column(name = "processor_id")
	private String processorId;
	
	@Column(name = "srh_id")
	private Long srhId;
	
	@Column(name = "settlement_date")
	private Date settlementDate;

	@Column(name = "settlement_part")
	private String settlementPart;
	
	@Column(name = "settlement_amount")
	private BigDecimal settlementAmount;
	
	@Column(name = "net_settlement_amount")
	private BigDecimal netSettlementAmount;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTotalReturnAmount() {
		return totalReturnAmount;
	}

	public void setTotalReturnAmount(BigDecimal totalReturnAmount) {
		this.totalReturnAmount = totalReturnAmount;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}


	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public Long getChargeRefNo() {
		return chargeRefNo;
	}

	public void setChargeRefNo(Long chargeRefNo) {
		this.chargeRefNo = chargeRefNo;
	}

	public Long getSrhId() {
		return srhId;
	}

	public void setSrhId(Long srhId) {
		this.srhId = srhId;
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

	public String getProcessorName() {
		return processorName;
	}

	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}

	public String getProcessorId() {
		return processorId;
	}

	public void setProcessorId(String processorId) {
		this.processorId = processorId;
	}

	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public BigDecimal getNetSettlementAmount() {
		return netSettlementAmount;
	}

	public void setNetSettlementAmount(BigDecimal netSettlementAmount) {
		this.netSettlementAmount = netSettlementAmount;
	}

	@Override
	public String toString() {
		return "MerchantReturnCharges [id=" + id + ", mid=" + mid + ", vpa=" + vpa + ", status=" + status + ", remark="
				+ remark + ", totalReturnAmount=" + totalReturnAmount + ", paymentMode=" + paymentMode + ", chargeType="
				+ chargeType + ", chargeRefNo=" + chargeRefNo + ", srhId=" + srhId + ", settlementDate="
				+ settlementDate + ", settlementPart=" + settlementPart + ", processorName=" + processorName
				+ ", processorId=" + processorId + ", settlementAmount=" + settlementAmount + ", netSettlementAmount="
				+ netSettlementAmount + "]";
	}



}
