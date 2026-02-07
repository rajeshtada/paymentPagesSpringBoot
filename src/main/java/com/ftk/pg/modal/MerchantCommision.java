package com.ftk.pg.modal;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Audited
@Table(name = "merchant_commision")
public class MerchantCommision implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "commision_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "merchant_id")
	private Long merchantId;
	@Column(name = "merchant_name")
	private String merchantName;
	@Column(name = "commision_type")
	private String commisionType;
	@Column(name = "commision_value")
	private BigDecimal commisionvalue;
	@Column(name = "is_default")
	private boolean isDefault;
	@Column(name = "charge_type")
	private String chargeType;
	@Column(name = "payment_mode")
	private String paymentMode;
	@Column(name = "product_type")
	private String productType;
	@Column(name="sub_type")
	private String subType;
	@Column(name="from_amount")
	private BigDecimal fromAmount;
	@Column(name="to_amount")
	private BigDecimal toAmount;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getCommisionType() {
		return commisionType;
	}

	public void setCommisionType(String commisionType) {
		this.commisionType = commisionType;
	}

	public BigDecimal getCommisionvalue() {
		return commisionvalue;
	}

	public void setCommisionvalue(BigDecimal commisionvalue) {
		this.commisionvalue = commisionvalue;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
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

	@Override
	public String toString() {
		return "MerchantCommision [id=" + id + ", merchantId=" + merchantId + ", merchantName=" + merchantName
				+ ", commisionType=" + commisionType + ", commisionvalue=" + commisionvalue + ", isDefault=" + isDefault
				+ ", chargeType=" + chargeType + ", paymentMode=" + paymentMode + ", productType=" + productType
				+ ", subType=" + subType + ", fromAmount=" + fromAmount + ", toAmount=" + toAmount + "]";
	}

}
