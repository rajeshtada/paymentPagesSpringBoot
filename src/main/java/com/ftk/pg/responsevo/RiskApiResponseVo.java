package com.ftk.pg.responsevo;

public class RiskApiResponseVo {

	private Long id;
	private int risk_status;
	private String risk_description;
	private String description;
//	private String hold;
	private String status_code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRisk_status() {
		return risk_status;
	}

	public void setRisk_status(int risk_status) {
		this.risk_status = risk_status;
	}

	public String getRisk_description() {
		return risk_description;
	}

	public void setRisk_description(String risk_description) {
		this.risk_description = risk_description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus_code() {
		return status_code;
	}

	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}

	@Override
	public String toString() {
		return "RiskApiResponseVo [id=" + id + ", risk_status=" + risk_status + ", risk_description=" + risk_description
				+ ", description=" + description + ", status_code=" + status_code + "]";
	}

}
