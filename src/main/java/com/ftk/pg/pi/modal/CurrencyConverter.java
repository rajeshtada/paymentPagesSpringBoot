package com.ftk.pg.pi.modal;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "currency_converter")
public class CurrencyConverter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "from_currency")
	private String fromCurrency;

	@Column(name = "to_currency")
	private String toCurrency;

	@Column(name = "value_time")
	private LocalDateTime valueTime;

	@Column(name = "value")
	private BigDecimal value;
	
//	@Column(name = "status")
//	private Boolean status;
	


	@ColumnDefault(value = "1")
	private Boolean status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromCurrency() {
		return fromCurrency;
	}

	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	public String getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}

	public LocalDateTime getValueTime() {
		return valueTime;
	}

	public void setValueTime(LocalDateTime valueTime) {
		this.valueTime = valueTime;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CurrencyConverter [id=" + id + ", fromCurrency=" + fromCurrency + ", toCurrency=" + toCurrency
				+ ", valueTime=" + valueTime + ", value=" + value + ", status=" + status + "]";
	}

	

	

}
