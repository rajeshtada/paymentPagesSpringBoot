package com.ftk.pg.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "payout_beneficiary_transaction")
@Entity
public class PayoutBeneficiaryTransaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private Long mid;
    @Column(name = "payout_beneficiary_id")
    private Long payoutBeneficiaryId;

    @Column(name = "customer_mid")
    private String customerMid;
    private String vpa;
    @Column(name = "merchant_name")
    private String merchantName;
    @Column(name = "beneficiary_account_name")
    private String beneficiaryAccountName;
    @Column(name = "beneficiary_account_number")
    private String beneficiaryAccountNumber;
    @Column(name = "beneficiary_ifsc_code")
    private String beneficiaryIfscCode;

    @Column(name = "beneficiary_bank_name")
    private String beneficiaryBankName;

    private int status;

    private String message;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Long getId() {
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

    public String getCustomerMid() {
        return customerMid;
    }

    public void setCustomerMid(String customerMid) {
        this.customerMid = customerMid;
    }

    public String getVpa() {
        return vpa;
    }

    public void setVpa(String vpa) {
        this.vpa = vpa;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getBeneficiaryAccountName() {
        return beneficiaryAccountName;
    }

    public void setBeneficiaryAccountName(String beneficiaryAccountName) {
        this.beneficiaryAccountName = beneficiaryAccountName;
    }

    public String getBeneficiaryAccountNumber() {
        return beneficiaryAccountNumber;
    }

    public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
    }

    public String getBeneficiaryIfscCode() {
        return beneficiaryIfscCode;
    }

    public void setBeneficiaryIfscCode(String beneficiaryIfscCode) {
        this.beneficiaryIfscCode = beneficiaryIfscCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getPayoutBeneficiaryId() {
        return payoutBeneficiaryId;
    }

    public void setPayoutBeneficiaryId(Long payoutBeneficiaryId) {
        this.payoutBeneficiaryId = payoutBeneficiaryId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBeneficiaryBankName() {
        return beneficiaryBankName;
    }

    public void setBeneficiaryBankName(String beneficiaryBankName) {
        this.beneficiaryBankName = beneficiaryBankName;
    }
}
