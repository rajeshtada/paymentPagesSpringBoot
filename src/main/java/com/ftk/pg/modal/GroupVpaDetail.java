package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Group_vpa_detail")
public class GroupVpaDetail implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	@Column(name = "master_aacount")
	private String accountnumber;
	@Column(name = "member_vpa")
	private String memberVpa;
	@Column(name = "member_mid")
	private Long memberMid;
	
	
	@CreationTimestamp
	private LocalDateTime createdDate;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAccountnumber() {
		return accountnumber;
	}


	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}


	public String getMemberVpa() {
		return memberVpa;
	}


	public void setMemberVpa(String memberVpa) {
		this.memberVpa = memberVpa;
	}


	public Long getMemberMid() {
		return memberMid;
	}


	public void setMemberMid(Long memberMid) {
		this.memberMid = memberMid;
	}


	public LocalDateTime getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}


	@Override
	public String toString() {
		return "GroupVpaDetail [id=" + id + ", accountnumber=" + accountnumber + ", memberVpa=" + memberVpa
				+ ", memberMid=" + memberMid + ", createdDate=" + createdDate + "]";
	}



	

}
