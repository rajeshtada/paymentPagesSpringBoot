package com.ftk.pg.vo.nbbl.reqTxnInit;


public class Txn {
	public String refID;
	public String ts;
	public Integer expiry;
	public String initiationMode;
	public String getRefID() {
		return refID;
	}
	public void setRefID(String refID) {
		this.refID = refID;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public Integer getExpiry() {
		return expiry;
	}
	public void setExpiry(Integer expiry) {
		this.expiry = expiry;
	}
	public String getInitiationMode() {
		return initiationMode;
	}
	public void setInitiationMode(String initiationMode) {
		this.initiationMode = initiationMode;
	}
	@Override
	public String toString() {
		return "TXN [refID=" + refID + ", ts=" + ts + ", expiry=" + expiry + ", initiationMode=" + initiationMode + "]";
	}
	
	public static class Builder {
        private final Txn txn = new Txn();

        public Builder refID(String name) {
        	txn.setRefID(name);
            return this;
        }

        public Builder ts(String value) {
        	txn.setTs(value);
            return this;
        }
        
        public Builder expiry(int name) {
        	txn.setExpiry(name);
            return this;
        }

        public Builder initiationMode(String value) {
        	txn.setInitiationMode(value);
            return this;
        }

        public Txn build() {
            return txn;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
	
}