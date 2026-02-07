package com.ftk.pg.responsevo;
public class TransactionRequeryResponse {

    private String mid;
    private String getepayTxnId;
    private String txnAmount;
    private String txnStatus;
    private String merchantOrderNo;
    private String udf1;
    private String udf2;
    private String udf3;
    private String udf4;
    private String udf5;
    private String paymentMode;
    private String commission;
    private String description;

    private String requeryStatus;
    private String requeryMessage;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getGetepayTxnId() {
        return getepayTxnId;
    }

    public void setGetepayTxnId(String getepayTxnId) {
        this.getepayTxnId = getepayTxnId;
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
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

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequeryStatus() {
        return requeryStatus;
    }

    public void setRequeryStatus(String requeryStatus) {
        this.requeryStatus = requeryStatus;
    }

    public String getRequeryMessage() {
        return requeryMessage;
    }

    public void setRequeryMessage(String requeryMessage) {
        this.requeryMessage = requeryMessage;
    }
}
