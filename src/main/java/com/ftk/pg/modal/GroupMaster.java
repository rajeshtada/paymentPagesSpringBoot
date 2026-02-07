package com.ftk.pg.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "group_master")
public class GroupMaster implements Serializable {
	
	private static final long serialVersionUID = 988688253961424241L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "group_name")
	private String groupName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "message_setting")
	private String messageSetting;
	
	@Column(name = "vpa")
	private String vpa;
	
	@Column(name = "mid")
	private Long mid;

	@Column(name = "account_number")
	private String accountNumber;
	
	@Column(name = "status")
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMessageSetting() {
		return messageSetting;
	}

	public void setMessageSetting(String messageSetting) {
		this.messageSetting = messageSetting;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	@Override
	public String toString() {
		return "GroupMaster [id=" + id + ", groupName=" + groupName + ", description=" + description
				+ ", messageSetting=" + messageSetting + ", vpa=" + vpa + ", mid=" + mid + ", accountNumber="
				+ accountNumber + ", status=" + status + "]";
	}

}
