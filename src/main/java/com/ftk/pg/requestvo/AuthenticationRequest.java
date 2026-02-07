package com.ftk.pg.requestvo;

public class AuthenticationRequest {

	private String BankId;
	private String MerchantId;
	private String TerminalId;
	private String OrderId;
	private String MCC;
	private String AccessCode;
	private String Command;
	private String chFirstName;
	private String chLastName;
	private String chAddrStreet;
	private String chAddrCity;
	private String chAddrState;
	private String chAddrZip;
	private String chEmail;
	private String chPhone;
	private String Currency;
	private String Amount;
	private String SecureHash;
	private String OrderInfo;
	private String PaymentOption;
	private String IpAddress;
	private String BrowserDetails;
	private String UserAgent;
	private String CardNumber;
	private String ExpiryDate;
	private String CVV;
	private String UDF01;
	private String UDF02;
	private String UDF03;
	private String UDF04;
	private String UDF05;
	private String UDF06;
	private String UDF07;
	private String UDF08;
	private String UDF09;
	private String UDF010;
	private String AcceptHeader;
	private String AuthenticationResponseURL;
	private String EmiTenure;
	private String EmiInterestRate;
	private String EmiAmount;
	private String CustomerId;
	private String SiType;
	private String SitxnType;
	private String SiStartDate;
	private String SiEndDate;
	private String PaymentFrequency;
	private String SiAmount;
	private String SiMaxAmount;
	private String SiMaxTxnCount;
	private String SiPGRegID;
	private String siAlertpreference;
	private String chTokenizationConsent;
	private String chUserID;
	private String CardTokenPan;
	private String CardTokenExpiry;
	private String CardTokenCrypto;
	private String MerchantTRID;
	private String CardTokenReferenceNo;
	private String browserTZ;
	private String browserScreenHeight;
	private String browserScreenWidth;
	private String browserJavaEnabled;
	private String browserColorDepth;
	private String browserJavascriptEnabled;
	private String PanSource;
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
	public String getMCC() {
		return MCC;
	}
	public void setMCC(String mCC) {
		MCC = mCC;
	}
	public String getAccessCode() {
		return AccessCode;
	}
	public void setAccessCode(String accessCode) {
		AccessCode = accessCode;
	}
	public String getCommand() {
		return Command;
	}
	public void setCommand(String command) {
		Command = command;
	}
	public String getChFirstName() {
		return chFirstName;
	}
	public void setChFirstName(String chFirstName) {
		this.chFirstName = chFirstName;
	}
	public String getChLastName() {
		return chLastName;
	}
	public void setChLastName(String chLastName) {
		this.chLastName = chLastName;
	}
	public String getChAddrStreet() {
		return chAddrStreet;
	}
	public void setChAddrStreet(String chAddrStreet) {
		this.chAddrStreet = chAddrStreet;
	}
	public String getChAddrCity() {
		return chAddrCity;
	}
	public void setChAddrCity(String chAddrCity) {
		this.chAddrCity = chAddrCity;
	}
	public String getChAddrState() {
		return chAddrState;
	}
	public void setChAddrState(String chAddrState) {
		this.chAddrState = chAddrState;
	}
	public String getChAddrZip() {
		return chAddrZip;
	}
	public void setChAddrZip(String chAddrZip) {
		this.chAddrZip = chAddrZip;
	}
	public String getChEmail() {
		return chEmail;
	}
	public void setChEmail(String chEmail) {
		this.chEmail = chEmail;
	}
	public String getChPhone() {
		return chPhone;
	}
	public void setChPhone(String chPhone) {
		this.chPhone = chPhone;
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
	public String getSecureHash() {
		return SecureHash;
	}
	public void setSecureHash(String secureHash) {
		SecureHash = secureHash;
	}
	public String getOrderInfo() {
		return OrderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		OrderInfo = orderInfo;
	}
	public String getPaymentOption() {
		return PaymentOption;
	}
	public void setPaymentOption(String paymentOption) {
		PaymentOption = paymentOption;
	}
	public String getIpAddress() {
		return IpAddress;
	}
	public void setIpAddress(String ipAddress) {
		IpAddress = ipAddress;
	}
	public String getBrowserDetails() {
		return BrowserDetails;
	}
	public void setBrowserDetails(String browserDetails) {
		BrowserDetails = browserDetails;
	}
	public String getUserAgent() {
		return UserAgent;
	}
	public void setUserAgent(String userAgent) {
		UserAgent = userAgent;
	}
	public String getCardNumber() {
		return CardNumber;
	}
	public void setCardNumber(String cardNumber) {
		CardNumber = cardNumber;
	}
	public String getExpiryDate() {
		return ExpiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		ExpiryDate = expiryDate;
	}
	public String getCVV() {
		return CVV;
	}
	public void setCVV(String cVV) {
		CVV = cVV;
	}
	public String getUDF01() {
		return UDF01;
	}
	public void setUDF01(String uDF01) {
		UDF01 = uDF01;
	}
	public String getUDF02() {
		return UDF02;
	}
	public void setUDF02(String uDF02) {
		UDF02 = uDF02;
	}
	public String getUDF03() {
		return UDF03;
	}
	public void setUDF03(String uDF03) {
		UDF03 = uDF03;
	}
	public String getUDF04() {
		return UDF04;
	}
	public void setUDF04(String uDF04) {
		UDF04 = uDF04;
	}
	public String getUDF05() {
		return UDF05;
	}
	public void setUDF05(String uDF05) {
		UDF05 = uDF05;
	}
	public String getUDF06() {
		return UDF06;
	}
	public void setUDF06(String uDF06) {
		UDF06 = uDF06;
	}
	public String getUDF07() {
		return UDF07;
	}
	public void setUDF07(String uDF07) {
		UDF07 = uDF07;
	}
	public String getUDF08() {
		return UDF08;
	}
	public void setUDF08(String uDF08) {
		UDF08 = uDF08;
	}
	public String getUDF09() {
		return UDF09;
	}
	public void setUDF09(String uDF09) {
		UDF09 = uDF09;
	}
	public String getUDF010() {
		return UDF010;
	}
	public void setUDF010(String uDF010) {
		UDF010 = uDF010;
	}
	public String getAcceptHeader() {
		return AcceptHeader;
	}
	public void setAcceptHeader(String acceptHeader) {
		AcceptHeader = acceptHeader;
	}
	public String getAuthenticationResponseURL() {
		return AuthenticationResponseURL;
	}
	public void setAuthenticationResponseURL(String authenticationResponseURL) {
		AuthenticationResponseURL = authenticationResponseURL;
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
	public String getEmiAmount() {
		return EmiAmount;
	}
	public void setEmiAmount(String emiAmount) {
		EmiAmount = emiAmount;
	}
	public String getCustomerId() {
		return CustomerId;
	}
	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}
	public String getSiType() {
		return SiType;
	}
	public void setSiType(String siType) {
		SiType = siType;
	}
	public String getSitxnType() {
		return SitxnType;
	}
	public void setSitxnType(String sitxnType) {
		SitxnType = sitxnType;
	}
	public String getSiStartDate() {
		return SiStartDate;
	}
	public void setSiStartDate(String siStartDate) {
		SiStartDate = siStartDate;
	}
	public String getSiEndDate() {
		return SiEndDate;
	}
	public void setSiEndDate(String siEndDate) {
		SiEndDate = siEndDate;
	}
	public String getPaymentFrequency() {
		return PaymentFrequency;
	}
	public void setPaymentFrequency(String paymentFrequency) {
		PaymentFrequency = paymentFrequency;
	}
	public String getSiAmount() {
		return SiAmount;
	}
	public void setSiAmount(String siAmount) {
		SiAmount = siAmount;
	}
	public String getSiMaxAmount() {
		return SiMaxAmount;
	}
	public void setSiMaxAmount(String siMaxAmount) {
		SiMaxAmount = siMaxAmount;
	}
	public String getSiMaxTxnCount() {
		return SiMaxTxnCount;
	}
	public void setSiMaxTxnCount(String siMaxTxnCount) {
		SiMaxTxnCount = siMaxTxnCount;
	}
	public String getSiPGRegID() {
		return SiPGRegID;
	}
	public void setSiPGRegID(String siPGRegID) {
		SiPGRegID = siPGRegID;
	}
	public String getSiAlertpreference() {
		return siAlertpreference;
	}
	public void setSiAlertpreference(String siAlertpreference) {
		this.siAlertpreference = siAlertpreference;
	}
	public String getChTokenizationConsent() {
		return chTokenizationConsent;
	}
	public void setChTokenizationConsent(String chTokenizationConsent) {
		this.chTokenizationConsent = chTokenizationConsent;
	}
	public String getChUserID() {
		return chUserID;
	}
	public void setChUserID(String chUserID) {
		this.chUserID = chUserID;
	}
	public String getCardTokenPan() {
		return CardTokenPan;
	}
	public void setCardTokenPan(String cardTokenPan) {
		CardTokenPan = cardTokenPan;
	}
	public String getCardTokenExpiry() {
		return CardTokenExpiry;
	}
	public void setCardTokenExpiry(String cardTokenExpiry) {
		CardTokenExpiry = cardTokenExpiry;
	}
	public String getCardTokenCrypto() {
		return CardTokenCrypto;
	}
	public void setCardTokenCrypto(String cardTokenCrypto) {
		CardTokenCrypto = cardTokenCrypto;
	}
	public String getMerchantTRID() {
		return MerchantTRID;
	}
	public void setMerchantTRID(String merchantTRID) {
		MerchantTRID = merchantTRID;
	}
	public String getCardTokenReferenceNo() {
		return CardTokenReferenceNo;
	}
	public void setCardTokenReferenceNo(String cardTokenReferenceNo) {
		CardTokenReferenceNo = cardTokenReferenceNo;
	}
	public String getBrowserTZ() {
		return browserTZ;
	}
	public void setBrowserTZ(String browserTZ) {
		this.browserTZ = browserTZ;
	}
	public String getBrowserScreenHeight() {
		return browserScreenHeight;
	}
	public void setBrowserScreenHeight(String browserScreenHeight) {
		this.browserScreenHeight = browserScreenHeight;
	}
	public String getBrowserScreenWidth() {
		return browserScreenWidth;
	}
	public void setBrowserScreenWidth(String browserScreenWidth) {
		this.browserScreenWidth = browserScreenWidth;
	}
	public String getBrowserJavaEnabled() {
		return browserJavaEnabled;
	}
	public void setBrowserJavaEnabled(String browserJavaEnabled) {
		this.browserJavaEnabled = browserJavaEnabled;
	}
	public String getBrowserColorDepth() {
		return browserColorDepth;
	}
	public void setBrowserColorDepth(String browserColorDepth) {
		this.browserColorDepth = browserColorDepth;
	}
	public String getBrowserJavascriptEnabled() {
		return browserJavascriptEnabled;
	}
	public void setBrowserJavascriptEnabled(String browserJavascriptEnabled) {
		this.browserJavascriptEnabled = browserJavascriptEnabled;
	}
	public String getPanSource() {
		return PanSource;
	}
	public void setPanSource(String panSource) {
		PanSource = panSource;
	}
	
	
	
	
	
	

}
