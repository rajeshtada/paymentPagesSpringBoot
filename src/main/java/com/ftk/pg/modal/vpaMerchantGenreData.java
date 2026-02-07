package com.ftk.pg.modal;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vpa_merchant_genre")
public class vpaMerchantGenreData {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "genre_id")
	private Long genreId;
	
	@Column(name = "vpa")
	private String vpa;
	
	@Column(name = "merchant_genre")
	private String mechantGenre;

	@Column(name = "status")
	@ColumnDefault("'0'")
	private int status;


	public Long getGenreId() {
		return genreId;
	}

	public void setGenreId(Long genreId) {
		this.genreId = genreId;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public String getMechantGenre() {
		return mechantGenre;
	}

	public void setMechantGenre(String mechantGenre) {
		this.mechantGenre = mechantGenre;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "vpaMerchantGenreData [genreId=" + genreId + ", vpa=" + vpa + ", mechantGenre=" + mechantGenre
				+ ", status=" + status + "]";
	}
	
	
}
