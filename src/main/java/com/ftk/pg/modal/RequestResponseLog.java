package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "request_response_log")
public class RequestResponseLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	private String vpa;
	
	private Long mid;

	private String request;
	
	private String response;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	@Column(name = "unique_entity_id")
	private Long uniqueEntityId;
	
	@Column(name = "unique_logger_id")
	private String uniqueLoggerId;
	
	private String remarks;
	
	@Column(name = "api_name")
	private String apiName;
	
	private Integer status;
	
	@Column(name = "udf1")
	private String udf1;
	
	@Column(name = "udf2")
	private String udf2;
	
	@Column(name = "udf3")
	private String udf3;

	@Column(name = "udf4")
	private String udf4;
	
	@Column(name = "udf5")
	private String udf5;

	
	public String getUniqueLoggerId() {
		return uniqueLoggerId;
	}

	public void setUniqueLoggerId(String messageId) {
		this.uniqueLoggerId = messageId;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getUniqueEntityId() {
		return uniqueEntityId;
	}

	public void setUniqueEntityId(Long uniqueEntityId) {
		this.uniqueEntityId = uniqueEntityId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	@Override
	public String toString() {
		return "RequestResponseLog [id=" + id + ", vpa=" + vpa + ", mid=" + mid + ", request=" + request + ", response="
				+ response + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", uniqueEntityId="
				+ uniqueEntityId + ", uniqueLoggerId=" + uniqueLoggerId + ", remarks=" + remarks + ", apiName="
				+ apiName + ", status=" + status + ", udf1=" + udf1 + ", udf2=" + udf2 + ", udf3=" + udf3 + ", udf4="
				+ udf4 + ", udf5=" + udf5 + "]";
	}

	
		
}
