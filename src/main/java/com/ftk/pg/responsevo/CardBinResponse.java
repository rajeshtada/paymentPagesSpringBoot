package com.ftk.pg.responsevo;

public class CardBinResponse {
	private String bank_name;
	private String country;
	private String url;
	private String type;
	private String scheme;
	private String bin;
	private String category;

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}
	

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "CardBinResponse [bank_name=" + bank_name + ", country=" + country + ", url=" + url + ", type=" + type
				+ ", scheme=" + scheme + ", bin=" + bin + "]";
	}

}
