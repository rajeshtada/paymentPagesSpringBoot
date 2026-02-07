package com.ftk.pg.modal;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "merchant_callback_setting")
@Entity
public class MerchantCallbackSetting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1058162496984565985L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private long mid;
	private String callbackUrl;
	private String qrcallbackUrl;
	private String settlementCallbackUrl;
	private int status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getMid() {
		return mid;
	}
	public void setMid(long mid) {
		this.mid = mid;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getQrcallbackUrl() {
		return qrcallbackUrl;
	}
	public void setQrcallbackUrl(String qrcallbackUrl) {
		this.qrcallbackUrl = qrcallbackUrl;
	}
	public String getSettlementCallbackUrl() {
		return settlementCallbackUrl;
	}
	public void setSettlementCallbackUrl(String settlementCallbackUrl) {
		this.settlementCallbackUrl = settlementCallbackUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	

}
