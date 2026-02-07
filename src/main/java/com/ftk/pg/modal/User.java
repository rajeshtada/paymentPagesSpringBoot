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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Audited
@Setter
@Getter
@Table(name = "pg_users")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "mobile_no")
	public String mobileNo;

	@Column(name = "email")
	private String email;

	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "address")
	private String address;

	@Column(name = "created_date")
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	@JoinColumn(name = "created_by")
	private String created_by;

	@JoinColumn(name = "modified_by")
	private String modified_by;

	private boolean enabled;

	@Column(name = "password")
	private String password;

	private Long mid;

	
}
