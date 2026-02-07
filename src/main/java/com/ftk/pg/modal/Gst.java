package com.ftk.pg.modal;



import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Audited
@Table(name="gst_config")
public class Gst implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="cgst")
	private String cGst;
	@Column(name="sgst")
	private String sGst;
	@Column(name="igst")
	private String iGst;
	@CreationTimestamp
	@Column(name="created_date",updatable=false)
	private Date createdDate;
	@UpdateTimestamp
	@Column(name="modified_date")
	private Date modifiedDate;
	@Column(name="created_by",updatable=false)
	private Long createdBy;
	@Column(name="modified_by")
	private Long modifiedBy;
	private boolean status;
	@Column(name="defaultState")
	private String defaultState;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getcGst() {
		return cGst;
	}
	public void setcGst(String cGst) {
		this.cGst = cGst;
	}
	public String getsGst() {
		return sGst;
	}
	public void setsGst(String sGst) {
		this.sGst = sGst;
	}
	public String getiGst() {
		return iGst;
	}
	public void setiGst(String iGst) {
		this.iGst = iGst;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getDefaultState() {
		return defaultState;
	}
	public void setDefaultState(String defaultState) {
		this.defaultState = defaultState;
	}
	@Override
	public String toString() {
		return "Gst [id=" + id + ", cGst=" + cGst + ", sGst=" + sGst + ", iGst=" + iGst + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", status=" + status + ", defaultState=" + defaultState + "]";
	}
	
	
}
