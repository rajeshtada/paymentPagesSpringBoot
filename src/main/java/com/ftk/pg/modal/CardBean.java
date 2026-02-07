package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "card_bean")
@Entity
@Getter
@Setter
public class CardBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1826288190297534705L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "from_bin")
	private Integer fromBin;

	@Column(name = "to_bin")
	private Integer toBin;

	@Column(name = "cardType")
	private String cardType;

	@Column(name = "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "modify_date")
	@UpdateTimestamp
	private LocalDateTime modifyDate;

	@Column(name = "type_of_card")
	private String typeOfCard;

	@Column(name = "domestic_international")
	private String domesticInternational;

	
}
