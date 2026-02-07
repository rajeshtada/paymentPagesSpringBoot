package com.ftk.pg.pi.modal;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "merchant_keys")
public class MerchantKeys implements Serializable {

    /**
	 * advance_properties
	 */
	private static final long serialVersionUID = -754554283733979105L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long mid;
    
    @Column(name = "encryption_iv")
    private String iv;

    @Column(name = "encryption_key")
    private String key;
    
    @Column(name = "terminal_id")
    private String terminalId;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "modified_date")
    private Date modifiedDate;
    
    @Column(name = "minute")
    private Long minute; 
}

