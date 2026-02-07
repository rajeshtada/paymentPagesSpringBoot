package com.ftk.pg.vo.nbbl.reqTxnInit;

public class AdditionalInfo {
	public String name;
	public String value;
	public boolean visibility;
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
	public boolean isVisibility() {
		return visibility;
	}
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}
	@Override
	public String toString() {
		return "AdditionalInfo [name=" + name + ", value=" + value + ", visibility=" + visibility + "]";
	}
	
	public static class Builder {
        private final AdditionalInfo additionalInfo = new AdditionalInfo();

        public Builder name(String name) {
        	additionalInfo.setName(name);
            return this;
        }

        public Builder value(String value) {
        	additionalInfo.setValue(value);
            return this;
        }

        public AdditionalInfo build() {
            return additionalInfo;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
    
}