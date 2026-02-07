package com.ftk.pg.requestvo;
import java.io.Serializable;

public class DebitCardEmiAuthenticationRequest implements Serializable {

	private String BankId;

	private String MerchantId;

	private String TerminalId;

	private String OrderId;
	
	private String AccessCode;
	
	private String MCC;

	private String Currency;

	private String Amount;

	private String TxnType;
	
	private String chPhone;
	
	private String CardNumber;

	private String EmiTenure;

	private String EmiInterestRate;

	private String Product;
	
	private String ProductCategory;
	
	private String ProductSubCategory;

	private String ProdDesc;

	private String ManufacturerName;

	private String SerialNumber;
	
	private String Consent;
	
	private String SecureHash;

	public String getBankId() {
		return BankId;
	}

	public void setBankId(String bankId) {
		BankId = bankId;
	}

	public String getMerchantId() {
		return MerchantId;
	}

	public void setMerchantId(String merchantId) {
		MerchantId = merchantId;
	}

	public String getTerminalId() {
		return TerminalId;
	}

	public void setTerminalId(String terminalId) {
		TerminalId = terminalId;
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getAccessCode() {
		return AccessCode;
	}

	public void setAccessCode(String accessCode) {
		AccessCode = accessCode;
	}

	public String getMCC() {
		return MCC;
	}

	public void setMCC(String mCC) {
		MCC = mCC;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getTxnType() {
		return TxnType;
	}

	public void setTxnType(String txnType) {
		TxnType = txnType;
	}

	public String getChPhone() {
		return chPhone;
	}

	public void setChPhone(String chPhone) {
		this.chPhone = chPhone;
	}

	public String getCardNumber() {
		return CardNumber;
	}

	public void setCardNumber(String cardNumber) {
		CardNumber = cardNumber;
	}

	public String getEmiTenure() {
		return EmiTenure;
	}

	public void setEmiTenure(String emiTenure) {
		EmiTenure = emiTenure;
	}

	public String getEmiInterestRate() {
		return EmiInterestRate;
	}

	public void setEmiInterestRate(String emiInterestRate) {
		EmiInterestRate = emiInterestRate;
	}

	public String getProduct() {
		return Product;
	}

	public void setProduct(String product) {
		Product = product;
	}

	public String getProductCategory() {
		return ProductCategory;
	}

	public void setProductCategory(String productCategory) {
		ProductCategory = productCategory;
	}

	public String getProductSubCategory() {
		return ProductSubCategory;
	}

	public void setProductSubCategory(String productSubCategory) {
		ProductSubCategory = productSubCategory;
	}

	public String getProdDesc() {
		return ProdDesc;
	}

	public void setProdDesc(String prodDesc) {
		ProdDesc = prodDesc;
	}

	public String getManufacturerName() {
		return ManufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		ManufacturerName = manufacturerName;
	}

	public String getSerialNumber() {
		return SerialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		SerialNumber = serialNumber;
	}

	public String getConsent() {
		return Consent;
	}

	public void setConsent(String consent) {
		Consent = consent;
	}

	public String getSecureHash() {
		return SecureHash;
	}

	public void setSecureHash(String secureHash) {
		SecureHash = secureHash;
	}

	@Override
	public String toString() {
		return "DebitCardEmiAuthenticationRequest [BankId=" + BankId + ", MerchantId=" + MerchantId + ", TerminalId="
				+ TerminalId + ", OrderId=" + OrderId + ", AccessCode=" + AccessCode + ", MCC=" + MCC + ", Currency="
				+ Currency + ", Amount=" + Amount + ", TxnType=" + TxnType + ", chPhone=" + chPhone + ", CardNumber="
				+ CardNumber + ", EmiTenure=" + EmiTenure + ", EmiInterestRate=" + EmiInterestRate + ", Product="
				+ Product + ", ProductCategory=" + ProductCategory + ", ProductSubCategory=" + ProductSubCategory
				+ ", ProdDesc=" + ProdDesc + ", ManufacturerName=" + ManufacturerName + ", SerialNumber=" + SerialNumber
				+ ", Consent=" + Consent + ", SecureHash=" + SecureHash + "]";
	}

	
	
	
}
