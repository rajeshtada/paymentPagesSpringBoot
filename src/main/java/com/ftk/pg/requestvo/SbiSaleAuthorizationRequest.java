package com.ftk.pg.requestvo;

import com.ftk.pg.util.Util;

public class SbiSaleAuthorizationRequest {
private String pgInstanceId;
private String merchantId;
private String acquiringBankId;
private String action;
private String transactionTypeCode;
private String deviceCategory;
private String pan;
private String expiryDateYYYY;
private String expiryDateMM;
private String cvv2;
private String nameOnCard;
private String email;
private String currencyCode;
private String amount;
private String merchantReferenceNo;
private String orderDesc;
private String customerDeviceId;
private String mpiTransactionId;
private String threeDsStatus;
private String threeDsEci;
private String threeDsXid;
private String threeDsCavvAav;
private String messageHash;
private String ext1;
private String ext2;
private String ext3;
private String ext4;
private String ext5;
private String ext6;
private String ext7;
private String ext8;
private String ext9;
private String ext10;
private String amounInInr;
private String trid;
private String cryptogram;
private String threeDsVersion;
private String threeDsTxnId;
private String installmentFrequency;
private String maxDebitAmount;
private String siUniqueRefNum;
private String installment;
private String postalCode;
private String streetAddress;
private String shippingAddress;
private String altIdFlag;
public String getPgInstanceId() {
	return pgInstanceId;
}
public void setPgInstanceId(String pgInstanceId) {
	this.pgInstanceId = pgInstanceId;
}
public String getMerchantId() {
	return merchantId;
}
public void setMerchantId(String merchantId) {
	this.merchantId = merchantId;
}
public String getAcquiringBankId() {
	return acquiringBankId;
}
public void setAcquiringBankId(String acquiringBankId) {
	this.acquiringBankId = acquiringBankId;
}
public String getAction() {
	return action;
}
public void setAction(String action) {
	this.action = action;
}
public String getTransactionTypeCode() {
	return transactionTypeCode;
}
public void setTransactionTypeCode(String transactionTypeCode) {
	this.transactionTypeCode = transactionTypeCode;
}
public String getDeviceCategory() {
	return deviceCategory;
}
public void setDeviceCategory(String deviceCategory) {
	this.deviceCategory = deviceCategory;
}
public String getPan() {
	return pan;
}
public void setPan(String pan) {
	this.pan = pan;
}
public String getExpiryDateYYYY() {
	return expiryDateYYYY;
}
public void setExpiryDateYYYY(String expiryDateYYYY) {
	this.expiryDateYYYY = expiryDateYYYY;
}
public String getExpiryDateMM() {
	return expiryDateMM;
}
public void setExpiryDateMM(String expiryDateMM) {
	this.expiryDateMM = expiryDateMM;
}
public String getCvv2() {
	return cvv2;
}
public void setCvv2(String cvv2) {
	this.cvv2 = cvv2;
}
public String getNameOnCard() {
	return nameOnCard;
}
public void setNameOnCard(String nameOnCard) {
	this.nameOnCard = nameOnCard;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getCurrencyCode() {
	return currencyCode;
}
public void setCurrencyCode(String currencyCode) {
	this.currencyCode = currencyCode;
}
public String getAmount() {
	return amount;
}
public void setAmount(String amount) {
	this.amount = amount;
}
public String getMerchantReferenceNo() {
	return merchantReferenceNo;
}
public void setMerchantReferenceNo(String merchantReferenceNo) {
	this.merchantReferenceNo = merchantReferenceNo;
}
public String getOrderDesc() {
	return orderDesc;
}
public void setOrderDesc(String orderDesc) {
	this.orderDesc = orderDesc;
}
public String getCustomerDeviceId() {
	return customerDeviceId;
}
public void setCustomerDeviceId(String customerDeviceId) {
	this.customerDeviceId = customerDeviceId;
}
public String getMpiTransactionId() {
	return mpiTransactionId;
}
public void setMpiTransactionId(String mpiTransactionId) {
	this.mpiTransactionId = mpiTransactionId;
}
public String getThreeDsStatus() {
	return threeDsStatus;
}
public void setThreeDsStatus(String threeDsStatus) {
	this.threeDsStatus = threeDsStatus;
}
public String getThreeDsEci() {
	return threeDsEci;
}
public void setThreeDsEci(String threeDsEci) {
	this.threeDsEci = threeDsEci;
}
public String getThreeDsXid() {
	return threeDsXid;
}
public void setThreeDsXid(String threeDsXid) {
	this.threeDsXid = threeDsXid;
}
public String getThreeDsCavvAav() {
	return threeDsCavvAav;
}
public void setThreeDsCavvAav(String threeDsCavvAav) {
	this.threeDsCavvAav = threeDsCavvAav;
}
public String getMessageHash() {
	return messageHash;
}
public void setMessageHash(String messageHash) {
	this.messageHash = messageHash;
}
public String getExt1() {
	return ext1;
}
public void setExt1(String ext1) {
	this.ext1 = ext1;
}
public String getExt2() {
	return ext2;
}
public void setExt2(String ext2) {
	this.ext2 = ext2;
}
public String getExt3() {
	return ext3;
}
public void setExt3(String ext3) {
	this.ext3 = ext3;
}
public String getExt4() {
	return ext4;
}
public void setExt4(String ext4) {
	this.ext4 = ext4;
}
public String getExt5() {
	return ext5;
}
public void setExt5(String ext5) {
	this.ext5 = ext5;
}
public String getExt6() {
	return ext6;
}
public void setExt6(String ext6) {
	this.ext6 = ext6;
}
public String getExt7() {
	return ext7;
}
public void setExt7(String ext7) {
	this.ext7 = ext7;
}
public String getExt8() {
	return ext8;
}
public void setExt8(String ext8) {
	this.ext8 = ext8;
}
public String getExt9() {
	return ext9;
}
public void setExt9(String ext9) {
	this.ext9 = ext9;
}
public String getExt10() {
	return ext10;
}
public void setExt10(String ext10) {
	this.ext10 = ext10;
}
public String getAmounInInr() {
	return amounInInr;
}
public void setAmounInInr(String amounInInr) {
	this.amounInInr = amounInInr;
}
public String getTrid() {
	return trid;
}
public void setTrid(String trid) {
	this.trid = trid;
}
public String getCryptogram() {
	return cryptogram;
}
public void setCryptogram(String cryptogram) {
	this.cryptogram = cryptogram;
}
public String getThreeDsVersion() {
	return threeDsVersion;
}
public void setThreeDsVersion(String threeDsVersion) {
	this.threeDsVersion = threeDsVersion;
}
public String getThreeDsTxnId() {
	return threeDsTxnId;
}
public void setThreeDsTxnId(String threeDsTxnId) {
	this.threeDsTxnId = threeDsTxnId;
}
public String getInstallmentFrequency() {
	return installmentFrequency;
}
public void setInstallmentFrequency(String installmentFrequency) {
	this.installmentFrequency = installmentFrequency;
}
public String getMaxDebitAmount() {
	return maxDebitAmount;
}
public void setMaxDebitAmount(String maxDebitAmount) {
	this.maxDebitAmount = maxDebitAmount;
}
public String getSiUniqueRefNum() {
	return siUniqueRefNum;
}
public void setSiUniqueRefNum(String siUniqueRefNum) {
	this.siUniqueRefNum = siUniqueRefNum;
}
public String getInstallment() {
	return installment;
}
public void setInstallment(String installment) {
	this.installment = installment;
}
public String getPostalCode() {
	return postalCode;
}
public void setPostalCode(String postalCode) {
	this.postalCode = postalCode;
}
public String getStreetAddress() {
	return streetAddress;
}
public void setStreetAddress(String streetAddress) {
	this.streetAddress = streetAddress;
}
public String getShippingAddress() {
	return shippingAddress;
}
public void setShippingAddress(String shippingAddress) {
	this.shippingAddress = shippingAddress;
}
public String getAltIdFlag() {
	return altIdFlag;
}
public void setAltIdFlag(String altIdFlag) {
	this.altIdFlag = altIdFlag;
}
@Override
public String toString() {
	return "SbiSaleAuthorizationRequest [pgInstanceId=" + pgInstanceId + ", merchantId=" + merchantId
			+ ", acquiringBankId=" + acquiringBankId + ", action=" + action + ", transactionTypeCode="
			+ transactionTypeCode + ", deviceCategory=" + deviceCategory + ", pan=" +  Util.maskStringValue(pan) + ", expiryDateYYYY="
			+  Util.maskStringValue(expiryDateYYYY) + ", expiryDateMM=" +  Util.maskStringValue(expiryDateMM) + ", cvv2=" +  Util.maskStringValue(cvv2) + ", nameOnCard=" +  Util.maskStringValue(nameOnCard)
			+ ", email=" + email + ", currencyCode=" + currencyCode + ", amount=" + amount + ", merchantReferenceNo="
			+ merchantReferenceNo + ", orderDesc=" + orderDesc + ", customerDeviceId=" + customerDeviceId
			+ ", mpiTransactionId=" + mpiTransactionId + ", threeDsStatus=" + threeDsStatus + ", threeDsEci="
			+ threeDsEci + ", threeDsXid=" + threeDsXid + ", threeDsCavvAav=" + threeDsCavvAav + ", messageHash="
			+ messageHash + ", ext1=" + ext1 + ", ext2=" + ext2 + ", ext3=" + ext3 + ", ext4=" + ext4 + ", ext5=" + ext5
			+ ", ext6=" + ext6 + ", ext7=" + ext7 + ", ext8=" + ext8 + ", ext9=" + ext9 + ", ext10=" + ext10
			+ ", amounInInr=" + amounInInr + ", trid=" + trid + ", cryptogram=" + cryptogram + ", threeDsVersion="
			+ threeDsVersion + ", threeDsTxnId=" + threeDsTxnId + ", installmentFrequency=" + installmentFrequency
			+ ", maxDebitAmount=" + maxDebitAmount + ", siUniqueRefNum=" + siUniqueRefNum + ", installment="
			+ installment + ", postalCode=" + postalCode + ", streetAddress=" + streetAddress + ", shippingAddress="
			+ shippingAddress + ", altIdFlag=" + altIdFlag + "]";
}




}
