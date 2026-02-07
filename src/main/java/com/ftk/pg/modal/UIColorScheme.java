package com.ftk.pg.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "ui_color_Scheme")
public class UIColorScheme implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "partner_id")
	private Long partnerId;
    @Column(name = "primary_Color")
	private String primaryColor;
	@Column(name = "secondary_Color")
	private String secondaryColor;
	@Column(name = "logoPath")
	private String logoPath;
	@Column(name = "vpa")
	private String vpa;
	private String textHexaCode;
	
}
