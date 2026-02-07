package com.ftk.pg.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CommissionModel {
	private BigDecimal amount;
	private BigDecimal charges;
	private BigDecimal total;
	private BigDecimal convenience;
	private String paymentMode;

	
	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public BigDecimal getConvenience() {
		return convenience;
	}

	public void setConvenience(BigDecimal convenience) {
		this.convenienceStr = convenience.setScale(2, RoundingMode.HALF_UP).toString();
		this.convenience = convenience;
	}

	public String getConvenienceStr() {
		return convenienceStr;
	}

	public void setConvenienceStr(String convenienceStr) {
		this.convenienceStr = convenienceStr;
	}

	private String amountStr;
	private String chargesStr;
	private String totalStr;
	private String convenienceStr;

	private String message;

	public void setZeroCharges(BigDecimal amount) {
		this.setAmount(amount);
		this.setCharges(BigDecimal.ZERO);
		this.setTotal(amount);
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amountStr = amount.setScale(2, RoundingMode.HALF_UP).toString();
		this.amount = amount;
	}

	public BigDecimal getCharges() {
		return charges;
	}

	public void setCharges(BigDecimal charges) {
		this.chargesStr = charges.setScale(2, RoundingMode.HALF_UP).toString();
		this.charges = charges;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.totalStr = total.setScale(2, RoundingMode.HALF_UP).toString();
		this.total = total;
	}

	public String getAmountStr() {
		return amountStr;
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}

	public String getChargesStr() {
		return chargesStr;
	}

	public void setChargesStr(String chargesStr) {
		this.chargesStr = chargesStr;
	}

	public String getTotalStr() {
		return totalStr;
	}

	public void setTotalStr(String totalStr) {
		this.totalStr = totalStr;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public BigDecimal getTotal2() {
		return total;
	}

	public void setTotal2(BigDecimal total) {
		this.totalStr = total.setScale(4, RoundingMode.HALF_UP).toString();
		this.total = total;
	}
	
	public BigDecimal getCharges2() {
		return charges;
	}

	public void setCharges2(BigDecimal charges) {
		this.chargesStr = charges.setScale(4, RoundingMode.HALF_UP).toString();
		this.charges = charges;
	}
	
	

	@Override
	public String toString() {
		return "CommissionModel [amount=" + amount + ", charges=" + charges + ", total=" + total + ", convenience="
				+ convenience + ", amountStr=" + amountStr + ", chargesStr=" + chargesStr + ", totalStr=" + totalStr
				+ ", convenienceStr=" + convenienceStr + ", message=" + message + "]";
	}
	

}