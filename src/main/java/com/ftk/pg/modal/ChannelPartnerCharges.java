package com.ftk.pg.modal;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "channel_partner_charges")
public class ChannelPartnerCharges extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "is_default")
	private boolean isDefault;
	
	@Column(name = "channer_partner_id")
	private Long channerPartnerId;
	
	@Column(name = "from_amount")
	private Double fromAmount;

	@Column(name = "to_amount")
	private Double toAmount;
	
	@Column(name = "charge_type")
	private String chargeType;

	@Column(name = "charge_value")
	private Double chargeValue;
	
	@Column(name="card_type")
	private String cardType;
	
	@Column(name="card_category")
	private String cardCategory;
	
	@Column(name="net_banking_id")
	private Long netBankingId;
	
	@Column(name="payment_mode")
	private String paymentMode;
	
	@Column(name="status")
	@NotNull
	@ColumnDefault("'1'")
	private int status;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Long getChannerPartnerId() {
		return channerPartnerId;
	}

	public void setChannerPartnerId(Long channerPartnerId) {
		this.channerPartnerId = channerPartnerId;
	}

	public Double getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(Double fromAmount) {
		this.fromAmount = fromAmount;
	}

	public Double getToAmount() {
		return toAmount;
	}

	public void setToAmount(Double toAmount) {
		this.toAmount = toAmount;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public Double getChargeValue() {
		return chargeValue;
	}

	public void setChargeValue(Double chargeValue) {
		this.chargeValue = chargeValue;
	}

	@Override
	public LocalDateTime getCreatedDate() {
		// TODO Auto-generated method stub
		return super.getCreatedDate();
	}

	@Override
	public void setCreatedDate(LocalDateTime createdDate) {
		// TODO Auto-generated method stub
		super.setCreatedDate(createdDate);
	}

	@Override
	public LocalDateTime getModifiedDate() {
		// TODO Auto-generated method stub
		return super.getModifiedDate();
	}

	@Override
	public void setModifiedDate(LocalDateTime modifiedDate) {
		// TODO Auto-generated method stub
		super.setModifiedDate(modifiedDate);
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardCategory() {
		return cardCategory;
	}

	public void setCardCategory(String cardCategory) {
		this.cardCategory = cardCategory;
	}

	public Long getNetBankingId() {
		return netBankingId;
	}

	public void setNetBankingId(Long netBankingId) {
		this.netBankingId = netBankingId;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ChannelPartnerCharges [id=" + id + ", isDefault=" + isDefault + ", channerPartnerId=" + channerPartnerId
				+ ", fromAmount=" + fromAmount + ", toAmount=" + toAmount + ", chargeType=" + chargeType
				+ ", chargeValue=" + chargeValue + ", cardType=" + cardType + ", cardCategory=" + cardCategory
				+ ", netBankingId=" + netBankingId + ", paymentMode=" + paymentMode + ", status=" + status + "]";
	}

	
}
