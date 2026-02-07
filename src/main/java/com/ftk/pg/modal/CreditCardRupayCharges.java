package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "credit_card_rupay_charges")
public class CreditCardRupayCharges implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "charges_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mcc")
	private String mcc;

	@Column(name = "commision_type")
	private String commisionType;

	@Column(name = "charge_type")
	private String chargeType;

	@Column(name = "commision_value")
	private BigDecimal commisionValue;

	@Column(name = "from_amount")
	private BigDecimal fromAmount;

	@Column(name = "to_amount")
	private BigDecimal toAmount;

	@Column(name = "merchant_type")
	private String merchantType;

	@Column(name = "fixed_variable")
	private Double fixedVarible;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getCommisionType() {
		return commisionType;
	}

	public void setCommisionType(String commisionType) {
		this.commisionType = commisionType;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public BigDecimal getCommisionValue() {
		return commisionValue;
	}

	public void setCommisionValue(BigDecimal commisionValue) {
		this.commisionValue = commisionValue;
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

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getFixedVarible() {
		return fixedVarible;
	}

	public void setFixedVarible(Double fixedVarible) {
		this.fixedVarible = fixedVarible;
	}

	@Override
	public String toString() {
		return "CreditCardRupayCharges [id=" + id + ", mcc=" + mcc + ", commisionType=" + commisionType
				+ ", chargeType=" + chargeType + ", commisionValue=" + commisionValue + ", fromAmount=" + fromAmount
				+ ", toAmount=" + toAmount + ", merchantType=" + merchantType + ", fixedVarible=" + fixedVarible + "]";
	}

}
