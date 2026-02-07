package com.ftk.pg.vo.sbiNb;

public class SBIPaymentRequest {
	private String crn;
	private String ref_no;
	private String amount;
	private String transaction_category;
	private String redirect_url;
	private String checkSum;
	

	

	public String getCrn() {
		return crn;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}

	public String getRef_no() {
		return ref_no;
	}

	public void setRef_no(String ref_no) {
		this.ref_no = ref_no;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTransaction_category() {
		return transaction_category;
	}

	public void setTransaction_category(String transaction_category) {
		this.transaction_category = transaction_category;
	}

	public String getRedirect_url() {
		return redirect_url;
	}

	public void setRedirect_url(String redirect_url) {
		this.redirect_url = redirect_url;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}
	

	@Override
	public String toString() {
		return "SBIPaymentRequest [crn=" + crn + ", ref_no=" + ref_no + ", amount=" + amount + ", transaction_category="
				+ transaction_category + ", redirect_url=" + redirect_url + ", checkSum=" + checkSum + "]";
	}

	public String checksumEncrypted() {
		return "crn=" + crn + "|" + "ref_no=" + ref_no + "|" + "amount=" + amount +"|"  + "transaction_category=" + transaction_category +"|"+"redirect_url=" + redirect_url + "|"
				+ "checkSum=" + checkSum;
	}

	public String checksum() {
		return  "crn="+crn+"|"+"ref_no="+ref_no+"|"+"amount="+ amount+"|"+"transaction_category="+transaction_category+"|"+"redirect_url="+redirect_url;

	}

}
