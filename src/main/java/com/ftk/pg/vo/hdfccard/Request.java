package com.ftk.pg.vo.hdfccard;

import java.math.BigDecimal;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Request {

	// 1. TranPortal Identification Number (Tranportal ID)
	private int id;

	// 2. TranPortal Password
	private String password;

	// 3. Transaction Action Type
	private String action;

	// 4. Transaction Amount
	private BigDecimal amt;

	// 5. Currency Code
	private String currencycode;

	// 6. Tracking ID
	private String trackid;

	// 7. Type of Card
	private String type;

	// 8. Response URL
	private String responseURL;

	// 9. Error URL
	private String errorURL;

	// 10. Card Number
	private String card;

	// 11. Card Expiry Month
	private String expmonth;

	// 12. Card Expiry Year
	private String expyear;

	// 13. Card Verification Value (CVV2)
	private String cvv2;

	// 14. Card Holder Name
	private String member;

	// 15-19. UDF Fields (user-defined fields)
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;

	// 20. COF parameter (for saved card transactions)
	private String cof;

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int i) {
		this.id = i;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal mAmount) {
		this.amt = mAmount;
	}

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

	public String getTrackid() {
		return trackid;
	}

	public void setTrackid(String trackid) {
		this.trackid = trackid;
	}

	public String getResponseURL() {
		return responseURL;
	}

	public void setResponseURL(String responseURL) {
		this.responseURL = responseURL;
	}

	public String getErrorURL() {
		return errorURL;
	}

	public void setErrorURL(String errorURL) {
		this.errorURL = errorURL;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getExpmonth() {
		return expmonth;
	}

	public void setExpmonth(String expmonth) {
		this.expmonth = expmonth;
	}

	public String getExpyear() {
		return expyear;
	}

	public void setExpyear(String expyear) {
		this.expyear = expyear;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getUdf1() {
		return udf1;
	}

	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	public String getUdf2() {
		return udf2;
	}

	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}

	public String getUdf3() {
		return udf3;
	}

	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}

	public String getUdf4() {
		return udf4;
	}

	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}

	public String getUdf5() {
		return udf5;
	}

	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}

	public String getCof() {
		return cof;
	}

	public void setCof(String cof) {
		this.cof = cof;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", password=" + password + ", action=" + action + ", amt=" + amt
				+ ", currencycode=" + currencycode + ", trackid=" + trackid + ", type=" + type + ", responseURL="
				+ responseURL + ", errorURL=" + errorURL + ", card=" + card + ", expmonth=" + expmonth + ", expyear="
				+ expyear + ", cvv2=" + cvv2 + ", member=" + member + ", udf1=" + udf1 + ", udf2=" + udf2 + ", udf3="
				+ udf3 + ", udf4=" + udf4 + ", udf5=" + udf5 + ", cof=" + cof + "]";
	}

}
