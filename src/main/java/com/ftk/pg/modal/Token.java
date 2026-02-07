package com.ftk.pg.modal;

import java.io.Serializable;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Audited
@Table(name = "token")
public class Token implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "token_id", updatable = false, nullable = false, length = 255)
	private String token;

	@Lob
	private String url;

	@Column(name = "method_type")
	private String methodType;

	@Column(name = "token_status")
	private int tokenStatus;
	
//	@Column(name = "response_json")
//	private String responseJson;

	public String getMethodType() {
		return methodType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTokenStatus() {
		return tokenStatus;
	}

	public void setTokenStatus(int tokenStatus) {
		this.tokenStatus = tokenStatus;
	}

	@Override
	public String toString() {
		return "Token [token=" + token + ", url=" + url + ", methodType=" + methodType + ", tokenStatus=" + tokenStatus
				+ "]";
	}

/*	public String getResponseJson() {
		return responseJson;
	}

	public void setResponseJson(String responseJson) {
		this.responseJson = responseJson;
	}
*/
}
