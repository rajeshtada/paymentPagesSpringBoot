package com.ftk.pg.vo.nbbl.reqTxnInit;

import java.util.List;

public class Payer {
	public Amount amount;
	public Device device;
	public List<TPVDetail> TPV;
	public List<Details> details;
	public Amount getAmount() {
		return amount;
	}
	public void setAmount(Amount amount) {
		this.amount = amount;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public List<TPVDetail> getTPV() {
		return TPV;
	}
	public void setTPV(List<TPVDetail> tPV) {
		TPV = tPV;
	}
	
	public List<Details> getDetails() {
		return details;
	}
	public void setDetails(List<Details> details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "Payer [amount=" + amount + ", device=" + device + ", TPV=" + TPV + ", details=" + details + "]";
	}
	
}