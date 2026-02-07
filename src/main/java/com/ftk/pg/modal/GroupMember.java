package com.ftk.pg.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "group_member")
public class GroupMember implements Serializable {
	
	private static final long serialVersionUID = 3888648831568460871L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "master_id")
	private Long masterId;
	
	@Column(name = "mid")
	private Long mid;

	@Column(name = "vpa")	
	private String vpa;
	
	@Column(name = "status")	
	private int status;




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public Long getMasterId() {
		return masterId;
	}




	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}




	public Long getMid() {
		return mid;
	}




	public void setMid(Long mid) {
		this.mid = mid;
	}




	public String getVpa() {
		return vpa;
	}




	public void setVpa(String vpa) {
		this.vpa = vpa;
	}




	public int getStatus() {
		return status;
	}




	public void setStatus(int status) {
		this.status = status;
	}




	@Override
	public String toString() {
		return "GroupMember [id=" + id + ", masterId=" + masterId  + ", mid=" + mid
				+ ", vpa=" + vpa + ", status=" + status + "]";
	}
	
}
