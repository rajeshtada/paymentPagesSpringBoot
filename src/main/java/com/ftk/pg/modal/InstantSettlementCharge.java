package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "instant_settlement_charge")

public class InstantSettlementCharge implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "mid")
	private Long mid;
	
	@Column(name = "charge_type")
	private String chargeType;
	
	@Column(name = "charge_value")
	private String chargeValue;
	
	@Column(name = "from_amount")
	private BigDecimal fromAmount;
	
	@Column(name = "to_amount")
	private BigDecimal toAmount;
	
	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	@Column(name = "status")
	private Integer status;

	
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

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getChargeValue() {
		return chargeValue;
	}

	public void setChargeValue(String chargeValue) {
		this.chargeValue = chargeValue;
	}

	public BigDecimal getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}

	public BigDecimal getToAmount() {
		return toAmount;
	}

	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "InstantSettlementCharge [id=" + id + ", mid=" + mid + ", chargeType=" + chargeType + ", chargeValue="
				+ chargeValue + ", fromAmount=" + fromAmount + ", toAmount=" + toAmount + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", status=" + status + "]";
	}



	

//	public void populate(ResultSet rs) {
//		try {
//			this.id = rs.getLong("id");
//			this.mid = rs.getLong("mid");
//			this.carddata = rs.getString("card_data");
//			this.bankname = rs.getString("bank_name");
//			this.merchanttxnid = rs.getString("merchant_txn_id");
//			this.prodid = rs.getString("product_id");
//			this.billingaddress = rs.getString("billing_address");
//			this.date = rs.getString("transaction_date_time");
//			this.txncurr = rs.getString("transaction_currency");
//			this.emailid = rs.getString("email_id");
//			this.amt = rs.getBigDecimal("amount");
//
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//		}
//
//	}
	

}
