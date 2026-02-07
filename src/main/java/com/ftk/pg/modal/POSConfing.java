package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author saagar
 *
 */
@Entity
@Table(name = "pos_config")
public class POSConfing implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "serial_no")
	private String serialNo;
	@Column(name = "pos_device_id")
	private String posDeviceId;
	@Column(name = "merchant_id")
	private Long merchantId;
	@Column(name = "terminal_id")
	private String terminalId;
	@Column(name = "merchant_pos_id")
	private String merchantPosId;
	@Column(name = "merchant_category_code")
	private String merchantCategoryCode;
	@Column(name = "acquirer_id")
	private String accquirerId;
	@Column(name = "master_key")
	private String masterKey;
	@Column(name = "working_key")
	private String workingKey;
	private String tmk;
	private String kcv;
	private String ipek;
	private String ksn;
	@Column(name = "is_dukpt")
	private boolean isDukpt;
	private String stan;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "card_acceptor_id")
	private String cardAcceptorId;
	@Column(name = "response_code")
	private String responseCode;
	@Column(name = "initial_tmk")
	private String initialTmk;
	private boolean status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getPosDeviceId() {
		return posDeviceId;
	}

	public void setPosDeviceId(String posDeviceId) {
		this.posDeviceId = posDeviceId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getMerchantPosId() {
		return merchantPosId;
	}

	public void setMerchantPosId(String merchantPosId) {
		this.merchantPosId = merchantPosId;
	}

	public String getMerchantCategoryCode() {
		return merchantCategoryCode;
	}

	public void setMerchantCategoryCode(String merchantCategoryCode) {
		this.merchantCategoryCode = merchantCategoryCode;
	}

	public String getAccquirerId() {
		return accquirerId;
	}

	public void setAccquirerId(String accquirerId) {
		this.accquirerId = accquirerId;
	}

	public String getMasterKey() {
		return masterKey;
	}

	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}

	public String getWorkingKey() {
		return workingKey;
	}

	public void setWorkingKey(String workingKey) {
		this.workingKey = workingKey;
	}

	public String getTmk() {
		return tmk;
	}

	public void setTmk(String tmk) {
		this.tmk = tmk;
	}

	public String getKcv() {
		return kcv;
	}

	public void setKcv(String kcv) {
		this.kcv = kcv;
	}

	public String getIpek() {
		return ipek;
	}

	public void setIpek(String ipek) {
		this.ipek = ipek;
	}

	public String getKsn() {
		return ksn;
	}

	public void setKsn(String ksn) {
		this.ksn = ksn;
	}

	public boolean isDukpt() {
		return isDukpt;
	}

	public void setDukpt(boolean isDukpt) {
		this.isDukpt = isDukpt;
	}

	public String getStan() {
		return stan;
	}

	public void setStan(String stan) {
		this.stan = stan;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getCardAcceptorId() {
		return cardAcceptorId;
	}

	public void setCardAcceptorId(String cardAcceptorId) {
		this.cardAcceptorId = cardAcceptorId;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getInitialTmk() {
		return initialTmk;
	}

	public void setInitialTmk(String initialTmk) {
		this.initialTmk = initialTmk;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "POSConfing [id=" + id + ", serialNo=" + serialNo + ", posDeviceId=" + posDeviceId + ", merchantId="
				+ merchantId + ", terminalId=" + terminalId + ", merchantPosId=" + merchantPosId
				+ ", merchantCategoryCode=" + merchantCategoryCode + ", accquirerId=" + accquirerId + ", masterKey="
				+ masterKey + ", workingKey=" + workingKey + ", tmk=" + tmk + ", kcv=" + kcv + ", ipek=" + ipek
				+ ", ksn=" + ksn + ", isDukpt=" + isDukpt + ", stan=" + stan + ", createdDate=" + createdDate
				+ ", cardAcceptorId=" + cardAcceptorId + ", responseCode=" + responseCode + ", initialTmk=" + initialTmk
				+ ", status=" + status + "]";
	}

}
