package com.ftk.pg.modal;

import java.io.Serializable;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Audited
@Table(name = "merchant_bin_routing")
public class MerchantBinRouting  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "bin_routing_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "merchant_id")
	private Long mid;

	@Column(name = "bins")
	private String bins;

	private String currency;

	@Column(name = "setting_1")
	private String setting1;

	@Column(name = "setting_2")
	private String setting2;

	@Column(name = "setting_3")
	private String setting3;

	@Column(name = "setting_4")
	private String setting4;

	@Column(name = "setting_5")
	private String setting5;

	private String processor;

	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getBins() {
		return bins;
	}

	public void setBins(String bins) {
		this.bins = bins;
	}

	public String getSetting1() {
		return setting1;
	}

	public void setSetting1(String setting1) {
		this.setting1 = setting1;
	}

	public String getSetting2() {
		return setting2;
	}

	public void setSetting2(String setting2) {
		this.setting2 = setting2;
	}

	public String getSetting3() {
		return setting3;
	}

	public void setSetting3(String setting3) {
		this.setting3 = setting3;
	}

	public String getSetting4() {
		return setting4;
	}

	public void setSetting4(String setting4) {
		this.setting4 = setting4;
	}

	public String getSetting5() {
		return setting5;
	}

	public void setSetting5(String setting5) {
		this.setting5 = setting5;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "BinRouting [id=" + id + ", mid=" + mid + ", bins=" + bins + ", setting1=" + setting1 + ", setting2="
				+ setting2 + ", setting3=" + setting3 + ", setting4=" + setting4 + ", setting5=" + setting5
				+ ", processor=" + processor + ", status=" + status + ", currency= " + currency + "]";
	}

}
