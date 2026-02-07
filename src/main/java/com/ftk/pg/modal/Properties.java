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
import lombok.Getter;
import lombok.Setter;

@Entity
@Audited
@Table(name = "properties")
@Setter
@Getter
public class Properties implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "property_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long propertyId;

	@Column(name = "property_key")
	private String propertykey;

	@Column(name = "property_value")
	private String propertyValue;

	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private Date createdDate;

	@UpdateTimestamp
	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "created_by", updatable = false)
	private Long createdBy;

	@Column(name = "modified_by")
	private Long modifiedBy;

	private boolean status;

}
