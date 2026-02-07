package com.ftk.pg.modal;

import java.io.Serializable;
import java.sql.ResultSet;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import com.ftk.pg.util.Util2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "merchant_details")
@Data
@Audited
public class Merchant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "merchant_id")
	private Long mid;

	@Column(name = "merchant_name")
	private String merchantName;
    
	private String password;

	private String state;

	private String city;

	private String address;

	private String district;

	@Column(name = "merchant_contact_person")
	private String merchantContactPerson;

	@Column(name = "registration_number")
	private String registrationNumber;

	@Column(name = "company_pan_number")
	private String companyPAN_Number;

	@Column(name = "contact_person_pan_number")
	private String contactPersonPAN_Number;

	@Column(name = "companyGST_Number")
	private String companyGST_Number;

	private String pincode;

	@Column(name = "business_operation_state")
	private String businessOperationState;

	@Column(name = "business_operation_city")
	private String businessOperationCity;

	@Column(name = "business_operation_address")
	private String businessOperationAddress;

	@Column(name = "business_operation_district")
	private String businessOperationDistrict;

	@Column(name = "business_operation_pincode")
	private String businessOperationPincode;

	@Column(name = "business_operation_telephone")
	private String businessOperationTelephone;

	@Column(name = "brand_name")
	private String brandName;

	@Column(name = "business_category")
	private String businessCategory;

	@Column(name = "no_of_employees")
	private String noOfEmployees;

	@Column(name = "other_business_locations")
	private String otherBusinessLocations;

	@Column(name = "app_url")
	private String appURL;

	private String fax;

	private String url;

	private String email;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "merchant_private_key")
	private String merchantPrivateKey;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	private int status;

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

	@Column(name = "id_cardtype")
	private String idCardType;

	@Column(name = "id_proof")
	private String idProofPath;

	@Column(name = "address_proof")
	private String addressProofPath;

	@Column(name = "certification_of_incorporation_path")
	private String certificationOfIncorporationPath;

	@Column(name = "gst_number_path")
	private String gstNumberPath;

	@Column(name = "company_pan_path")
	private String companyPAN_Path;

	@Column(name = "contact_person_pan_path")
	private String contactPersonPAN_Path;

	@Column(name = "account_statement_with_address_proof")
	private String accountStatementwithAddressProof;

	@Column(name = "image")
	private String imagePath;

	@Column(name = "enabled_products")
	private String enabledProducts;

	@Column(name = "request_hash_key")
	private String requestHashKey;

	@Column(name = "response_hash_key")
	private String responseHashKey;

	@Column(name = "entity_type")
	private String entityType;

	@Column(name = "company_name")
	private String companyName;
	@Column(name = "mcc_code")
	private String mccCode;

	@Column(name = "business_type")
	private String businessType;

	@Column(name = "monthly_sales")
	private String monthlySales;

	@Column(name = "own_GST_number")
	private String ownGST_Number;

	@Column(name = "setting")
	private String setting; // using in case of upload settlement file for bob

	@Column(name = "enableDuplicateMTxnId", nullable = false, columnDefinition = "BOOLEAN DEFAULT 0")
	private Boolean enableDuplicateMTxnId;

//	for force update :0
//	else 1
	@Column(name = "forceUpdate", nullable = false, columnDefinition = "BOOLEAN DEFAULT 0")
	private Boolean forceUpdate;

	
	public void populate(ResultSet rs) {

		try {
			this.mid = rs.getLong("merchant_id");
			this.accountNumber = rs.getString("account_number");
			this.merchantName = rs.getString("merchant_name").replace("||", " ");
			this.ifscCode = rs.getString("ifsc_code");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


}
