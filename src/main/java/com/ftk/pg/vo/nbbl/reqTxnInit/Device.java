package com.ftk.pg.vo.nbbl.reqTxnInit;

import java.util.List;

public class Device {
	public String mobile;
	public List<Tag> tag;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<Tag> getTag() {
		return tag;
	}

	public void setTag(List<Tag> tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "Device [mobile=" + mobile + ", tag=" + tag + "]";
	}
	
	

}