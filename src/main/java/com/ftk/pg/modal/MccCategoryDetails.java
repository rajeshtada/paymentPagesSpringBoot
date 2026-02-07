package com.ftk.pg.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mcc_category_details")
public class MccCategoryDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mcc_category")
	private String mccCategory;

	@Column(name = "mcc_code")
	private Long mccCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMccCategory() {
		return mccCategory;
	}

	public void setMccCategory(String mccCategory) {
		this.mccCategory = mccCategory;
	}

	public Long getMccCode() {
		return mccCode;
	}

	public void setMccCode(Long mccCode) {
		this.mccCode = mccCode;
	}

	@Override
	public String toString() {
		return "MccCategoryDetails [id=" + id + ", mccCaregory=" + mccCategory + ", mccCode=" + mccCode + "]";
	}

}
