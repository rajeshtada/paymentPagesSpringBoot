package com.ftk.pg.responsevo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NorthAcrossPaymentStatusResponse {
	   private List<Data> data;
	    private String hash;
	 
		
		public String getHash() {
			return hash;
		}
		public void setHash(String hash) {
			this.hash = hash;
		}
		
		public List<Data> getData() {
			return data;
		}
		public void setData(List<Data> data) {
			this.data = data;
		}
		@Override
		public String toString() {
			return "NorthAcrossPaymentStatusResponse [hash=" + hash + ", data=" + data + "]";
		}
		
	


}
