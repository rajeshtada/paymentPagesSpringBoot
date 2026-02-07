package com.ftk.pg.vo.nbbl.reqTxnInit;

import com.ftk.pg.vo.nbbl.reqTxnInit.Details.Builder;

public class Creds {
	public String type;
	public String value;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Creds [type=" + type + ", value=" + value + "]";
	}

	public static class Builder {
        private final Creds creds = new Creds();

        public Builder type(String name) {
        	creds.setType(name);
            return this;
        }

        public Builder value(String value) {
        	creds.setValue(value);
            return this;
        }

        public Creds build() {
            return creds;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}