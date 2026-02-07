package com.ftk.pg.responsevo;

import java.util.Map;

public class PaymentResponse {

	private String login;
	private String amt;
	private String txncurr;
	private String merchantTxnId;
	private String ru;
	private String merchantRefNumber;
	private String processorCode;
	private String responseCode;
	private String status;
	private String description;
	private String threeDSecureUrl;
	private boolean result;
	private Long transactionId;
	private Long mId;
	private String signature;
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	// new add
	private String paymentMode;
	private Map<String, String> postReqParam;

	private boolean enableHtml;
	private String html;

	private String type;
	private String processor;
	private String totalCharges;
	private String totalAmount;
	private boolean isPost;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getTxncurr() {
		return txncurr;
	}

	public void setTxncurr(String txncurr) {
		this.txncurr = txncurr;
	}

	public String getMerchantTxnId() {
		return merchantTxnId;
	}

	public void setMerchantTxnId(String merchantTxnId) {
		this.merchantTxnId = merchantTxnId;
	}

	public String getRu() {
		return ru;
	}

	public void setRu(String ru) {
		this.ru = ru;
	}

	public String getMerchantRefNumber() {
		return merchantRefNumber;
	}

	public void setMerchantRefNumber(String merchantRefNumber) {
		this.merchantRefNumber = merchantRefNumber;
	}

	public String getProcessorCode() {
		return processorCode;
	}

	public void setProcessorCode(String processorCode) {
		this.processorCode = processorCode;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getThreeDSecureUrl() {
		return threeDSecureUrl;
	}

	public void setThreeDSecureUrl(String threeDSecureUrl) {
		this.threeDSecureUrl = threeDSecureUrl;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getmId() {
		return mId;
	}

	public void setmId(Long mId) {
		this.mId = mId;
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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	@Override
	public String toString() {
		return "PaymentResponse [login=" + login + ", amt=" + amt + ", txncurr=" + txncurr + ", merchantTxnId="
				+ merchantTxnId + ", ru=" + ru + ", merchantRefNumber=" + merchantRefNumber + ", processorCode="
				+ processorCode + ", responseCode=" + responseCode + ", status=" + status + ", description="
				+ description + ", threeDSecureUrl=" + threeDSecureUrl + ", result=" + result + ", transactionId="
				+ transactionId + ", mId=" + mId + ", signature=" + signature + ", udf1=" + udf1 + ", udf2="
				+ maskStringValue(udf2) + ", udf3=" + maskStringValue(udf3) + ", udf4=" + udf4 + ", udf5=" + udf5
				+ ", paymentMode=" + paymentMode + ", postReqParam=" + postReqParam + ", enableHtml=" + enableHtml
				+ ", html=" + html + ", type=" + type + ", processor=" + processor + ", totalCharges=" + totalCharges
				+ ", totalAmount=" + totalAmount + "]";
	}

	public Map<String, String> getPostReqParam() {
		return postReqParam;
	}

	public void setPostReqParam(Map<String, String> postReqParam) {
		this.postReqParam = postReqParam;
	}

	public boolean isEnableHtml() {
		return enableHtml;
	}

	public void setEnableHtml(boolean enableHtml) {
		this.enableHtml = enableHtml;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getTotalCharges() {
		return totalCharges;
	}

	public void setTotalCharges(String totalCharges) {
		this.totalCharges = totalCharges;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	

	public boolean isPost() {
		return isPost;
	}

	public void setPost(boolean isPost) {
		this.isPost = isPost;
	}

	public String maskStringValue(String stringForMask) {
		String maskString = "";
		try {
			String last = "";
			if (stringForMask.length() > 4) {
				last = stringForMask.substring(stringForMask.length() - 4);
			} else {
				last = stringForMask.substring(stringForMask.length() - 0);
			}
			int len = stringForMask.length() - last.length();
			String subString = stringForMask.substring(0, len);
			int size = subString.length();
			for (int i = 0; i < size; i++) {
				maskString += "*";
			}
			maskString += last;
		} catch (Exception e) {
		}
		return maskString;
	}

	public String maskEmailStringValue(String email) {
		if (email != null && !email.equals("")) {
			int atIndex = email.indexOf('@');
			if (atIndex == -1) {
				return email;
			}
			int dotIndex = email.indexOf('.', atIndex);
			if (dotIndex == -1) {
				return email;
			}
			String prefix = email.substring(0, 3);
			String suffix = email.substring(dotIndex - 0);
			String mask = "";
			for (int i = 3; i < dotIndex - 3; i++) {
				mask += "*";
			}
			return prefix + mask + suffix;
		}
		return null;
	}

}
