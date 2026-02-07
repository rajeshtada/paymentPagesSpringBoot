package com.ftk.pg.pi.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "merchant_invoice")
public class Invoice implements Serializable {

	
	private static final long serialVersionUID = 1l;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mid")
	private Long mId;

	@Column(name = "invoice_no")
	private String invoiceNo;

	@Column(name = "invoice_no_type")
	private Integer invoiceNoType; // 0=> default 1=> manual

	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "currency")
	private String currency;

	@Column(name = "paymentMode")
	private String paymentMode;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "customer_emailId")
	private String customerEmailId;

	@Column(name = "customer_mobile_number")
	private String customerMobileNo;

	@Column(name = "token")
	private String token;

	@Column(name = "expireTime", nullable = false)
	private Date expireTime;

	private String productType;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "email_status")
	@ColumnDefault(value = "0")
	private Boolean emailStatus;

	@Column(name = "payment_status")
	@ColumnDefault(value = "0")
	private Boolean payStatus;

	@Column(name = "txn_id")
	private Long txnId;

	@Column(name = "isSMS")
	@ColumnDefault(value = "0")
	private int isSMS;

	@Column(name = "isEMail")
	@ColumnDefault(value = "0")
	private int isEMail;

//
	@Column(name = "item_name")
	private String itemName;

	@Column(name = "item_order_number")
	private String itemOrderNumber;

	@Column(name = "commission_amount")
	private String commissionAmount;

	private String igst;

	private String sgst;

	private String cgst;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@Column(name = "commission_type")
	private String commissionType;

	@Column(name = "item_serial_no")
	private String itemSerialNo;

	@Column(name = "invoice_type")
	private String invoiceType;

	@Column(name = "recursive_date")
	private String recursiveDate;

	@Column(name = "coupon_code")
	private String couponCode;

	@Column(name = "discount")
	private String discount;

	@Column(name = "due_charges")
	private String dueCharges;

	@Column(name = "customer_gst_no")
	private String customerGstNo;

	@Column(name = "invoice_notes")
	private String invoiceNotes;

	@Column(name = "invoice_description")
	private String invoiceDescription;

	@Column(name = "vpa")
	private String vpa;

	@Column(name = "expiryDate")
	private Date expiryDate;

	@Column(name = "fine")
	private BigDecimal fine;

	@Column(name = "udf1") // block Id
	private Long udf1;

	@Column(name = "udf2") // flat Id
	private Long udf2;

	@Column(name = "udf3")
	private Long udf3;

	@Column(name = "udf4")
	private Long udf4;

	@Column(name = "udf5")
	private String udf5; // Block name / bId

	@Column(name = "udf6") // Flat name / sId
	private String udf6;

	@Column(name = "udf7")
	private String udf7; // merchant order no

	@Column(name = "udf8")
	private String udf8;

	@Column(name = "udf9") // from date
	private LocalDate udf9;

	@Column(name = "udf10") // to date
	private LocalDate udf10;

	@Column(name = "status")
	@ColumnDefault(value = "1")
	private Integer status;

	@Column(name = "type", nullable = false, columnDefinition = "int default 0")
	private int type; // 0 old invoice and 1 new format invoice

	@Column(name = "cess_tax")
	private String cessTax;

	private String udf11; // invoicing

	
}
