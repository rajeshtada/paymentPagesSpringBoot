package com.ftk.pg.vo.nbbl.reqTxnInit;


public class Details {

	private String name;
	private String value;
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
		return "Details [name=" + name + ", value=" + value + "]";
	}
	
	public static class Builder {
        private final Details details = new Details();

        public Builder name(String name) {
        	details.setName(name);
            return this;
        }

        public Builder value(String value) {
        	details.setValue(value);
            return this;
        }

        public Details build() {
            return details;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
