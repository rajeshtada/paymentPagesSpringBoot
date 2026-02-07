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
@Table(name = "default_setting")
public class DefaultSetting implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "default_setting_id")
	private Long defaultSettingId;

	@Column(name = "mcc_code")
	private String mccCode;

	@Column(name = "title")
	private String title;

	public Long getDefaultSettingId() {
		return defaultSettingId;
	}

	public void setDefaultSettingId(Long defaultSettingId) {
		this.defaultSettingId = defaultSettingId;
	}

	public String getMccCode() {
		return mccCode;
	}

	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "DefaultSetting [defaultSettingId=" + defaultSettingId + ", mccCode=" + mccCode + ", title=" + title
				+ "]";
	}

}
