package com.ftk.pg.vo.nbbl.reqTxnInit;

public class Tag {
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
		return "Tag [name=" + name + ", value=" + value + "]";
	}
	
	public static class Builder {
        private final Tag tag = new Tag();

        public Builder name(String name) {
        	tag.setName(name);
            return this;
        }

        public Builder value(String value) {
        	tag.setValue(value);
            return this;
        }

        public Tag build() {
            return tag;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
    
}