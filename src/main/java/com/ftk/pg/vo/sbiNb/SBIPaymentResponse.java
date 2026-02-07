package com.ftk.pg.vo.sbiNb;

import java.time.LocalDateTime;

public class SBIPaymentResponse {
	private String bank_ref_no;
	private String amount;
	private String ref_no;
	private String status;
	private String crn;
	private String status_description;
	private String checksum;
	
	public String getBank_ref_no() {
		return bank_ref_no;
	}

	public void setBank_ref_no(String bank_ref_no) {
		this.bank_ref_no = bank_ref_no;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRef_no() {
		return ref_no;
	}

	public void setRef_no(String ref_no) {
		this.ref_no = ref_no;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCrn() {
		return crn;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}

	public String getStatus_description() {
		return status_description;
	}

	public void setStatus_description(String status_description) {
		this.status_description = status_description;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	

	public SBIPaymentResponse(String bank_ref_no, String amount, String ref_no, String status, String crn,
			String status_description, String checksum) {
		super();
		this.bank_ref_no = bank_ref_no;
		this.amount = amount;
		this.ref_no = ref_no;
		this.status = status;
		this.crn = crn;
		this.status_description = status_description;
		this.checksum = checksum;
	}

	@Override
	public String toString() {
		return "SBIPaymentResponse [bank_ref_no=" + bank_ref_no + ", amount=" + amount + ", ref_no=" + ref_no
				+ ", status=" + status + ", crn=" + crn + ", status_description=" + status_description + ", checksum="
				+ checksum + "]";
	}

	public SBIPaymentResponse(String str) {
		String arr[] =str.split("\\|");
		this.bank_ref_no =arr[0].split("=").length <=1 ? "": arr[0].split("=")[1];
		this.amount = arr[1].split("=").length <=1 ? "": arr[1].split("=")[1];
		this.ref_no = arr[2].split("=").length <=1 ? "": arr[2].split("=")[1];
		this.status = arr[3].split("=").length <=1 ? "": arr[3].split("=")[1];
		this.crn = arr[4].split("=").length <=1 ? "": arr[4].split("=")[1];
		this.status_description = arr[5].split("=").length <=1 ? "": arr[5].split("=")[1];
		this.checksum = arr[6].split("=").length <=1 ? "": arr[6].split("=")[1];
	}
	
	
	
	
	
	
	

}
