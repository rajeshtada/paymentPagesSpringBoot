package com.ftk.pg.vo.nbbl.reqTxnInit;

public class Amount {
	public double value;
	public String curr;
	public AmountBreakUp amountBreakUp;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public AmountBreakUp getAmountBreakUp() {
		return amountBreakUp;
	}

	public void setAmountBreakUp(AmountBreakUp amountBreakUp) {
		this.amountBreakUp = amountBreakUp;
	}

	@Override
	public String toString() {
		return "Amount [value=" + value + ", curr=" + curr + ", amountBreakUp=" + amountBreakUp + "]";
	}

}