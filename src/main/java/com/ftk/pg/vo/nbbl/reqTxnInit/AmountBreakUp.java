package com.ftk.pg.vo.nbbl.reqTxnInit;

import java.util.List;

public class AmountBreakUp {
	public List<Tag> tag;

	public List<Tag> getTag() {
		return tag;
	}

	public void setTag(List<Tag> tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "AmountBreakUp [tag=" + tag + "]";
	}

}