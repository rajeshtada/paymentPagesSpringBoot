package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Audited
@Table(name = "merchant_product_details")
public class MerchantProductIdDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7014071072306322769L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "merchant_id")
	private Long merchantId;

	@Column(name = "product_code")
	private String productCode;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "ifsc_code")
	private String ifscCode;

	@Column(name = "branch_name")
	private String branchName;

	@Column(name = "branch_code")
	private String branchCode;

	@Column(name = "beneficiary_name")
	private String beneficiaryName;

	@Column(name = "branch_address")
	private String branchAddress;

	@Column(name = "branch_city")
	private String branchCity;

	@Column(name = "branch_pincode")
	private String branchPinCode;

	@Column(name = "branch_state")
	private String branchState;

	@Column(name = "status")
	private int status;
	
	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "updated_date")
	@UpdateTimestamp
	private LocalDateTime updatedDate;

	@Column(name = "created_by", updatable = false)
	private Long createdBy;

	@Column(name = "updated_by")
	private Long updatedBy;
	
	@Column(name = "vendor_name")
	private Long vendorName;


}
