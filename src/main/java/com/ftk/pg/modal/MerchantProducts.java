package com.ftk.pg.modal;

import java.io.Serializable;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Audited
@Table(name = "merchant_products")
public class MerchantProducts implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "enabled_currencies")
	private String enabledCurrencies;
	@Column(name = "enabled_payment_modes")
	private String enabledPaymentModes;
	@Column(name = "product_type")
	private String productType;
	@Column(name = "terminal_id")
	private String terminalId;
	@Column(name = "pos_id")
	private String posId;
	@Column(name = "default_processor")
	private String defaultProcessor;
	@Column(name="merchant_name")
	private String merchantName;
	@Column(name="merchant_id")
	private Long merchantId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnabledCurrencies() {
		return enabledCurrencies;
	}

	public void setEnabledCurrencies(String enabledCurrencies) {
		this.enabledCurrencies = enabledCurrencies;
	}

	public String getEnabledPaymentModes() {
		return enabledPaymentModes;
	}

	public void setEnabledPaymentModes(String enabledPaymentModes) {
		this.enabledPaymentModes = enabledPaymentModes;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getDefaultProcessor() {
		return defaultProcessor;
	}

	public void setDefaultProcessor(String defaultProcessor) {
		this.defaultProcessor = defaultProcessor;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	@Override
	public String toString() {
		return "MerchantProducts [id=" + id + ", enabledCurrencies=" + enabledCurrencies + ", enabledPaymentModes="
				+ enabledPaymentModes + ", productType=" + productType + ", terminalId=" + terminalId + ", posId="
				+ posId + ", defaultProcessor=" + defaultProcessor + ", merchantName=" + merchantName + ", merchantId="
				+ merchantId + "]";
	}


}
