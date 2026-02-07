package com.ftk.pg.modal;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "aunb_summery_report")
public class AunbSummeryReport implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	private String tranaction_type;
	private String channel_ref_no;
	private String payment_id_ext;
	private String userreferenceno;
	private String host_ref_no;
	private String payment_date;
	private Double payment_amt;
	private Double refund_amount;
	private String debit_account_no;
	private String status;
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String udf6;
	private String udf7;
	private String udf8;
	private String udf9;
	private String udf10;
	
	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private Date createdDate;
	
	
	

	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTranaction_type() {
		return tranaction_type;
	}
	public void setTranaction_type(String tranaction_type) {
		this.tranaction_type = tranaction_type;
	}
	public String getChannel_ref_no() {
		return channel_ref_no;
	}
	public void setChannel_ref_no(String channel_ref_no) {
		this.channel_ref_no = channel_ref_no;
	}
	public String getPayment_id_ext() {
		return payment_id_ext;
	}
	public void setPayment_id_ext(String payment_id_ext) {
		this.payment_id_ext = payment_id_ext;
	}
	public String getUserreferenceno() {
		return userreferenceno;
	}
	public void setUserreferenceno(String userreferenceno) {
		this.userreferenceno = userreferenceno;
	}
	public String getHost_ref_no() {
		return host_ref_no;
	}
	public void setHost_ref_no(String host_ref_no) {
		this.host_ref_no = host_ref_no;
	}
	public String getPayment_date() {
		return payment_date;
	}
	public void setPayment_date(String payment_date) {
		this.payment_date = payment_date;
	}
	public Double getPayment_amt() {
		return payment_amt;
	}
	public void setPayment_amt(Double payment_amt) {
		this.payment_amt = payment_amt;
	}
	public Double getRefund_amount() {
		return refund_amount;
	}
	public void setRefund_amount(Double refund_amount) {
		this.refund_amount = refund_amount;
	}
	public String getDebit_account_no() {
		return debit_account_no;
	}
	public void setDebit_account_no(String debit_account_no) {
		this.debit_account_no = debit_account_no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUdf1() {
		return udf1;
	}
	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}
	public String getUdf2() {
		return udf2;
	}
	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}
	public String getUdf3() {
		return udf3;
	}
	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}
	public String getUdf4() {
		return udf4;
	}
	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}
	public String getUdf5() {
		return udf5;
	}
	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}
	public String getUdf6() {
		return udf6;
	}
	public void setUdf6(String udf6) {
		this.udf6 = udf6;
	}
	public String getUdf7() {
		return udf7;
	}
	public void setUdf7(String udf7) {
		this.udf7 = udf7;
	}
	public String getUdf8() {
		return udf8;
	}
	public void setUdf8(String udf8) {
		this.udf8 = udf8;
	}
	public String getUdf9() {
		return udf9;
	}
	public void setUdf9(String udf9) {
		this.udf9 = udf9;
	}
	public String getUdf10() {
		return udf10;
	}
	public void setUdf10(String udf10) {
		this.udf10 = udf10;
	}
	@Override
	public String toString() {
		return "AunbSummeryReport [id=" + id + ", tranaction_type=" + tranaction_type + ", channel_ref_no="
				+ channel_ref_no + ", payment_id_ext=" + payment_id_ext + ", userreferenceno=" + userreferenceno
				+ ", host_ref_no=" + host_ref_no + ", payment_date=" + payment_date + ", payment_amt=" + payment_amt
				+ ", refund_amount=" + refund_amount + ", debit_account_no=" + debit_account_no + ", status=" + status
				+ ", udf1=" + udf1 + ", udf2=" + udf2 + ", udf3=" + udf3 + ", udf4=" + udf4 + ", udf5=" + udf5
				+ ", udf6=" + udf6 + ", udf7=" + udf7 + ", udf8=" + udf8 + ", udf9=" + udf9 + ", udf10=" + udf10 + "]";
	}
	
	
}
