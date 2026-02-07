package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "transaction_essentials")
public class TransactionEssentials implements Serializable {

	private static final long serialVersionUID = -937542610394637133L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "transaction_id")
	private Long transactionId;

	private Long udf11;
	private Long udf12;
	private Long udf13;
	private Long udf14;
	private Long udf15;
	private Long udf16;
	private Long udf17;
	private Long udf18;
	private Long udf19;
	private Long udf20;

	private LocalDateTime udf21; // txn Created date
	private LocalDateTime udf22;
	private LocalDateTime udf23;
	private LocalDateTime udf24;
	private LocalDateTime udf25;
	private LocalDateTime udf26;
	private LocalDateTime udf27;
	private LocalDateTime udf28;
	private LocalDateTime udf29;
	private LocalDateTime udf30;

	private String udf31; // BOB Browser Details
	private String udf32; 
	private String udf33;
	private String udf34;
	private String udf35;
	private String udf36;
	private String udf37;
	private String udf38;
	private String udf39;//SBI Rupay Cards
	private String udf40;//SBI Rupay Cards

	private String udf41;//SBI Rupay Cards
	private String udf42;
	private String udf43; // SBI Cryptogram value //Kotak RetRefNo
	private String udf44; // SBI ALT ID Expiry
	private String udf45; // SBI Alt ID Encrypted
	private String udf46; // SBI card Authentication Url
	private String udf47; // SBI ThreeDSServerTransID
	private String udf48; // SBI Cres Response
	private String udf49; // SBI AcsTransID
	private String udf50; // SBI DsTransID

	private String udf51; // Au Instant Transaction RequestId
	private String udf52; // Au Instant Transaction BatchNo
	private String udf53;// Requestor Id for the Centerlized Logger
	private String udf54;
	private String udf55;
	private String udf56;
	private String udf57;
	private String udf58; // payerAccountType icici callback
	private String udf59; // Virtual Vpa
	private String udf60; // ip_address

	
}
