package com.ftk.pg.vo.sbiNb;

public class SbiSaleAuthorizationResponse {
private String transactionId;
private String status;
private String pgErrorCode;
private String pgErrorDetail;
private String orderDesc;
private String merchantReferenceNo;
private String approvalCode;
private String rrn;
private String creditDebitCardFlag;
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
public String getTransactionId() {
	return transactionId;
}
public void setTransactionId(String transactionId) {
	this.transactionId = transactionId;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getPgErrorCode() {
	return pgErrorCode;
}
public void setPgErrorCode(String pgErrorCode) {
	this.pgErrorCode = pgErrorCode;
}
public String getPgErrorDetail() {
	return pgErrorDetail;
}
public void setPgErrorDetail(String pgErrorDetail) {
	this.pgErrorDetail = pgErrorDetail;
}
public String getOrderDesc() {
	return orderDesc;
}
public void setOrderDesc(String orderDesc) {
	this.orderDesc = orderDesc;
}
public String getMerchantReferenceNo() {
	return merchantReferenceNo;
}
public void setMerchantReferenceNo(String merchantReferenceNo) {
	this.merchantReferenceNo = merchantReferenceNo;
}
public String getApprovalCode() {
	return approvalCode;
}
public void setApprovalCode(String approvalCode) {
	this.approvalCode = approvalCode;
}
public String getRrn() {
	return rrn;
}
public void setRrn(String rrn) {
	this.rrn = rrn;
}
public String getCreditDebitCardFlag() {
	return creditDebitCardFlag;
}
public void setCreditDebitCardFlag(String creditDebitCardFlag) {
	this.creditDebitCardFlag = creditDebitCardFlag;
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
@Override
public String toString() {
	return "SaleAuthorizationResponse [transactionId=" + transactionId + ", status=" + status + ", pgErrorCode="
			+ pgErrorCode + ", pgErrorDetail=" + pgErrorDetail + ", orderDesc=" + orderDesc + ", merchantReferenceNo="
			+ merchantReferenceNo + ", approvalCode=" + approvalCode + ", rrn=" + rrn + ", creditDebitCardFlag="
			+ creditDebitCardFlag + ", ext1=" + ext1 + ", ext2=" + ext2 + ", ext3=" + ext3 + ", ext4=" + ext4
			+ ", ext5=" + ext5 + ", ext6=" + ext6 + ", ext7=" + ext7 + ", ext8=" + ext8 + ", ext9=" + ext9 + ", ext10="
			+ ext10 + "]";
}

}
