package com.ftk.pg.modal;

import java.util.List;

public class FilterVo {
	private String fromDate;
	private String toDate;
	private String username;
	private Long merchantId;
	private String merchantTxnId;
	private Long txnId;
	private String responseCode;
	private String status;
	private String searchData;
	private String selectData;
	private String settlementRefNo;
	private int settlementStatus;
	private int settlementFilterType;
	private List<Long> mids;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantTxnId() {
		return merchantTxnId;
	}

	public void setMerchantTxnId(String merchantTxnId) {
		this.merchantTxnId = merchantTxnId;
	}

	public Long getTxnId() {
		return txnId;
	}

	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSearchData() {
		return searchData;
	}

	public void setSearchData(String searchData) {
		this.searchData = searchData;
	}

	public String getSelectData() {
		return selectData;
	}

	public void setSelectData(String selectData) {
		this.selectData = selectData;
	}

	public String getSettlementRefNo() {
		return settlementRefNo;
	}

	public void setSettlementRefNo(String settlementRefNo) {
		this.settlementRefNo = settlementRefNo;
	}

	public int getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(int settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public int getSettlementFilterType() {
		return settlementFilterType;
	}

	public void setSettlementFilterType(int settlementFilterType) {
		this.settlementFilterType = settlementFilterType;
	}
	
	public List<Long> getMids() {
		return mids;
	}

	public void setMids(List<Long> mids) {
		this.mids = mids;
	}

	@Override
	public String toString() {
		return "FilterVo [fromDate=" + fromDate + ", toDate=" + toDate + ", username=" + username + ", merchantId="
				+ merchantId + ", merchantTxnId=" + merchantTxnId + ", txnId=" + txnId + ", responseCode="
				+ responseCode + ", status=" + status + ", searchData=" + searchData + ", selectData=" + selectData
				+ ", settlementRefNo=" + settlementRefNo + ", settlementStatus=" + settlementStatus
				+ ", settlementFilterType=" + settlementFilterType + "]";
	}

}
