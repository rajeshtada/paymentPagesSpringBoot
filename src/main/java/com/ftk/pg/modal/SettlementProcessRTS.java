package com.ftk.pg.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="settlement_process_rts")
public class SettlementProcessRTS implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "mid")
	private long mid;
	
	@Column(name = "request_id")
	private long requestId;
	
	@Column(name = "pg_txn_id")
	private long pgTxnId;
	
	@Column(name = "portal_txn_id")
	private long portalTxnId;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "rrn")
	private String rrn;

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

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public long getPgTxnId() {
		return pgTxnId;
	}

	public void setPgTxnId(long pgTxnId) {
		this.pgTxnId = pgTxnId;
	}

	public long getPortalTxnId() {
		return portalTxnId;
	}

	public void setPortalTxnId(long portalTxnId) {
		this.portalTxnId = portalTxnId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	@Override
	public String toString() {
		return "InstantSettlementProcess [id=" + id + ", mid=" + mid + ", requestId=" + requestId + ", pgTxnId="
				+ pgTxnId + ", portalTxnId=" + portalTxnId + ", status=" + status + ", remark=" + remark + ", rrn="
				+ rrn + "]";
	}

	
	
	
}
