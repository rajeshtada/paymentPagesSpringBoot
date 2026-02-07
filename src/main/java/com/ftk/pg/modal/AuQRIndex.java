package com.ftk.pg.modal;

import java.io.Serializable;

import org.hibernate.envers.Audited;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Audited
@Table(name = "au_qr_index_bak")
public class AuQRIndex implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 116360517436169490L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String rrn;
	private String externalRefno;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getExternalRefno() {
		return externalRefno;
	}

	public void setExternalRefno(String externalRefno) {
		this.externalRefno = externalRefno;
	}

	@Override
	public String toString() {
		return "AuQRIndex [id=" + id + ", rrn=" + rrn + ", externalRefno=" + externalRefno + "]";
	}

}
