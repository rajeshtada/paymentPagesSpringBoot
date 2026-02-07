package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dmo_logs")
public class DMOLogs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -937542610394637133L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "vpa")
	private String vpa;

	@Column(name = "request")
	private String request;

	@Column(name = "response")
	private String response;
	
	@Column(name = "date")
	private LocalDateTime date;
	
	@Column(name = "api_name")
	private String apiName;

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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	@Override
	public String toString() {
		return "DMOLogs [id=" + id +  ", vpa=" + vpa + ", request=" + request + ", response=" + response
				+ ", date=" + date + ", apiName=" + apiName + "]";
	}
	
}
