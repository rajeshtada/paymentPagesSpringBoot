package com.ftk.pg.pi.modal;

import java.time.LocalDateTime;


import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "vpa_qr_code")
@Data
public class VPAQrCode {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "m_id")
	private Long mid;

	@Column(name = "vpa")
	private String vpa;

	@Column(name = "title")
	private String title;

	@Column(name = "qrPath")
	private String qrPath;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "createdDate")
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "merchant_name")
	private String merchantName;

	// Bank Details
	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "branch_name")
	private String branchName;

	@Column(name = "accountName")
	private String accountName;

	@Column(name = "accountNumber")
	private String accountNumber;

	@Column(name = "ifsc_code")
	private String ifscCode;

	@Column(name = "gst_no")
	private String gstNo;

	@Column(name = "gst_flag")
	private Boolean gstFlag;

	@Column(name = "micr_code")
	private String MICRCode;
	
	@Column(name = "merchantGenre")
	private String merchantGenere;

}
