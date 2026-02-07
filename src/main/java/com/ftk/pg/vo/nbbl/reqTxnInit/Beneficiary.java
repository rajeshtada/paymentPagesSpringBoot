package com.ftk.pg.vo.nbbl.reqTxnInit;

public class Beneficiary {
	public String id;
	public String acnum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAcnum() {
		return acnum;
	}

	public void setAcnum(String acnum) {
		this.acnum = acnum;
	}

	@Override
	public String toString() {
		return "Beneficiary [id=" + id + ", acnum=" + acnum + "]";
	}

	public static class Builder {
        private final Beneficiary beneficiary = new Beneficiary();

        public Builder bankId(String name) {
        	beneficiary.setId(name);
            return this;
        }

        public Builder accNumber(String value) {
        	beneficiary.setAcnum(value);
            return this;
        }

        public Beneficiary build() {
            return beneficiary;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
    
}
