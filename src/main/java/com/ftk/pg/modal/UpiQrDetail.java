package com.ftk.pg.modal;

import java.io.Serializable;
import java.sql.ResultSet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "upi_qr_detail")
public class UpiQrDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String vpa;

	private Long mid;
	
	private String responseUrl;
	
	private long bankId;
	
	private long gindex;
	
	private boolean enable;
	
	private String merchantGenre;

	

	public void populate(ResultSet rs){
	        try {
	            this.mid = rs.getLong("mid");
	            this.vpa=rs.getString("vpa");
	            this.enable=rs.getBoolean("enable");
	            this.merchantGenre=rs.getString("merchantGenre");
	        }
	        catch (Exception e) {
				// TODO: handle exception
			}
	 }
	
}
