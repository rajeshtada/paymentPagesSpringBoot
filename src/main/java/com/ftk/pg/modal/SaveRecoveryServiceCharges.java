
package com.ftk.pg.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "save_recovery_service_charges")
public class SaveRecoveryServiceCharges implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "merchant_Recovery_Charges_id")
	private Long merchantRecoveryChargesId;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "vpa")
	private String vpa;

	@Column(name = "started_date")
	private String startedDate;
	
	@Column(name = "recovery_date")
	private String recoveryDate;

}
