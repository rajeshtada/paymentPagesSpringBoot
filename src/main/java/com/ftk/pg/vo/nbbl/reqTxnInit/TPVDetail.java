package com.ftk.pg.vo.nbbl.reqTxnInit;

public class TPVDetail {
	public String name;
	public String value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "TPVDetail [name=" + name + ", value=" + value + "]";
	}
	
	public static class Builder {
        private final TPVDetail tpvDetail = new TPVDetail();

        public Builder name(String name) {
            tpvDetail.setName(name);
            return this;
        }

        public Builder value(String value) {
            tpvDetail.setValue(value);
            return this;
        }

        public TPVDetail build() {
            return tpvDetail;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
    
}
