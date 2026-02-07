package com.ftk.pg.vo.nbbl.reqTxnInit;

import java.util.List;

import com.ftk.pg.vo.nbbl.Signature;

public class PaymentRequestNBBL {
	 public Head head;
	    public Txn txn;
	    public Pa pa;
	    public Merchant merchant;
	    public Payer payer;
	    public List<AdditionalInfo> additionalInfo;
	    
	    public Signature signature = null;
	    
		public Head getHead() {
			return head;
		}
		public void setHead(Head head) {
			this.head = head;
		}
		public Txn getTxn() {
			return txn;
		}
		public void setTxn(Txn txn) {
			this.txn = txn;
		}
		public Pa getPa() {
			return pa;
		}
		public void setPa(Pa pa) {
			this.pa = pa;
		}
		public Merchant getMerchant() {
			return merchant;
		}
		public void setMerchant(Merchant merchant) {
			this.merchant = merchant;
		}
		public Payer getPayer() {
			return payer;
		}
		public void setPayer(Payer payer) {
			this.payer = payer;
		}
		public List<AdditionalInfo> getAdditionalInfo() {
			return additionalInfo;
		}
		public void setAdditionalInfo(List<AdditionalInfo> additionalInfo) {
			this.additionalInfo = additionalInfo;
		}
		public Signature getSignature() {
			return signature;
		}
		public void setSignature(Signature signature) {
			this.signature = signature;
		}
	    
	    

}
